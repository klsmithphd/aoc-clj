(ns aoc-clj.2024.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2024.day03 :as d03]))

(def d03-s00
  "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

(deftest real-mul-insts-test
  (testing "Finds the real mul instructions in a string"
    (is (= ["mul(2,4)"
            "mul(5,5)"
            "mul(11,8)"
            "mul(8,5)"]
           (d03/real-mul-insts d03-s00)))))