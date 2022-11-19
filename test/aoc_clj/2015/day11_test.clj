(ns aoc-clj.2015.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day11 :as t]))

(def day11-sample1 (t/str->nums "hijklmmn"))
(def day11-sample2 (t/str->nums "abbceffg"))
(def day11-sample3 (t/str->nums "abbcegjk"))

(def day11-sample4      "abcdefgh")
(def day11-sample4-next "abcdffaa")

(def day11-sample5      "ghijklmn")
(def day11-sample5-next "ghjaabcc")

(deftest rules-test
  (testing "Correctly applies valid password rules"
    (is (t/increasing-straight?     day11-sample1))
    (is (not (t/no-disallowed?      day11-sample1)))
    (is (not (t/two-distinct-pairs? day11-sample1)))
    (is (not (t/valid-password?     day11-sample1)))

    (is (not (t/increasing-straight? day11-sample2)))
    (is (t/no-disallowed?            day11-sample2))
    (is (t/two-distinct-pairs?       day11-sample2))
    (is (not (t/valid-password?      day11-sample2)))

    (is (not (t/increasing-straight? day11-sample3)))
    (is (t/no-disallowed?            day11-sample3))
    (is (not (t/two-distinct-pairs?  day11-sample3)))
    (is (not (t/valid-password?      day11-sample3)))

    (is (t/valid-password? (t/str->nums day11-sample4-next)))
    (is (t/valid-password? (t/str->nums day11-sample5-next)))))

(deftest increment-test
  (testing "Verifying that incrementing works correctly"
    (is [0 0 0 0 0 0 0 1]  (t/increment [0 0 0 0 0 0 0 0]))
    (is [0 0 0 0 0 0 0 25] (t/increment [0 0 0 0 0 0 0 24]))
    (is [0 0 0 0 0 0 1 0]  (t/increment [0 0 0 0 0 0 0 25]))
    (is [1 0 0 0 0 0 0 0]  (t/increment [0 25 25 25 25 25 25 25]))))

;; FIXME: 2015.day11 solution is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/3
;; (deftest next-valid-password-test
;;   (testing "Finds the next valid password"
;;     (is (= day11-sample4-next (t/next-valid-password day11-sample4)))
;;     (is (= day11-sample5-next (t/next-valid-password day11-sample5)))))

;; (deftest day11-part1-soln
;;   (testing "Reproduces the answer for day11, part1"
;;     (is (= "hxbxxyzz" (t/day11-part1-soln)))))

;; (deftest day11-part2-soln
;;   (testing "Reproduces the answer for day11, part2"
;;     (is (= "hxcaabcc" (t/day11-part2-soln)))))