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

(deftest euclidean-test
  (testing "Demonstration of euclidean - computes the Euclidean distance
            (L2 norm) between two vectors"
    (is (= 5.0  (math/euclidean [0 0] [3 4])))
    (is (= 13.0 (math/euclidean [-3 4] [2 16])))
    ;; Can be arbitrary n-dimensional points
    (is (= 5.0  (math/euclidean [1 1 1] [1 4 5])))
    (is (= 2.0  (math/euclidean [1] [3])))))

(deftest l1-norm-test
  (testing "Demonstration of l1-norm - computes the L1 norm of a vector"
    (is (= 9 (math/l1-norm [4 5])))
    (is (= 6 (math/l1-norm [-2 -4])))
    ;; Can be arbitrary n-dimensional points
    (is (= 8 (math/l1-norm [1 4 3])))))

(deftest l2-norm-test
  (testing "Demonstration of l2-norm - computes the L2 norm of a vector"
    (is (= 5.0 (math/l2-norm [3 4])))
    (is (= 1.0 (math/l2-norm [1])))
    (is (= 13.0 (math/l2-norm [0 5 12])))))
