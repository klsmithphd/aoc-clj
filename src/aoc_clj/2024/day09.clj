(ns aoc-clj.2024.day09
  "Solution to https://adventofcode.com/2024/day/9")

;; Input parsing
(defn parse
  [input]
  (mapv (comp read-string str) (first input)))

;; Puzzle logic
(defn data-block
  [coll]
  (map-indexed vector coll))

(defn blocks
  [coll]
  {:blocks (into {} (data-block (take-nth 2 coll)))
   :gaps   (take-nth 2 (rest coll))})

(defn block
  [id blocks]
  (repeat (blocks id) id))

(defn end-blocks
  [blocks]
  (let [max-id (apply max (keys blocks))]
    (mapcat #(block % blocks) (range max-id -1 -1))))

(defn fills
  [gaps coll]
  (loop [gps gaps xs coll return []]
    (if (nil? gps)
      return
      (let [size (first gps)]
        (recur
         (next gps)
         (drop size xs)
         (conj return (take size xs)))))))

(defn compacted
  [{:keys [blocks gaps]}]
  (let [size (reduce + (vals blocks))]
    (->> (interleave (map #(block % blocks) (range))
                     (fills gaps (end-blocks blocks)))
         flatten
         (take size))))

(defn checksum
  [coll]
  (->> (map-indexed * coll)
       (reduce +)))

;; Puzzle solutions
(defn part1
  [input]
  (checksum (compacted (blocks input))))