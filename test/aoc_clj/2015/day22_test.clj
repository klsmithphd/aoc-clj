(ns aoc-clj.2015.day22-test
  (:require [clojure.test :refer [deftest testing is]]
            ;; [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day22 :as t]))

(def day22-sample1
  {:player {:hit-points 10 :mana 250 :armor 0}
   :boss   {:hit-points 13 :damage 8}
   :effects {}})
(def day22-sample1-moves [:poison :magic-missile])

(def day22-sample2
  {:player {:hit-points 10 :mana 250 :armor 0}
   :boss   {:hit-points 14 :damage 8}
   :effects {}})
(def day22-sample2-moves [:recharge :shield :drain :poison :magic-missile])


(deftest sample-battles
  (testing "Arrives at the correct end state for the sample battles"
    (is (= {:player {:hit-points 2 :mana 24 :armor 0}
            :boss   {:hit-points 0 :damage 8}
            :effects {:poison 3}}
           (reduce t/combat-round day22-sample1 day22-sample1-moves)))
    (is (= {:player {:hit-points 1 :mana 114 :armor 0}
            :boss   {:hit-points -1 :damage 8}
            :effects {:poison 3}}
           (reduce t/combat-round day22-sample2 day22-sample2-moves)))))

;; (def day22-input (u/parse-puzzle-input t/parse 2015 22))

(deftest day22-part1-soln
  (testing "Reproduces the answer for day22, part1"
    (is (= 1269 (t/day22-part1-soln)))))

(deftest day22-part2-soln
  (testing "Reproduces the answer for day22, part2"
    (is (= 1309 (t/day22-part2-soln)))))