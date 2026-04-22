(ns aoc-clj.grid.mapgrid-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.grid.interface :refer
             [width height value pos-seq val-seq slice neighbors-4 neighbors-8]]
            [aoc-clj.grid.interface :as mg :refer [->MapGrid2D]]))

;; A 2×3 grid (2 rows, 3 cols) in [row col] coordinates:
;;   row 0: 1 2 3
;;   row 1: 4 5 6
(def sample
  (->MapGrid2D 3 2 {[0 0] 1 [0 1] 2 [0 2] 3
                    [1 0] 4 [1 1] 5 [1 2] 6}))

(deftest MapGrid2D-test
  (testing "Satisfies the Grid2D protocol"
    (is (= 3 (width sample)))
    (is (= 2 (height sample)))
    (is (= 5 (value sample [1 1])))
    (is (= [[0 0] [0 1] [0 2] [1 0] [1 1] [1 2]] (pos-seq sample)))
    (is (= [1 2 3 4 5 6] (val-seq sample)))
    (is (= (->MapGrid2D 3 1 {[0 0] 1 [0 1] 2 [0 2] 3})
           (slice sample :row 0)))
    (is (= (->MapGrid2D 1 2 {[0 2] 3 [1 2] 6})
           (slice sample :col 2)))
    (is (= {[0 1] 2 [1 2] 6 [2 1] nil [1 0] 4}
           (neighbors-4 sample [1 1])))
    (is (= {[-1 0] nil [-1 1] nil [-1 2] nil
            [0 0] 1             [0 2] 3
            [1 0] 4   [1 1] 5   [1 2] 6}
           (neighbors-8 sample [0 1])))))

(deftest lists->MapGrid2D-test
  (testing "Transforms a list-of-lists into a MapGrid2D with [row col] coordinates"
    (is (= (->MapGrid2D 2 2 {[0 0] :a [0 1] :b [1 0] :c [1 1] :d})
           (mg/lists->MapGrid2D [[:a :b] [:c :d]])))))

(deftest ascii->MapGrid2D-test
  (testing "Successfully transforms ASCII art into a MapGrid2D, row 0 first"
    (is (= (->MapGrid2D
            3 2
            {[0 0] :space [0 1] :space [0 2] :wall
             [1 0] :space [1 1] :wall  [1 2] :space})
           (mg/ascii->MapGrid2D
            {\. :space \# :wall}
            ["..#"
             ".#."])))))
