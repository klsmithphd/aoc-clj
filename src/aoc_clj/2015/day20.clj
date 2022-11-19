(ns aoc-clj.2015.day20)

(def day20-input 33100000)

(defn house-presents
  [house]
  (let [factors (->> (range 1 (inc house))
                     (filter #(zero? (rem house %))))]
    (* 10 (reduce + factors))))

(defn first-house-with-n-presents
  [present-logic start input]
  (ffirst (filter #(>= (second %) input) (map #(vector % (present-logic %)) (range start input)))))

(defn house-presents-part2
  [house]
  (let [start   (int (Math/ceil (/ house 50)))
        factors (->> (range (if (zero? start) 1 start) (inc house))
                     (filter #(zero? (rem house %))))]
    (* 11 (reduce + factors))))

(defn day20-part1-soln
  []
  (first-house-with-n-presents house-presents 776159 day20-input))

(defn day20-part2-soln
  []
  (first-house-with-n-presents house-presents-part2 786239 day20-input))