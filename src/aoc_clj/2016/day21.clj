(ns aoc-clj.2016.day21
  "Solution to https://adventofcode.com/2016/day/21")

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
                 "based" {:cmd w0 :let w6}))))

(defn parse
  [input]
  (map parse-line input))

