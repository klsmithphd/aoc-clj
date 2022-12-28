(ns aoc-clj.2022.day21
  "Solution to https://adventofcode.com/2022/day/21"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [[l r] (str/split line #": ")
        args  (str/split r #" ")]
    [l (if (= 1 (count args))
         (read-string (first args))
         args)]))

(defn parse
  [input]
  (into {} (map parse-line input)))

(def d21-s01
  (parse
   ["root: pppw + sjmn"
    "dbpl: 5"
    "cczh: sllz + lgvd"
    "zczc: 2"
    "ptdq: humn - dvpt"
    "dvpt: 3"
    "lfqf: 4"
    "humn: 5"
    "ljgn: 2"
    "sjmn: drzm * dbpl"
    "sllz: 4"
    "pppw: cczh / lfqf"
    "lgvd: ljgn * ptdq"
    "drzm: hmdt - zczc"
    "hmdt: 32"]))

(def day21-input (parse (u/puzzle-input "2022/day21-input.txt")))

(defn equation
  [jobs monkey]
  (let [value (jobs monkey)]
    (if (vector? value)
      (let [[a op b] value]
        (list (read-string op) (equation jobs a) (equation jobs b)))
      value)))

(defn root-yell
  [jobs]
  (eval (equation jobs "root")))

(defn update-jobs
  [jobs]
  (-> jobs
      (assoc-in ["root" 1] "=")
      (assoc-in ["humn"] :humn)))

(defn evalable?
  [x]
  (try
    (eval x)
    true
    (catch Exception _ false)))

(evalable? (nth (equation (update-jobs d21-s01) "root") 2))
(str (equation (update-jobs day21-input) "root"))
(update-jobs d21-s01)

(def inverse-op
  {'* '/
   '/ '*
   '+ '-
   '- '+})

((inverse-op *) 1 2)

(defn handle-root
  [[_ a b]]
  (if (evalable? a)
    [b (eval a)]
    [a (eval b)]))

(defn unpeel
  [[[op a b] arg]]
  (if (or (= :humn a) (= :humn b))
    (if (= :humn a)
      (list (inverse-op op) arg b)
      (if (or (= op '/) (= op '-))
        (list op arg a)
        (list (inverse-op op) arg a)))
    (if (evalable? a)
      (if (or (= op '/) (= op '-))
        [b (list op arg (eval a))]
        [b (list (inverse-op op) (eval a) arg)])
      [a (list (inverse-op op) (eval b) arg)])))

(defn safe-unpeel
  [[eqn arg]]
  (try
    (unpeel [eqn arg])
    (catch Exception _ nil)))

(safe-unpeel (handle-root (equation (update-jobs d21-s01) "root")))
(safe-unpeel '[(+ 4 (* 2 (- :humn 3))) (* 4 150)])
(safe-unpeel '[(* 2 (- :humn 3)) (- (* 4 150) 4)])
(safe-unpeel '[(- :humn 3) (/ (- (* 4 150) 4) 2)])
(safe-unpeel '(+ (/ (- (* 4 150) 4) 2) 3))

(eval (last (take-while some? (iterate safe-unpeel (handle-root (equation (update-jobs day21-input) "root"))))))
;; not 960
(long 9147294904925473988288299985066581351232/7402433194058496223063861724959835859)
;; not 1235
1435841306317776376254144880011006672612027025/1495660230557456757378437572101284033632832


(defn day21-part1-soln
  []
  (root-yell day21-input))