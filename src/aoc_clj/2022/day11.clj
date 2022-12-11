(ns aoc-clj.2022.day11
  "Solution to https://adventofcode.com/2022/day/11"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.math :as math]))

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
  (str/split (subs s oper-str-len) #" "))

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

(def day11-input (parse (u/puzzle-input "2022/day11-input.txt")))

(def d11-s01
  (parse
   ["Monkey 0:"
    "  Starting items: 79, 98"
    "  Operation: new = old * 19"
    "  Test: divisible by 23"
    "    If true: throw to monkey 2"
    "    If false: throw to monkey 3"
    ""
    "Monkey 1:"
    "  Starting items: 54, 65, 75, 74"
    "  Operation: new = old + 6"
    "  Test: divisible by 19"
    "    If true: throw to monkey 2"
    "    If false: throw to monkey 0"
    ""
    "Monkey 2:"
    "  Starting items: 79, 60, 97"
    "  Operation: new = old * old"
    "  Test: divisible by 13"
    "    If true: throw to monkey 1"
    "    If false: throw to monkey 3"
    ""
    "Monkey 3:"
    "  Starting items: 74"
    "  Operation: new = old + 3"
    "  Test: divisible by 17"
    "    If true: throw to monkey 0"
    "    If false: throw to monkey 1"]))

(defn items
  "The items held by each monkey"
  [monkeys]
  (map :items monkeys))

(defn counts
  "The counts of items that each monkey has processed"
  [monkeys]
  (map :counts monkeys))

(defn operate
  [[op arg] item]
  (if (= "+" op)
    (+' item (read-string arg))
    (if (= "old" arg)
      (*' item item)
      (*' item (read-string arg)))))

(defn update-2
  [[op arg] [mod rem]]
  (case op
    "+" [mod (math/mod-add mod rem (read-string arg))]
    "*" [mod (math/mod-mul mod rem (if (= "old" arg) rem (read-string arg)))]))

(defn operate-2
  [operation item]
  (into {} (map #(update-2 operation %) item)))

(defn monkey-do-1
  [monkeys monkey-id]
  (let [monkey (get monkeys monkey-id)
        {:keys [items operation test true-op false-op]} monkey
        item   (first items)
        worry  (quot (operate operation item) 3)
        dest   (if (zero? (rem worry test)) true-op false-op)]
    (-> monkeys
        (update-in [monkey-id :items] (comp vec rest))
        (update-in [monkey-id :counts] inc)
        (update-in [dest :items] conj worry))))

(defn monkey-do-2
  [monkeys monkey-id]
  (let [monkey (get monkeys monkey-id)
        {:keys [items operation test true-op false-op]} monkey
        item   (first items)
        worry  (operate-2 operation item)
        dest   (if (zero? (worry test)) true-op false-op)]
    (-> monkeys
        (update-in [monkey-id :items] (comp vec rest))
        (update-in [monkey-id :counts] inc)
        (update-in [dest :items] conj worry))))

(defn monkey-turn
  "Process all the items held by the monkey identified by `monkey-id`"
  [monkey-do-fn monkeys monkey-id]
  (loop [ms monkeys]
    (if (empty? (get-in ms [monkey-id :items]))
      ms
      (recur (monkey-do-fn ms monkey-id)))))

(defn round
  "Have every monkey in turn process their items"
  [monkeys]
  (reduce (partial monkey-turn monkey-do-1) monkeys (range (count monkeys))))

(defn remainders
  [item]
  (let [rems [2 3 5 7 9 11 13 17 19 23]]
    (zipmap rems (map #(mod item %) rems))))

(defn remainderize
  [{:keys [items] :as monkey}]
  (assoc monkey :items (map remainders items)))

(defn part2-augment
  [monkeys]
  (mapv remainderize monkeys))

(defn round-2
  "Have every monkey in turn process their items, but the worry levels
   have no relief (aren't divided by three)"
  [monkeys]
  (reduce (partial monkey-turn monkey-do-2) monkeys (range (count monkeys))))

(part2-augment d11-s01)
(round d11-s01)
(round-2 (round-2 (part2-augment d11-s01)))

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

(def monkey-business-1 (partial monkey-business round))
(def monkey-business-2 (partial monkey-business round-2))

(defn day11-part1-soln
  "What is the level of monkey business after 20 rounds of stuff-slinging 
   simian shenanigans?"
  []
  (monkey-business-1 day11-input 20))

(defn day11-part2-soln
  "Worry levels are no longer divided by three after each item is inspected; 
   you'll need to find another way to keep your worry levels manageable. 
   Starting again from the initial state in your puzzle input, what is the 
   level of monkey business after 10000 rounds?"
  []
  (monkey-business-2 (part2-augment day11-input) 10000))