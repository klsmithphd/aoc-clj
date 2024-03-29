(ns aoc-clj.2015.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day11 :as d11]))

(def d11-s00 (d11/str->nums "hijklmmn"))
(def d11-s01 (d11/str->nums "abbceffg"))
(def d11-s02 (d11/str->nums "abbcegjk"))

(def d11-s03      "abcdefgh")
(def d11-s03-next "abcdffaa")

(def d11-s04      "ghijklmn")
(def d11-s04-next "ghjaabcc")

(deftest rules-test
  (testing "Correctly applies valid password rules"
    (is (d11/increasing-straight?     d11-s00))
    (is (not (d11/no-disallowed?      d11-s00)))
    (is (not (d11/two-distinct-pairs? d11-s00)))
    (is (not (d11/valid-password?     d11-s00)))

    (is (not (d11/increasing-straight? d11-s01)))
    (is (d11/no-disallowed?            d11-s01))
    (is (d11/two-distinct-pairs?       d11-s01))
    (is (not (d11/valid-password?      d11-s01)))

    (is (not (d11/increasing-straight? d11-s02)))
    (is (d11/no-disallowed?            d11-s02))
    (is (not (d11/two-distinct-pairs?  d11-s02)))
    (is (not (d11/valid-password?      d11-s02)))

    (is (d11/valid-password? (d11/str->nums d11-s03-next)))
    (is (d11/valid-password? (d11/str->nums d11-s04-next)))))

(deftest increment-test
  (testing "Verifying that incrementing works correctly"
    (is [0 0 0 0 0 0 0 1]  (d11/increment [0 0 0 0 0 0 0 0]))
    (is [0 0 0 0 0 0 0 25] (d11/increment [0 0 0 0 0 0 0 24]))
    (is [0 0 0 0 0 0 1 0]  (d11/increment [0 0 0 0 0 0 0 25]))
    (is [1 0 0 0 0 0 0 0]  (d11/increment [0 25 25 25 25 25 25 25]))))

(deftest next-valid-password-test
  (testing "Finds the next valid password"
    (is (= d11-s03-next (d11/next-valid-password d11-s03)))
    (is (= d11-s04-next (d11/next-valid-password d11-s04)))))

(deftest next-without-disallowed-chars-test
  (testing "Can increment the password the next potentially allowed
            number if it contains disallowed chars"
    (is (= "hjaaaaaa" (d11/nums-fn d11/next-wo-disallowed-chars "hijklmno")))
    (is (= "jaaaaaaa" (d11/nums-fn d11/next-wo-disallowed-chars "ijklmnop")))
    (is (= "abcdefgh" (d11/nums-fn d11/next-wo-disallowed-chars "abcdefgh")))))

(deftest next-aabcc-test
  (testing "Can skip ahead to the next valid aabcc-style pattern"
    (is (= "hacaabcc" (d11/nums-fn d11/next-aabcc "habxxzzz")))
    (is (= "habxxyzz" (d11/nums-fn d11/next-aabcc "habxxyzz")))
    (is (= "habbbcdd" (d11/nums-fn d11/next-aabcc "habaabcd")))
    (is (= "ghjaabcc" (d11/nums-fn d11/next-aabcc "ghjaaaaa")))))

(def day11-input (u/parse-puzzle-input d11/parse 2015 11))

(deftest part1-test
  (testing "Reproduces the answer for day11, part1"
    (is (= "hxbxxyzz" (d11/part1 day11-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day11, part2"
    (is (= "hxcaabcc" (d11/part2 day11-input)))))