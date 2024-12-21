(ns aoc-clj.2024.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day17 :as d17]))

(def d17-s00-raw
  ["Register A: 729"
   "Register B: 0"
   "Register C: 0"
   ""
   "Program: 0,1,5,4,3,0"])

(def d17-s00
  {:regs {:a 729 :b 0 :c 0}
   :prog [0 1 5 4 3 0]})

(def d17-s01
  {:regs {:a 0 :b 0 :c 9}
   :prog [2 6]})

(def d17-s02
  {:regs {:a 10 :b 0 :c 0}
   :prog [5 0 5 1 5 4]})

(def d17-s03
  {:regs {:a 2024 :b 0 :c 0}
   :prog [0 1 5 4 3 0]})

(def d17-s04
  {:regs {:a 0 :b 29 :c 0}
   :prog [1 7]})

(def d17-s05
  {:regs {:a 0 :b 2024 :c 43690}
   :prog [4 0]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d17-s00 (d17/parse d17-s00-raw)))))

(deftest execute-test
  (testing "Returns the expected result for the program"
    (is (= (-> (d17/init-state d17-s00)
               (assoc-in [:regs :a] 0)
               (assoc :out [4 6 3 5 6 3 5 2 1 0])
               (assoc :ip 6))
           (d17/execute d17-s00)))

    (is (= (-> (d17/init-state d17-s01)
               (assoc-in [:regs :b] 1)
               (assoc :ip 2))
           (d17/execute d17-s01)))

    (is (= (-> (d17/init-state d17-s02)
               (assoc :out [0 1 2])
               (assoc :ip 6))
           (d17/execute d17-s02)))

    (is (= (-> (d17/init-state d17-s03)
               (assoc-in [:regs :a] 0)
               (assoc :out [4 2 5 6 7 7 7 7 3 1 0])
               (assoc :ip 6))
           (d17/execute d17-s03)))

    (is (= (-> (d17/init-state d17-s04)
               (assoc-in [:regs :b] 26)
               (assoc :ip 2))
           (d17/execute d17-s04)))

    (is (= (-> (d17/init-state d17-s05)
               (assoc-in [:regs :b] 44354)
               (assoc :ip 2))
           (d17/execute d17-s05)))))

(def day17-input (u/parse-puzzle-input d17/parse 2024 17))

(deftest part1-test
  (testing "Reproduces the answer for day17, part1"
    (is (= "1,4,6,1,6,4,3,0,3" (d17/part1 day17-input)))))