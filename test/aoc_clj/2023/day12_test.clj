(ns aoc-clj.2023.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day12 :as t]))

(def d12-s01-raw
  ["???.### 1,1,3"
   ".??..??...?##. 1,1,3"
   "?#?#?#?#?#?#?#? 1,3,1,6"
   "????.#...#... 4,1,1"
   "????.######..#####. 1,6,5"
   "?###???????? 3,2,1"])

(def d12-s01
  [["???.###"             [1 1 3]]
   [".??..??...?##."      [1 1 3]]
   ["?#?#?#?#?#?#?#?"     [1 3 1 6]]
   ["????.#...#..."       [4 1 1]]
   ["????.######..#####." [1 6 5]]
   ["?###????????"        [3 2 1]]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d12-s01 (t/parse d12-s01-raw)))))

(deftest num-arrangements-test
  (testing "Counts the number of possible arrangements that satisfy
            the group conditions"
    (is (= 0 (t/num-arrangements ["" [1]])))
    (is (= 1 (t/num-arrangements ["" []])))
    (is (= 1 (t/num-arrangements ["?" [1]])))
    (is (= 1 (t/num-arrangements [".?" [1]])))
    (is (= 1 (t/num-arrangements [".?....." [1]])))
    (is (= 1 (t/num-arrangements [".#" [1]])))
    (is (= 2 (t/num-arrangements ["??" [1]])))
    (is (= 0 (t/num-arrangements ["##?#?#?#?" [1 6]])))

    (is (= 1 (t/num-arrangements (nth d12-s01 0))))
    (is (= 4 (t/num-arrangements (nth d12-s01 1))))
    (is (= 1 (t/num-arrangements (nth d12-s01 2))))
    (is (= 1 (t/num-arrangements (nth d12-s01 3))))
    (is (= 4 (t/num-arrangements (nth d12-s01 4))))
    (is (= 10 (t/num-arrangements (nth d12-s01 5))))))

(deftest num-arrangements-sum-test
  (testing "Sum of all the valid arrangement counts"
    (is (= 21 (t/num-arrangements-sum d12-s01)))))

(def day12-input (u/parse-puzzle-input t/parse 2023 12))

(deftest day12-part1-soln
  (testing "Reproduces the answer for day12, part1"
    (is (= 1 (t/day12-part1-soln day12-input)))))
