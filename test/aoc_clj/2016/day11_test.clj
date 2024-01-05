(ns aoc-clj.2016.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            ;; [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day11 :as t]))

"The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip."
"The second floor contains a hydrogen generator."
"The third floor contains a lithium generator."
"The fourth floor contains nothing relevant."
(def d11-s00
  {:e 1
   1 #{[:m "H"] [:m "Li"]}
   2 #{[:g "H"]}
   3 #{[:g "Li"]}
   4 #{}})

(deftest move-count
  (testing "Counts the minimum number of moves "
    (is (= 11 (t/move-count d11-s00)))))

"The first floor contains a thulium generator, a thulium-compatible microchip, a plutonium generator, and a strontium generator.
The second floor contains a plutonium-compatible microchip and a strontium-compatible microchip.
The third floor contains a promethium generator, a promethium-compatible microchip, a ruthenium generator, and a ruthenium-compatible microchip.
The fourth floor contains nothing relevant."
(def day11-input
  {:e 1
  ;;  :move 0
   1 #{[:m "Tm"] [:g "Tm"] [:g "Pu"] [:g "Sr"]}
   2 #{[:m "Pu"] [:m "Sr"]}
   3 #{[:m "Pm"] [:m "Ru"] [:g "Pm"] [:g "Ru"]}
   4 #{}})
;; (def day11-input (u/parse-puzzle-input t/parse 2016 11))

;; FIXME: Implementation is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/31
(deftest ^:slow part1
  (testing "Reproduces the answer for day11, part1"
    (is (= 31 (t/part1 day11-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day11, part2"
    (is (= 55 (t/part2 day11-input)))))