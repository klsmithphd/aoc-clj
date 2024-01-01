(ns aoc-clj.2021.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day17 :as t]))

(def d17-s00
  (t/parse ["target area: x=20..30, y=-10..-5"]))

(deftest hits-target?
  (testing "Correctly identifies whether probe ever hits target"
    (is (= true  (t/will-hit-target? d17-s00 [7 2])))
    (is (= true  (t/will-hit-target? d17-s00 [6 3])))
    (is (= true  (t/will-hit-target? d17-s00 [9 0])))
    (is (= true  (t/will-hit-target? d17-s00 [6 9])))
    (is (= false (t/will-hit-target? d17-s00 [17 -4])))))

(deftest viable-init-vels
  (testing "Identifies the viable initial velocities that hit the sample target"
    (is (= 112 (t/viable-init-vels d17-s00 10)))))

(def day17-input (u/parse-puzzle-input t/parse 2021 17))

(deftest day17-part1-soln
  (testing "Reproduces the answer for day17, part1"
    (is (= 2701 (t/day17-part1-soln day17-input)))))

(deftest day17-part2-soln
  (testing "Reproduces the answer for day17, part2"
    (is (= 1070 (t/day17-part2-soln day17-input)))))
