(ns aoc-clj.2016.day05
  "Solution to https://adventofcode.com/2016/day/5"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.digest :as d]))

;; Constants
(def cached-indices
  "The first 30 indices that, when concatenated with the prefix (key),
   result in an md5-hash that begins with five zeros. These values
   where computed using `(take 30 (five-zero-indices x))` where `x` is
   the prefix either in the sample problem or my actual input. 
   
   Caching (hard-coding) these values saves a lot of time (nearly a minute)
   for the unit tests."
  {"abc"      [3231929 5017308 5278568 5357525 5708769
               6082117 8036669 8605828 8609554 8760605
               9495334 10767910 11039607 12763908 13666005
               13753421 14810753 15274776 15819744 18455182
               20014135 23595234 25025007 25162359 26439072
               30594017 31051866 33050215 33841441 34415073]
   "reyedfim" [797564 938629 1617991 2104453 2564359
               2834991 3605750 7183955 7292419 7668370
               8059094 9738948 10098451 10105659 11395933
               12187005 13432325 17274562 18101341 19897122
               21475898 21671457 21679503 21842490 23036372
               23090544 25067104 26815976 27230372 27410373]})

;; Input parsing
(def parse first)

;; Puzzle logic
(defn md5-digest
  "For a given prefix, returns a function that will compute the MD5 digest 
   (in bytes) of the concatenation of that prefix and anything else"
  [prefix]
  (fn [idx] (d/md5-digest (str prefix idx))))

(defn five-zero-indices
  "A lazy sequence of the indices that, when appended to the string `prefix`,
   result in an MD5 hash that begins with five zeroes"
  [prefix]
  (filter (comp d/five-zero-start? (md5-digest prefix)) (range)))

(defn indices-to-try
  "A sequence of indices to try concatenating with prefix. If the values
   have already been cached, return those, else return an infinite range
   starting at zero."
  [prefix]
  (get cached-indices prefix (range)))

(defn five-zero-hashes
  "A sequence of the MD5 hashes (in bytes form) for consecutive prefix-number
   strings that start with five zeroes."
  [prefix]
  (->> (indices-to-try prefix)
       (map (md5-digest prefix))
       (filter d/five-zero-start?)))

(defn sixth-char
  "Returns a pair of the index and the hex value (0-f) of the sixth character 
   (in the string representation of) the MD5 digest"
  [idx digest]
  [idx (format "%x" (nth digest 2))])

(defn pos-char-pair
  "For a collection of bytes representing an MD5 hash, interpret the hash
   as having the sixth character represent a position and the seventh
   character represent the actual password character."
  [bytes]
  ;; We want the data in the 3rd and 4th bytes, so drop 2 and take 2.
  (let [[pos ch] (take 2 (drop 2 bytes))]
    ;; The position is just the value in the 3rd byte, but for the
    ;; the seventh character, we look at the 4th byte, map to a zero-padded
    ;; hex string and then only take the first character.
    [(int pos) (subs (format "%02x" ch) 0 1)]))

(defn password-chars
  "Apply the logic of part1 or part2 to emit pairs of position/character
   pairs in the deciphered password"
  [part hashes]
  (case part
    :part1 (map-indexed sixth-char hashes)
    :part2 (map pos-char-pair hashes)))

(defn set-char
  "If the character at position `idx` in collection `password` is seen for 
   the first time, update it to `c`, else return `password` untouched."
  [password [idx ch]]
  (if (= \* (get password idx))
    (assoc password idx ch)
    password))

(defn password
  "Using the puzzle logic given by `part` (either `:part1` or `:part2`),
   decipher the password for the door id (`prefix`)."
  [part prefix]
  (->> (five-zero-hashes prefix)
       (password-chars part)
       ;; Ignore any position index values outside the range 0-7 for
       ;; our 8-character password
       (filter #(<= 0 (first %) 7))
       ;; `reductions` returns a lazy seq of all intermediate password states.
       (reductions set-char [\* \* \* \* \* \* \* \*])
       ;; We drop intermediate states that still have an unset character
       (drop-while #(some (u/equals? \*) %))
       first
       str/join))

(def password-part1 (partial password :part1))
(def password-part2 (partial password :part2))

;; Puzzle solutions
(defn part1
  "Given a door id, what is the password using the logic in part 1"
  [input]
  (password-part1 input))

(defn part2
  "Given a door id, what is the password using the logic in part 2"
  [input]
  (password-part2 input))