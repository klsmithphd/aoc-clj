(ns aoc-clj.2023.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day03 :as t]))

(def d03-s01-raw ["467..114.."
                  "...*......"
                  "..35..633."
                  "......#..."
                  "617*......"
                  ".....+.58."
                  "..592....."
                  "......755."
                  "...$.*...."
                  ".664.598.."])

(def d03-s01
  {:numbers [{:points [[0 0] [1 0] [2 0]] :value 467}
             {:points [[5 0] [6 0] [7 0]] :value 114}
             {:points [[2 2] [3 2]] :value 35}
             {:points [[6 2] [7 2] [8 2]] :value 633}
             {:points [[0 4] [1 4] [2 4]] :value 617}
             {:points [[7 5] [8 5]] :value 58}
             {:points [[2 6] [3 6] [4 6]] :value 592}
             {:points [[6 7] [7 7] [8 7]] :value 755}
             {:points [[1 9] [2 9] [3 9]] :value 664}
             {:points [[5 9] [6 9] [7 9]] :value 598}]
   :symbols [{:point [3 1] :value \*}
             {:point [6 3] :value \#}
             {:point [3 4] :value \*}
             {:point [5 5] :value \+}
             {:point [3 8] :value \$}
             {:point [5 8] :value \*}]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d03-s01 (t/parse d03-s01-raw)))))

(deftest part-numbers-test
  (testing "Finds all of the part numbers"
    (is (= [467 35 633 617 592 755 664 598] (t/part-numbers d03-s01)))))

(deftest part-numbers-sum-test
  (testing "Returns the sum of all the part numbers"
    (is (= 4361 (t/part-numbers-sum d03-s01)))))


(def day03-input (u/parse-puzzle-input t/parse 2023 3))

(deftest day03-part1-soln
  (testing "Reproduces the answer for day03, part1"
    (is (= 509115 (t/day03-part1-soln day03-input)))))

;; (deftest day03-part2-soln
;;   (testing "Reproduces the answer for day03, part2"
;;     (is (= 1 (t/day03-part2-soln day03-input)))))