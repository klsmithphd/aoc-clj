(ns aoc-clj.2017.day09
  "Solution to https://adventofcode.com/2017/day/9")

;; Input parsing
(def parse first)

;; Puzzle logic
(defn drop-garbage
  "Given a character sequence `s` starting with the first character
   inside a garbage sequence, continuously drop characters until
   reaching the end of the garbage sequence"
  [s]
  (loop [[ch & nxt] s]
    (if (= ch \>)
      nxt
      (if (= ch \!)
        (recur (rest nxt))
        (recur nxt)))))

(defn without-garbage
  "Given a character sequence `s`, return a new sequence with all
   the garbage sequences removed"
  [s]
  (if (nil? s)
    '()
    (lazy-seq
     (let [[ch & nxt] s]
       (if (= ch \<)
         (without-garbage (drop-garbage nxt))
         (cons ch (without-garbage nxt)))))))

(defn cleaned
  "Return a string with all the garbage sequences and commas removed.
   
   This string should only contain open or closed brace characters"
  [s]
  (->> (without-garbage s)
       (remove #{\,})
       (apply str)))

(defn scores
  "Compute the depth score for each open-close brace pair
   
   Returns in depth-first order."
  [s]
  (loop [depths [] depth 0 chars (cleaned s)]
    (if-not (seq chars)
      depths
      (if (= \} (first chars))
        (recur (conj depths depth) (dec depth) (rest chars))
        (recur depths (inc depth) (rest chars))))))

(defn total-score
  "Compute the total depth score for the string"
  [s]
  (reduce + (scores s)))

;; Puzzle solutions
(defn part1
  "What is the total score for all the groups in your input?"
  [input]
  (total-score input))