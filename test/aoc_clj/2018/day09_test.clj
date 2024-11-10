(ns aoc-clj.2018.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day09 :as d09]))

(def d09-s00-raw ["10 players; last marble is worth 1618 points"])

(def d09-s00 [10 1618])
(def d09-s01 [13 7999])
(def d09-s02 [17 1104])
(def d09-s03 [21 6111])
(def d09-s04 [30 5807])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d09-s00 (d09/parse d09-s00-raw)))))

(deftest insert-marble-test
  (testing "Updates the game state with a new marble"
    (is (= {:marbles [0 1]
            :marble 2
            :players {0 0 1 0 2 0 3 0 4 0 5 0 6 0 7 0 8 0}
            :player 1
            :pos 1}
           (d09/insert-marble (d09/init-state 9))))

    (is (= {:marbles [0 2 1]
            :marble 3
            :players {0 0 1 0 2 0 3 0 4 0 5 0 6 0 7 0 8 0}
            :player 2
            :pos 1}
           (d09/insert-marble
            {:marbles [0 1]
             :marble 2
             :players {0 0 1 0 2 0 3 0 4 0 5 0 6 0 7 0 8 0}
             :player 1
             :pos 1})))

    (is (= {:marbles [0 2 1 3]
            :marble 4
            :players {0 0 1 0 2 0 3 0 4 0 5 0 6 0 7 0 8 0}
            :player 3
            :pos 3}
           (d09/insert-marble
            {:marbles [0 2 1]
             :marble 3
             :players {0 0 1 0 2 0 3 0 4 0 5 0 6 0 7 0 8 0}
             :player 2
             :pos 1})))

    ;; Skip ahead to 23
    (is (= {:marbles [0 16 8 17 4 18 19 2 20 10 21 5 22 11 1 12 6 13 3 14 7 15]
            :marble 24
            :players {0 0 1 0 2 0 3 0 4 32 5 0 6 0 7 0 8 0}
            :player 5
            :pos 6}
           (d09/insert-marble
            {:marbles [0 16 8 17 4 18 9 19 2 20 10 21 5 22 11 1 12 6 13 3 14 7 15]
             :marble 23
             :players {0 0 1 0 2 0 3 0 4 0 5 0 6 0 7 0 8 0}
             :player 4
             :pos 13})))))


(deftest play-test
  (testing "Achieves correct end state at the last marble"
    (is (= {:marbles [0 16 8 17 4 18 19 2 24 20 25 10 21 5 22 11 1 12 6 13 3 14 7 15]
            :marble 26
            :players {0 0 1 0 2 0 3 0 4 32 5 0 6 0 7 0 8 0}
            :player 7
            :pos 10}
           (d09/play [9 25])))))

(deftest high-score-test
  (testing "Reports out the correct high score after playing the game"
    (is (= 8317   (d09/high-score d09-s00)))
    (is (= 146373 (d09/high-score d09-s01)))
    (is (= 2764   (d09/high-score d09-s02)))
    (is (= 54718  (d09/high-score d09-s03)))
    (is (= 37305  (d09/high-score d09-s04)))))

(def day09-input (u/parse-puzzle-input d09/parse 2018 9))

;; FIXME too slow: https://github.com/klsmithphd/aoc-clj/issues/124
(deftest ^:slow part1-test
  (testing "Reproduces the answer for day09, part1"
    (is (= 404611 (d09/part1 day09-input)))))

(deftest ^:slow part2-test
  (testing "Reproduces the answer for day09, part2"
    (is (= 1 (d09/part2 day09-input)))))