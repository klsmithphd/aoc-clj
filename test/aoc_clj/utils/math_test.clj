(ns aoc-clj.utils.math-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.math :as math]))

(deftest gcd-test
  (testing "Computes the Greatest Common Divisor (GCD)"
    (is (= 1 (math/gcd 17 19)))
    (is (= 4 (math/gcd 8 12)))
    (is (= 5 (math/gcd 5 0)))))

(deftest lcm-test
  (testing "Computes the Least Common Multiple (LCM)"
    (is (= 12 (math/lcm 4 6)))
    (is (= 36 (math/lcm 4 6 9)))))

(deftest mod-pow-test
  (testing "Computes powers of an integer modulo m"
    (is (= 1 (math/mod-pow 15 2 0)))
    (is (= 8 (math/mod-pow 10 2 3)))
    (is (= 2 (math/mod-pow 3 2 5)))))
