(ns aoc-clj.2022.day11
  "Solution to https://adventofcode.com/2022/day/11"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.math :as math]))

;;;; Input parsing

(def start-str-len  (count "  Starting items: "))
(def oper-str-len   (count "  Operation: new = old "))
(def test-str-len   (count "  Test: divisible by "))
(def true-str-len   (count "    If true: throw to monkey "))
(def false-str-len  (count "    If false: throw to monkey "))

(defn parse-num
  [s skip-length]
  (read-string (subs s skip-length)))

(defn parse-items [s] (u/firstv [(subs s start-str-len)]))
(defn parse-test  [s] (parse-num s test-str-len))
(defn parse-true  [s] (parse-num s true-str-len))
(defn parse-false [s] (parse-num s false-str-len))
(defn parse-oper
  [s]
  (let [[op arg] (str/split (subs s oper-str-len) #" ")]
    [op (if (= "old" arg) arg (read-string arg))]))

(defn parse-monkey
  [[_ items oper test true-op false-op]]
  {:counts    0
   :items     (parse-items items)
   :operation (parse-oper oper)
   :test      (parse-test test)
   :true-op   (parse-true true-op)
   :false-op  (parse-false false-op)})

(defn parse
  [input]
  (mapv parse-monkey (u/split-at-blankline input)))

;;;; Puzzle logic

(defn items
  "The items held by each monkey"
  [monkeys]
  (map :items monkeys))

(defn counts
  "The counts of items that each monkey has processed"
  [monkeys]
  (map :counts monkeys))

(defn operate-1
  "Apply the monkey's operation rule to the item"
  [[op arg] item]
  (case op
    "+" (+' item arg)
    "*" (*' item (if (= "old" arg) item arg))))

(defn operate-2
  [[op arg] [mod rem]]
  (case op
    "+" [mod (math/mod-add mod rem arg)]
    "*" [mod (math/mod-mul mod rem (if (= "old" arg) rem arg))]))

(defn worry-1
  [operation item]
  (quot (operate-1 operation item) 3))

(defn worry-2
  "Apply the monkey's operation rule to the item, only keeping track of
   mod remainders"
  [operation item]
  (into {} (map #(operate-2 operation %) item)))

(defn divides?-1
  [worry test]
  (zero? (rem worry test)))

(defn divides?-2
  [worry test]
  (zero? (worry test)))

(defn monkey-do
  "Let the current monkey identified by `monkey-id` process its
   next item"
  [worry-fn divides?-fn monkeys monkey-id]
  (let [monkey (get monkeys monkey-id)
        {:keys [items operation test true-op false-op]} monkey
        item   (first items)
        worry  (worry-fn operation item)
        dest   (if (divides?-fn worry test) true-op false-op)]
    (-> monkeys
        (update-in [monkey-id :items] (comp vec rest))
        (update-in [monkey-id :counts] inc)
        (update-in [dest :items] conj worry))))

(def monkey-do-1 (partial monkey-do worry-1 divides?-1))
(def monkey-do-2 (partial monkey-do worry-2 divides?-2))

(defn monkey-turn
  "Process all the items held by the monkey identified by `monkey-id`"
  [monkey-do-fn monkeys monkey-id]
  (loop [ms monkeys]
    (if (empty? (get-in ms [monkey-id :items]))
      ms
      (recur (monkey-do-fn ms monkey-id)))))

(defn round
  "Have every monkey in turn process their items"
  [monkey-do-fn monkeys]
  (reduce (partial monkey-turn monkey-do-fn) monkeys (range (count monkeys))))

(def round-1 (partial round monkey-do-1))
(def round-2 (partial round monkey-do-2))

(defn monkey-business
  "The product of the number of items inspected by the two most active 
   monkeys after `rounds`"
  [round-fn monkeys rounds]
  (let [cnts (-> (iterate round-fn monkeys)
                 (nth rounds)
                 counts)]
    (->> (sort > cnts)
         (take 2)
         (reduce *))))

(def monkey-business-1 (partial monkey-business round-1))
(def monkey-business-2 (partial monkey-business round-2))

(defn remainders
  "Creates a map, where the keys are the first ten prime numbers
   and the values are the mods of `item` relative to those prime numbers"
  [item]
  (let [rems [2 3 5 7 9 11 13 17 19 23]]
    (zipmap rems (map #(mod item %) rems))))

(defn remainderize
  "Replaces the worry value of each of a monkey's items with a map of
   the mods of that worry value relative to the first ten prime numbers"
  [{:keys [items] :as monkey}]
  (assoc monkey :items (map remainders items)))

(defn part2-augment
  "In part 2, working with the actual result of applying the operations
   becomes too prohibitively expensive. All we actually need to know is
   whether the worry value is divisible by one of the first ten prime
   numbers. As such, we can make the problem tractable by only keeping
   track of the mods of the worry values and applying mod arithmetic
   appropriately. This function will replace the numeric items worry value
   with a map, where the keys are the first ten prime numbers and the
   values are the mods of the worry value relative to those primes. This
   bootstraps the rest of the changes for part 2."
  [monkeys]
  (mapv remainderize monkeys))

;;;; Puzzle solutions

(defn day11-part1-soln
  "What is the level of monkey business after 20 rounds of stuff-slinging 
   simian shenanigans?"
  [input]
  (monkey-business-1 input 20))

(defn day11-part2-soln
  "Worry levels are no longer divided by three after each item is inspected; 
   you'll need to find another way to keep your worry levels manageable. 
   Starting again from the initial state in your puzzle input, what is the 
   level of monkey business after 10000 rounds?"
  [input]
  (monkey-business-2 (part2-augment input) 10000))