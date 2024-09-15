(ns aoc-clj.utils.binary
  (:require [clojure.pprint :refer [cl-format]]))

(defn bitstr->int
  "Converts a bit string (e.g. '1101') to the corresponding integer (may be a long or bigint)"
  [s]
  (->> s (str "2r") read-string))

(defn hexstr->int
  "Converts a hex string (e.g. 'ff00aa' to an integer value"
  [s]
  (->> s (str "0x") read-string))

(defn int->bitstr
  "Converts any integer (or long or bigint) to a bit string"
  ([x]
   (cl-format nil "~b" x))
  ([width x]
   (cl-format nil (str "~" width ",'0b") x)))