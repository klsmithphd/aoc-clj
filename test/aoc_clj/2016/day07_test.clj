(ns aoc-clj.2016.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day07 :as d07]))

(def d07-s00-raw
  ["abba[mnop]qrst"
   "abcd[bddb]xyyx"
   "aaaa[qwer]tyui"
   "ioxxoj[asdfgh]zxcvbn"])

(def d07-s01-raw
  ["aba[bab]xyz"
   "xyx[xyx]xyx"
   "aaa[kek]eke"
   "zazbz[bzb]cdb"])

(def d07-s02-raw
  ["gdlrknrmexvaypu[crqappbbcaplkkzb]vhvkjyadjsryysvj[nbvypeadikilcwg]jwxlimrgakadpxu[dgoanojvdvwfabtt]yqsalmulblolkgsheo"])

(d07/parse d07-s02-raw)

(def d07-s00
  [{:supernets ["abba" "qrst"] :hypernets ["mnop"]}
   {:supernets ["abcd" "xyyx"] :hypernets ["bddb"]}
   {:supernets ["aaaa" "tyui"] :hypernets ["qwer"]}
   {:supernets ["ioxxoj" "zxcvbn"] :hypernets ["asdfgh"]}])

(def d07-s01
  [{:supernets ["aba" "xyz"] :hypernets ["bab"]}
   {:supernets ["xyx" "xyx"] :hypernets ["xyx"]}
   {:supernets ["aaa" "eke"] :hypernets ["kek"]}
   {:supernets ["zazbz" "cdb"] :hypernets ["bzb"]}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d07-s00 (d07/parse d07-s00-raw)))
    (is (= d07-s01 (d07/parse d07-s01-raw)))))

(deftest abba?
  (testing "Correct logic for detecting ABBA patterns"
    (is (nil?  (d07/abba? "abbc")))
    (is (nil?  (d07/abba? "aaaa")))
    (is (some? (d07/abba? "abba")))
    (is (some? (d07/abba? "aaaaxyyx")))))

(deftest supports-tls?
  (testing "Matches sample data determination about supporting TLS"
    (is (= [true false false true]
           (map d07/supports-tls? d07-s00)))))

(deftest supports-ssl?
  (testing "Matches sample data determination about supporting SSL"
    (is (= [true false true true]
           (map d07/supports-ssl? d07-s01)))))

(def day07-input (u/parse-puzzle-input d07/parse 2016 7))

(deftest part1-test
  (testing "Reproduces the answer for day07, part1"
    (is (= 110 (d07/part1 day07-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day07, part2"
    (is (= 242 (d07/part2 day07-input)))))