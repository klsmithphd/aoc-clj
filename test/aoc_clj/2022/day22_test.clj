(ns aoc-clj.2022.day22-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day22 :as t]))

(def d22-s01
  (t/parse
   ["        ...#"
    "        .#.."
    "        #..."
    "        ...."
    "...#.......#"
    "........#..."
    "..#....#...."
    "..........#."
    "        ...#...."
    "        .....#.."
    "        .#......"
    "        ......#."
    ""
    "10R5L5R10L4R5L5"]))

;; (deftest day22-part1-soln
;;   (testing "Reproduces the answer for day22, part1"
;;     (is (= 0 (t/day22-part1-soln)))))

;; (deftest day22-part2-soln
;;   (testing "Reproduces the answer for day22, part2"
;;     (is (= 0 (t/day22-part2-soln)))))