(ns aoc-clj.utils.maze-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.maze :as m]))

(deftest next-move-attempt-test
  (testing "Tests the direction logic for selecting the next space to try"
    ;; If we know nothing, check the west wall first (when facing north)
    (is (= :w (m/next-move-attempt
               {:pos [0 0] :dir :n :maze {}})))
    ;; If the position to the west is a wall, check north (when facing north)
    (is (= :n (m/next-move-attempt
               {:pos [0 0] :dir :n :maze {[-1 0] :wall}})))
    ;; West/North are walls, try east
    (is (= :e (m/next-move-attempt
               {:pos [0 0] :dir :n :maze {[-1 0] :wall [0 1] :wall}})))
    ;; Dead-end, try south
    (is (= :s (m/next-move-attempt
               {:pos [0 0] :dir :n :maze {[-1 0] :wall [0 1] :wall [1 0] :wall}})))))