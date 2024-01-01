(ns aoc-clj.2022.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day07 :as t]))

(def d07-s00-raw
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

(def d07-s00
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

(deftest parse-test
  (testing "Parses the terminal commands to construct the directory tree"
    (is (= d07-s00 (t/parse d07-s00-raw)))))

(deftest node-size-test
  (testing "Returns the size of the node in the directory tree"
    (is (= 584 (t/node-size d07-s00 ["/" "a" "e"])))
    (is (= 94853 (t/node-size d07-s00 ["/" "a"])))
    (is (= 24933642 (t/node-size d07-s00 ["/" "d"])))
    (is (= 48381165 (t/node-size d07-s00 ["/"])))))

(deftest dir-path-test
  (testing "Returns the paths to all the directory nodes"
    (is (= [["/"] ["/" "a"] ["/" "a" "e"] ["/" "d"]]
           (t/dir-paths d07-s00)))))

(deftest dir-total-below-100k-test
  (testing "Finds the sum of the sizes of directories smaller than 100k"
    (is (= 95437 (t/dir-total-below-100k d07-s00)))))

(deftest smallest-dir-size-to-remove-test
  (testing "Find the size of the smallest directory that can be removed
            to free up the necessary disk space"
    (is (= 24933642 (t/smallest-dir-size-to-remove d07-s00)))))

(def day07-input (u/parse-puzzle-input t/parse 2022 7))

(deftest day07-part1-soln
  (testing "Reproduces the answer for day07, part1"
    (is (= 1306611 (t/day07-part1-soln day07-input)))))

(deftest day07-part2-soln
  (testing "Reproduces the answer for day07, part2"
    (is (= 13210366 (t/day07-part2-soln day07-input)))))