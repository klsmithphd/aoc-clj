(ns aoc-clj.2015.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day11 :as t]))

(def d11-s00 (t/str->nums "hijklmmn"))
(def d11-s01 (t/str->nums "abbceffg"))
(def d11-s02 (t/str->nums "abbcegjk"))

(def d11-s03      "abcdefgh")
(def d11-s03-next "abcdffaa")

(def d11-s04      "ghijklmn")
(def d11-s04-next "ghjaabcc")

(deftest rules-test
  (testing "Correctly applies valid password rules"
    (is (t/increasing-straight?     d11-s00))
    (is (not (t/no-disallowed?      d11-s00)))
    (is (not (t/two-distinct-pairs? d11-s00)))
    (is (not (t/valid-password?     d11-s00)))

    (is (not (t/increasing-straight? d11-s01)))
    (is (t/no-disallowed?            d11-s01))
    (is (t/two-distinct-pairs?       d11-s01))
    (is (not (t/valid-password?      d11-s01)))

    (is (not (t/increasing-straight? d11-s02)))
    (is (t/no-disallowed?            d11-s02))
    (is (not (t/two-distinct-pairs?  d11-s02)))
    (is (not (t/valid-password?      d11-s02)))

    (is (t/valid-password? (t/str->nums d11-s03-next)))
    (is (t/valid-password? (t/str->nums d11-s04-next)))))

(deftest increment-test
  (testing "Verifying that incrementing works correctly"
    (is [0 0 0 0 0 0 0 1]  (t/increment [0 0 0 0 0 0 0 0]))
    (is [0 0 0 0 0 0 0 25] (t/increment [0 0 0 0 0 0 0 24]))
    (is [0 0 0 0 0 0 1 0]  (t/increment [0 0 0 0 0 0 0 25]))
    (is [1 0 0 0 0 0 0 0]  (t/increment [0 25 25 25 25 25 25 25]))))

;; FIXME: 2015.day11 solution is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/3
(deftest ^:slow next-valid-password-test
  (testing "Finds the next valid password"
    (is (= d11-s03-next (t/next-valid-password d11-s03)))
    (is (= d11-s04-next (t/next-valid-password d11-s04)))))

(def day11-input (u/parse-puzzle-input t/parse 2015 11))

(deftest ^:slow day11-part1-soln
  (testing "Reproduces the answer for day11, part1"
    (is (= "hxbxxyzz" (t/day11-part1-soln day11-input)))))

(deftest ^:slow day11-part2-soln
  (testing "Reproduces the answer for day11, part2"
    (is (= "hxcaabcc" (t/day11-part2-soln day11-input)))))