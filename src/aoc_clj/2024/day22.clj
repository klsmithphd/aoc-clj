(ns aoc-clj.2024.day22
  "Solution to https://adventofcode.com/2024/day/22")

;; Constants
(def part1-n 2000)

;; Input parsing
(defn parse
  [input]
  (map read-string input))

;; Puzzle logic
(defn mix
  "Mixing is defined as taking this bitwise XOR of a value into the number"
  [num mix-val]
  (bit-xor num mix-val))

(defn prune
  "Pruning is defined as the value of the number modulo 16777216"
  [num]
  (mod num 16777216))

(defn op-mix-and-prune
  "For the given number, perform the math operation `op` on the number with 
   the second argument `arg`, and then mix that result into the original
   number and prune the result"
  [op arg num]
  (->> (op num arg)
       (mix num)
       prune))

(defn next-secret
  "Returns the next secret in the sequence from the current num"
  [num]
  (->> num
       (op-mix-and-prune bit-shift-left 6)  ;; Multiply by 64
       (op-mix-and-prune bit-shift-right 5) ;; Truncate-divide by 32
       (op-mix-and-prune bit-shift-left 11) ;; Multiply by 2048
       ))

(defn secret-seq
  "Returns a lazy seq of the next values of the secret"
  [num]
  (iterate next-secret num))

(defn secret-at-n
  "Returns the value of the secret at the nth iteration"
  [n num]
  (first (drop n (secret-seq num))))

(defn secrets-at-n-sum
  "Returns the sum of all the secrets at the nth iteration given their
   starting secret values"
  [n nums]
  (->> nums
       (map #(secret-at-n n %))
       (reduce +)))

(defn ones-digit
  "Returns the ones digit of the number"
  [num]
  (mod num 10))

(defn price-changes
  "Returns a sequence of the differences between consecutive values"
  [nums]
  (->> (partition 2 1 nums)
       (map #(apply - (reverse %)))))

(defn changeseq-to-price-map
  "Constructs a mapping between the preceding price changes and a
   given price. `n` is the number of secret iterations to perform,
   and `num` is the starting value."
  [n num]
  (let [ones (->> (secret-seq num)
                  (take (inc n))
                  (map ones-digit))
        changes   (price-changes ones)]
    (->> (map vector (partition 4 1 changes) (drop 4 ones))
         ;; We reverse the sequence so that the earliest changes end up
         ;; overwriting the later changes when we insert this into our 
         ;; map. We want the map to tell us what first price we get
         ;; for a given value
         (reverse)
         (into {}))))

(defn all-prices
  "Across all the different monkeys, constructs a single unified
   map from the change-sequence to the total achievable price.
   `n` is the number of secret iterations to perform,
   and `num` is the starting value."
  [n nums]
  (->> nums
       (map #(changeseq-to-price-map n %))
       (apply merge-with +)))

(defn most-bananas
  "For a changeseq-to-price mapping, returns the changeseq/price
   combo that maximizes the price."
  [pricemap]
  (apply max-key val pricemap))

;; Puzzle solutions
(defn part1
  "What is the sum of the 2000th secret number generated by each buyer?"
  [input]
  (secrets-at-n-sum part1-n input))

(defn part2
  "Figure out the best sequence to tell the monkey so that by looking for that
   same sequence of changes in every buyer's future prices, you get the most
   bananas in total. What is the most bananas you can get?"
  [input]
  (->> (all-prices part1-n input)
       most-bananas
       second))