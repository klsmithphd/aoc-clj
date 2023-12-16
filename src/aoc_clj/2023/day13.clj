(ns aoc-clj.2023.day13
  (:require [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (u/split-at-blankline input))

(defn differences
  "Returns 0 if `a` and `b` are equal, otherwise 1"
  [a b]
  (if (= a b) 0 1))

(defn trim-and-reflect
  "Takes the `top` and `bot` collections of rows, trims the larger
   collection to the size of the smaller one, and then reflects 
   one of them. Returns a vector of two concatenated strings
   that can then be compared for differences."
  [top bot]
  (let [size-top (count top)
        size-bot (count bot)]
    (if (< size-top size-bot)
      [(apply str top)
       (apply str (reverse (take size-top bot)))]

      [(apply str (take size-bot (reverse top)))
       (apply str bot)])))

(defn mirror-differences
  "Computes the number of differences in characters between the 
   top and bottom if they're treated as reflections of each other"
  [[top bot]]
  (let [[str-a str-b] (trim-and-reflect top bot)]
    (reduce + (map differences str-a str-b))))

(defn split-differences
  "For a given set of `rows`, computes the mirror differences for 
   all of the possible split points"
  [rows]
  (->> (range 1 (count rows))
       (map #(split-at % rows))
       (map mirror-differences)))

(defn reflection-row
  "Returns the position of the row where the reflection condition holds, 
   i.e. where the number of differences between rows and their reflections
   matches `diffs`"
  [diffs rows]
  (u/index-of (u/equals? diffs) (split-differences rows)))

(defn mirror-pos
  "Returns the type and position of the reflection line based on
   how many differences are allowed to exist between the reflections"
  [diffs rows]
  (let [h-mirror (reflection-row diffs rows)
        ;; For simplicity, reuse the row logic on the transposed rows 
        ;; to find vertical reflection lines
        v-mirror (reflection-row diffs (u/str-transpose rows))]
    (if (nil? v-mirror)
      {:type :horizontal :pos (inc h-mirror)}
      {:type :vertical   :pos (inc v-mirror)})))

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