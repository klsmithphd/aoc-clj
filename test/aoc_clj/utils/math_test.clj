(ns aoc-clj.utils.math-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.math :as math]))

(deftest manhattan-test
  (testing "Demonstration of manhattan - computes the Manhattan distance 
            (L1 norm) between two points"
    (is (= 9 (math/manhattan [0 0] [4 5])))
    (is (= 4 (math/manhattan [-1 -1] [-2 -4])))
    ;; Can be arbitrary n-dimensional points
    (is (= 8 (math/manhattan [0 0 0] [1 4 3])))
    (is (= 7 (math/manhattan [3] [-4])))))
