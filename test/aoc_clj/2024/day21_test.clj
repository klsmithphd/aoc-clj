(ns aoc-clj.2024.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day21 :as d21]))

(def d21-s00
  ["029A"
   "980A"
   "179A"
   "456A"
   "379A"])

(deftest keypad-paths-test
  (testing "Constructs the mapping between any two keys and the shortest moves
            necessary to get from one key to the other"
    (is (= {[\A \^] ["<A"]
            [\A \<] ["v<<A" "<v<A"]
            [\A \>] ["vA"]
            [\A \A] ["A"]
            [\A \v] ["v<A" "<vA"]

            [\^ \^] ["A"]
            [\^ \<] ["v<A"]
            [\^ \>] [">vA" "v>A"]
            [\^ \A] [">A"]
            [\^ \v] ["vA"]

            [\< \^] [">^A"]
            [\< \<] ["A"]
            [\< \>] [">>A"]
            [\< \A] [">>^A" ">^>A"]
            [\< \v] [">A"]

            [\> \^] ["^<A" "<^A"]
            [\> \<] ["<<A"]
            [\> \>] ["A"]
            [\> \A] ["^A"]
            [\> \v] ["<A"]

            [\v \^] ["^A"]
            [\v \<] ["<A"]
            [\v \>] [">A"]
            [\v \A] [">^A" "^>A"]
            [\v \v] ["A"]}
           (d21/keypad-paths d21/directional-keypad)))))

(deftest robot-dirs-test
  (testing "Converts the keypad code into robot arm moves"
    (is (= #{"<A^A>^^AvvvA"
             "<A^A^>^AvvvA"
             "<A^A^^>AvvvA"}
           (set (d21/robot-dirs "029A"))))))

;; (deftest remote-dirs-test
;;   (testing "Converts the robot move codes into remote directions"
;;     (is (= "v<<A>>^A<A>AvA<^AA>A<vAAA>^A"
;;            (->> "029A"
;;                 d21/robot-dirs
;;                 d21/remote-dirs)))

;;     (is (= "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A"
;;            (d21/all-presses "029A")))

;;     (is (= "<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A"
;;            (d21/all-presses "379A")))))

(deftest seq-length
  (testing "Computes the shortest move sequence"
    (is (= [68 60 68 64 64] (map #(d21/seq-length 2 %) d21-s00)))))

(deftest complexity-sum
  (testing "Computes the sum of the complexity of the codes"
    (is (= 126384 (d21/complexity-sum 2 d21-s00)))))

(def day21-input (u/parse-puzzle-input d21/parse 2024 21))

(deftest ^:slow part1-test
  (testing "Reproduces the answer for day21, part1"
    (is (= 94284 (d21/part2 day21-input)))))