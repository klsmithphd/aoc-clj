(ns aoc-clj.2016.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2016.day08 :as t]))

(def day08-sample
  (map t/parse-line
       ["rect 3x2"
        "rotate column x=1 by 1"
        "rotate row y=0 by 4"
        "rotate column x=1 by 1"]))

(deftest apply-instructions
  (testing "Can apply the instructions for the sample data"
    (is (= #{[1 0] [4 0] [6 0] [0 1] [2 1] [1 2]}
           (->>  (t/apply-instructions 7 3 day08-sample)
                 :grid
                 (filter #(= :on (val %)))
                 keys
                 set)))))

(deftest day08-part1-soln
  (testing "Reproduces the answer for day08, part1"
    (is (= 128 (t/day08-part1-soln)))))

(deftest day08-part2-soln
  (testing "Reproduces the answer for day08, part2"
    (is (= "EOARGPHYAO" (t/day08-part2-soln)))))