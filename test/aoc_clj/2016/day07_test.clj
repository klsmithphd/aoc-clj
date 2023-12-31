(ns aoc-clj.2016.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day07 :as t]))

(def day07-sample1
  (map t/parse-line
       ["abba[mnop]qrst"
        "abcd[bddb]xyyx"
        "aaaa[qwer]tyui"
        "ioxxoj[asdfgh]zxcvbn"]))

(def day07-sample2
  (map t/parse-line
       ["aba[bab]xyz"
        "xyx[xyx]xyx"
        "aaa[kek]eke"
        "zazbz[bzb]cdb"]))

(deftest abba?
  (testing "Correct logic for detecting ABBA patterns"
    (is (nil?  (t/abba? "abbc")))
    (is (nil?  (t/abba? "aaaa")))
    (is (some? (t/abba? "abba")))
    (is (some? (t/abba? "aaaaxyyx")))))

(deftest supports-tls?
  (testing "Matches sample data determination about supporting TLS"
    (is (= [true false false true]
           (map t/supports-tls? day07-sample1)))))

(deftest supports-ssl?
  (testing "Matches sample data determination about supporting SSL"
    (is (= [true false true true]
           (map t/supports-ssl? day07-sample2)))))

(def day07-input (u/parse-puzzle-input t/parse 2016 7))

(deftest day07-part1-soln
  (testing "Reproduces the answer for day07, part1"
    (is (= 110 (t/day07-part1-soln day07-input)))))

(deftest day07-part2-soln
  (testing "Reproduces the answer for day07, part2"
    (is (= 242 (t/day07-part2-soln day07-input)))))