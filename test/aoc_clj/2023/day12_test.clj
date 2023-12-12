(ns aoc-clj.2023.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2023.day12 :as t]))

(def d12-s01-raw
  ["???.### 1,1,3"
   ".??..??...?##. 1,1,3"
   "?#?#?#?#?#?#?#? 1,3,1,6"
   "????.#...#... 4,1,1"
   "????.######..#####. 1,6,5"
   "?###???????? 3,2,1"])

(def d12-s01
  [{:springs "???.###" :groups [1 1 3]}
   {:springs ".??..??...?##." :groups [1 1 3]}
   {:springs "?#?#?#?#?#?#?#?" :groups [1 3 1 6]}
   {:springs "????.#...#..." :groups [4 1 1]}
   {:springs "????.######..#####." :groups [1 6 5]}
   {:springs "?###????????" :groups [3 2 1]}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d12-s01 (t/parse d12-s01-raw)))))