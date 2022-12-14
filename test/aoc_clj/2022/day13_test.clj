(ns aoc-clj.2022.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day13 :as t]))

(def d13-s01
  (t/parse
   ["[1,1,3,1,1]"
    "[1,1,5,1,1]"
    ""
    "[[1],[2,3,4]]"
    "[[1],4]"
    ""
    "[9]"
    "[[8,7,6]]"
    ""
    "[[4,4],4,4]"
    "[[4,4],4,4,4]"
    ""
    "[7,7,7,7]"
    "[7,7,7]"
    ""
    "[]"
    "[3]"
    ""
    "[[[]]]"
    "[[]]"
    ""
    "[1,[2,[3,[4,[5,6,7]]]],8,9]"
    "[1,[2,[3,[4,[5,6,0]]]],8,9]"]))


(deftest in-order?-test
  (testing "Follows the correct in-order logic for the sample data"
    ;; If the right list runs out of items first, 
    ;; the inputs are not in the right order.
    (is (true? (t/in-order? [1 2] [2])))
    (is (true?  (apply t/in-order? (nth d13-s01 0))))
    (is (true?  (apply t/in-order? (nth d13-s01 1))))
    (is (false? (apply t/in-order? (nth d13-s01 2))))
    (is (true?  (apply t/in-order? (nth d13-s01 3))))
    (is (false? (apply t/in-order? (nth d13-s01 4))))
    (is (true?  (apply t/in-order? (nth d13-s01 5))))
    (is (false? (apply t/in-order? (nth d13-s01 6))))
    (is (false? (apply t/in-order? (nth d13-s01 7))))))

(deftest right-order-packet-id-sum-test
  (testing "The sum of the packet pair ids for in-order packets"
    (is (= 13 (t/right-order-packet-id-sum d13-s01)))))

(deftest day13-part1-soln
  (testing "Reproduces the answer for day13, part1"
    (is (= 5503 (t/day13-part1-soln)))))

;; (deftest day13-part2-soln
;;   (testing "Reproduces the answer for day13, part2"
;;     (is (= 0 (t/day13-part2-soln)))))