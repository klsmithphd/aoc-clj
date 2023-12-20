(ns aoc-clj.2023.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2023.day20 :as t]))

(def d20-s01-raw
  ["broadcaster -> a, b, c"
   "%a -> b"
   "%b -> c"
   "%c -> inv"
   "&inv -> a"])

(def d20-s02-raw
  ["broadcaster -> a"
   "%a -> inv, con"
   "&inv -> b"
   "%b -> con"
   "&con -> output"])

(def d20-s01
  [{:type :broadcast :id "broadcaster" :dest ["a" "b" "c"]}
   {:type :flip-flop :id "a" :dest ["b"]}
   {:type :flip-flop :id "b" :dest ["c"]}
   {:type :flip-flop :id "c" :dest ["inv"]}
   {:type :conjunction :id "inv" :dest ["a"]}])

(def d20-s02
  [{:type :broadcast :id "broadcaster" :dest ["a"]}
   {:type :flip-flop :id "a" :dest ["inv" "con"]}
   {:type :conjunction :id "inv" :dest ["b"]}
   {:type :flip-flop :id "b" :dest ["con"]}
   {:type :conjunction :id "con" :dest ["output"]}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d20-s01 (t/parse d20-s01-raw)))
    (is (= d20-s02 (t/parse d20-s02-raw)))))