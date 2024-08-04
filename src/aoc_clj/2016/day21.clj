(ns aoc-clj.2016.day21
  "Solution to https://adventofcode.com/2016/day/21"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-line
  [line]
  (let [[w0 w1 w2 _ w4 w5 w6] (re-seq #"\w+" line)]
    (case w0
      "move"    {:cmd w0 :p1 (read-string w2) :p2 (read-string w5)}
      "reverse" {:cmd w0 :p1 (read-string w2) :p2 (read-string w4)}
      "swap"    (if (= w1 "position")
                  {:cmd (str w0 "-pos") :p1 (read-string w2) :p2 (read-string w5)}
                  {:cmd (str w0 "-let") :l1 w2 :l2 w5})
      "rotate" (case w1
                 "left"  {:cmd (str w0 "-l") :amt (read-string w2)}
                 "right" {:cmd (str w0 "-r") :amt (read-string w2)}
                 "based" {:cmd w0 :lt w6}))))

(defn parse
  [input]
  (map parse-line input))


;; Puzzle logic
(defn do-swap-pos
  [s inst]
  (let [[p1 p2] (sort [(:p1 inst) (:p2 inst)])]
    (str/join (concat (subs s 0 p1)
                      (subs s p2 (inc p2))
                      (subs s (inc p1) p2)
                      (subs s p1 (inc p1))
                      (subs s (inc p2))))))

(defn do-swap-let
  [s {:keys [l1 l2]}]
  (do-swap-pos s {:p1 (u/index-of (u/equals? (first l1)) s)
                  :p2 (u/index-of (u/equals? (first l2)) s)}))

(defn do-reverse
  [s inst]
  (let [[p1 p2] (sort [(:p1 inst) (:p2 inst)])]
    (str/join (concat (subs s 0 p1)
                      (str/reverse (subs s p1 (inc p2)))
                      (subs s (inc p2))))))

(defn insert
  [s pos char]
  (str/join (concat (subs s 0 pos)
                    char
                    (subs s pos))))

(defn do-move
  [s {:keys [p1 p2]}]
  (insert (str/join (concat (subs s 0 p1)
                            (subs s (inc p1))))
          p2
          (subs s p1 (inc p1))))

(defn do-rotate-l
  [s {:keys [amt]}]
  (str/join (u/rotate amt s)))

(defn do-rotate-r
  [s {:keys [amt]}]
  (str/join (u/rotate (- amt) s)))

(defn do-rotate
  [s {:keys [lt]}]
  (let [pos (u/index-of (u/equals? (first lt)) s)]
    (do-rotate-r s {:amt (+ (inc pos) (if (>= pos 4) 1 0))})))

(defn scramble
  [s {:keys [cmd] :as inst}]
  (case cmd
    "swap-pos" (do-swap-pos s inst)
    "swap-let" (do-swap-let s inst)
    "move"     (do-move s inst)
    "reverse"  (do-reverse s inst)
    "rotate"   (do-rotate s inst)
    "rotate-l" (do-rotate-l s inst)
    "rotate-r" (do-rotate-r s inst)))


;; Puzzle solutions
(defn part1
  [input]
  (reduce scramble "abcdefgh" input))
