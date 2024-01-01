(ns aoc-clj.2022.day07
  "Solution to https://adventofcode.com/2022/day/7"
  (:require [clojure.string :as str]))

;;;; Constants

(def total-disk-space
  "The total disk space available to the filesystem is 70000000"
  70000000)

(def min-free-space
  "To run the update, you need unused space of at least 30000000"
  30000000)

;;;; Input Parsing

(defn cd-command?
  "Is this `cmd` a cd (change directory) command?"
  [cmd]
  (str/starts-with? cmd "$ cd"))

(defn ls-command?
  "Is this `cmd` a ls (list) command?"
  [cmd]
  (= cmd "$ ls"))

(defn change-dir
  "Process a cd command to descend into a subdirectory or return to 
   the parent directory"
  [{:keys [node cmds] :as state}]
  (let [newdir (subs (first cmds) 5)]
    (assoc state
           :node (if (= ".." newdir)
                   (pop node)
                   (conj node newdir))
           :cmds (rest cmds))))

(defn add-dir
  "Add a new directory into the file hierarchy"
  [{:keys [tree node cmds] :as state}]
  (let [dirname (subs (first cmds) 4)]
    (assoc state
           :tree (assoc-in tree (conj node dirname) {})
           :cmds (rest cmds))))

(defn add-file
  "Add a new file into the file hierarchy"
  [{:keys [tree node cmds] :as state}]
  (let [[size name] (str/split (first cmds) #" ")]
    (assoc state
           :tree (assoc-in tree (conj node name) (read-string size))
           :cmds (rest cmds))))

(defn list-dir
  "Process a ls command. Subsequent lines until the next command
   must be files or directories"
  [{:keys [cmds] :as state}]
  (if (ls-command? (first cmds))
    (assoc state :cmds (rest cmds))
    (if (str/starts-with? (first cmds) "dir")
      (add-dir state)
      (add-file state))))

(defn process
  "Process each line of the terminal output"
  [{:keys [cmds] :as state}]
  (if (cd-command? (first cmds))
    (change-dir state)
    (list-dir state)))

(defn parse
  "Parse the day07 input"
  [input]
  (loop [state {:tree {} :node [] :cmds input}]
    (if (empty? (:cmds state))
      (:tree state)
      (recur (process state)))))

;;;; Puzzle logic

(defn node-size
  "Find the size of a given node identified by its `path`.
   For a file, the file's size is returned.
   For a directory, the total of all files, directly or indirectly contained
   in the directory is returned."
  [tree path]
  (let [contents (get-in tree path)]
    (if (number? contents)
      contents
      (reduce + (map #(node-size tree (conj path %)) (keys contents))))))

(defn dir-paths
  "Finds the path to all of the directory nodes in map `m`"
  [m]
  (letfn [(children [node]
            (let [v (get-in m node)]
              (if (map? v)
                (remove #(number? (get-in m %))
                        (map (fn [x] (conj node x)) (keys v)))
                [])))
          (branch? [node] (-> (children node) seq boolean))]
    (->> (keys m)
         (map vector)
         (mapcat #(tree-seq branch? children %)))))

(defn dir-sizes
  "Return the sizes of all directories in the tree"
  [tree]
  (let [size (memoize (partial node-size tree))]
    (map size (dir-paths tree))))

(defn dir-total-below-100k
  "Return the sum of the sizes of directories no larger than 100000"
  [tree]
  (->> (dir-sizes tree)
       (filter #(<= % 100000))
       (reduce +)))

(defn smallest-dir-size-to-remove
  "Find the size of the smallest directory to remove that frees up enough space"
  [tree]
  (let [sizes        (dir-sizes tree)
        used         (apply max sizes)
        min-required (- min-free-space (- total-disk-space used))]
    (->> sizes
         sort
         (filter #(>= % min-required))
         first)))

;;;; Puzzle solutions

(defn day07-part1-soln
  "Find all of the directories with a total size of at most 100000. 
   What is the sum of the total sizes of those directories?"
  [input]
  (dir-total-below-100k input))

(defn day07-part2-soln
  "Find the smallest directory that, if deleted, would free up enough space 
   on the filesystem to run the update. What is the total size of that 
   directory?"
  [input]
  (smallest-dir-size-to-remove input))
