(ns aoc-clj.utils.assembunny
  "*Assembunny* helper utilities featured in 2016 AoC problems"
  (:require [clojure.string :as str]))

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

(defn parse
  [input]
  (mapv parse-line input))

(defn dref
  "If the value `x` is a number, it's treated as a literal value. Else,
   it's interpreted as a register and the value in that register is returned."
  [state x]
  (if (number? x) x (get state x)))

(defn cpy-cmd
  "`cpy` copies `x` (an integer or the value of a register) into register `y` "
  [state x y]
  (assoc state y (dref state x)))

(defn jnz-cmd
  "`jnz` jumps to an instruction `y` away, either positive or negative,
   but only if `x` is not zero"
  [state x y]
  (if (zero? (dref state x))
    (update state :inst inc)
    (update state :inst + y)))

(defn apply-cmd
  "Apply the given instruction (cmd plus args) to update the state"
  [state {:keys [cmd x y]}]
  (case cmd
    "inc" (-> (update state x inc) (update :inst inc))
    "dec" (-> (update state x dec) (update :inst inc))
    "cpy" (-> (cpy-cmd state x y) (update :inst inc))
    "jnz" (jnz-cmd state x y)))

(defn execute
  "Execute the supplied assembunny code in cmds given the `init` state"
  [init cmds]
  (loop [state init]
    (let [cmd (get cmds (:inst state))]
      (if (nil? cmd)
        state
        (recur (apply-cmd state cmd))))))