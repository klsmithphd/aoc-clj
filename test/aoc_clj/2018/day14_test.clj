(ns aoc-clj.2018.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day14 :as d14]))

(deftest step-test
  (testing "Updates the state by one iteration"
    (is (= {:e1 0 :e2 1 :scores [3 7 1 0]}
           (d14/step d14/init-state)))
    (is (= {:e1 4 :e2 3 :scores [3 7 1 0 1 0]}
           (d14/step
            {:e1 0 :e2 1 :scores [3 7 1 0]})))
    (is (= {:e1 6 :e2 4 :scores [3 7 1 0 1 0 1]}
           (d14/step
            {:e1 4 :e2 3 :scores [3 7 1 0 1 0]})))
    (is (= {:e1 0 :e2 6 :scores [3 7 1 0 1 0 1 2]}
           (d14/step
            {:e1 6 :e2 4 :scores [3 7 1 0 1 0 1]})))))

(deftest ten-recipes-after-n
  (testing "The scores of the 10 recipes after making n recipes"
    (is (= "5158916779" (d14/ten-recipes-after-n 9)))
    (is (= "0124515891" (d14/ten-recipes-after-n 5)))
    (is (= "9251071085" (d14/ten-recipes-after-n 18)))
    (is (= "5941429882" (d14/ten-recipes-after-n 2018)))))

(deftest recipes-until-score
  (testing "How many recipes until the final scores match the digits provided"
    (is (= 9    (d14/recipes-until-score "51589" 10)))
    (is (= 5    (d14/recipes-until-score "01245" 10)))
    (is (= 18   (d14/recipes-until-score "92510" 20)))
    (is (= 2018 (d14/recipes-until-score "59414" 3000)))))

(def day14-input (u/parse-puzzle-input d14/parse 2018 14))

(deftest part1-test
  (testing "Reproduces the answer for day14, part1"
    (is (= "9315164154" (d14/part1 day14-input)))))

(deftest ^:slow part2-test
  (testing "Reproduces the answer for day14, part2"
    (is (= 20231866 (d14/part2 day14-input)))))
