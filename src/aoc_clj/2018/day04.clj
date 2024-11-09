(ns aoc-clj.2018.day04
  "Solution to https://adventofcode.com/2018/day/4"
  (:require [aoc-clj.utils.core :as u]))

;; Input parsing
(defn last-number
  "Returns the last multi-digit number contained in the string as an integer"
  [s]
  (->> s
       (re-seq #"\d+")
       last
       ;; Use parseInt instead of read-string because read-string
       ;; attempts to interpret '09' as an octal value, which is invalid.
       Integer/parseInt))

(defn parse-shift
  [[[shift-start] sleep-cycles]]
  ;; The last number in the shift-start is the Guard id
  ;; The last number in every other line is the minutes after the hour
  {(last-number shift-start) (map last-number sleep-cycles)})

(defn parse
  [input]
  (->>
   ;; Puzzle input isn't chronologically sorted
   (sort input)
   ;; Break seq whenever a new Guard is declared
   (partition-by #(re-find #"Guard" %))
   ;; Combine the guard and the sleep/wake times
   (partition 2)
   (map parse-shift)
   ;; Combine the different shifts over different days into one coll
   (apply merge-with concat)
   ;; Update the guard
   (u/fmap #(partition 2 %))))

;; Puzzle logic
(defn guard-sleep-minutes
  "Returns an unordered seq of all the minutes the guard was ever asleep"
  [windows]
  (mapcat #(apply range %) windows))

(defn sleepiest-guard-and-optimal-minute
  "Returns the id of the guard who sleeps the most and which
   minute they were asleep the most"
  [shifts]
  (let [sleep-sched     (u/fmap guard-sleep-minutes shifts)
        sleepiest-guard (u/max-val (u/fmap count sleep-sched))
        optimal-minute  (->> (get sleep-sched sleepiest-guard)
                             frequencies
                             u/max-val)]
    [sleepiest-guard optimal-minute]))

(defn sleepiest-minute
  "Returns the minute and the number of times that any given guard slept
   the most"
  [windows]
  (->> windows
       guard-sleep-minutes
       frequencies
       (apply max-key val)))

(defn guard-most-frequently-asleep-at-minute
  "Returns which guard was asleep at the same minute the most times 
   and the minute they slept the most"
  [shifts]
  (let [max-minutes        (u/fmap sleepiest-minute shifts)
        [guard [minute _]] (apply max-key (comp second val) max-minutes)]
    [guard minute]))

;; Puzzle solutions
(defn part1
  "What is the ID of the guard you chose multiplied by the minute you chose?"
  [input]
  (reduce * (sleepiest-guard-and-optimal-minute input)))

(defn part2
  "What is the ID of the guard you chose multiplied by the minute you chose?"
  [input]
  (reduce * (guard-most-frequently-asleep-at-minute input)))