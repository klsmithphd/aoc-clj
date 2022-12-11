(ns aoc-clj.utils.math-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.math :as math]))

(deftest manhattan-test
  (testing "Demonstration of manhattan - computes the Manhattan distance 
            (L1 norm) between two vectors"
    (is (= 9 (math/manhattan [0 0] [4 5])))
    (is (= 4 (math/manhattan [-1 -1] [-2 -4])))
    ;; Can be arbitrary n-dimensional points
    (is (= 8 (math/manhattan [0 0 0] [1 4 3])))
    (is (= 7 (math/manhattan [3] [-4])))))

(deftest l1-norm-test
  (testing "Demonstration of l1-norm - computes the L1 norm of a vector"
    (is (= 9 (math/l1-norm [4 5])))
    (is (= 6 (math/l1-norm [-2 -4])))
    ;; Can be arbitrary n-dimensional points
    (is (= 8 (math/l1-norm [1 4 3])))))

