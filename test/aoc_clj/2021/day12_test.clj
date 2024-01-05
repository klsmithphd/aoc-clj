(ns aoc-clj.2021.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day12 :as t]))

(def d12-s00
  (t/parse
   ["start-A"
    "start-b"
    "A-c"
    "A-b"
    "b-d"
    "A-end"
    "b-end"]))

(def d12-s01
  (t/parse
   ["dc-end"
    "HN-start"
    "start-kj"
    "dc-start"
    "dc-HN"
    "LN-dc"
    "HN-end"
    "kj-sa"
    "kj-HN"
    "kj-dc"]))

(def d12-s02
  (t/parse
   ["fs-end"
    "he-DX"
    "fs-he"
    "start-DX"
    "pj-DX"
    "end-zg"
    "zg-sl"
    "zg-pj"
    "pj-he"
    "RW-he"
    "fs-DX"
    "pj-RW"
    "zg-RW"
    "start-pj"
    "he-WI"
    "zg-he"
    "pj-fs"
    "start-RW"]))

(deftest count-of-map-paths
  (testing "Correctly counts the number of unique paths in sample data"
    (is (= 10  (count (t/map-cave d12-s00))))
    (is (= 19  (count (t/map-cave d12-s01))))
    (is (= 226 (count (t/map-cave d12-s02))))))

(deftest count-of-map-paths-part2
  (testing "Correctly counts the number of unique paths in sample data with part2 logic"
    (is (= 36   (count (t/map-cave d12-s00 t/allowed-part2?))))
    (is (= 103  (count (t/map-cave d12-s01 t/allowed-part2?))))
    (is (= 3509 (count (t/map-cave d12-s02 t/allowed-part2?))))))

(def day12-input (u/parse-puzzle-input t/parse 2021 12))

(deftest part1-test
  (testing "Reproduces the answer for day12, part1"
    (is (= 5874 (t/part1 day12-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day12, part2"
    (is (= 153592 (t/part2 day12-input)))))
