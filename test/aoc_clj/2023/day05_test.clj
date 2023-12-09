(ns aoc-clj.2023.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day05 :as t]))

(def d05_s01_raw ["seeds: 79 14 55 13"
                  ""
                  "seed-to-soil map:"
                  "50 98 2"
                  "52 50 48"
                  ""
                  "soil-to-fertilizer map:"
                  "0 15 37"
                  "37 52 2"
                  "39 0 15"
                  ""
                  "fertilizer-to-water map:"
                  "49 53 8"
                  "0 11 42"
                  "42 0 7"
                  "57 7 4"
                  ""
                  "water-to-light map:"
                  "88 18 7"
                  "18 25 70"
                  ""
                  "light-to-temperature map:"
                  "45 77 23"
                  "81 45 19"
                  "68 64 13"
                  ""
                  "temperature-to-humidity map:"
                  "0 69 1"
                  "1 0 69"
                  ""
                  "humidity-to-location map:"
                  "60 56 37"
                  "56 93 4"])

(def d05_s01
  {:seeds [79 14 55 13]
   :maps  [[[50 98 2] [52 50 48]]
           [[0 15 37] [37 52 2] [39 0 15]]
           [[49 53 8] [0 11 42] [42 0 7] [57 7 4]]
           [[88 18 7] [18 25 70]]
           [[45 77 23] [81 45 19] [68 64 13]]
           [[0 69 1] [1 0 69]]
           [[60 56 37] [56 93 4]]]})

(deftest parse-test
  (testing "Parses the input correctly"
    (is (= d05_s01 (t/parse d05_s01_raw)))))

(deftest apply-map-test
  (testing "Correctly maps a source number to a target number given a map"
    (is (= 0 (t/apply-map 0 (first (:maps d05_s01)))))
    (is (= 49 (t/apply-map 49 (first (:maps d05_s01)))))
    (is (= 52 (t/apply-map 50 (first (:maps d05_s01)))))
    (is (= 99 (t/apply-map 97 (first (:maps d05_s01)))))
    (is (= 50 (t/apply-map 98 (first (:maps d05_s01)))))
    (is (= 51 (t/apply-map 99 (first (:maps d05_s01)))))))

(deftest mappings-test
  (testing "Maps a seed number through each of the mappings"
    (is (= [79 81 81 81 74 78 78 82] (t/mappings 79 (:maps d05_s01))))
    (is (= [14 14 53 49 42 42 43 43] (t/mappings 14 (:maps d05_s01))))
    (is (= [55 57 57 53 46 82 82 86] (t/mappings 55 (:maps d05_s01))))
    (is (= [13 13 52 41 34 34 35 35] (t/mappings 13 (:maps d05_s01))))))

(deftest location-test
  (testing "Maps a seed to its location"
    (is (= 82 (t/location 79 (:maps d05_s01))))
    (is (= 43 (t/location 14 (:maps d05_s01))))
    (is (= 86 (t/location 55 (:maps d05_s01))))
    (is (= 35 (t/location 13 (:maps d05_s01))))))

(deftest lowest-location-test
  (testing "Finds the lowest location value"
    (is (= 35 (t/lowest-location d05_s01)))))

(deftest seed-ranges
  (testing "Converts the seed numbers into ranges to explore"
    (is (= [79 80 81 82 83 84 85 86 87 88 89 90 91 92
            55 56 57 58 59 60 61 62 63 64 65 66 67]
           (t/seed-ranges d05_s01)))))

(deftest lowest-location-ranges-test
  (testing "Finds the lowest location value among seed ranges"
    (is (= 46 (t/lowest-location-ranges d05_s01)))))


(def day05-input (u/parse-puzzle-input t/parse 2023 5))

(deftest day05-part1-soln
  (testing "Reproduces the answer for day05, part1"
    (is (= 323142486 (t/day05-part1-soln day05-input)))))

(deftest ^:slow day05-part2-soln
  (testing "Reproduces the answer for day05, part2"
    (is (= 1 (t/day05-part2-soln day05-input)))))
