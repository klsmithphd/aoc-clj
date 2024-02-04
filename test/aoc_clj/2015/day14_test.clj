(ns aoc-clj.2015.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day14 :as d14]))

(def d14-s00-raw
  ["Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds."
   "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."])

(def d14-s00
  [{:speed 14 :fly 10 :span 137}
   {:speed 16 :fly 11 :span 173}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d14-s00 (d14/parse d14-s00-raw)))))

(deftest position-test
  (testing "Finds the distance traveled by each reindeer after 1000 seconds"
    (is (= 1120 (d14/position 1000 (nth d14-s00 0))))
    (is (= 1056 (d14/position 1000 (nth d14-s00 1))))))

(deftest cumulative-points-test
  (testing "Finds the points scored by each reindeer after 1000 seconds"
    (is (= [312 689] (d14/cumulative_points 1000 d14-s00)))))

(def day14-input (u/parse-puzzle-input d14/parse 2015 14))

(deftest part1-test
  (testing "Reproduces the answer for day14, part1"
    (is (= 2660 (d14/part1 day14-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day14, part2"
    (is (= 1256 (d14/part2 day14-input)))))