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

(deftest wrap-around-test
  (testing "Computes the wrap-around positions correctly"
    (is (= [9 1] (t/wrap-around d22-s01 :R [13 1])))
    (is (= [12 1] (t/wrap-around d22-s01 :L [8 1])))
    (is (= [9 12] (t/wrap-around d22-s01 :U [9 0])))
    (is (= [12 5] (t/wrap-around d22-s01 :L [0 5])))))

(deftest follow-path-test
  (testing "Follows the path and arrives at the correct final position/orientation"
    (is (= {:pos [8 6] :facing :R} (t/follow-path d22-s01)))))

(deftest day22-part1-soln
  (testing "Reproduces the answer for day22, part1"
    (is (= 1428 (t/day22-part1-soln)))))

;; (deftest day22-part2-soln
;;   (testing "Reproduces the answer for day22, part2"
;;     (is (= 0 (t/day22-part2-soln)))))