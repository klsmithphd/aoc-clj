(ns aoc-clj.2020.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day23 :as t]))

(def d23-s00         [3 8 9 1 2 5 4 6 7])
(def d23-s00-move001 [2 8 9 1 5 4 6 7 3])
(def d23-s00-move002 [5 4 6 7 8 9 1 3 2])
(def d23-s00-move003 [8 9 1 3 4 6 7 2 5])
(def d23-s00-move004 [4 6 7 9 1 3 2 5 8])
(def d23-s00-move010 [8 3 7 4 1 9 2 6 5])
(def d23-s00-move100 [1 6 7 3 8 4 5 2 9])

(deftest moves
  (testing "Crab moves"
    (is (= d23-s00-move001 (t/representation (t/n-moves d23-s00 1))))
    (is (= d23-s00-move002 (t/representation (t/n-moves d23-s00 2))))
    (is (= d23-s00-move003 (t/representation (t/n-moves d23-s00 3))))
    (is (= d23-s00-move004 (t/representation (t/n-moves d23-s00 4))))
    (is (= d23-s00-move010 (t/representation (t/n-moves d23-s00 10))))
    (is (= d23-s00-move100 (t/representation (t/n-moves d23-s00 100))))))

;; Too slow
;; (deftest big-moves
;;   (is (= '(934001 159792) (take 2 (drop 1 (t/star-cups d23-s00 10000000))))))

(def day23-input (u/parse-puzzle-input t/parse 2020 23))

(deftest part1-test
  (testing "Reproduces the answer for day23, part1"
    (is (= "62934785" (t/part1 day23-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day23, part2"
    (is (= 693659135400 (t/part2 day23-input)))))