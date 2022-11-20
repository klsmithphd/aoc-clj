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
    [m1] :modes
    [p1] :params
    :as state}]
  (assoc state
         :iptr (+ iptr 2)
         :out  (conj out (read-parameter intcode m1 p1))))

(defn jump-if-true
  [{:keys [intcode iptr]
    [m1 m2] :modes
    [p1 p2] :params
    :as state}]
  (assoc state :iptr (if (zero? (read-parameter intcode m1 p1))
                       (+ iptr 3)
                       (read-parameter intcode m2 p2))))

(defn jump-if-false
  [{:keys [intcode iptr]
    [m1 m2] :modes
    [p1 p2] :params
    :as state}]
  (assoc state :iptr (if (zero? (read-parameter intcode m1 p1))
                       (read-parameter intcode m2 p2)
                       (+ iptr 3))))

(defn less-than
  [{:keys [intcode iptr]
    [m1 m2 _] :modes
    [p1 p2 p3] :params
    :as state}]
  (assoc state
         :intcode (assoc intcode p3 (if (< (read-parameter intcode m1 p1)
                                           (read-parameter intcode m2 p2))
                                      1
                                      0))
         :iptr    (+ iptr 4)))

(defn equals
  [{:keys [intcode iptr]
    [m1 m2 _] :modes
    [p1 p2 p3] :params
    :as state}]
  (assoc state
         :intcode (assoc intcode p3 (if (= (read-parameter intcode m1 p1)
                                           (read-parameter intcode m2 p2))
                                      1
                                      0))
         :iptr    (+ iptr 4)))

(defn halt
  [state]
  state)