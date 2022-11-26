(ns aoc-clj.utils.intcode
  (:require [clojure.string :as str]
            [manifold.stream :as s]
            [aoc-clj.utils.intcode.ops :as ops]))

(def opcodes
  {1  {:op ops/add           :width 4}
   2  {:op ops/multiply      :width 4}
   3  {:op ops/input         :width 2}
   4  {:op ops/output        :width 2}
   5  {:op ops/jump-if-true  :width 3}
   6  {:op ops/jump-if-false :width 3}
   7  {:op ops/less-than     :width 4}
   8  {:op ops/equals        :width 4}
   9  {:op ops/offset        :width 2}
   99 {:op ops/halt          :width 1}})

(defn parameter-mode
  "Determine the parameter mode (either immediate or position) based on 
   the character `mode` provided"
  [mode]
  (case mode
    \2 :relative
    \1 :immediate
    :position))

(defn parse-instruction
  "Identify the opcode and the parameter modes for the given integer `instruction`"
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
   (intcode-exec intcode (s/stream) (s/stream)))
  ([intcode in]
   (intcode-exec intcode (if (s/stream? in) in (s/->source in)) (s/stream)))
  ([intcode in out]
   (loop [state {:intcode intcode
                 :iptr 0
                 :base 0
                 :in in
                 :out out}]
     (if (= 99 ((:intcode state) (:iptr state)))
       (do
         (s/close! (:out state))
         state)
       (recur (apply-inst state))))))

(defn out-seq
  "Return a seq of the output of an Intcode execution"
  [intcode-state]
  (s/stream->seq (:out intcode-state)))

(defn last-out
  "Return the very last value output from an Intcode execution"
  [intcode-state]
  (last (out-seq intcode-state)))
