(ns aoc-clj.2023.day13
  (:require [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (u/split-at-blankline input))

(defn differences
  [a b]
  (if (= a b) 0 1))

(defn mirror-test
  [idx [a b]]
  (let [size-a (count a)
        size-b (count b)]
    (if (< size-a size-b)
      [idx (reduce + (map differences
                          (apply str a)
                          (apply str (reverse (take size-a b)))))]
      [idx (reduce + (map differences
                          (apply str (drop (- size-a size-b) a))
                          (apply str (reverse b))))])))

(defn test-splits
  [rows]
  (->> (range 1 (count rows))
       (map #(split-at % rows))
       (map-indexed mirror-test)))

(defn vert-mirror
  [diffs rows]
  (->> (test-splits rows)
       (filter (comp #(= diffs %) second))
       (ffirst)))

(defn str-transpose
  [rows]
  (mapv #(apply str %) (u/transpose rows)))

(defn mirror-pos
  [diffs rows]
  (let [h-mirror (vert-mirror diffs rows)
        v-mirror (vert-mirror diffs (str-transpose rows))]
    (if (nil? v-mirror)
      {:type :horizontal :pos (inc h-mirror)}
      {:type :vertical :pos (inc v-mirror)})))

(defn summarize-math
  "To summarize your pattern notes, add up the number of columns to the left 
   of each vertical line of reflection; to that, also add 100 multiplied by 
   the number of rows above each horizontal line of reflection."
  [{:keys [type pos]}]
  (case type
    :vertical pos
    :horizontal (* 100 pos)))

(defn summarize
  "Returns the summarization sum after finding the reflection position
   that allows `diffs` values to be different across the reflection"
  [diffs input]
  (->> input
       (map (partial mirror-pos diffs))
       (map summarize-math)
       (reduce +)))

(defn day13-part1-soln
  "Find the line of reflection in each of the patterns in your notes. 
   What number do you get after summarizing all of your notes?"
  [input]
  (summarize 0 input))

(defn day13-part2-soln
  "In each pattern, fix the smudge and find the different line of reflection. 
   What number do you get after summarizing the new reflection line in each 
   pattern in your notes?"
  [input]
  (summarize 1 input))