(ns aoc-clj.2020.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day21 :as t]))

;; (def d21-s00
;;   (t/parse
;;    ["mxmxvkd kfcds sqjhc nhms (contains dairy, fish)"
;;     "trh fvjkl sbzzf mxmxvkd (contains dairy)"
;;     "sqjhc fvjkl (contains soy)"
;;     "sqjhc mxmxvkd sbzzf (contains fish)"]))

(def day21-input (u/parse-puzzle-input t/parse 2020 21))

(deftest part1-test
  (testing "Reproduces the answer for day21, part1"
    (is (= 2280 (t/part1 day21-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day21, part2"
    (is (= "vfvvnm,bvgm,rdksxt,xknb,hxntcz,bktzrz,srzqtccv,gbtmdb"
           (t/part2 day21-input)))))