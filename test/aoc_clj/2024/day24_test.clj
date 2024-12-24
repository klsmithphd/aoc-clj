(ns aoc-clj.2024.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day24 :as d24]))

(def d24-s00-raw
  ["x00: 1"
   "x01: 1"
   "x02: 1"
   "y00: 0"
   "y01: 1"
   "y02: 0"
   ""
   "x00 AND y00 -> z00"
   "x01 XOR y01 -> z01"
   "x02 OR y02 -> z02"])

(def d24-s00
  {:wires {"x00" 1
           "x01" 1
           "x02" 1
           "y00" 0
           "y01" 1
           "y02" 0}

   :gates [["x00" "y00" :and "z00"]
           ["x01" "y01" :xor "z01"]
           ["x02" "y02" :or  "z02"]]})

(def d24-s01
  (d24/parse
   ["x00: 1"
    "x01: 0"
    "x02: 1"
    "x03: 1"
    "x04: 0"
    "y00: 1"
    "y01: 1"
    "y02: 1"
    "y03: 1"
    "y04: 1"
    ""
    "ntg XOR fgs -> mjb"
    "y02 OR x01 -> tnw"
    "kwq OR kpj -> z05"
    "x00 OR x03 -> fst"
    "tgd XOR rvg -> z01"
    "vdt OR tnw -> bfw"
    "bfw AND frj -> z10"
    "ffh OR nrd -> bqk"
    "y00 AND y03 -> djm"
    "y03 OR y00 -> psh"
    "bqk OR frj -> z08"
    "tnw OR fst -> frj"
    "gnj AND tgd -> z11"
    "bfw XOR mjb -> z00"
    "x03 OR x00 -> vdt"
    "gnj AND wpb -> z02"
    "x04 AND y00 -> kjc"
    "djm OR pbm -> qhw"
    "nrd AND vdt -> hwm"
    "kjc AND fst -> rvg"
    "y04 OR y02 -> fgs"
    "y01 AND x02 -> pbm"
    "ntg OR kjc -> kwq"
    "psh XOR fgs -> tgd"
    "qhw XOR tgd -> z09"
    "pbm OR djm -> kpj"
    "x03 XOR y03 -> ffh"
    "x00 XOR y04 -> ntg"
    "bfw OR bqk -> z06"
    "nrd XOR fgs -> wpb"
    "frj XOR qhw -> z04"
    "bqk OR frj -> z07"
    "y03 OR x01 -> nrd"
    "hwm AND bqk -> z03"
    "tgd XOR rvg -> z12"
    "tnw OR pbm -> gnj"]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d24-s00 (d24/parse d24-s00-raw)))))

(deftest ready-gates-test
  (testing "Identifies the gates whose inputs are ready"
    (is (= (seq (:gates d24-s00))
           (d24/ready-gates d24-s00)))))

(deftest apply-gate-test
  (testing "Updates the state with a ready gate"
    (is (= {"x00" 1
            "x01" 1
            "x02" 1
            "y00" 0
            "y01" 1
            "y02" 0
            "z00" 0}
           (:wires (d24/apply-gate d24-s00 ["x00" "y00" :and "z00"]))))))

(deftest step-test
  (testing "Updates the state with all currently ready gates"
    (is (= {:wires {"x00" 1
                    "x01" 1
                    "x02" 1
                    "y00" 0
                    "y01" 1
                    "y02" 0
                    "z00" 0
                    "z01" 0
                    "z02" 1}
            :gates []}
           (d24/step d24-s00)))))

(deftest output-bits-test
  (testing "Returns the output bits after updating the circuit"
    (is (= [1 0 0] (d24/output-bits (d24/step d24-s00))))))

(deftest circuit-output-test
  (testing "Returns the value output by the circuit"
    (is (= 4    (d24/circuit-output d24-s00)))
    (is (= 2024 (d24/circuit-output d24-s01)))))

(def day24-input (u/parse-puzzle-input d24/parse 2024 24))

(deftest part1-test
  (testing "Reproduces the answer for day24, part1"
    (is (= 46463754151024 (d24/part1 day24-input)))))