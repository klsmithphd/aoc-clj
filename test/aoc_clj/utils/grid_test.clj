(ns aoc-clj.utils.grid-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.grid :as grid]))

(def sample
  {[0 2] 7 [1 2] 8 [2 2] 9
   [0 1] 4 [1 1] 5 [2 1] 6
   [0 0] 1 [1 0] 2 [2 0] 3})

(deftest neighbor-value-test
  (testing "Retrieves the correct value of a cardinal direction neighbor"
    (is (= 8 (grid/neighbor-value sample [1 1] :n)))
    (is (= 6 (grid/neighbor-value sample [1 1] :e)))
    (is (= 2 (grid/neighbor-value sample [1 1] :s)))
    (is (= 4 (grid/neighbor-value sample [1 1] :w)))))

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