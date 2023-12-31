(ns aoc-clj.2016.day12
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-var
  [x]
  (if (number? (read-string x))
    (read-string x)
    (keyword x)))

(defn parse-line
  [line]
  (let [[a b c] (str/split line #" ")]
    (if (or (str/starts-with? line "cpy")
            (str/starts-with? line "jnz"))
      {:cmd a :x (parse-var b) :y (parse-var c)}
      {:cmd a :x (parse-var b)})))

(def day12-input (mapv parse-line (u/puzzle-input "inputs/2016/day12-input.txt")))

(defn dref
  [state x]
  (if (number? x) x (get state x)))

(defn cpy-cmd
  [state x y]
  (assoc state y (dref state x)))

(defn jnz-cmd
  [state x y]
  (if (zero? (dref state x))
    (update state :inst inc)
    (update state :inst + y)))

(defn apply-cmd
  [state {:keys [cmd x y]}]
  (case cmd
    "inc" (-> (update state x inc) (update :inst inc))
    "dec" (-> (update state x dec) (update :inst inc))
    "cpy" (-> (cpy-cmd state x y) (update :inst inc))
    "jnz" (jnz-cmd state x y)))

(def init-state {:a 0 :b 0 :c 0 :d 0 :inst 0})

(defn execute
  [init cmds]
  (loop [state init]
    (let [cmd (get cmds (:inst state))]
      (if (nil? cmd)
        state
        (recur (apply-cmd state cmd))))))

(defn day12-part1-soln
  []
  (:a (execute init-state day12-input)))

(defn day12-part2-soln
  []
  (:a (execute
       (assoc init-state :c 1)
       day12-input)))

