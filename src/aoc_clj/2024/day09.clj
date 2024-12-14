(ns aoc-clj.2024.day09
  "Solution to https://adventofcode.com/2024/day/9")

;; Input parsing
(defn block
  [idx v type]
  {:pos idx
   :data (case type
           :file (vec (repeat v (quot idx 2)))
           :free [])
   :space (case type
            :file 0
            :free v)})

(defn blocks
  [coll]
  (->> (map block (range) coll (cycle [:file :free]))
       vec))

(defn parse
  [input]
  (->> (first input)
       (map (comp read-string str))
       blocks))

;; Puzzle logic
(defn end-data
  "Returns a lazy collection of the data elements taken from the end
   and iterating forward"
  [blocks]
  (mapcat :data (reverse blocks)))

(defn parition-by-sizes
  "Partitions the data elements from `coll` into chunks sized by the elements
   of `sizes`"
  [sizes coll]
  (loop [szs sizes xs coll return []]
    (if (nil? szs)
      return
      (let [size (first szs)]
        (recur
         (next szs)
         (drop size xs)
         (conj return (take size xs)))))))

(defn single-datum-compacted
  "Returns the data elements in order after compacting the data by moving
   each individual element from the end into the earliest available gap"
  [blocks]
  (let [size      (reduce + (map #(count (:data %)) blocks))
        data-blks (take-nth 2 blocks)
        gap-blks  (take-nth 2 (rest blocks))]
    (->> (interleave (map :data data-blks)
                     (parition-by-sizes (map :space gap-blks) (end-data blocks)))
         flatten
         (take size))))

(defn checksum
  "Computes the checksum, which is the sum of each element's position
   by the value of the data held at that position"
  [coll]
  (->> (map-indexed * coll)
       (reduce +)))


;; This needs to be updated to move just the original
;; block, not any subsequent bits that end up there.
;;
;; See the puzzle example and what happens to "2"
(defn try-move
  [blocks idx]
  (let [{:keys [data]} (nth blocks idx)]
    (if-let [{:keys [pos]} (->> (take (dec idx) blocks)
                                (filter #(>= (:space %) (count data)))
                                first)]
      (-> blocks
          (update-in [pos :data] into data)
          (update-in [pos :space] - (count data))
          (assoc-in  [idx :data] [])
          (update-in [idx :space] + (count data)))
      blocks)))

(defn part2-compacted
  [blocks]
  (let [len (count blocks)]
    (loop [blks blocks idx (dec len)]
      (if (= 1 idx)
        blks
        (recur (try-move blks idx) (dec idx))))))

(defn block-rep
  [{:keys [data space]}]
  (if (zero? space)
    data
    (into data (repeat space 0))))

(defn part2-rep
  [blocks]
  (mapcat block-rep blocks))

;; Puzzle solutions
(defn part1
  "Compact the amphipod's hard drive using the process he requested.
   What is the resulting filesystem checksum?"
  [input]
  (checksum (single-datum-compacted input)))

(defn part2
  [input]
  (checksum (part2-rep (part2-compacted input))))