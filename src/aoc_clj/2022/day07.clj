(ns aoc-clj.2022.day07
  "Solution to https://adventofcode.com/2022/day/7"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(def total-disk-space 70000000)
(def min-free-space   30000000)

(defn cd-command?
  [cmd]
  (str/starts-with? cmd "$ cd"))

(defn ls-command?
  [cmd]
  (= cmd "$ ls"))

(defn change-dir
  [{:keys [node cmds] :as state}]
  (let [newdir (subs (first cmds) 5)]
    (assoc state
           :node (if (= ".." newdir)
                   (pop node)
                   (conj node newdir))
           :cmds (rest cmds))))

(defn add-dir
  [{:keys [tree node cmds] :as state}]
  (let [dirname (subs (first cmds) 4)]
    (assoc state
           :tree (assoc-in tree (conj node dirname) {})
           :cmds (rest cmds))))

(defn add-file
  [{:keys [tree node cmds] :as state}]
  (let [[size name] (str/split (first cmds) #" ")]
    (assoc state
           :tree (assoc-in tree (conj node name) (read-string size))
           :cmds (rest cmds))))

(defn list-dir
  [{:keys [cmds] :as state}]
  (if (ls-command? (first cmds))
    (assoc state :cmds (rest cmds))
    (if (str/starts-with? (first cmds) "dir")
      (add-dir state)
      (add-file state))))

(defn process
  [{:keys [cmds] :as state}]
  (if (cd-command? (first cmds))
    (change-dir state)
    (list-dir state)))

(defn crawl-tree
  [terminal]
  (loop [state {:tree {} :node [] :cmds terminal}]
    (if (empty? (:cmds state))
      (:tree state)
      (recur (process state)))))

(def day07-input (crawl-tree (u/puzzle-input "2022/day07-input.txt")))

(defn node-size
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
  [tree]
  (let [size (memoize (partial node-size tree))]
    (map size (dir-paths tree))))

(defn dir-total-below-100k
  [tree]
  (->> (dir-sizes tree)
       (filter #(<= % 100000))
       (reduce +)))

(defn smallest-dir-size-to-remove
  [tree]
  (let [sizes        (dir-sizes tree)
        used         (apply max sizes)
        min-required (- min-free-space (- total-disk-space used))]
    (->> sizes
         sort
         (filter #(>= % min-required))
         first)))

(defn day07-part1-soln
  "Find all of the directories with a total size of at most 100000. 
   What is the sum of the total sizes of those directories?"
  []
  (dir-total-below-100k day07-input))

(defn day07-part2-soln
  "Find the smallest directory that, if deleted, would free up enough space 
   on the filesystem to run the update. What is the total size of that 
   directory?"
  []
  (smallest-dir-size-to-remove day07-input))
