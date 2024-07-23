(ns aoc-clj.2016.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day10 :as d10]))

(def d10-s00-raw
  ["value 5 goes to bot 2"
   "bot 2 gives low to bot 1 and high to bot 0"
   "value 3 goes to bot 1"
   "bot 1 gives low to output 1 and high to bot 0"
   "bot 0 gives low to output 2 and high to output 0"
   "value 2 goes to bot 2"])

(def d10-s00
  {:assignments {"bot2" #{5 2}
                 "bot1" #{3}}
   :comparisons {"bot2" {:low "bot1" :high "bot0"}
                 "bot1" {:low "output1" :high "bot0"}
                 "bot0" {:low "output2" :high "output0"}}})

(def d10-s00-assignments-1 {"bot0" #{5} "bot1" #{2 3} "bot2" #{}})
(def d10-s00-assignments-2 {"bot0" #{3 5} "bot1" #{} "bot2" #{} "output1" #{2}})
(def d10-s00-assignments-3 {"bot0" #{} "bot1" #{} "bot2" #{} 
                            "output0" #{5} "output1" #{2} "output2" #{3}})

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= d10-s00 (d10/parse d10-s00-raw)))))

(deftest step-test
  (testing "Checks each of the steps result in the correct next set of assignments"
    (is (= d10-s00-assignments-1 (:assignments (d10/step d10-s00))))
    (is (= d10-s00-assignments-2 (:assignments (d10/step (d10/step d10-s00)))))
    (is (= d10-s00-assignments-3 (:assignments (d10/step (d10/step (d10/step d10-s00))))))))

(deftest bot-that-compares-test
  (testing "Find the bots that will compare the values in the sample data"
    (is (= 2 (d10/bot-that-compares d10-s00 #{2 5})))
    (is (= 1 (d10/bot-that-compares d10-s00 #{2 3})))
    (is (= 0 (d10/bot-that-compares d10-s00 #{3 5})))))

(def day10-input (u/parse-puzzle-input d10/parse 2016 10))

(deftest part1-test
  (testing "Reproduces the answer for day10, part1"
    (is (= 93 (d10/part1 day10-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day10, part2"
    (is (= 47101 (d10/part2 day10-input)))))