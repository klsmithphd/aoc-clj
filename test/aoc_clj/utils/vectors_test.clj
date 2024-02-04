(ns aoc-clj.utils.vectors-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.vectors :as v]))

(deftest scalar-mult-test
  (testing "Multiplication of a vector by a scalar"
    (is (= [5 10 15] (v/scalar-mult [1 2 3] 5)))
    (is (= [0 0 0 0] (v/scalar-mult [1 2 3 4] 0)))
    (is (= [1.0 2.0 3.0] (v/scalar-mult [2 4 6] 0.5)))))

(deftest vec-sum-test
  (testing "Sum of a collection of vectors"
    (is (= [1 2 3]    (v/vec-sum [[1 2 3]])))
    (is (= [1 2 3]    (v/vec-sum [[0 0 0] [1 2 3]])))
    (is (= [12 15 18] (v/vec-sum [[1 2 3] [4 5 6] [7 8 9]])))))

(deftest manhattan-test
  (testing "Demonstration of manhattan - computes the Manhattan distance 
            (L1 norm) between two vectors"
    (is (= 9 (v/manhattan [0 0] [4 5])))
    (is (= 4 (v/manhattan [-1 -1] [-2 -4])))
    ;; Can be arbitrary n-dimensional points
    (is (= 8 (v/manhattan [0 0 0] [1 4 3])))
    (is (= 7 (v/manhattan [3] [-4])))))

(deftest euclidean-test
  (testing "Demonstration of euclidean - computes the Euclidean distance
            (L2 norm) between two vectors"
    (is (= 5.0  (v/euclidean [0 0] [3 4])))
    (is (= 13.0 (v/euclidean [-3 4] [2 16])))
    ;; Can be arbitrary n-dimensional points
    (is (= 5.0  (v/euclidean [1 1 1] [1 4 5])))
    (is (= 2.0  (v/euclidean [1] [3])))))

(deftest l1-norm-test
  (testing "Demonstration of l1-norm - computes the L1 norm of a vector"
    (is (= 9 (v/l1-norm [4 5])))
    (is (= 6 (v/l1-norm [-2 -4])))
    ;; Can be arbitrary n-dimensional points
    (is (= 8 (v/l1-norm [1 4 3])))))

(deftest l2-norm-test
  (testing "Demonstration of l2-norm - computes the L2 norm of a vector"
    (is (= 5.0 (v/l2-norm [3 4])))
    (is (= 1.0 (v/l2-norm [1])))
    (is (= 13.0 (v/l2-norm [0 5 12])))))