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

(deftest next-valid-password-test
  (testing "Finds the next valid password"
    (is (= d11-s03-next (t/next-valid-password d11-s03)))
    (is (= d11-s04-next (t/next-valid-password d11-s04)))))

(deftest next-without-disallowed-chars-test
  (testing "Can increment the password the next potentially allowed
            number if it contains disallowed chars"
    (is (= "hjaaaaaa" (-> "hijklmn"
                          t/str->nums
                          t/next-without-disallowed-chars
                          t/nums->str)))
    (is (= "jaaaaaaa" (-> "ijklmnopq"
                          t/str->nums
                          t/next-without-disallowed-chars
                          t/nums->str)))
    (is (= "abcdefgh" (-> "abcdefgh"
                          t/str->nums
                          t/next-without-disallowed-chars
                          t/nums->str)))))

(def day11-input (u/parse-puzzle-input t/parse 2015 11))

(deftest part1-test
  (testing "Reproduces the answer for day11, part1"
    (is (= "hxbxxyzz" (t/part1 day11-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day11, part2"
    (is (= "hxcaabcc" (t/part2 day11-input)))))