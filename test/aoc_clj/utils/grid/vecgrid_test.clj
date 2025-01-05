(ns aoc-clj.utils.grid.vecgrid-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.grid :refer
             [width height value pos-seq val-seq slice neighbors-4 neighbors-8]]
            [aoc-clj.utils.grid.vecgrid :as vg :refer [->VecGrid2D]]))

(def sample
  (->VecGrid2D [[1 2 3]
                [4 5 6]]))

(def sample-grid
  [[3 1 4 1 5 9]
   [2 6 5 3 5 8]
   [9 7 9 3 2 3]
   [8 4 6 2 6 4]
   [3 3 8 3 2 7]
   [9 5 0 2 8 8]])

(def summed-area-table-sample
  [[3   4  8   9  14 23]
   [5  12 21  25  35 52]
   [14 28 46  53  65 85]
   [22 40 64  73  91 115]
   [25 46 78  90 110 141]
   [34 60 92 106 134 173]])

(deftest VecGrid2D-test
  (testing "Satisifies the Grid2D protocol"
    (is (= 3 (width sample)))
    (is (= 2 (height sample)))
    (is (= 5 (value sample [1 1])))
    (is (= [[0 0] [1 0] [2 0] [0 1] [1 1] [2 1]] (pos-seq sample)))
    (is (= [1 2 3 4 5 6] (val-seq sample)))
    (is (= (->VecGrid2D [[4 5 6]])
           (slice sample :row 1)))
    (is (= (->VecGrid2D [[3] [6]])
           (slice sample :col 2)))
    (is (= {[1 0] 2 [0 1] 4 [1 2] nil [2 1] 6}
           (neighbors-4 sample [1 1])))
    (is (= {[0 -1] nil [1 -1] nil [2 -1] nil
            [0 0] 1               [2 0] 3
            [0 1] 4    [1 1] 5    [2 1] 6}
           (neighbors-8 sample [1 0])))))

(deftest ascii->VecGrid2D-test
  (testing "Successfully transforms ASCII art into a VecGrid2D"
    (is (= (->VecGrid2D
            [[:space :space :wall]
             [:space :wall  :space]])
           (vg/ascii->VecGrid2D
            {\. :space \# :wall}
            ["..#"
             ".#."]
            :down true)))))

(deftest summed-area-table-test
  (testing "Constructs a summed-area table for the input values"
    (is (= summed-area-table-sample (vg/summed-area-table sample-grid)))))

(deftest area-sum-test
  (testing "Computes the sum of the values included in an area
            using a summed area table"
    (is (= 27 (vg/area-sum summed-area-table-sample [2 3] [4 4])))))

(deftest square-area-sum-test
  (testing "Computes the enclosed sum for square regions"
    (is (= 46 (vg/square-area-sum summed-area-table-sample [0 0 3])))
    (is (= 39 (vg/square-area-sum summed-area-table-sample [1 0 3])))
    (is (= 37 (vg/square-area-sum summed-area-table-sample [2 0 3])))
    (is (= 39 (vg/square-area-sum summed-area-table-sample [3 0 3])))

    (is (= 45 (vg/square-area-sum summed-area-table-sample [1 1 3])))))