(ns aoc-clj.2016.day21
  "Solution to https://adventofcode.com/2016/day/21"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

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
                 "left"  ["rotate-left" [(read-string w2)]]
                 "right" ["rotate-right" [(read-string w2)]]
                 "based" [w0 [w6]]))))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn find-pos
  [s lt-s]
  (u/index-of (u/equals? (first lt-s)) s))

(defn do-swap-positions
  [s [_ args]]
  (let [[p1 p2] (sort args)]
    (str/join (concat (subs s 0 p1)
                      (subs s p2 (inc p2))
                      (subs s (inc p1) p2)
                      (subs s p1 (inc p1))
                      (subs s (inc p2))))))


(defn do-swap-letters
  [s [_ [l1 l2]]]
  (do-swap-positions s ["swap-positions" [(find-pos s l1)
                                          (find-pos s l2)]]))

(defn do-reverse
  [s [_ args]]
  (let [[p1 p2] (sort args)]
    (str/join (concat (subs s 0 p1)
                      (str/reverse (subs s p1 (inc p2)))
                      (subs s (inc p2))))))

(defn insert
  [s pos char]
  (str/join (concat (subs s 0 pos)
                    char
                    (subs s pos))))

(defn do-move
  [s [_ [p1 p2]]]
  (insert (str/join (concat (subs s 0 p1)
                            (subs s (inc p1))))
          p2
          (subs s p1 (inc p1))))

(defn do-rotate-l
  [s [_ [amt]]]
  (str/join (u/rotate amt s)))

(defn do-rotate-r
  [s [_ [amt]]]
  (str/join (u/rotate (- amt) s)))

(defn do-rotate
  [s [_ [lt]]]
  (let [pos (find-pos s lt)]
    (do-rotate-r s ["rotate-right" [(+ (inc pos) (if (>= pos 4) 1 0))]])))

(defn scramble
  [s [cmd :as inst]]
  (case cmd
    "swap-positions" (do-swap-positions s inst)
    "swap-letters"   (do-swap-letters s inst)
    "move"           (do-move s inst)
    "reverse"        (do-reverse s inst)
    "rotate"         (do-rotate s inst)
    "rotate-left"    (do-rotate-l s inst)
    "rotate-right"   (do-rotate-r s inst)))


;; Puzzle solutions
(defn part1
  [input]
  (reduce scramble "abcdefgh" input))
