(ns aoc-clj.2018.day10-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day10 :as d10]))

(def d10-s00-raw
  ["position=< 9,  1> velocity=< 0,  2>"
   "position=< 7,  0> velocity=<-1,  0>"
   "position=< 3, -2> velocity=<-1,  1>"
   "position=< 6, 10> velocity=<-2, -1>"])

(def d10-s00
  [[[9 1] [0 2]]
   [[7 0] [-1 0]]
   [[3 -2] [-1 1]]
   [[6 10] [-2 -1]]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d10-s00 (d10/parse d10-s00-raw)))))

(def d10-s01
  [[[9 1]   [0 2]]
   [[7 0]   [-1 0]]
   [[3 -2]  [-1 1]]
   [[6 10]  [-2 -1]]
   [[2 -4]  [2 2]]
   [[-6 10] [2 -2]]
   [[1 8]   [1 -1]]
   [[1 7]   [1 0]]
   [[-3 11] [1 -2]]
   [[7 6]   [-1 -1]]
   [[-2 3]  [1 0]]
   [[-4 3]  [2 0]]
   [[10 -3] [-1 1]]
   [[5 11]  [1 -2]]
   [[4 7]   [0 -1]]
   [[8 -2]  [0 1]]
   [[15 0]  [-2 0]]
   [[1 6]   [1 0]]
   [[8 9]   [0 -1]]
   [[3 3]   [-1 1]]
   [[0 5]   [0 -1]]
   [[-2 2]  [2 0]]
   [[5 -2]  [1 2]]
   [[1 4]   [2 1]]
   [[-2 7]  [2 -2]]
   [[3 6]   [-1 -1]]
   [[5 0]   [1 0]]
   [[-6 0]  [2 0]]
   [[5 9]   [1 -2]]
   [[14 7]  [-2 0]]
   [[-3 6]  [2 -1]]])

(deftest condensed-time-test
  (testing "Finds the time when the lights are most condensed"
    (is (= 3 (d10/condensed-time d10-s01)))))

(def d10-s01-soln
  (str/join
   "\n"
   ["#   #  ###"
    "#   #   # "
    "#   #   # "
    "#####   # "
    "#   #   # "
    "#   #   # "
    "#   #   # "
    "#   #  ###"]))

(deftest light-message-test
  (testing "Constructs the string representation of the message"
    (is (= d10-s01-soln (d10/light-message d10-s01)))))

(def day10-input (u/parse-puzzle-input d10/parse 2018 10))

(def day10-part1-soln
  (str/join
   "\n"
   [" ####   #####   #    #  #    #  ######  ######  #####   ######"
    "#    #  #    #  #    #  #   #   #            #  #    #       #"
    "#       #    #   #  #   #  #    #            #  #    #       #"
    "#       #    #   #  #   # #     #           #   #    #      # "
    "#       #####     ##    ##      #####      #    #####      #  "
    "#       #  #      ##    ##      #         #     #         #   "
    "#       #   #    #  #   # #     #        #      #        #    "
    "#       #   #    #  #   #  #    #       #       #       #     "
    "#    #  #    #  #    #  #   #   #       #       #       #     "
    " ####   #    #  #    #  #    #  ######  ######  #       ######"]))

(deftest part1-test
  (testing "Reproduces the answer for day10, part1"
    (is (= day10-part1-soln (d10/part1 day10-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day10, part2"
    (is (= 10081 (d10/part2 day10-input)))))
