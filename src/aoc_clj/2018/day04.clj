(ns aoc-clj.2018.day04
  "Solution to https://adventofcode.com/2018/day/4"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-shift
  [shift]
  (let [guard-id (Integer/parseInt (subs (re-find #"#[0-9]+" (first shift)) 1))
        cycles (map (comp #(Integer/parseInt %)
                          #(subs % 15 17)) (rest shift))]
    [guard-id cycles]))

(defn parse
  [input]
  (->> (str/split (str/join "\n" (sort input)) #"\[.{16}\] Guard ")
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
  [input]
  (reduce * (sleepiest-guard-and-optimal-minute input)))

(defn day04-part2-soln
  [input]
  (reduce * (guard-most-frequently-asleep-at-minute input)))