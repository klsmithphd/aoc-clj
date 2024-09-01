(ns aoc-clj.utils.assembunny
  "*Assembunny* helper utilities featured in 2016 AoC problems"
  (:require [clojure.string :as str]))

;; Constants
(def init-state {:a 0 :b 0 :c 0 :d 0 :inst 0 :out []})

;; Input parsing
(defn parse-var
  [x]
  (if (number? (read-string x))
    (read-string x)
    (keyword x)))

(defn parse-line
  [line]
  (let [[a b c] (str/split line #" ")]
    (if (or (= a "cpy") (= a "jnz"))
      [a (parse-var b) (parse-var c)]
      [a (parse-var b)])))

(defn parse
  [input]
  (mapv parse-line input))

;; Assembunny logic
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
    (update state :inst + (dref state y))))

(defn out-cmd
  "`out` transmits `x` (an integer or the value of a register) as the next
   value to the clock signal"
  [state x]
  (update state :out conj (dref state x)))

(defn one-arg-tgl
  [[c x]]
  (if (= "inc" c)
    ["dec" x]
    ["inc" x]))

(defn two-arg-tgl
  [[c x y]]
  (if (= "jnz" c)
    ["cpy" x y]
    ["jnz" x y]))

(defn tgl-cmd
  [{:keys [inst cmds] :as state} x]
  (let [index (+ inst (dref state x))
        cmd (get-in state [:cmds index])]
    (if (< index (count cmds))
      (if (= 3 (count cmd))
        (assoc-in state [:cmds index] (two-arg-tgl cmd))
        (assoc-in state [:cmds index] (one-arg-tgl cmd)))
      state)))

(defn apply-cmd
  "Apply the given instruction (cmd plus args) to update the state"
  [state [cmd x y]]
;;   (println cmd x y (:a state) (:b state) (:c state) (:d state) (:inst state))
  (case cmd
    "inc" (-> (update state x inc) (update :inst inc))
    "dec" (-> (update state x dec) (update :inst inc))
    "cpy" (-> (cpy-cmd state x y) (update :inst inc))
    "tgl" (-> (tgl-cmd state x) (update :inst inc))
    "out" (-> (out-cmd state x) (update :inst inc))
    "jnz" (jnz-cmd state x y)))

(defn execute
  "Execute the supplied assembunny code in cmds given the `init` state"
  [init cmds]
  (loop [state (assoc init :cmds cmds)]
    (let [cmd (get-in state [:cmds (:inst state)])]
      (if (nil? cmd)
        state
        (recur (apply-cmd state cmd))))))