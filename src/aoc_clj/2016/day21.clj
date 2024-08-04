(ns aoc-clj.2016.day21
  "Solution to https://adventofcode.com/2016/day/21"
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.core :as u]))

;; Constants
(def part1-str "abcdefgh")
(def part2-str "fbgdceah")

;; Input parsing
(defn parse-line
  [line]
  (let [[w0 w1 w2 _ w4 w5 w6] (re-seq #"\w+" line)]
    (case w0
      "move"    [w0 (mapv read-string [w2 w5])]
      "reverse" [w0 (mapv read-string [w2 w4])]
      "swap"    (if (= w1 "position")
                  ["swap-positions" (mapv read-string [w2 w5])]
                  ["swap-letters"   [w2 w5]])
      "rotate" (case w1
                 "left"  ["rotate-left" (read-string w2)]
                 "right" ["rotate-right" (read-string w2)]
                 "based" [w0 w6]))))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn find-pos
  [s lt-s]
  (u/index-of (u/equals? (first lt-s)) s))

(defn do-swap-positions
  [s args]
  (let [[p1 p2] (sort args)]
    (str/join (concat (subs s 0 p1)
                      (subs s p2 (inc p2))
                      (subs s (inc p1) p2)
                      (subs s p1 (inc p1))
                      (subs s (inc p2))))))


(defn do-swap-letters
  [s [l1 l2]]
  (do-swap-positions s [(find-pos s l1) (find-pos s l2)]))

(defn do-reverse
  [s args]
  (let [[p1 p2] (sort args)]
    (str/join (concat (subs s 0 p1)
                      (str/reverse (subs s p1 (inc p2)))
                      (subs s (inc p2))))))

(defn ch-insert
  [s pos char]
  (str/join (concat (subs s 0 pos)
                    char
                    (subs s pos))))

(defn ch-remove
  [s pos]
  (str/join (concat (subs s 0 pos)
                    (subs s (inc pos)))))

(defn do-move
  [s [p1 p2]]
  (ch-insert (ch-remove s p1) p2 (subs s p1 (inc p1))))

(defn do-rotate-l
  [s amt]
  (str/join (u/rotate amt s)))

(defn do-rotate-r
  [s amt]
  (str/join (u/rotate (- amt) s)))

(defn do-rotate
  [s lt]
  (let [pos (find-pos s lt)
        amt (+ (inc pos) (if (>= pos 4) 1 0))]
    (do-rotate-r s amt)))

(defn scramble-step
  [s [cmd args]]
  (case cmd
    "swap-positions" (do-swap-positions s args)
    "swap-letters"   (do-swap-letters s args)
    "move"           (do-move s args)
    "reverse"        (do-reverse s args)
    "rotate"         (do-rotate s args)
    "rotate-left"    (do-rotate-l s args)
    "rotate-right"   (do-rotate-r s args)))

(defn scramble
  [s insts]
  (reduce scramble-step s insts))

(defn unscramble
  [s insts]
  (->> (combo/permutations s)
       (map str/join)
       (filter #(= s (scramble % insts)))
       first))

;; Puzzle solutions
(defn part1
  [input]
  (scramble part1-str input))

(defn part2
  [input]
  (unscramble part2-str input))