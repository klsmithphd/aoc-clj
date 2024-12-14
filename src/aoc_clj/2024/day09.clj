(ns aoc-clj.2024.day09
  "Solution to https://adventofcode.com/2024/day/9")

;; Input parsing
(defn file
  [idx v type]
  {:pos idx
   :data (case type
           :file (vec (repeat v (quot idx 2)))
           :free [])
   :space (case type
            :file 0
            :free v)})

(defn files
  [coll]
  (->> (map file (range) coll (cycle [:file :free]))
       vec))

(defn parse
  [input]
  (->> (first input)
       (map (comp read-string str))
       files))

;; Puzzle logic
(defn end-data
  "Returns a lazy collection of the data elements taken from the end
   and iterating forward"
  [files]
  (mapcat :data (reverse files)))

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

(defn block-compacted
  "Returns the disk layout by moving individual blocks from the end to the
   earliest available gap"
  [files]
  (let [size      (reduce + (map #(count (:data %)) files))
        data-blks (take-nth 2 files)
        gap-blks  (take-nth 2 (rest files))]
    (->> (interleave (map :data data-blks)
                     (parition-by-sizes (map :space gap-blks) (end-data files)))
         flatten
         (take size))))

(defn checksum
  "Computes the checksum, which is the sum of each element's position
   by the value of the data held at that position"
  [coll]
  (->> (map-indexed * coll)
       (reduce +)))

(defn try-move
  "Returns the disk layout after attempting to move the provided file
   to the earliest large enough open space. If the file can't fit,
   the disk layout is returned unchanged"
  [files {:keys [pos data]}]
  (let [size (count data)]
    (if-let [open (->> (take pos files)
                       (filter #(>= (:space %) size))
                       first
                       :pos)]
      (-> files
          (update-in [open :data] into data)
          (update-in [open :space] - size)
          (assoc-in  [pos :data] [])
          (assoc-in  [pos :space] size))
      files)))

(defn file->blocks
  "Converts a file into its individual blocks, with 0s added for empty data"
  [{:keys [data space]}]
  (if (zero? space)
    data
    (into data (repeat space 0))))

(defn file-compacted
  "Returns the disk layout by moving entire files from the end of the disk
   to any sufficiently large enough open space earlier on the disk."
  [files]
  (->> (reverse files)
       (take-nth 2)
       (butlast)
       (reduce try-move files)
       (mapcat file->blocks)))

;; Puzzle solutions
(defn part1
  "Compact the amphipod's hard drive using the process he requested.
   What is the resulting filesystem checksum?"
  [input]
  (checksum (block-compacted input)))

(defn part2
  "Start over, now compacting the amphipod's hard drive using this new
   method instead. What is the resulting filesystem checksum?"
  [input]
  (checksum (file-compacted input)))