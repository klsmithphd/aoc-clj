(ns aoc-clj.utils.grid-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mg]))

(def sample
  {[0 2] 7 [1 2] 8 [2 2] 9
   [0 1] 4 [1 1] 5 [2 1] 6
   [0 0] 1 [1 0] 2 [2 0] 3})

(def sample-grid (mg/->MapGrid2D 3 3 sample))

(deftest neighbor-data-test
  (testing "Retrieves the position, value, and bearing of all the neighboring values"
    (is (= [{:pos [1 2] :val 8 :heading :n}
            {:pos [2 1] :val 6 :heading :e}
            {:pos [1 0] :val 2 :heading :s}
            {:pos [0 1] :val 4 :heading :w}]
           (grid/neighbor-data sample-grid [1 1])))

    (is (= [{:pos [0 3] :val nil  :heading :n}
            {:pos [1 2] :val 8    :heading :e}
            {:pos [0 1] :val 4    :heading :s}
            {:pos [-1 2] :val nil :heading :w}]
           (grid/neighbor-data sample-grid [0 2])))

    (is (= [{:pos [1 2] :val 8 :heading :n}
            {:pos [2 1] :val 6 :heading :e}
            {:pos [1 0] :val 2 :heading :s}
            {:pos [0 1] :val 4 :heading :w}
            {:pos [2 2] :val 9 :heading :ne}
            {:pos [2 0] :val 3 :heading :se}
            {:pos [0 0] :val 1 :heading :sw}
            {:pos [0 2] :val 7 :heading :nw}]
           (grid/neighbor-data sample-grid [1 1] :diagonals true)))))

(deftest with-rel-bearings-test
  (testing "Augments the neighbor data with relative bearings"
    (is (= [{:pos [1 2] :val 8 :heading :n :bearing :left}
            {:pos [2 1] :val 6 :heading :e :bearing :forward}
            {:pos [1 0] :val 2 :heading :s :bearing :right}
            {:pos [0 1] :val 4 :heading :w :bearing :backward}]
           (->> (grid/neighbor-data sample-grid [1 1])
                (grid/with-rel-bearings :e))))

    (is (= [{:pos [1 2] :val 8 :heading :n  :bearing :backward-right}
            {:pos [2 1] :val 6 :heading :e  :bearing :backward-left}
            {:pos [1 0] :val 2 :heading :s  :bearing :forward-left}
            {:pos [0 1] :val 4 :heading :w  :bearing :forward-right}
            {:pos [2 2] :val 9 :heading :ne :bearing :backward}
            {:pos [2 0] :val 3 :heading :se :bearing :left}
            {:pos [0 0] :val 1 :heading :sw :bearing :forward}
            {:pos [0 2] :val 7 :heading :nw :bearing :right}]
           (->> (grid/neighbor-data sample-grid [1 1] :diagonals true)
                (grid/with-rel-bearings :sw))))))

(deftest neighbor-value-test
  (testing "Retrieves the correct value of a cardinal direction neighbor"
    (is (= 8 (grid/neighbor-value sample [1 1] :n)))
    (is (= 6 (grid/neighbor-value sample [1 1] :e)))
    (is (= 2 (grid/neighbor-value sample [1 1] :s)))
    (is (= 4 (grid/neighbor-value sample [1 1] :w)))))

(deftest neighbor-pos-test
  (testing "Retrieves the position of the neighbor in the given direction"
    (is (= [0 1] (grid/neighbor-pos [1 1] :w)))
    (is (= [2 1] (grid/neighbor-pos [2 0] :n)))
    (is (= [1 0] (grid/neighbor-pos [1 1] :s)))
    (is (= [2 2] (grid/neighbor-pos [1 2] :e)))))

(deftest rel-neighbors-test
  (testing "Returns a map of the relative-direction neighboring values"
    (is (= {:forward 8 :right 6 :backward 2 :left 4}
           (grid/rel-neighbors sample [1 1] :n)))
    (is (= {:forward 6 :right 2 :backward 4 :left 8}
           (grid/rel-neighbors sample [1 1] :e)))
    (is (= {:forward 2 :right 4 :backward 8 :left 6}
           (grid/rel-neighbors sample [1 1] :s)))
    (is (= {:forward 4 :right 8 :backward 6 :left 2}
           (grid/rel-neighbors sample [1 1] :w)))))

(deftest adj-coords-2d-test
  (testing "Returns the directly adjacent coordinates to the given pos"
    (is (= [[0 1] [1 0] [0 -1] [-1 0]] (grid/adj-coords-2d [0 0])))
    (is (= [[-1 -1] [0 -1] [1 -1]
            [-1  0]        [1  0]
            [-1  1] [0  1] [1  1]]
           (grid/adj-coords-2d [0 0] :include-diagonals true)))))

(deftest adj-coords-3d-test
  (testing "Returns the directly adjacent coordinates to the given pos"
    (is (= [[0 0 1]
            [0 0 -1]
            [0 1 0]
            [0 -1 0]
            [1 0 0]
            [-1 0 0]]
           (grid/adj-coords-3d [0 0 0])))))


(deftest neighbors-test
  (testing "Returns the position and values of points adjacent to pos"
    (is (= {[0 1] 4 [1 2] 8 [2 1] 6 [1 0] 2}
           (grid/neighbors-2d sample [1 1])))
    (is (= {[0 2] 7 [1 2] 8 [2 2] 9
            [0 1] 4         [2 1] 6
            [0 0] 1 [1 0] 2 [2 0] 3}
           (grid/neighbors-2d sample [1 1] :include-diagonals true)))))

(def sparse-sample {[-1 -1] 1 [3 0] 2 [1 2] 3})
(deftest mapgrid->vectors-test
  (testing "Converts a sparse mapgrid to a vector of vectors"
    (is (= [[0 0 3 0 0]
            [0 0 0 0 0]
            [0 0 0 0 2]
            [1 0 0 0 0]]
           (grid/mapgrid->vectors sparse-sample)))))


(deftest interpolated-test
  (testing "Computes the collection of points between two co-linear points"
    (is (= [[1 4] [2 4] [3 4] [4 4]] (grid/interpolated [[1 4] [4 4]])))
    (is (= [[3 2] [3 1] [3 0] [3 -1]] (grid/interpolated [[3 2] [3 -1]])))
    (is (= [[1 1]] (grid/interpolated [[1 1] [1 1]])))))