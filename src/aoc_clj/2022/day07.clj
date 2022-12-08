(ns aoc-clj.2022.day07
  "Solution to https://adventofcode.com/2022/day/7"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

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

[["/"] ["a" {} "b.txt" 14848514 "c.dat" 8504156 "d" {}]
 ["/" "a"] ["e" {} "f"]]

(update-in
 (update-in {} [:a :b :c] #(assoc % :d 2557))
 [:a :b] #(assoc % :e {}))

(def day07-input (u/puzzle-input "2022/day07-input.txt"))

(defn change-dir
  [tree node cmds]
  (let [line (first cmds)
        newdir (subs line 5)])
  [tree])

(defn process
  [tree node cmds]
  (let [line (first cmds)]
    (if (str/starts-with? line "$ cd")
      (change-dir tree node cmds)
      (list-dir tree node cmds))))

(defn crawl-tree
  [terminal]
  (loop [tree {} node nil cmds terminal]
    (if (empty? cmds)
      tree
      (let [[t n c] (process tree node cmds)]
        (recur t n c)))))