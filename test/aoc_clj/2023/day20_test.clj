(ns aoc-clj.2023.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day20 :as t]))

(def d20-s00-raw
  ["broadcaster -> a, b, c"
   "%a -> b"
   "%b -> c"
   "%c -> inv"
   "&inv -> a"])

(def d20-s01-raw
  ["broadcaster -> a"
   "%a -> inv, con"
   "&inv -> b"
   "%b -> con"
   "&con -> output"])

(def d20-s00
  {"broadcaster" {:type :broadcast   :dest ["a" "b" "c"]}
   "a"           {:type :flip-flop   :dest ["b"]}
   "b"           {:type :flip-flop   :dest ["c"]}
   "c"           {:type :flip-flop   :dest ["inv"]}
   "inv"         {:type :conjunction :dest ["a"]}})

(def d20-s01
  {"broadcaster" {:type :broadcast   :dest ["a"]}
   "a"           {:type :flip-flop   :dest ["inv" "con"]}
   "inv"         {:type :conjunction :dest ["b"]}
   "b"           {:type :flip-flop   :dest ["con"]}
   "con"         {:type :conjunction :dest ["output"]}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d20-s00 (t/parse d20-s00-raw)))
    (is (= d20-s01 (t/parse d20-s01-raw)))))

(deftest conj-inputs-test
  (testing "Finds all the input modules for a conjunction module"
    (is (= ["c"]     (t/conj-inputs d20-s00 "inv")))
    (is (= ["a"]     (t/conj-inputs d20-s01 "inv")))
    (is (= ["a" "b"] (t/conj-inputs d20-s01 "con")))))

(deftest initial-circuit-state-test
  (testing "Constructs the initial state of the connected modules"
    (is (= {"a" :off
            "b" :off
            "c" :off
            "inv" {"c" :low}} (t/initial-circuit-state d20-s00)))
    (is (= {"a" :off
            "b" :off
            "inv" {"a" :low}
            "con" {"a" :low "b" :low}} (t/initial-circuit-state d20-s01)))))

(deftest pulse-history-test
  (testing "Confirms the number of high and low pulses after processing
            initial pulse"
    (is (= {:low 8 :high 4 :buttons 1}
           (:pulse-history (t/after-n-buttons d20-s00 1))))
    (is (= {:low 4 :high 4 :buttons 1}
           (:pulse-history (t/after-n-buttons d20-s01 1))))
    (is (= {:low 8 :high 6 :buttons 2}
           (:pulse-history (t/after-n-buttons d20-s01 2))))
    (is (= {:low 13 :high 9 :buttons 3}
           (:pulse-history (t/after-n-buttons d20-s01 3))))
    (is (= {:low 17 :high 11 :buttons 4}
           (:pulse-history (t/after-n-buttons d20-s01 4))))))

(deftest circuit-state-cycle-test
  (testing "Confirms that the circuit state returns to the initial value
            after the correct number of cycles"
    ;; Just one button press for the first sample data
    (is (= (:circuit-state (t/init-state d20-s00))
           (:circuit-state (t/after-n-buttons d20-s00 1))))
    ;; Four button presses for the second sample date
    (is (= (:circuit-state (t/init-state d20-s01))
           (:circuit-state (t/after-n-buttons d20-s01 4))))))

(deftest pulses-until-cycle
  (testing "Computes the number of pulses of high and low type are 
            processed before the network cycles back to its initial state"
    (is (= {:low 8  :high 4  :buttons 1} (t/pulses-until-cycle d20-s00)))
    (is (= {:low 17 :high 11 :buttons 4} (t/pulses-until-cycle d20-s01)))))

(deftest pulses-after-1000
  (testing "Product of the number of pulses of each type after processing
            1000 button presses"
    (is (= 32000000 (t/pulses-after-1000 d20-s00)))
    (is (= 11687500 (t/pulses-after-1000 d20-s01)))))

(deftest pulses-after-1000-brute-force
  (testing "Product of the number of pulses of each type after processing
            1000 button presses by actually simulating all those presses"
    (is (= 32000000 (t/pulses-after-1000-brute-force d20-s00)))
    (is (= 11687500 (t/pulses-after-1000-brute-force d20-s01)))))

(def day20-input (u/parse-puzzle-input t/parse 2023 20))

(deftest day20-part1-soln
  (testing "Reproduces the answer for day20, part1"
    (is (= 821985143 (t/day20-part1-soln day20-input)))))

(deftest day20-part2-soln
  (testing "Reproduces the answer for day20, part2"
    (is (= 240853834793347 (t/day20-part2-soln day20-input)))))
