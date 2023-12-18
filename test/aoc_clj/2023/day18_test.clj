(ns aoc-clj.2023.day18-test
  (:require [clojure.test :refer [deftest testing is]]
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
  [{:dir "R" :dist 6 :color "#70c710"}
   {:dir "D" :dist 5 :color "#0dc571"}
   {:dir "L" :dist 2 :color "#5713f0"}
   {:dir "D" :dist 2 :color "#d2c081"}
   {:dir "R" :dist 2 :color "#59c680"}
   {:dir "D" :dist 2 :color "#411b91"}
   {:dir "L" :dist 5 :color "#8ceee2"}
   {:dir "U" :dist 2 :color "#caa173"}
   {:dir "L" :dist 1 :color "#1b58a2"}
   {:dir "U" :dist 2 :color "#caa171"}
   {:dir "R" :dist 2 :color "#7807d2"}
   {:dir "U" :dist 3 :color "#a77fa3"}
   {:dir "L" :dist 2 :color "#015232"}
   {:dir "U" :dist 2 :color "#7a21e3"}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d18-s01 (t/parse d18-s01-raw)))))