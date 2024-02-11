(ns aoc-clj.2015.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day21 :as d21]))

(def d21-s00-raw
  ["Hit Points: 104"
   "Damage: 8"
   "Armor: 1"])

(def d21-s00
  {:hit-points 104
   :damage 8
   :armor 1})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d21-s00 (d21/parse d21-s00-raw)))))

(deftest all-item-combos-test
  (testing "Creates the correct number of weapon/armor/ring combos"
    ;; Weapon choices = 5
    ;; Armor choices = 6 (1 option for no armor plus 5 choices)
    ;; Rings choices = 22:
    ;;   1 option for no ring plus
    ;;   6 options for one ring plus
    ;;   15 options (combinations of six rings take 2)
    ;; 660 = 5 * 6 * 22
    (is (= 660 (count (d21/all-item-combos))))))

(deftest player-stats-test
  (testing "Correctly totals up a combo option"
    (is (= {:hit-points 100 :damage 7 :armor 5 :cost 210}
           (d21/player-stats [(d21/weapons :dagger)
                              (d21/armors :platemail)
                              (d21/rings :damage+3)])))))

(deftest player-wins-test
  (testing "Correctly assesses whether the player can win"
    (is (d21/player-wins? {:hit-points 12 :damage 7 :armor 2}
                          {:hit-points  8 :damage 5 :armor 5}))))

(def day21-input (u/parse-puzzle-input d21/parse 2015 21))

(deftest part1-test
  (testing "Reproduces the answer for day21, part1"
    (is (= 78 (d21/part1 day21-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day21, part2"
    (is (= 148 (d21/part2 day21-input)))))