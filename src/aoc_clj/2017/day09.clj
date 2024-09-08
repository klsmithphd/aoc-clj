(ns aoc-clj.2017.day09
  "Solution to https://adventofcode.com/2017/day/9")

;; Input parsing
(def parse first)

;; Puzzle logic
(defn drop-garbage
  [s]
  (loop [[ch & nxt] s]
    (if (= ch \>)
      nxt
      (if (= ch \!)
        (recur (rest nxt))
        (recur nxt)))))

(defn without-garbage
  [s]
  (if (nil? s)
    '()
    (lazy-seq
     (let [[ch & nxt] s]
       (if (= ch \<)
         (without-garbage (drop-garbage nxt))
         (cons ch (without-garbage nxt)))))))

(defn cleaned
  [s]
  (remove #{\,} (without-garbage s)))

(defn scores
  [s]
  (loop [depths [] depth 0 chars (cleaned s)]
    (if-not (seq chars)
      depths
      (if (= \} (first chars))
        (recur (conj depths depth) (dec depth) (rest chars))
        (recur depths (inc depth) (rest chars))))))

(defn total-score
  [s]
  (reduce + (scores s)))

;; Puzzle solutions
(defn part1
  [input]
  (total-score input))