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
    (is (= 892 (d22/part1 day22-input)))))