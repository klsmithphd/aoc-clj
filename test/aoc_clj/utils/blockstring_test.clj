(ns aoc-clj.utils.blockstring-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.blockstring :as blstr]))

(def EO-pixels-6x5
  "The characters `EO` represented as pixels in the 6-by-5 font"
  [1 1 1 1 0 0 1 1 0 0
   1 0 0 0 0 1 0 0 1 0
   1 1 1 0 0 1 0 0 1 0
   1 0 0 0 0 1 0 0 1 0
   1 0 0 0 0 1 0 0 1 0
   1 1 1 1 0 0 1 1 0 0])

(def EO-str-6x5
  (str "####  ##  \n"
       "#    #  # \n"
       "###  #  # \n"
       "#    #  # \n"
       "#    #  # \n"
       "####  ##  "))

(def font-6x5-line1
  (str
   " ##  ###   ##  ###  #### ####  ##  #  #  ###   ## #  # #    #   #\n"
   "#  # #  # #  # #  # #    #    #  # #  #   #     # # #  #    #   #\n"
   "#  # ###  #    #  # ###  ###  #    ####   #     # ##   #    ## ##\n"
   "#### #  # #    #  # #    #    # ## #  #   #     # # #  #    # # #\n"
   "#  # #  # #  # #  # #    #    #  # #  #   #  #  # # #  #    #   #\n"
   "#  # ###   ##  ###  #### #     ### #  #  ###  ##  #  # #### #   #"))

(def font-6x5-line2
  (str
   "#  #  ##  ###   ##  ###   ### ######  # #  # #   ##  # #   ##### \n"
   "## # #  # #  # #  # #  # #      #  #  # #  # #   ##  # #   #   # \n"
   "# ## #  # #  # #  # #  # #      #  #  # #  # #   # ##   # #   #  \n"
   "#  # #  # ###  # ## ###   ##    #  #  # #  # # # # ##    #   #   \n"
   "#  # #  # #    #  # # #     #   #  #  #  ##  ## ###  #   #  #    \n"
   "#  #  ##  #     ## ##  # ###    #   ##   #   #   ##  #   #  #### "))

(def columns-sample
  ["a0" "a1" "a2" "b0" "b1" "b2" "c0" "c1" "c2"
   "a3" "a4" "a5" "b3" "b4" "b5" "c3" "c4" "c5"])

(def rows-sample
  ["a0" "a1" "a2" "a3" "a4" "a5"
   "b0" "b1" "b2" "b3" "b4" "b5"
   "c0" "c1" "c2" "c3" "c4" "c5"])

(deftest reweave-test
  (testing "Demonstration of data reweaving"
    ;; each *character* is 3 items wide. That's the first 3
    ;; We want to end up with 3 destination "rows" (one for each character)
    (is (= rows-sample (blstr/reweave 3 3 columns-sample)))
    ;; each *character* should end up 3 items wide. 
    ;; We should end up with 2 destination rows (representing a 2 pixel tall character)
    (is (= columns-sample (blstr/reweave 3 2 rows-sample)))))

(deftest pixel-string->str-test
  (testing "Can convert strings of block character pixels to a string"
    (is (= "EO" (blstr/blockstring->str blstr/fontmap-6x5 EO-pixels-6x5)))))

(deftest pixels->str-test
  (testing "Converts block chacter pixels to a printable string representation"
    (is (= EO-str-6x5 (blstr/printable-blockstring 6 EO-pixels-6x5)))))

(deftest str->pixels-test
  (testing "Converts a string into block character pixels"
    (is (= EO-pixels-6x5 (blstr/str->blockstring blstr/fontmap-6x5 "EO")))))

(deftest font-demo-test
  (testing "Reproduces a string representation of all the characters
            in the font alphabet"
    (is (= font-6x5-line1
           (blstr/str->printable-blockstring blstr/fontmap-6x5 "ABCDEFGHIJKLM")))
    (is (= font-6x5-line2
           (blstr/str->printable-blockstring blstr/fontmap-6x5 "NOPQRSTUVWXYZ")))))