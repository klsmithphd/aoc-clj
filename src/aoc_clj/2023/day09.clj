(ns aoc-clj.2023.day09)

(defn parse
  [input]
  (map #(read-string (str "[" % "]")) input))

(defn differences
  "Compute the difference between every consecutive pair of values"
  [nums]
  (mapv #(apply - (reverse %)) (partition 2 1 nums)))

(defn right-extrapolate
  "Extrapolate a new number at the end of the sequence"
  [nums]
  (if (every? zero? nums)
    nums
    (let [diffs  (differences nums)
          to-add (last (right-extrapolate diffs))]
      (into nums [(+ (last nums) to-add)]))))

(defn left-extrapolate
  "Extrapolate a new number at the beginning of the sequence"
  [nums]
  (if (every? zero? nums)
    nums
    (let [diffs  (differences nums)
          to-add (first (left-extrapolate diffs))]
      (into [(- (first nums) to-add)] nums))))

(defn extrapolation-sum
  "Compute the sum of all of the new extrapolated values.
   By default, extrapolates new values at the end of each sequence in `input`
   but specifing `reverse? = true` will return the sum of values extrapolated
   at the beginning."
  ([input]
   (extrapolation-sum input false))
  ([input reverse?]
   (let [added (if reverse?
                 (map (comp first left-extrapolate) input)
                 (map (comp last right-extrapolate) input))]
     (reduce + added))))

(defn part1
  "Analyze your OASIS report and extrapolate the next value for each history.
   What is the sum of these extrapolated values?"
  [input]
  (extrapolation-sum input))

(defn part2
  "Analyze your OASIS report again, this time extrapolating the previous value
   for each history. What is the sum of these extrapolated values?"
  [input]
  (extrapolation-sum input true))