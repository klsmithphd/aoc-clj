(ns aoc-clj.utils.grid-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.grid :as grid]))

(deftest adj-coords-2d-test
  (testing "Returns the directly adjacent (non-diagonal) coordinates to the given pos"
    (is (= [[0 -1] [-1 0] [0 1] [1 0]] (grid/adj-coords-2d [0 0])))
    (is (= [[-1 -1] [0 -1] [1 -1]
            [-1  0]        [1  0]
            [-1  1] [0  1] [1  1]]
           (grid/adj-coords-2d [0 0] :include-diagonals true)))))

(def sparse-sample {[-1 -1] 1 [3 0] 2 [1 2] 3})
(deftest mapgrid->vectors-test
  (testing "Converts a sparse mapgrid to a vector of vectors"
    (is (= [[0 0 3 0 0]
            [0 0 0 0 0]
            [0 0 0 0 2]
            [1 0 0 0 0]]
           (grid/mapgrid->vectors sparse-sample)))))