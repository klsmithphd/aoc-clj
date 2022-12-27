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


(deftest best-pressure-subpath-test
  (testing "Finds the optimum path to release the most pressure"
    (is (= [["CC" 6 1651] ["EE" 9 1639] ["HH" 13 1612] ["JJ" 21 1326]
            ["BB" 25 885] ["DD" 28 560] ["AA" 30 0]]
           (t/best-pressure-subpath
            (:graph (t/simpler-graph d16-s01))
            (t/valves d16-s01)
            [["AA" 30 0]])))))

(deftest best-pressure-test
  (testing "Finds the optimum amount of pressure released"
    (is (= 1651 (t/best-pressure d16-s01)))))

(deftest day16-part1-soln
  (testing "Reproduces the answer for day16, part1"
    (is (= 1701 (t/day16-part1-soln)))))

;; (deftest day16-part2-soln
;;   (testing "Reproduces the answer for day16, part2"
;;     (is (= 0 (t/day16-part2-soln)))))