(ns aoc-clj.2022.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
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

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= d16-s01
           [{:valve "AA", :flow 0,  :tunnels ["DD" "II" "BB"]}
            {:valve "BB", :flow 13, :tunnels ["CC" "AA"]}
            {:valve "CC", :flow 2,  :tunnels ["DD" "BB"]}
            {:valve "DD", :flow 20, :tunnels ["CC" "AA" "EE"]}
            {:valve "EE", :flow 3,  :tunnels ["FF" "DD"]}
            {:valve "FF", :flow 0,  :tunnels ["EE" "GG"]}
            {:valve "GG", :flow 0,  :tunnels ["FF" "HH"]}
            {:valve "HH", :flow 22, :tunnels ["GG"]}
            {:valve "II", :flow 0,  :tunnels ["AA" "JJ"]}
            {:valve "JJ", :flow 21, :tunnels ["II"]}]))))

(deftest best-pressure-subpath-test
  (testing "Finds the optimum path to release the most pressure"
    (is (= [["CC" 6 1651] ["EE" 9 1639] ["HH" 13 1612] ["JJ" 21 1326]
            ["BB" 25 885] ["DD" 28 560] ["AA" 30 0]]
           (t/best-subpath
            (:graph (t/simpler-graph d16-s01))
            (t/valves d16-s01)
            [["AA" 30 0]])))))

(deftest best-pressure-test
  (testing "Finds the optimum amount of pressure released"
    (is (= 1651 (t/best-pressure d16-s01)))))

(deftest best-pressure-2-test
  (testing "Finds the optimum amount of pressure released"
    (is (= 1707 (t/best-pressure-2 d16-s01)))))

(def day16-input (u/parse-puzzle-input t/parse 2022 16))

;; FIXME: The implementation is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/28

(deftest ^:slow day16-part1-soln
  (testing "Reproduces the answer for day16, part1"
    (is (= 1701 (t/day16-part1-soln day16-input)))))

(deftest ^:slow day16-part2-soln
  (testing "Reproduces the answer for day16, part2"
    (is (= 2455 (t/day16-part2-soln day16-input)))))