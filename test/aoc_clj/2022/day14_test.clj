(ns aoc-clj.2022.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day14 :as t]))

(def d14-s01
  (t/parse
   ["498,4 -> 498,6 -> 496,6"
    "503,4 -> 502,4 -> 502,9 -> 494,9"]))

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= d14-s01
           #{[498 4]
             [498 5]
             [498 6]
             [497 6]
             [496 6]
             [503 4]
             [502 4]
             [502 5]
             [502 6]
             [502 7]
             [502 8]
             [502 9]
             [501 9]
             [500 9]
             [499 9]
             [498 9]
             [497 9]
             [496 9]
             [495 9]
             [494 9]}))))

(deftest move-test
  (testing "Sand moves one tick based on space"
    (is (= [0 1]  (t/move #{} [0 0])))
    (is (= [-1 1] (t/move #{[0 1]} [0 0])))
    (is (= [1 1]  (t/move #{[0 1]
                            [-1 1]} [0 0])))
    (is (nil? (t/move #{[0 1]
                        [-1 1]
                        [1 1]} [0 0])))))

(def sample (t/init-state d14-s01))
(deftest deposit-sand-grain-test
  (testing "Updates the grid with a newly deposited sand grain"
    (is (= [500 8]
           (:last-added (t/deposit-sand-grain sample))))
    (is (= [499 8]
           (:last-added
            (nth (iterate t/deposit-sand-grain sample) 2))))
    (is (= [501 8]
           (:last-added
            (nth (iterate t/deposit-sand-grain sample) 3))))
    (is (= [500 7]
           (:last-added
            (nth (iterate t/deposit-sand-grain sample) 4))))
    (is (= [498 8]
           (:last-added
            (nth (iterate t/deposit-sand-grain sample) 5))))
    (is (= [500 2]
           (:last-added
            (nth (iterate t/deposit-sand-grain sample) 22))))
    (is (= [497 5]
           (:last-added
            (nth (iterate t/deposit-sand-grain sample) 23))))
    (is (= [495 8]
           (:last-added
            (nth (iterate t/deposit-sand-grain sample) 24))))))

(deftest sand-until-continuous-flow-test
  (testing "Finds the amount of sand that sticks before continuous flow"
    (is (= 24 (t/sand-until-continuous-flow d14-s01)))))

(deftest day14-part1-soln
  (testing "Reproduces the answer for day14, part1"
    (is (= 913 (t/day14-part1-soln)))))

(deftest day14-part2-soln
  (testing "Reproduces the answer for day14, part2"
    (is (= 30762 (t/day14-part2-soln)))))