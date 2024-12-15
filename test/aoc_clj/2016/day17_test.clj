(ns ^:eftest/synchronized aoc-clj.2016.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day17 :as d17]))

(def d17-s00 "hijkl")
(def d17-s01 "ihgpwlah")
(def d17-s02 "kglvqrro")
(def d17-s03 "ulqzkmiv")

(deftest move-options-test
  (testing "Finds the next move options for a given position"
    (is (= [{:passcode d17-s00 :pos [0 1] :path "D"}]
           (d17/move-options (d17/init-state d17-s00))))
    (is (= [{:passcode d17-s00 :pos [0 0] :path "DU"}
            {:passcode d17-s00 :pos [1 1] :path "DR"}]
           (d17/move-options {:passcode d17-s00 :pos [0 1] :path "D"})))
    (is (= []
           (d17/move-options {:passcode d17-s00 :pos [1 1] :path "DR"})))
    (is (= [{:passcode d17-s00 :pos [1 0] :path "DUR"}]
           (d17/move-options {:passcode d17-s00 :pos [0 0] :path "DU"})))
    (is (= []
           (d17/move-options {:passcode d17-s00 :pos [1 0] :path "DUR"})))))

(deftest shortest-path-test
  (testing "Finds the shortest path through the vault"
    (is (= "DDRRRD" (d17/shortest-path d17-s01)))
    (is (= "DDUDRLRRUDRD" (d17/shortest-path d17-s02)))
    (is (= "DRURDRUDDLLDLUURRDULRLDUUDDDRR" (d17/shortest-path d17-s03)))))

(deftest ^:eftest/synchronized longest-path-length-test
  (testing "Finds the length of the longest path to the vault"
    (is (= 370 (d17/longest-path-length d17-s01)))
    (is (= 492 (d17/longest-path-length d17-s02)))
    (is (= 830 (d17/longest-path-length d17-s03)))))

(def day17-input (u/parse-puzzle-input d17/parse 2016 17))

(deftest part1-test
  (testing "Reproduces the answer for day17, part1"
    (is (= "DDRRULRDRD" (d17/part1 day17-input)))))

(deftest ^:eftest/synchronized part2-test
  (testing "Reproduces the answer for day17, part2"
    (is (= 536 (d17/part2 day17-input)))))