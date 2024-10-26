(ns aoc-clj.2017.day22-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day22 :as d22]))

(def d22-s00-raw
  ["..#"
   "#.."
   "..."])

(def d22-s00
  #{[1 1] [-1 0]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d22-s00 (d22/parse d22-s00-raw)))))

(deftest step-test
  (testing "Evolves the state forward one iteration"
    (is (= {:infected #{[0 0] [-1 0] [1 1]}
            :infect-cnt 1 :pos [-1 0] :heading :w}
           (d22/step (d22/init-state d22-s00))))

    (is (= {:infected #{[0 0] [1 1]}
            :infect-cnt 1 :pos [-1 1] :heading :n}
           (d22/step
            {:infected #{[0 0] [-1 0] [1 1]}
             :infect-cnt 1 :pos [-1 0] :heading :w})))

    (is (= {:infected #{[0 0] [1 1] [-1 1]}
            :infect-cnt 2 :pos [-2 1] :heading :w}
           (d22/step
            {:infected #{[0 0] [1 1]}
             :infect-cnt 1 :pos [-1 1] :heading :n})))))

(deftest infections-caused-at-n-test
  (testing "Counts how many infections were caused after n bursts"
    (is (= 5 (d22/infections-caused-at-n d22-s00 7)))
    (is (= 41 (d22/infections-caused-at-n d22-s00 70)))
    (is (= 5587 (d22/infections-caused-at-n d22-s00 10000)))))

(def day22-input (u/parse-puzzle-input d22/parse 2017 22))

(deftest part1-test
  (testing "Reproduces the answer for day22, part1"
    (is (= 5280 (d22/part1 day22-input)))))