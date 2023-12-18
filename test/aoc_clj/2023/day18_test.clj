(ns aoc-clj.2023.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day18 :as t]))

(def d18-s01-raw
  ["R 6 (#70c710)"
   "D 5 (#0dc571)"
   "L 2 (#5713f0)"
   "D 2 (#d2c081)"
   "R 2 (#59c680)"
   "D 2 (#411b91)"
   "L 5 (#8ceee2)"
   "U 2 (#caa173)"
   "L 1 (#1b58a2)"
   "U 2 (#caa171)"
   "R 2 (#7807d2)"
   "U 3 (#a77fa3)"
   "L 2 (#015232)"
   "U 2 (#7a21e3)"])

(def d18-s01
  [{:dir "R" :dist 6 :color "70c710"}
   {:dir "D" :dist 5 :color "0dc571"}
   {:dir "L" :dist 2 :color "5713f0"}
   {:dir "D" :dist 2 :color "d2c081"}
   {:dir "R" :dist 2 :color "59c680"}
   {:dir "D" :dist 2 :color "411b91"}
   {:dir "L" :dist 5 :color "8ceee2"}
   {:dir "U" :dist 2 :color "caa173"}
   {:dir "L" :dist 1 :color "1b58a2"}
   {:dir "U" :dist 2 :color "caa171"}
   {:dir "R" :dist 2 :color "7807d2"}
   {:dir "U" :dist 3 :color "a77fa3"}
   {:dir "L" :dist 2 :color "015232"}
   {:dir "U" :dist 2 :color "7a21e3"}])

(def d18-s01-reinterpreted
  [{:dir "R" :dist 461937}
   {:dir "D" :dist 56407}
   {:dir "R" :dist 356671}
   {:dir "D" :dist 863240}
   {:dir "R" :dist 367720}
   {:dir "D" :dist 266681}
   {:dir "L" :dist 577262}
   {:dir "U" :dist 829975}
   {:dir "L" :dist 112010}
   {:dir "D" :dist 829975}
   {:dir "L" :dist 491645}
   {:dir "U" :dist 686074}
   {:dir "L" :dist 5411}
   {:dir "U" :dist 500254}])

(def d18-vertices
  [[6 0] [6 5] [4 5] [4 7] [6 7] [6 9] [1 9]
   [1 7] [0 7] [0 5] [2 5] [2 2] [0 2] [0 0]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d18-s01 (t/parse d18-s01-raw)))))

(deftest vertices-test
  (testing "Returns the collection of vertices for each segment"
    (is (= d18-vertices (t/vertices d18-s01)))))

(deftest perimeter-length-test
  (testing "Computes the length along the perimiter"
    (is (= 38 (t/perimeter-length d18-vertices)))))

(deftest polygon-area-test
  (testing "Computes the area of a polygon described by its vertices"
    (is (= 42 (t/polygon-area d18-vertices)))))

(deftest interior-count-test
  (testing "Computes the number of internal points within a polygon"
    (is (= 24 (t/interior-count d18-vertices)))))

(deftest dig-area-test
  (testing "Computes the number of tiles excavated"
    (is (= 62 (t/dig-area d18-s01)))))

(deftest interpret-hex-test
  (testing "Reinterprets the hex color codes as dist/dir values"
    (is (= d18-s01-reinterpreted (map t/interpret-hex d18-s01)))))

(deftest dig-area-reinterpreted-test
  (testing "Computes the dig area with the hex reinterpretation of the input"
    (is (= 952408144115 (t/dig-area-reinterpreted d18-s01)))))

(def day18-input (u/parse-puzzle-input t/parse 2023 18))

(deftest day18-part1-soln
  (testing "Reproduces the answer for day18, part1"
    (is (= 66993 (t/day18-part1-soln day18-input)))))

(deftest day18-part2-soln
  (testing "Reproduces the answer for day18, part2"
    (is (= 177243763226648 (t/day18-part2-soln day18-input)))))