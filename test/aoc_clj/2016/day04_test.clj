(ns aoc-clj.2016.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day04 :as d04]))

(def d04-s00-raw
  ["aaaaa-bbb-z-y-x-123[abxyz]"
   "a-b-c-d-e-f-g-h-987[abcde]"
   "not-a-real-room-404[oarel]"
   "totally-real-room-200[decoy]"])

(def d04-s00
  [{:encrypted-name ["aaaaa" "bbb" "z" "y" "x"]
    :sector-id 123
    :checksum "abxyz"}
   {:encrypted-name ["a" "b" "c" "d" "e" "f" "g" "h"]
    :sector-id 987
    :checksum "abcde"}
   {:encrypted-name ["not" "a" "real" "room"]
    :sector-id 404
    :checksum "oarel"}
   {:encrypted-name ["totally" "real" "room"]
    :sector-id 200
    :checksum "decoy"}])

(def d04-s01
  {:encrypted-name ["qzmt" "zixmtkozy" "ivhz"]
   :sector-id 343})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d04-s00 (d04/parse d04-s00-raw)))))

(deftest real-room?-test
  (testing "Identifies which rooms are real (not decoys)"
    (is (= [true true true false]
           (map d04/real-room? d04-s00)))))

(deftest real-room-sector-id-sum-test
  (testing "Adds up the sector ids of all the real rooms"
    (is (= 1514 (d04/real-room-sector-id-sum d04-s00)))))

(deftest decipher-test
  (testing "Can decipher the encrypted name"
    (is (= "very encrypted name" (d04/decipher d04-s01)))))

(def day04-input (u/parse-puzzle-input d04/parse 2016 4))

(deftest part1-test
  (testing "Reproduces the answer for day04, part1"
    (is (= 361724 (d04/part1 day04-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day04, part2"
    (is (= 482 (d04/part2 day04-input)))))
