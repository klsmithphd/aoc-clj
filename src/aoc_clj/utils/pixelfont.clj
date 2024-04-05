(ns aoc-clj.utils.pixelfont
  "Utilities for transforming block character *pixel fonts* to/from strings"
  (:require [clojure.string :as str]))

2r011001001010010111101001010010


(def charmap-6x5
  "2r011001001010010111101001010010"
  {"A" [" ##  "
        "#  # "
        "#  # "
        "#### "
        "#  # "
        "#  # "]

   "B" ["###  "
        "#  # "
        "###  "
        "#  # "
        "#  # "
        "###  "]

   "C" [" ##  "
        "#  # "
        "#    "
        "#    "
        "#  # "
        " ##  "]

   ;; Guess
   "D" ["###  "
        "#  # "
        "#  # "
        "#  # "
        "#  # "
        "###  "]

   "E" ["#### "
        "#    "
        "###  "
        "#    "
        "#    "
        "#### "]

   "F" ["#### "
        "#    "
        "###  "
        "#    "
        "#    "
        "#    "]

   "G" [" ##  "
        "#  # "
        "#    "
        "# ## "
        "#  # "
        " ### "]

   "H" ["#  # "
        "#  # "
        "#### "
        "#  # "
        "#  # "
        "#  # "]

   "I" [" ### "
        "  #  "
        "  #  "
        "  #  "
        "  #  "
        " ### "]

   "J" ["  ## "
        "   # "
        "   # "
        "   # "
        "#  # "
        " ##  "]

   "K" ["#  # "
        "# #  "
        "##   "
        "# #  "
        "# #  "
        "#  # "]

   "L" ["#    "
        "#    "
        "#    "
        "#    "
        "#    "
        "#### "]

   ;; Guess
   "M" ["#   #"
        "#   #"
        "## ##"
        "# # #"
        "#   #"
        "#   #"]

   ;; Guess
   "N" ["#  # "
        "## # "
        "# ## "
        "#  # "
        "#  # "
        "#  # "]

   "O" [" ##  "
        "#  # "
        "#  # "
        "#  # "
        "#  # "
        " ##  "]

   "P" ["###  "
        "#  # "
        "#  # "
        "###  "
        "#    "
        "#    "]

   ;; Guess
   "Q" [" ##  "
        "#  # "
        "#  # "
        "# ## "
        "#  # "
        " ## #"]

   "R" ["###  "
        "#  # "
        "#  # "
        "###  "
        "# #  "
        "#  # "]

   "S" [" ### "
        "#    "
        "#    "
        " ##  "
        "   # "
        "###  "]

   ;; Guess
   "T" ["#####"
        "  #  "
        "  #  "
        "  #  "
        "  #  "
        "  #  "]

   "U" ["#  # "
        "#  # "
        "#  # "
        "#  # "
        "#  # "
        " ##  "]

   ;; Guess
   "V" ["#  # "
        "#  # "
        "#  # "
        "#  # "
        " ##  "
        " #   "]

   ;; Guess
   "W" ["#   #"
        "#   #"
        "#   #"
        "# # #"
        "## ##"
        "#   #"]

   ;; Guess
   "X" ["#  # "
        "#  # "
        " ##  "
        " ##  "
        "#  # "
        "#  #"]

   "Y" ["#   #"
        "#   #"
        " # # "
        "  #  "
        "  #  "
        "  #  "]

   "Z" ["#### "
        "   # "
        "  #  "
        " #   "
        "#    "
        "#### "]})


(def foo
  ["####  ##  "
   "#    #  # "
   "###  #  # "
   "#    #  # "
   "#    #  # "
   "####  ##  "])

(def pixels
  [[1 1 1 1 0 0 1 1 0 0]
   [1 0 0 0 0 1 0 0 1 0]
   [1 1 1 0 0 1 0 0 1 0]
   [1 0 0 0 0 1 0 0 1 0]
   [1 0 0 0 0 1 0 0 1 0]
   [1 1 1 1 0 0 1 1 0 0]])


(defn block-chars
  [pixels width]
  (let [height (count pixels)]
    (->> (map #(partition width %) pixels)
         (apply interleave)
         (partition height))))


(println (str/join "\n" (map str/join (second (block-chars foo 5)))))