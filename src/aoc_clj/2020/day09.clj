(ns aoc-clj.2020.day09
  "Solution to https://adventofcode.com/2020/day/9")

(defn parse
  [input]
  (map read-string input))

(defn first-non-sum
  [nums window]
  (loop [pos window
         val (nth nums window)
         prev (take window nums)]
    (let [candidates (map (partial - val) prev)]
      (if (empty? (filter (set prev) candidates))
        val
        (recur (inc pos)
               (nth nums (inc pos))
               (take window (drop (- (inc pos) window) nums)))))))

(defn contiguous-range-to-sum
  [nums target-sum]
  (loop [left 0 right 1]
    (let [the-range (take (- right left) (drop left nums))
          range-sum (reduce + the-range)]
      (if (= target-sum range-sum)
        [(apply min the-range) (apply max the-range)]
        (if (< range-sum target-sum)
          (recur left (inc right))
          (recur (inc left) right))))))

(defn part1
  [input]
  (first-non-sum input 25))

(defn part2
  [input]
  (reduce + (contiguous-range-to-sum input (part1 input))))
