(ns aoc-clj.utils.intcode.ops)

(defn read-parameter
  [intcode mode value]
  (case mode
    :immediate value
    :position (intcode value)))

(defn add
  [{:keys [intcode iptr]
    [m1 m2 _]  :modes
    [p1 p2 p3] :params
    :as state}]
  (assoc state
         :intcode (assoc intcode p3 (+ (read-parameter intcode m1 p1)
                                       (read-parameter intcode m2 p2)))
         :iptr    (+ iptr 4)))

(defn multiply
  [{:keys [intcode iptr]
    [m1 m2 _]  :modes
    [p1 p2 p3] :params
    :as state}]
  (assoc state
         :intcode (assoc intcode p3 (* (read-parameter intcode m1 p1)
                                       (read-parameter intcode m2 p2)))
         :iptr    (+ iptr 4)))

(defn input
  [{:keys [intcode iptr in]
    [p1] :params
    :as state}]
  (assoc state
         :intcode (assoc intcode p1 (first in))
         :iptr    (+ iptr 2)
         :in      (rest in)))

(defn output
  [{:keys [intcode iptr out]
    [p1] :params
    :as state}]
  (assoc state
         :iptr (+ iptr 2)
         :out  (conj out (intcode p1))))

(defn halt
  [state]
  state)