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
  "Returns the state change for an `add` instruction, which updates
   the first argument's register value by adding the second argument's value."
  [{:keys [pos] :as state} [x y]]
  {x (+ (arg-val state x) (arg-val state y))
   :pos (inc pos)})

(defn jgz-cmd
  "Returns the state change for an `jgz` instruction, which jumps
   in instruction position by the offset of the second argument only
   if the value in the first argument's register is greater than zero."
  [{:keys [pos] :as state} [x y]]
  (if (> (arg-val state x) 0)
    {:pos (+ pos y)}
    {:pos (inc pos)}))

(defn mod-cmd
  "Returns the state change for a `mod` instruction, which updates
   the first argument's register value by modding it with the second
   argument's value"
  [{:keys [pos] :as state} [x y]]
  {x (mod (arg-val state x) (arg-val state y))
   :pos (inc pos)})

(defn mul-cmd
  "Returns the state change for a `mul` instruction, which updates
   the first argument's register value by multiplying it by the second
   argument's value"
  [{:keys [pos] :as state} [x y]]
  {x (* (arg-val state x) (arg-val state y))
   :pos (inc pos)})

(defn set-cmd
  "Returns the state change for a `set` instruction, which sets the
   first argument's register value to the second argument's value"
  [{:keys [pos] :as state} [x y]]
  {x (arg-val state y) :pos (inc pos)})

(defn snd-cmd-p1
  "Returns the state change for a `snd` instruction using the interpretation
   in part 1. It sets the `:snd` key to the value of the argument's register,
   indicating that frequency has been played as a sound"
  [{:keys [pos] :as state} [x]]
  {:snd (arg-val state x) :pos (inc pos)})

(defn rcv-cmd-p1
  "Returns the state change for a `rcv` instruction using the interpretation
   in part 1. If the value of the argument's register is zero, it does nothing,
   but if non-zero, it sets the instruction position out of bounds to
   stop the program from running."
  [{:keys [pos] :as state} [x]]
  (if (zero? (arg-val state x))
    {:pos (inc pos)}
    {:pos -1}))

;; (defn part2-snd-cmd
;;   "`snd X` **sends** the value of X to the other program. These values
;;    wait in a queue until that program is ready to receive them."
;;   [{:keys [id] :as state} [x]]
;;   (-> state
;;       (update-in [(bit-xor 1 id) :queue] (arg-val state x))
;;       (update :pos inc)))

(def cmd-map-p1
  {"add" add-cmd
   "jgz" jgz-cmd
   "mod" mod-cmd
   "mul" mul-cmd
   "rcv" rcv-cmd-p1
   "set" set-cmd
   "snd" snd-cmd-p1})

(defn step-p1
  "Evolve the state by one step based on the instruction indicated by `pos`"
  [{:keys [insts pos snd] :as state}]
  (if (< -1 pos (count insts))
    (let [[cmd args] (insts pos)]
      (merge state ((cmd-map-p1 cmd) state args)))
    snd))

(defn execute-p1
  "Execute the assembly programs instructions until it returns a recover value"
  [insts]
  (->> {:insts insts :pos 0}
       (iterate step-p1)
       (drop-while map?)
       first))

;; Puzzle solutions
(defn part1
  "What is the value of the recovered frequency the first time a `rcv` 
   instruction is executed with a non-zero value?"
  [input]
  (execute-p1 input))


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
