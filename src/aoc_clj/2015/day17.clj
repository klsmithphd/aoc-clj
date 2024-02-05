(ns aoc-clj.2015.day17
  "Solution to https://adventofcode.com/2015/day/17")

;; Constants
(def target-qty 150)

;; Input parsing
(defn parse
  [input]
  (sort > (map read-string input)))

;; Puzzle logic
(defn combinations
  "Returns a collection of the different container combinations that sum
   to `total`. `containers` is assumed to be pre-sorted in decscending
   order. The values in `containers` need not be unique, but each 
   occurrence will treated independently."
  ([total containers]
   (combinations total containers []))
  ([total containers used]
   (if (zero? total)
     ;; If we get down to zero for the target total, we've succeeded, so
     ;; return the containers we used to get here
     [used]
     (if (or (neg? total) (> total (reduce + containers)))
       ;; If the remaining containers aren't enough to meet the target, 
       ;; or if we've overshot the target and gone negative, don't look any 
       ;; further and return an empty vec indicating a dead branch 
       []
       ;; Otherwise, we explore the sub-problem of picking each container
       ;; (in descending size order), removing its size from the total, and
       ;; computing the total options for meeting the new smaller target total
       ;; from the remaining containers.
       ;;
       ;; First, filter to only containers that are less than or equal to 
       ;; our target
       (let [ctrs (filter #(<= % total) containers)]
         (->> (range (count ctrs))
              (mapcat #(combinations
                        (- total (nth ctrs %))
                        (drop (inc %) ctrs)
                        (conj used (nth ctrs %))))))))))

(defn total-options
  "Counts the number of combinations of `containers` that sum to `total`"
  [total containers]
  (count (combinations total containers)))

(defn min-container-total-options
  "Counts the number of combinations of `containers` that sum to `total`
   using the minimum number of containers."
  [total containers]
  (let [freqs (->> (combinations total containers)
                   (map count)
                   (frequencies))
        min-key (apply min (keys freqs))]
    (freqs min-key)))

;; Puzzle solutions
(defn part1
  "Counts the number of combinations of `containers` that sum to `total`"
  [input]
  (total-options target-qty input))

(defn part2
  "Counts the number of combinations of `containers` that sum to `total`
   using the minimum number of containers."
  [input]
  (min-container-total-options target-qty input))