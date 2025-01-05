(ns aoc-clj.2018.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day12 :as d12]))

(def d12-s00-raw
  ["initial state: #..#.#..##......###...###"
   ""
   "...## => #"
   "..#.. => #"
   ".#... => #"
   ".#.#. => #"
   ".#.## => #"
   ".##.. => #"
   ".#### => #"
   "#.#.# => #"
   "#.### => #"
   "##.#. => #"
   "##.## => #"
   "###.. => #"
   "###.# => #"
   "####. => #"])

(def d12-s00
  {:pots  [1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 1 1 1 0 0 0 1 1 1]
   :rules {[0 0 0 1 1] 1
           [0 0 1 0 0] 1
           [0 1 0 0 0] 1
           [0 1 0 1 0] 1
           [0 1 0 1 1] 1
           [0 1 1 0 0] 1
           [0 1 1 1 1] 1
           [1 0 1 0 1] 1
           [1 0 1 1 1] 1
           [1 1 0 1 0] 1
           [1 1 0 1 1] 1
           [1 1 1 0 0] 1
           [1 1 1 0 1] 1
           [1 1 1 1 0] 1}
   :left  0})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d12-s00 (d12/parse d12-s00-raw)))))

(deftest step-test
  (testing "Evolves the pot state one iteration forward"
    (is (= [0 1 0 0 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 1 0 0 1 0 0 1 0]
           (:pots (d12/step d12-s00))))
    (is (= [0 0 1 1 0 0 1 1 0 0 0 1 1 0 0 0 0 1 0 0 1 0 0 1 0 0 1 1 0]
           (:pots (d12/step (d12/step d12-s00)))))
    (is (= [0 0 1 0 1 0 0 0 1 0 0 1 0 1 0 0 0 0 1 0 0 1 0 0 1 0 0 0 1 0 0]
           (:pots (d12/step (d12/step (d12/step d12-s00))))))))

(deftest pots-with-plants-test
  (testing "Returns the pot numbers that contain plants"
    (is (= [0 3 5 8 9 16 17 18 22 23 24]
           (d12/pots-with-plants d12-s00)))
    (is (= [0 4 9 15 18 21 24]
           (d12/pots-with-plants (d12/step d12-s00))))
    (is (= [0 1 4 5 9 10 15 18 21 24 25]
           (d12/pots-with-plants (d12/step (d12/step d12-s00)))))
    (is (= [-1 1 5 8 10 15 18 21 25]
           (d12/pots-with-plants (d12/step (d12/step (d12/step d12-s00))))))))

(deftest pot-sum-at-n-test
  (testing "Returns the sum of the pot ids with plants after n steps"
    (is (= 325 (d12/pot-sum-at-n 20 d12-s00)))))

(deftest n-until-linear-test
  (testing "Finds the point where the simulation grows at a steady state"
    ;; The meaning is at n=86, the pot count is 1094, and from that point
    ;; forward, the pot count grows by 20 every generation.
    (is (= [86 1094 20] (d12/n-until-linear d12-s00)))))

(deftest pot-sum-at-large-n-test
  (testing "Finds the sum of occupied pots after a recurrence has kicked in"
    (is (= (d12/pot-sum-at-large-n 100 d12-s00)
           (d12/pot-sum-at-n 100 d12-s00)))))

(def day12-input (u/parse-puzzle-input d12/parse 2018 12))

(deftest part1-test
  (testing "Reproduces the answer for day12, part1"
    (is (= 3725 (d12/part1 day12-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day12, part2"
    (is (= 3100000000293 (d12/part2 day12-input)))))