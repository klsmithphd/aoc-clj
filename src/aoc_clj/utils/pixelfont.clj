(ns aoc-clj.utils.pixelfont
  "Utilities for transforming block character *pixel fonts* to/from strings"
  (:require [clojure.string :as str]
            [aoc-clj.utils.binary :as bin]
            [aoc-clj.utils.core :as u]))

(def fontmap-6x5
  {:height 6
   :width 5
   :mapping
   {2r011001001010010111101001010010 \A
    2r111001001011100100101001011100 \B
    2r011001001010000100001001001100 \C
    2r111001001010010100101001011100 \D ;; guessed at character shape
    2r111101000011100100001000011110 \E
    2r111101000011100100001000010000 \F
    2r011001001010000101101001001110 \G
    2r100101001011110100101001010010 \H
    2r011100010000100001000010001110 \I
    2r001100001000010000101001001100 \J
    2r100101010011000101001010010010 \K
    2r100001000010000100001000011110 \L
    2r100011000111011101011000110001 \M ;; guessed at character shape
    2r100101101010110100101001010010 \N
    2r011001001010010100101001001100 \O
    2r111001001010010111001000010000 \P
    2r011001001010010101101001001101 \Q ;; guessed at character shape
    2r111001001010010111001010010010 \R
    2r011101000010000011000001011100 \S
    2r111110010000100001000010000100 \T ;; guessed at character shape
    2r100101001010010100101001001100 \U
    2r100101001010010100100110001000 \V ;; guessed at character shape
    2r100011000110001101011101110001 \W ;; guessed at character shape
    2r100101001001100011001001010010 \X ;; guessed at character shape
    2r100011000101010001000010000100 \Y
    2r111100001000100010001000011110 \Z}})

(defn pixel-char->binary
  [pixels]
  (->> (apply concat pixels)
       (concat ["2" "r"])
       str/join
       read-string))

(defn pixel-string->str
  [{:keys [height width mapping]} pixels]
  (->> (map #(partition width %) pixels)
       (apply interleave)
       (partition height)
       (map (comp mapping pixel-char->binary))
       str/join))

(defn pixels->str
  ([pixels]
   (pixels->str {0 \  1 \#} pixels))
  ([charmap pixels]
   (->> (map #(str/join (map charmap %)) pixels)
        (str/join "\n"))))

(defn- num->bitarray
  [width num]
  (->> (bin/int->bitstr width num)
       (map {\0 0 \1 1})))

(defn str->pixels
  [{:keys [height width mapping]} s]
  (let [inv-map (u/invert-map mapping)]
    (->>
     (map (comp #(partition width %) #(num->bitarray (* width height) %) inv-map) s)
     (apply interleave)
     (partition (count s))
     (map flatten))))

(defn str->block-str
  [fontmap s]
  (->> s (str->pixels fontmap) pixels->str))