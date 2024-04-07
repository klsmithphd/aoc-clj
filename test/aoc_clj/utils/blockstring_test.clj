(ns aoc-clj.utils.blockstring-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.blockstring :as blstr]))

(def EO-pixels-6x5
  "The characters `EO` represented as pixels in the 6-by-5 font"
  [[1 1 1 1 0 0 1 1 0 0]
   [1 0 0 0 0 1 0 0 1 0]
   [1 1 1 0 0 1 0 0 1 0]
   [1 0 0 0 0 1 0 0 1 0]
   [1 0 0 0 0 1 0 0 1 0]
   [1 1 1 1 0 0 1 1 0 0]])

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

(deftest pixel-string->str-test
  (testing "Can convert strings of block character pixels to a string"
    (is (= "EO" (blstr/blockstring->str blstr/fontmap-6x5 EO-pixels-6x5)))))

(deftest pixels->str-test
  (testing "Converts block chacter pixels to a printable string representation"
    (is (= EO-str-6x5 (blstr/printable-blockstring EO-pixels-6x5)))))

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