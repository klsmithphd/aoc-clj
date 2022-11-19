(ns aoc-clj.2015.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day04 :as t]))

; SUPER SLOW!
; (deftest first-with-five-zeros
;   (testing "Finds the earliest number to result in md5 hash starting with five zeros"
;     (is (= 609043  (t/first-to-start-with-five-zeros "abcdef")))
;     (is (= 1048970 (t/first-to-start-with-five-zeros "pqrstuv")))))

;; FIXME: 2015.day04 solution is too slow to unit test
;; https://github.com/Ken-2scientists/aoc-clj/issues/1
(deftest day04-part1-soln
  (testing "Reproduces the answer for day04, part1"
    (is (= 282749 (t/day04-part1-soln)))))

; ULTRA SLOW!
; (deftest day04-part2-soln
;   (testing "Reproduces the answer for day04, part2"
;     (is (= 9962624 (t/day04-part2-soln)))))