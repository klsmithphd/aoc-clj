(ns aoc-clj.2017.day18
  "Solution to https://adventofcode.com/2017/day/18"
  (:require [clojure.string :as str]))

;; Input parsing
(defn parse-possible-num
  [s]
  (if (number? (read-string s))
    (read-string s)
    s))

(defn parse-line
  [line]
  (let [[cmd & args] (str/split line #" ")]
    [cmd (mapv parse-possible-num args)]))

(defn parse
  [input]
  (mapv parse-line input))

;; Puzzle logic
(defn arg-val
  "Parse the argument. If it's a literal (number), return the number,
   else return the value stored in the register or zero if the register
   is unset"
  [state x]
  (if (number? x)
    x
    (get state x 0)))

(defn add-cmd
  "`add X Y` **increases** register X by the value of Y"
  [{:keys [pos] :as state} [x y]]
  {x (+ (arg-val state x) (arg-val state y))
   :pos (inc pos)})

(defn jgz-cmd
  "`jgz X Y` **jumps** with an offset of the value of Y, but only if 
   the value of X is greater than zero."
  [{:keys [pos] :as state} [x y]]
  (if (> (arg-val state x) 0)
    {:pos (+ pos y)}
    {:pos (inc pos)}))

(defn mod-cmd
  "`mod X Y` sets register X to the **remainder** of dividing the value 
   contained in register X by the value of Y"
  [{:keys [pos] :as state} [x y]]
  {x (mod (arg-val state x) (arg-val state y))
   :pos (inc pos)})

(defn mul-cmd
  "`mul X Y` sets register X to the result of **multiplying** the value 
   contained in register X by the value of Y."
  [{:keys [pos] :as state} [x y]]
  {x (* (arg-val state x) (arg-val state y))
   :pos (inc pos)})

(defn set-cmd
  "`set X Y` **sets** register X to the value of Y."
  [{:keys [pos] :as state} [x y]]
  {x (arg-val state y) :pos (inc pos)})

(defn part1-rcv-cmd
  "`rcv X` **recovers** the frequency of the last sound played, but only when 
   the value of X is not zero. (If it is zero, the command does nothing.)"
  [{:keys [pos] :as state} [x]]
  (if (zero? (arg-val state x))
    {:pos (inc pos)}
    {:pos -1}))

(defn part1-snd-cmd
  "`snd X` **plays a sound** with a frequency equal to the value of X."
  [{:keys [pos] :as state} [x]]
  {:snd (arg-val state x) :pos (inc pos)})

;; (defn part2-snd-cmd
;;   "`snd X` **sends** the value of X to the other program. These values
;;    wait in a queue until that program is ready to receive them."
;;   [{:keys [id] :as state} [x]]
;;   (-> state
;;       (update-in [(bit-xor 1 id) :queue] (arg-val state x))
;;       (update :pos inc)))

(def cmd-map
  {"add" add-cmd
   "jgz" jgz-cmd
   "mod" mod-cmd
   "mul" mul-cmd
   "rcv" part1-rcv-cmd
   "set" set-cmd
   "snd" part1-snd-cmd})

(defn part1-step
  "Evolve the state by one step based on the instruction indicated by `pos`"
  [{:keys [insts pos snd] :as state}]
  (if (< -1 pos (count insts))
    (let [[cmd args] (insts pos)]
      (merge state ((cmd-map cmd) state args)))
    snd))

(defn part1-execute
  "Execute the assembly programs instructions until it returns a recover value"
  [insts]
  (->> {:insts insts :pos 0}
       (iterate part1-step)
       (drop-while map?)
       first))

;; Puzzle solutions
(defn part1
  "What is the value of the recovered frequency the first time a `rcv` 
   instruction is executed with a non-zero value?"
  [input]
  (part1-execute input))


;; Let's think about Part 2 now
;; We have one common set of instructions, but now we've got two
;; programs executing them independently and sending each other
;; messages over a queue.
;; The register values and positions are unique to each program now.
;;
;; Here's a possible structure for the state
(comment
  {:insts []
   0 {:pos 0
      :queue []
      :snd-count 0
      "p" 0}
   1 {:pos 0
      :queue []
      :snd-count 0
      "p" 1}})
