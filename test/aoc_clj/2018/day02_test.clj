(ns aoc-clj.2018.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day02 :as t]))

(deftest day02-part1-soln
  (testing "Reproduces the answer for day02, part1"
    (is (= 5478 (t/day02-part1-soln)))))

(deftest day02-part2-soln
  (testing "Reproduces the answer for day02, part2"
    (is (= "qyzphxoiseldjrntfygvdmanu" (t/day02-part2-soln)))))