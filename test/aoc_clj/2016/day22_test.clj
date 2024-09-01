(ns aoc-clj.2016.day22-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day22 :as d22]))

(def d22-s00-raw
  ["root@ebhq-gridcenter# df -h"
   "Filesystem              Size  Used  Avail  Use%"
   "/dev/grid/node-x0-y0     87T   71T    16T   81%"
   "/dev/grid/node-x0-y1     93T   72T    21T   77%"
   "/dev/grid/node-x0-y2     87T   67T    20T   77%"])

(def d22-s01-raw
  ["root@ebhq-gridcenter# df -h"
   "Filesystem            Size  Used  Avail  Use%"
   "/dev/grid/node-x0-y0   10T    8T     2T   80%"
   "/dev/grid/node-x0-y1   11T    6T     5T   54%"
   "/dev/grid/node-x0-y2   32T   28T     4T   87%"
   "/dev/grid/node-x1-y0    9T    7T     2T   77%"
   "/dev/grid/node-x1-y1    8T    0T     8T    0%"
   "/dev/grid/node-x1-y2   11T    7T     4T   63%"
   "/dev/grid/node-x2-y0   10T    6T     4T   60%"
   "/dev/grid/node-x2-y1    9T    8T     1T   88%"
   "/dev/grid/node-x2-y2    9T    6T     3T   66%"])

(def d22-s00
  [{:pos [0 0] :size 87 :used 71 :avail 16 :usepct 81}
   {:pos [0 1] :size 93 :used 72 :avail 21 :usepct 77}
   {:pos [0 2] :size 87 :used 67 :avail 20 :usepct 77}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d22-s00 (d22/parse d22-s00-raw)))))

(def day22-input (u/parse-puzzle-input d22/parse 2016 22))

(deftest part1-test
  (testing "Reproduces the answer for day22, part1"
    ;; In the input data, the grid is 37 x 25 = 925 cells
    ;; There's one empty cell that things can move to.
    ;; There are 32 immovable cells that are too big to move.
    ;; 925 - 1 - 32 = 892.
    ;;
    ;; The key obversations are that there's only one empty cell at any
    ;; given time into which data can move and there are some cells that
    ;; act as barriers.
    (is (= 892 (d22/part1 day22-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day22, part2"
    (is (= 227 (d22/part2 day22-input)))))