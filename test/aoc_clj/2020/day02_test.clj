(ns aoc-clj.2020.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day02 :as t]))

(def d02-s00 ["1-3 a: abcde"
              "1-3 b: cdefg"
              "2-9 c: ccccccccc"])

(deftest parser
  (testing "Testing the parser works correctly on the input"
    (is (= {:min 1 :max 3 :char \a :pass "abcde"}
           (t/parse-line (first d02-s00))))))

(deftest part1-validator
  (testing "Correctly validates per the rules in part1"
    (is (= true  (t/part1-valid? (t/parse-line (first d02-s00)))))
    (is (= false (t/part1-valid? (t/parse-line (second d02-s00)))))
    (is (= true  (t/part1-valid? (t/parse-line (last d02-s00)))))))

(deftest part2-validator
  (testing "Correctly validates per the rules in part2"
    (is (= true  (t/part2-valid? (t/parse-line (first d02-s00)))))
    (is (= false (t/part2-valid? (t/parse-line (second d02-s00)))))
    (is (= false (t/part2-valid? (t/parse-line (last d02-s00)))))))

(def day02-input (u/parse-puzzle-input t/parse 2020 2))

(deftest day02-part1-soln
  (testing "Reproduces the answer for day02, part1"
    (is (= 418 (t/day02-part1-soln day02-input)))))

(deftest day02-part2-soln
  (testing "Reproduces the answer for day02, part2"
    (is (= 616 (t/day02-part2-soln day02-input)))))