(ns aoc-clj.utils.intervals-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.intervals :as ivs]))

(deftest fully-contained-test
  (testing "Finds the pairs where one is fully contained in the other"
    (is (false? (ivs/fully-contained? [2 4] [6 8])))
    (is (false? (ivs/fully-contained? [2 3] [4 5])))
    (is (false? (ivs/fully-contained? [5 7] [7 9])))
    (is (true?  (ivs/fully-contained? [2 8] [3 7])))
    (is (true?  (ivs/fully-contained? [6 6] [4 6])))
    (is (false? (ivs/fully-contained? [2 6] [4 8])))))

(deftest overlap-test
  (testing "Finds the pairs where one overlaps with the other"
    (is (false? (ivs/overlap? [2 4] [6 8])))
    (is (false? (ivs/overlap? [2 3] [4 5])))
    (is (true?  (ivs/overlap? [5 7] [7 9])))
    (is (true?  (ivs/overlap? [2 8] [3 7])))
    (is (true?  (ivs/overlap? [6 6] [4 6])))
    (is (true?  (ivs/overlap? [2 6] [4 8])))))