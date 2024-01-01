(ns aoc-clj.2019.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day25 :as t]))

(def day25-input (u/parse-puzzle-input t/parse 2019 25))
(def day25-cmds  (u/puzzle-input "inputs/2019/day25-cmds.txt"))

(deftest day25-part1-soln-test
  (testing "Can reproduce the answer for part1"
    (is (= 16410 (t/day25-part1-soln day25-input day25-cmds)))))