(ns aoc-clj.utils.intcode)

(defn apply-inst
  "Apply the instruction located at instruction pointer `iptr`
   to the Intcode program `intcode`"
  [intcode iptr]
  (let [[op p1 p2 out] (take 4 (drop iptr intcode))
        in1 (get intcode p1)
        in2 (get intcode p2)
        newv (case op
               1 (+ in1 in2)
               2 (* in1 in2))]
    (assoc intcode out newv)))

(defn intcode-exec
  "Execute an Intcode program `intcode` until it terminates"
  [intcode]
  (loop [nextcode intcode iptr 0]
    (if (= 99 (nth nextcode iptr))
      nextcode
      (recur (apply-inst nextcode iptr) (+ 4 iptr)))))