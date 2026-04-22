(ns aoc-clj.grid.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.grid.interface :as grid]
            [aoc-clj.grid.interface :as mg :refer [->MapGrid2D]]))

;; A 3×3 grid in [row col] coordinates:
;;   row 0 (top):    7 8 9
;;   row 1 (middle): 4 5 6
;;   row 2 (bottom): 1 2 3
(def sample
  {[0 0] 7 [0 1] 8 [0 2] 9
   [1 0] 4 [1 1] 5 [1 2] 6
   [2 0] 1 [2 1] 2 [2 2] 3})

(def sample-grid (->MapGrid2D 3 3 sample))

(deftest find-nodes-test
  (testing "Retrieves the positions of the nodes with the provided value"
    (is (= [[1 1]] (grid/find-nodes 5 sample-grid)))))

(deftest Grid2D->ascii-test
  (testing "Converts a Grid2D to an ASCII-art string, row 0 at the top"
    (is (= "..#\n.#."
           (grid/Grid2D->ascii
            {\. :space \# :wall}
            (->MapGrid2D 3 2
                         {[0 0] :space [0 1] :space [0 2] :wall
                          [1 0] :space [1 1] :wall  [1 2] :space}))))))

(deftest within-grid?-test
  (testing "Returns true only when [row col] is inside the grid bounds"
    (is (true?  (grid/within-grid? sample-grid [0 0])))
    (is (true?  (grid/within-grid? sample-grid [2 2])))
    (is (false? (grid/within-grid? sample-grid [-1 0])))
    (is (false? (grid/within-grid? sample-grid [3 0])))
    (is (false? (grid/within-grid? sample-grid [0 -1])))
    (is (false? (grid/within-grid? sample-grid [0 3])))))

(deftest neighbor-data-test
  (testing "Retrieves the position, value, and bearing of all the neighboring values"
    (is (= [{:pos [0 1] :val 8 :heading :n}
            {:pos [1 2] :val 6 :heading :e}
            {:pos [2 1] :val 2 :heading :s}
            {:pos [1 0] :val 4 :heading :w}]
           (grid/neighbor-data sample-grid [1 1])))

    (is (= [{:pos [-1 2] :val nil :heading :n}
            {:pos [0 3]  :val nil :heading :e}
            {:pos [1 2]  :val 6   :heading :s}
            {:pos [0 1]  :val 8   :heading :w}]
           (grid/neighbor-data sample-grid [0 2])))

    (is (= [{:pos [0 1] :val 8 :heading :n}
            {:pos [1 2] :val 6 :heading :e}
            {:pos [2 1] :val 2 :heading :s}
            {:pos [1 0] :val 4 :heading :w}
            {:pos [0 2] :val 9 :heading :ne}
            {:pos [2 2] :val 3 :heading :se}
            {:pos [2 0] :val 1 :heading :sw}
            {:pos [0 0] :val 7 :heading :nw}]
           (grid/neighbor-data sample-grid [1 1] :diagonals true)))))

(deftest with-rel-bearings-test
  (testing "Augments the neighbor data with relative bearings"
    (is (= [{:pos [0 1] :val 8 :heading :n :bearing :left}
            {:pos [1 2] :val 6 :heading :e :bearing :forward}
            {:pos [2 1] :val 2 :heading :s :bearing :right}
            {:pos [1 0] :val 4 :heading :w :bearing :backward}]
           (->> (grid/neighbor-data sample-grid [1 1])
                (grid/with-rel-bearings :e))))

    (is (= [{:pos [0 1] :val 8 :heading :n  :bearing :backward-right}
            {:pos [1 2] :val 6 :heading :e  :bearing :backward-left}
            {:pos [2 1] :val 2 :heading :s  :bearing :forward-left}
            {:pos [1 0] :val 4 :heading :w  :bearing :forward-right}
            {:pos [0 2] :val 9 :heading :ne :bearing :backward}
            {:pos [2 2] :val 3 :heading :se :bearing :left}
            {:pos [2 0] :val 1 :heading :sw :bearing :forward}
            {:pos [0 0] :val 7 :heading :nw :bearing :right}]
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
    (is (= [1 0] (grid/neighbor-pos [1 1] :w)))
    (is (= [1 0] (grid/neighbor-pos [2 0] :n)))
    (is (= [2 1] (grid/neighbor-pos [1 1] :s)))
    (is (= [1 3] (grid/neighbor-pos [1 2] :e)))))

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
  (testing "Returns the directly adjacent coordinates in N E S W order"
    (is (= [[-1 0] [0 1] [1 0] [0 -1]] (grid/adj-coords-2d [0 0])))
    (is (= [[-1 -1] [-1 0] [-1 1]
            [0 -1]         [0  1]
            [1 -1]  [1 0]  [1  1]]
           (grid/adj-coords-2d [0 0] :include-diagonals true)))))

(deftest adj-coords-3d-test
  (testing "Returns the six face-adjacent coordinates in 3D"
    (is (= [[0 0 1]
            [0 0 -1]
            [0 1 0]
            [0 -1 0]
            [1 0 0]
            [-1 0 0]]
           (grid/adj-coords-3d [0 0 0])))))

(deftest neighbors-2d-test
  (testing "Returns a map of positions to values for the nearest neighbors"
    (is (= {[0 1] 8 [1 2] 6 [2 1] 2 [1 0] 4}
           (grid/neighbors-2d sample-grid [1 1])))
    (is (= {[0 0] 7 [0 1] 8 [0 2] 9
            [1 0] 4         [1 2] 6
            [2 0] 1 [2 1] 2 [2 2] 3}
           (grid/neighbors-2d sample-grid [1 1] :include-diagonals true)))))

(def sparse-sample {[-1 -1] 1 [0 3] 2 [2 1] 3})

(deftest mapgrid->vectors-test
  (testing "Converts a sparse [row col] mapgrid to a vector of vectors"
    (is (= [[1 0 0 0 0]
            [0 0 0 0 2]
            [0 0 0 0 0]
            [0 0 3 0 0]]
           (grid/mapgrid->vectors sparse-sample)))))

(deftest interpolated-test
  (testing "Computes the collection of [row col] points between two co-linear points"
    (is (= [[4 1] [4 2] [4 3] [4 4]] (grid/interpolated [[4 1] [4 4]])))
    (is (= [[2 3] [1 3] [0 3] [-1 3]] (grid/interpolated [[2 3] [-1 3]])))
    (is (= [[1 1]] (grid/interpolated [[1 1] [1 1]])))))
