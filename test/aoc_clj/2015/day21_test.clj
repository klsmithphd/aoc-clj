(ns aoc-clj.2015.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day21 :as t]))

(deftest combo-total-test
  (testing "Correctly totals up a combo option"
    (is (= {:hit-points 100 :damage 7 :armor 5 :cost 210}
           (t/combo-total {:weapon :dagger :armor :platemail :ring [:damage+3]})))))

(deftest player-wins-test
  (testing "Correctly assesses whether the player can win"
    (is (t/player-wins? {:hit-points 12 :damage 7 :armor 2}
                        {:hit-points  8 :damage 5 :armor 5}))))

(def day21-input (u/parse-puzzle-input t/parse 2015 21))

(deftest day21-part1-soln
  (testing "Reproduces the answer for day21, part1"
    (is (= 78 (t/day21-part1-soln day21-input)))))

(deftest day21-part2-soln
  (testing "Reproduces the answer for day21, part2"
    (is (= 148 (t/day21-part2-soln day21-input)))))