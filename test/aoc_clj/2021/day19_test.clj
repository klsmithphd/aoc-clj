(ns aoc-clj.2021.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day19 :as t]))

(def day19-sample1 (u/fmap t/add-relative-vectors (t/parse (u/puzzle-input "inputs/2021/day19-sample1.txt"))))

(deftest orient-sensor-to-known
  (testing "Can compute the offset for sensor 1 to sensor 0 in the sample data"
    (is (= [68 -1246 -43]
           (:offset (t/orient-sensor-to-known (get day19-sample1 0) (get day19-sample1 1)))))))

(deftest count-all-beacons
  (testing "Counts all the beacons in the sample data"
    (is (= 79 (count (t/all-beacons day19-sample1))))))

(deftest max-sensor-distance
  (testing "Finds the maximum (Manhattan) distance between any two sensors"
    (is (= 3621 (t/max-sensor-distance day19-sample1)))))

;; FIXME: 2021.day19 is too slow to unit test
;; https://github.com/Ken-2scientists/aoc-clj/issues/7
(deftest ^:slow day19-part1-soln
  (testing "Reproduces the answer for day19, part1"
    (is (= 449 (t/day19-part1-soln)))))

(deftest ^:slow day19-part2-soln
  (testing "Reproduces the answer for day19, part2"
    (is (= 13128 (t/day19-part2-soln)))))
