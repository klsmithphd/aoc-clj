(ns aoc-clj.utils.digest
  (:import java.security.MessageDigest)
  (:require [clojure.string :as str]))

(def md5-alg (MessageDigest/getInstance "MD5"))

(defn md5-digest
  "Computes the MD5 digest of s as a byte array"
  [^String s]
  (.digest md5-alg (.getBytes s)))

(defn md5-str
  "Computes the string representation of the MD5 digest of s"
  [^String s]
  (->> (md5-digest s)
       (map #(format "%02x" %))
       str/join))

(defn five-zero-start?
  "Whether the digest (as bytes) starts with five zeroes"
  [digest]
  (let [[a b c] (take 3 digest)]
    (and (zero? a) (zero? b) (<= 0 c 15))))

(defn six-zero-start?
  "Whether the digest (as bytes) starts with six zeroes"
  [bytes]
  (every? zero? (take 3 bytes)))
