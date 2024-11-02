(ns aoc-clj.2017.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day25 :as d25]))

(def d25-s00-raw
  ["Begin in state A."
   "Perform a diagnostic checksum after 6 steps."
   ""
   "In state A:"
   "  If the current value is 0:"
   "    - Write the value 1."
   "    - Move one slot to the right."
   "    - Continue with state B."
   "  If the current value is 1:"
   "    - Write the value 0."
   "    - Move one slot to the left."
   "    - Continue with state B."
   ""
   "In state B:"
   "  If the current value is 0:"
   "    - Write the value 1."
   "    - Move one slot to the left."
   "    - Continue with state A."
   "  If the current value is 1:"
   "    - Write the value 1."
   "    - Move one slot to the right."
   "    - Continue with state A."])

(def d25-s00
  {:start "A"
   :steps 6
   "A" {0 {:write 1 :move :right :nxt-state "B"}
        1 {:write 0 :move :left :nxt-state "B"}}
   "B" {0 {:write 1 :move :left :nxt-state "A"}
        1 {:write 1 :move :right :nxt-state "A"}}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d25-s00 (d25/parse d25-s00-raw)))))

(deftest step-test
  (testing "Updates to the next state one step at a time"
    (is (= {:tape {0 1} :state "B" :slot 1}
           (d25/step
            d25-s00
            (assoc d25/init-state :state "A"))))

    (is (= {:tape {0 1 1 1} :state "A" :slot 0}
           (d25/step
            d25-s00
            {:tape {0 1} :state "B" :slot 1})))

    (is (= {:tape {0 0 1 1} :state "B" :slot -1}
           (d25/step
            d25-s00
            {:tape {0 1 1 1} :state "A" :slot 0})))

    (is (= {:tape {-1 1 0 0 1 1} :state "A" :slot -2}
           (d25/step
            d25-s00
            {:tape {0 0 1 1} :state "B" :slot -1})))

    (is (= {:tape {-2 1 -1 1 0 0 1 1} :state "B" :slot -1}
           (d25/step
            d25-s00
            {:tape {-1 1 0 0 1 1} :state "A" :slot -2})))

    (is (= {:tape {-2 1 -1 1 0 0 1 1} :state "A" :slot 0}
           (d25/step
            d25-s00
            {:tape {-2 1 -1 1 0 0 1 1} :state "B" :slot -1})))))

(deftest execute-test
  (testing "Executes the prescribed number of steps of the Turing machine"
    (is (= {:tape {-2 1 -1 1 0 0 1 1} :state "A" :slot 0}
           (d25/execute d25-s00)))))

(deftest checksum-test
  (testing "Computes sum of all the ones after prescribed steps"
    (is (= 3 (d25/checksum d25-s00)))))

(def day25-input (u/parse-puzzle-input d25/parse 2017 25))

(deftest part1-test
  (testing "Reproduces the answer for day25, part1"
    (is (= 3732 (d25/part1 day25-input)))))