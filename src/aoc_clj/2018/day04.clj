(ns aoc-clj.2018.day04
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(def day04-input (str/join "\n" (sort (u/puzzle-input "inputs/2018/day04-input.txt"))))

(defn parse-shift
  [shift]
  (let [guard-id (Integer/parseInt (subs (re-find #"#[0-9]+" (first shift)) 1))
        cycles (map (comp #(Integer/parseInt %)
                          #(subs % 15 17)) (rest shift))]
    [guard-id cycles]))

(defn parse
  [input]
  (->> (str/split input #"\[.{16}\] Guard ")
       (map #(str/split % #"\n"))
       rest
       (map parse-shift)
       (group-by first)
       (u/fmap (comp
                (partial partition 2)
                (partial mapcat second)))))

(defn guard-sleep-minutes
  [windows]
  (mapcat #(range (first %) (second %)) windows))

(defn sleepiest-guard-and-optimal-minute
  [shifts]
  (let [sleep-sched (u/fmap guard-sleep-minutes shifts)
        sleepiest-guard (u/max-val (u/fmap count sleep-sched))
        optimal-minute (->> (get sleep-sched sleepiest-guard)
                            frequencies
                            u/max-val)]
    [sleepiest-guard optimal-minute]))

(defn guard-most-frequently-asleep-at-minute
  [shifts]
  (let [max-minutes (->> (u/fmap (comp frequencies guard-sleep-minutes) shifts)
                         (filter #(seq (val %)))
                         (into {})
                         (u/fmap (partial apply max-key val)))
        [guard [minute _]] (apply max-key (comp second val) max-minutes)]
    [guard minute]))

(defn day04-part1-soln
  []
  (reduce * (sleepiest-guard-and-optimal-minute (parse day04-input))))

(defn day04-part2-soln
  []
  (reduce * (guard-most-frequently-asleep-at-minute (parse day04-input))))