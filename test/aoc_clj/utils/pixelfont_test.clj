(ns aoc-clj.utils.pixelfont-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.pixelfont :as pf]))


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

(deftest pixel-string->str-test
  (testing "Can convert strings of block character pixels to a string"
    (is (= "EO" (pf/pixel-string->str pf/fontmap-6x5 EO-pixels-6x5)))))


(deftest pixels->str-test
  (testing "Converts block chacter pixels to a printable string representation"
    (is (= EO-str-6x5 (pf/pixels->str EO-pixels-6x5)))))