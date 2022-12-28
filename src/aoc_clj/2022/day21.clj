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
      (assoc-in ["humn"] :x)))

(defn evalable?
  [x]
  (try
    (eval x)
    (not (keyword? x))
    (catch Exception _ false)))

(def inverse-op
  {'* '/
   '/ '*
   '+ '-
   '- '+})

(defn handle-equality
  [[_ a b]]
  (if (evalable? a)
    [b (eval a)]
    [a (eval b)]))

;; (defn invert
;;   [[[op left right] arg]]
;;   (if (or (= :x left) (= :x right))
;;     (if (= :x left)
;;       (list (inverse-op op) arg right)
;;       (if (or (= op '/) (= op '-))
;;         (list op left arg)
;;         (list (inverse-op op) arg left)))
;;     (if (evalable? left)
;;       (if (or (= op '/) (= op '-))
;;         [right (list op left arg)]
;;         [right (list (inverse-op op) arg left)])
;;       (if (or (= op '/) (= op '-))
;;         [left (list op right arg)]
;;         [left (list (inverse-op op) arg right)]))))

(defn invert
  [[[op left right] arg]]
  (if (or (= op '/) (= op '-))
    (if (evalable? left)
      [right (list op left arg)]
      [left  (list (inverse-op op) right arg)])
    (if (evalable? left)
      [right (list (inverse-op op) arg left)]
      [left  (list (inverse-op op) arg right)])))

(defn safe-invert
  [[eqn arg]]
  (try
    (invert [eqn arg])
    (catch Exception _ nil)))

(safe-invert '[(- :x 3) 8])

;; (defn solve-for-x
;;   [equation]
;;   (->> equation
;;        handle-equality
;;        (iterate safe-invert)
;;        (take-while some?)
;;        last))

(defn solve-for-x
  [equation]
  (loop [[eqn known] (handle-equality equation)]
    (if (keyword? eqn)
      known
      (recur (invert [eqn known])))))

(solve-for-x '(= (+ 6 (- :x 0)) (* 4 (+ 1 1))))


(let [foo (filter vector? (vals day21-input))
      ls  (map first foo)
      rs  (map #(get % 2) foo)]
  (filter #(> (val %) 1) (frequencies (concat ls rs))))

(solve-for-x d21-s01)
(equation (update-jobs d21-s01) "root")

(eval (solve-for-x (equation (update-jobs day21-input) "root")))

(safe-invert (handle-equality (equation (update-jobs d21-s01) "root")))
(safe-invert '[(+ 4 (* 2 (- :humn 3))) (* 4 150)])
(safe-invert '[(* 2 (- :humn 3)) (- (* 4 150) 4)])
(safe-invert '[(- :humn 3) (/ (- (* 4 150) 4) 2)])
(safe-invert '(+ (/ (- (* 4 150) 4) 2) 3))

(eval (last (take-while some? (iterate safe-invert (handle-equality (equation (update-jobs day21-input) "root"))))))
;; not 960
(long 9147294904925473988288299985066581351232/7402433194058496223063861724959835859)
;; not 1235
1435841306317776376254144880011006672612027025/1495660230557456757378437572101284033632832


(defn day21-part1-soln
  []
  (root-yell day21-input))