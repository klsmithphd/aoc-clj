(ns aoc-clj.2018.day01
  "Solution to https://adventofcode.com/2018/day/1")

(defn parse
  [input]
  (map read-string input))

(defn net-freq-change
  "Sums all the changes in frequency, starting with zero"
  [deltas]
  (reduce + deltas))

(defn find-first-repeated-freq
  "Find the first net frequency that repeats"
  [deltas]
  (loop [freqs    (reductions + (cycle deltas))
         observed #{0}]
    (let [freq (first freqs)]
      (if (contains? observed freq)
        freq
        (recur (rest freqs) (conj observed freq))))))

(defn day01-part1-soln
  [input]
  (net-freq-change input))

(defn day01-part2-soln
  [input]
  (find-first-repeated-freq input))