(ns aoc-clj.2023.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day12 :as t]))

(def d12-s00-raw
  ["???.### 1,1,3"
   ".??..??...?##. 1,1,3"
   "?#?#?#?#?#?#?#? 1,3,1,6"
   "????.#...#... 4,1,1"
   "????.######..#####. 1,6,5"
   "?###???????? 3,2,1"])

(def d12-s00
  [["???.###"             [1 1 3]]
   [".??..??...?##."      [1 1 3]]
   ["?#?#?#?#?#?#?#?"     [1 3 1 6]]
   ["????.#...#..."       [4 1 1]]
   ["????.######..#####." [1 6 5]]
   ["?###????????"        [3 2 1]]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d12-s00 (t/parse d12-s00-raw)))))

(deftest unfold-test
  (testing "Unfolds the values"
    (is (= [".#?.#?.#?.#?.#" [1,1,1,1,1]] (t/unfold [".#" [1]])))
    (is (= ["???.###????.###????.###????.###????.###"
            [1,1,3,1,1,3,1,1,3,1,1,3,1,1,3]]
           (t/unfold (nth d12-s00 0))))))

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

    (is (= 1 (t/num-arrangements (nth d12-s00 0))))
    (is (= 4 (t/num-arrangements (nth d12-s00 1))))
    (is (= 1 (t/num-arrangements (nth d12-s00 2))))
    (is (= 1 (t/num-arrangements (nth d12-s00 3))))
    (is (= 4 (t/num-arrangements (nth d12-s00 4))))
    (is (= 10 (t/num-arrangements (nth d12-s00 5))))

    (is (= 1      (t/num-arrangements (t/unfold (nth d12-s00 0)))))
    (is (= 16384  (t/num-arrangements (t/unfold (nth d12-s00 1)))))
    (is (= 1      (t/num-arrangements (t/unfold (nth d12-s00 2)))))
    (is (= 16     (t/num-arrangements (t/unfold (nth d12-s00 3)))))
    (is (= 2500   (t/num-arrangements (t/unfold (nth d12-s00 4)))))
    (is (= 506250 (t/num-arrangements (t/unfold (nth d12-s00 5)))))))

(deftest num-arrangements-sum-test
  (testing "Sum of all the valid arrangement counts"
    (is (= 21 (t/num-arrangements-sum d12-s00)))))

(deftest unfolded-arrangements-sum-test
  (testing "Sums all the valid arrangements with the unfolded input"
    (is (= 525152 (t/unfolded-arrangements-sum d12-s00)))))

(def day12-input (u/parse-puzzle-input t/parse 2023 12))

(deftest part1-test
  (testing "Reproduces the answer for day12, part1"
    (is (= 8193 (t/part1 day12-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day12, part2"
    (is (= 45322533163795 (t/part2 day12-input)))))