(ns aoc-clj.digest.interface
  (:import java.security.MessageDigest)
  (:require [clojure.string :as str]))

(def md5-alg (ThreadLocal/withInitial #(MessageDigest/getInstance "MD5")))

(defn md5-digest
  "Computes the MD5 digest of s as a byte array"
  [^String s]
  (.digest ^MessageDigest (.get ^ThreadLocal md5-alg) (.getBytes s)))

(defn md5-str
  "Computes the string representation of the MD5 digest of s"
  [^String s]
  (->> (md5-digest s)
       (map #(format "%02x" %))
       str/join))

(defn five-zero-start?
  "Whether the digest (as bytes) starts with five zeroes"
  [^bytes digest]
  (and (zero? (aget digest 0)) (zero? (aget digest 1)) (<= 0 (aget digest 2) 15)))

(defn six-zero-start?
  "Whether the digest (as bytes) starts with six zeroes"
  [^bytes digest]
  (and (zero? (aget digest 0)) (zero? (aget digest 1)) (zero? (aget digest 2))))

(defn- search-batch
  "First long n in [start, end) for which (pred n) is truthy, else nil."
  [pred ^long start ^long end]
  (loop [n start]
    (cond
      (>= n end)   nil
      (pred n)     n
      :else        (recur (inc n)))))

(defn find-first-int
  "Smallest non-negative integer n for which (pred n) is truthy.

   Distributes work across `availableProcessors` threads in batched rounds:
   each round dispatches one batch per thread, advancing only after no
   thread in the round finds a match. Bounded wasted work — at most
   `nthreads * batch-size` candidates past the answer."
  ([pred] (find-first-int pred 100000))
  ([pred ^long batch-size]
   (let [nthreads (.. Runtime getRuntime availableProcessors)
         step     (* nthreads batch-size)]
     (loop [round-start 0]
       (let [futures (mapv (fn [i]
                             (let [s (+ round-start (* i batch-size))]
                               (future (search-batch pred s (+ s batch-size)))))
                           (range nthreads))
             hits    (keep deref futures)]
         (if (seq hits)
           (apply min hits)
           (recur (+ round-start step))))))))