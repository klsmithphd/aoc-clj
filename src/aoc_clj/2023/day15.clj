(ns aoc-clj.2023.day15
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (str/split (first input) #","))

(defn hash-char
  "Determine the ASCII code for the current character of the string.
   Increase the current value by the ASCII code you just determined.
   Set the current value to itself multiplied by 17.
   Set the current value to the remainder of dividing itself by 256."
  [acc char]
  (-> (+ acc (int char))
      (* 17)
      (rem 256)))

(defn hash-alg
  "The HASH algorithm applies incrementally to the `chars` in a string
   starting with a current value of 0"
  [chars]
  (reduce hash-char 0 chars))

(defn hash-sum
  "The sum of applying the HASH algorithm to each of the steps
   in the initialization sequence"
  [steps]
  (reduce + (map hash-alg steps)))

(defn match-lens
  [label [lens-label _]]
  (= label lens-label))

(defn seq-add
  "If the operation character is an equals sign (=), it will be followed by a 
   number indicating the focal length of the lens that needs to go into the 
   relevant box; be sure to use the label maker to mark the lens with the 
   label given in the beginning of the step so you can find it later. 
   There are two possible situations:

   - If there is already a lens in the box with the same label, replace the old
     lens with the new lens: remove the old lens and put the new lens in its 
     place, not moving any other lenses in the box.
   - If there is not already a lens in the box with the same label, add the 
     lens to the box immediately behind any lenses already in the box. 
     Don't move any of the other lenses when you do this. 
     If there aren't any lenses in the box, the new lens goes all the way to 
     the front of the box."
  [boxes step]
  (let [[label r] (str/split step #"=")
        box-id (hash-alg label)
        lens (read-string r)
        index (u/index-of (partial match-lens label) (boxes box-id))]
    (if index
      (update boxes box-id #(assoc % index [label lens]))
      (update boxes box-id #(if (seq %) (conj % [label lens]) [[label lens]])))))

(defn vec-without
  "Returns a vector based on `v` with the single element at `index` removed"
  [v index]
  (let [[l r] (split-at index v)]
    (into [] (concat l (rest r)))))

(defn seq-remove
  "If the operation character is a dash (-), go to the relevant box and 
   remove the lens with the given label if it is present in the box. 
   Then, move any remaining lenses as far forward in the box as they can go 
   without changing their order, filling any space made by removing the 
   indicated lens. 
   (If no lens in that box has the given label, nothing happens.)"
  [boxes step]
  (let [label (subs step 0 (dec (count step)))
        box-id (hash-alg label)
        index (u/index-of (partial match-lens label) (boxes box-id))]
    (if index
      (update boxes box-id #(vec-without % index))
      boxes)))

(defn sequence-step
  "Update the contents of `boxes` based on the current `step` instruction"
  [boxes step]
  (if (some #{\=} step)
    (seq-add boxes step)
    (seq-remove boxes step)))

(defn lens-arrangement
  "Returns a map of the arrangement of the labeled lenses in each of the 
   occupied boxes, based on following the instructions in `steps`"
  [steps]
  (reduce sequence-step {} steps))

(defn box-focusing-power
  "The focusing power of a single lens is the result of multiplying together:
   - One plus the box number of the lens in question.
   - The slot number of the lens within the box: 1 for the first lens, 
     2 for the second lens, and so on.
   - The focal length of the lens."
  [[box-id lenses]]
  (* (inc box-id)
     (reduce + (map-indexed (fn [slot [_ lens]] (* (inc slot) lens)) lenses))))

(defn focusing-power
  "Adds up the focusing power of all of the lenses once arranged
   according to the `steps` in the initialization sequence"
  [steps]
  (->> (lens-arrangement steps)
       (map box-focusing-power)
       (reduce +)))

(defn part1
  "Run the HASH algorithm on each step in the initialization sequence. 
   What is the sum of the results?"
  [input]
  (hash-sum input))

(defn part2
  "With the help of an over-enthusiastic reindeer in a hard hat, follow the 
   initialization sequence. What is the focusing power of the resulting lens 
   configuration?"
  [input]
  (focusing-power input))