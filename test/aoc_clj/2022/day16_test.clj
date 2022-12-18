(ns aoc-clj.2022.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day16 :as t]))

(def d16-s01
  (t/parse
   ["Valve AA has flow rate=0; tunnels lead to valves DD, II, BB"
    "Valve BB has flow rate=13; tunnels lead to valves CC, AA"
    "Valve CC has flow rate=2; tunnels lead to valves DD, BB"
    "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE"
    "Valve EE has flow rate=3; tunnels lead to valves FF, DD"
    "Valve FF has flow rate=0; tunnels lead to valves EE, GG"
    "Valve GG has flow rate=0; tunnels lead to valves FF, HH"
    "Valve HH has flow rate=22; tunnel leads to valve GG"
    "Valve II has flow rate=0; tunnels lead to valves AA, JJ"
    "Valve JJ has flow rate=21; tunnel leads to valve II"]))

;; (deftest day16-part1-soln
;;   (testing "Reproduces the answer for day16, part1"
;;     (is (= 0 (t/day16-part1-soln)))))

;; (deftest day16-part2-soln
;;   (testing "Reproduces the answer for day16, part2"
;;     (is (= 0 (t/day16-part2-soln)))))