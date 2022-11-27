(ns aoc-clj.2016.day05
  (:require [aoc-clj.utils.digest :as d]))

(def day05-input "reyedfim")

(defn starts-with-five-zeros?
  [bytes]
  (and (zero? (aget bytes 0))
       (zero? (aget bytes 1))
       (<= 0 (aget bytes 2) 15)))

(defn valid-hash-offsets
  [door-id]
  (let [hashed #(d/md5-bytes (str door-id %))]
    (->>  (range)
          (filter (comp starts-with-five-zeros? hashed))
          (take 30))))

(def day05-input-valid-offsets
  "Found by calling (valid-hash-offsets day05-input) and waiting a long time!"
  [797564 938629 1617991 2104453 2564359
   2834991 3605750 7183955 7292419 7668370
   8059094 9738948 10098451 10105659 11395933
   12187005 13432325 17274562 18101341 19897122
   21475898 21671457 21679503 21842490 23036372
   23090544 25067104 26815976 27230372 27410373])

(defn password
  [door-id indices]
  (->> (take 8 indices)
       (map #(d/md5-str (str door-id %)))
       (map #(nth % 5))
       (apply str)))

(defn day05-part1-soln
  []
  (password day05-input day05-input-valid-offsets))

(defn set-char
  [s [idx c]]
  (let [pos (read-string (str idx))]
    (if (= \* (get s pos))
      (assoc s pos c)
      s)))

(defn password-part2
  [door-id indices]
  (->> (map #(d/md5-str (str door-id %)) indices)
       (map #(take 2 (drop 5 %)))
       (filter #(#{\0 \1 \2 \3 \4 \5 \6 \7} (first %)))
       (reduce set-char [\* \* \* \* \* \* \* \*])
       (apply str)))

(defn day05-part2-soln
  []
  (password-part2 day05-input day05-input-valid-offsets))