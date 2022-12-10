(ns aoc-clj.2022.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day07 :as t]))

(def d07-s01
  ["$ cd /"
   "$ ls"
   "dir a"
   "14848514 b.txt"
   "8504156 c.dat"
   "dir d"
   "$ cd a"
   "$ ls"
   "dir e"
   "29116 f"
   "2557 g"
   "62596 h.lst"
   "$ cd e"
   "$ ls"
   "584 i"
   "$ cd .."
   "$ cd .."
   "$ cd d"
   "$ ls"
   "4060174 j"
   "8033020 d.log"
   "5626152 d.ext"
   "7214296 k"])

(def d07-s01-parsed
  {"/"
   {"a"
    {"e"
     {"i" 584}
     "f" 29116
     "g" 2557
     "h.lst" 62596}
    "b.txt" 14848514
    "c.dat" 8504156
    "d"
    {"j" 4060174
     "d.log" 8033020
     "d.ext" 5626152
     "k" 7214296}}})

(deftest crawl-tree-test
  (testing "Parses the terminal commands to construct the directory tree"
    (is (= d07-s01-parsed (t/crawl-tree d07-s01)))))


;; (deftest day07-part1-soln
;;   (testing "Reproduces the answer for day07, part1"
;;     (is (= 0 (t/day07-part1-soln)))))

;; (deftest day07-part2-soln
;;   (testing "Reproduces the answer for day07, part2"
;;     (is (= 0 (t/day07-part2-soln)))))