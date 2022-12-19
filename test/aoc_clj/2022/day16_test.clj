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

(deftest pressure-released-test
  (testing "Computes the total pressure released by opening various valves
            at specific times"
    (is (= 1651 (t/pressure-released
                 (t/valves d16-s01)
                 {"DD" 28 "BB" 25 "JJ" 21 "HH" 13 "EE" 9 "CC" 6})))))

;; (deftest day16-part1-soln
;;   (testing "Reproduces the answer for day16, part1"
;;     (is (= 0 (t/day16-part1-soln)))))

;; (deftest day16-part2-soln
;;   (testing "Reproduces the answer for day16, part2"
;;     (is (= 0 (t/day16-part2-soln)))))