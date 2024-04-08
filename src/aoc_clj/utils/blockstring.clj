(ns aoc-clj.utils.blockstring
  "Utilities for transforming block character strings (represented in 2d pixel
   arrays) to and from normal character strings"
  (:require [clojure.string :as str]
            [aoc-clj.utils.binary :as bin]
            [aoc-clj.utils.core :as u]))

(def fontmap-6x5
  "A fontmap for a set of 6-tall by 5-wide uppercase block characters"
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

(defn reweave
  "Reorder a vec representing 2d data"
  [char-width chunks v]
  (->> v
       (partition char-width)
       (partition chunks)
       (apply interleave)
       (apply concat)))

(defn blockstring->str
  "Converts a blockstring into the normal string it represents, given
   a fontmap"
  [{:keys [height width mapping]} blockstring]
  (let [n-chars   (/ (count blockstring) height width)
        char-size (* height width)]
    (->> blockstring
         (reweave width n-chars)
         (partition char-size)
         (map (comp mapping bin/bitstr->int str/join))
         str/join)))

(defn printable-blockstring
  "Converts a blockstring into a printable string representation"
  ([height pixels]
   (printable-blockstring {0 \  1 \#} height pixels))
  ([charmap height pixels]
   (->> (map charmap pixels)
        (partition (/ (count pixels) height))
        (map str/join)
        (str/join "\n"))))

(defn- num->bitseq
  "Convert a number into a sequence of bits, padded with zeros to `width`
   if necessary"
  [width num]
  (->> (bin/int->bitstr width num)
       (map {\0 0 \1 1})))

(defn str->blockstring
  "Convert a string `s` into a blockstring given a fontmap"
  [{:keys [height width mapping]} s]
  (let [inv-map (u/invert-map mapping)
        char-size (* width height)]
    (->> (mapcat #(num->bitseq char-size (inv-map %)) s)
         (reweave width height)
         vec)))

(defn str->printable-blockstring
  "Convert a string `s` into a printable representation of a blockstring
   given a fontmap"
  [{:keys [height] :as fontmap} s]
  (->> s (str->blockstring fontmap) (printable-blockstring height)))