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
