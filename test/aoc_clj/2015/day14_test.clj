(ns aoc-clj.2015.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day14 :as t]))

(def d14-s00
  (t/parse
   ["Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds."
    "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."]))

(deftest distance-test
  (testing "Finds the distance traveled by each reindeer after 1000 seconds"
    (is (= 1120 (t/distance-at-time 1000 (get d14-s00 "Comet"))))
    (is (= 1056 (t/distance-at-time 1000 (get d14-s00 "Dancer"))))))

(deftest points-test
  (testing "Finds the points scored by each reindeer after 1000 seconds"
    (is (= [312 689] (t/points-at-time 1000 d14-s00)))))

(def day14-input (u/parse-puzzle-input t/parse 2015 14))

(deftest part1-test
  (testing "Reproduces the answer for day14, part1"
    (is (= 2660 (t/part1 day14-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day14, part2"
    (is (= 1256 (t/part2 day14-input)))))