(ns aoc-clj.2023.day15)

(defn hash-char
  [acc char]
  (-> (+ acc (int char))
      (* 17)
      (rem 256)))

(defn hash-alg
  [chars]
  (reduce hash-char 0 chars))