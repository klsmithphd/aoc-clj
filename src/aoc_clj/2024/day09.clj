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

;; (defn part2-block
;;   [idx [size gap]]
;;   {:pos  idx
;;    :data (vec (repeat size idx))
;;    :size (+ size gap)
;;    :space gap})

(defn part2-block
  [idx v type]
  {:pos idx
   :data (case type
           :file (vec (repeat v (quot idx 2)))
           :free [])
   :size v
   :space (case type
            :file 0
            :free v)})

(defn part2-blocks
  [coll]
  (->> (map part2-block (range) coll (cycle [:file :free]))
       (vec)))

;; (defn part2-blocks
;;   [coll]
;;   (->> (partition 2 2 [0] coll)
;;        (map-indexed part2-block)
;;        vec))

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
  [input]
  (checksum (compacted (blocks input))))

(defn part2
  [input]
  (checksum (part2-rep (part2-compacted (part2-blocks input)))))