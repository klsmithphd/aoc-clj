(ns aoc-clj.2015.day22-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day22 :as d22]))

(def d22-s00-raw
  ["Hit Points: 58"
   "Damage: 9"])

(def d22-s00
  {:hit-points 58 :damage 9})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d22-s00 (d22/parse d22-s00-raw)))))

(def d22-s01
  {:player {:hit-points 10 :mana 250 :armor 0}
   :boss   {:hit-points 13 :damage 8}
   :effects {}})
(def d22-s01-moves [:poison :magic-missile])

(def d22-s02
  {:player {:hit-points 10 :mana 250 :armor 0}
   :boss   {:hit-points 14 :damage 8}
   :effects {}})
(def d22-s02-moves [:recharge :shield :drain :poison :magic-missile])

(deftest sample-battles-test
  (testing "Arrives at the correct end state for the sample battles"
    (is (= {:player {:hit-points 2 :mana 24 :armor 0}
            :boss   {:hit-points 0 :damage 8}
            :effects {:poison 3}
            :last-spell :magic-missile}
           (reduce d22/combat-round d22-s01 d22-s01-moves)))
    (is (= {:player {:hit-points 1 :mana 114 :armor 0}
            :boss   {:hit-points -1 :damage 8}
            :effects {:poison 3}
            :last-spell :magic-missile}
           (reduce d22/combat-round d22-s02 d22-s02-moves)))))

(deftest winning-spells-test
  (testing "Determines the spells that cost the least mana to win"
    (is (= [:poison :recharge :shield :poison :recharge :magic-missile
            :poison :drain :magic-missile]
           (d22/winning-spells d22-s00)))
    (is (= [:poison :recharge :shield :poison :recharge :shield
            :poison :magic-missile :magic-missile]
           (d22/winning-spells true d22-s00)))))

(def day22-input (u/parse-puzzle-input d22/parse 2015 22))

(deftest part1-test
  (testing "Reproduces the answer for day22, part1"
    (is (= 1269 (d22/part1 day22-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day22, part2"
    (is (= 1309 (d22/part2 day22-input)))))
