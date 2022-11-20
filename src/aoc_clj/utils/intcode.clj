(ns aoc-clj.utils.intcode
  (:require [clojure.string :as str]
            [aoc-clj.utils.intcode.ops :as ops]))

(def opcodes
  {1  {:op ops/add      :width 4}
   2  {:op ops/multiply :width 4}
   3  {:op ops/input    :width 2}
   4  {:op ops/output   :width 2}
   99 {:op ops/halt     :width 1}})

(defn parameter-mode
  [mode]
  (case mode
    \1 :immediate
    :position))

(defn parse-instruction
  [instruction]
  (let [opcode (opcodes (mod instruction 100))
        pstr   (format (str "%0" (dec (:width opcode)) "d") ;; e.g. "%03d" 
                       (quot instruction 100))]
    (assoc opcode :modes (mapv parameter-mode (str/reverse pstr)))))

(defn apply-inst
  "Apply the instruction located at instruction pointer `iptr`
   to the Intcode program `intcode`"
  [{:keys [intcode iptr] :as state}]
  (let [{:keys [op width modes]} (parse-instruction (intcode iptr))
        params (subvec intcode (inc iptr) (+ iptr width))]
    (op (assoc state :params params :modes modes))))

(defn intcode-exec
  "Execute an Intcode program `intcode` until it terminates"
  ([intcode]
   (intcode-exec intcode [] []))
  ([intcode in out]
   (loop [state {:intcode intcode
                 :iptr 0
                 :in in
                 :out out}]
     (if (= 99 ((:intcode state) (:iptr state)))
       state
       (recur (apply-inst state))))))