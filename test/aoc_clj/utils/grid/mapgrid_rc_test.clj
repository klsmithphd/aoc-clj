(ns aoc-clj.utils.grid.mapgrid-rc-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.grid.core :refer
             [width height value pos-seq val-seq slice neighbors-4 neighbors-8]]
            [aoc-clj.utils.grid.mapgrid-rc :as mg :refer [->MapGridRC]]))

;; A 2×3 grid (2 rows, 3 cols) in [row col] coordinates:
;;   row 0: 1 2 3
;;   row 1: 4 5 6
(def sample
  (->MapGridRC 3 2 {[0 0] 1 [0 1] 2 [0 2] 3
                    [1 0] 4 [1 1] 5 [1 2] 6}))

(deftest MapGridRC-test
  (testing "Satisfies the GridRC protocol"
    (is (= 3 (width sample)))
    (is (= 2 (height sample)))
    (is (= 5 (value sample [1 1])))
    (is (= [[0 0] [0 1] [0 2] [1 0] [1 1] [1 2]] (pos-seq sample)))
    (is (= [1 2 3 4 5 6] (val-seq sample)))
    (is (= (->MapGridRC 3 1 {[0 0] 1 [0 1] 2 [0 2] 3})
           (slice sample :row 0)))
    (is (= (->MapGridRC 1 2 {[0 2] 3 [1 2] 6})
           (slice sample :col 2)))
    (is (= {[0 1] 2 [1 2] 6 [2 1] nil [1 0] 4}
           (neighbors-4 sample [1 1])))
    (is (= {[-1 0] nil [-1 1] nil [-1 2] nil
            [0 0] 1             [0 2] 3
            [1 0] 4   [1 1] 5   [1 2] 6}
           (neighbors-8 sample [0 1])))))

(deftest lists->MapGridRC-test
  (testing "Transforms a list-of-lists into a MapGridRC with [row col] coordinates"
    (is (= (->MapGridRC 2 2 {[0 0] :a [0 1] :b [1 0] :c [1 1] :d})
           (mg/lists->MapGridRC [[:a :b] [:c :d]])))))

(deftest ascii->MapGridRC-test
  (testing "Successfully transforms ASCII art into a MapGridRC, row 0 first"
    (is (= (->MapGridRC
            3 2
            {[0 0] :space [0 1] :space [0 2] :wall
             [1 0] :space [1 1] :wall  [1 2] :space})
           (mg/ascii->MapGridRC
            {\. :space \# :wall}
            ["..#"
             ".#."])))))
