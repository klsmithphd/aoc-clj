(ns aoc-clj.2023.day22-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day22 :as t]))

(def d22-s01-raw
  ["1,0,1~1,2,1"
   "0,0,2~2,0,2"
   "0,2,3~2,2,3"
   "0,0,4~0,2,4"
   "2,0,5~2,2,5"
   "0,1,6~2,1,6"
   "1,1,8~1,1,9"])

(def d22-s01
  [[[1 0 1] [1 2 1]]
   [[0 0 2] [2 0 2]]
   [[0 2 3] [2 2 3]]
   [[0 0 4] [0 2 4]]
   [[2 0 5] [2 2 5]]
   [[0 1 6] [2 1 6]]
   [[1 1 8] [1 1 9]]])

(def d22-s01-expanded
  [[[1 0 1] [1 1 1] [1 2 1]]
   [[0 0 2] [1 0 2] [2 0 2]]
   [[0 2 3] [1 2 3] [2 2 3]]
   [[0 0 4] [0 1 4] [0 2 4]]
   [[2 0 5] [2 1 5] [2 2 5]]
   [[0 1 6] [1 1 6] [2 1 6]]
   [[1 1 8] [1 1 9]]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d22-s01 (t/parse d22-s01-raw)))))

(deftest expanded-brick-coords-test
  (testing "Expands the input into occupied coordinate cells"
    (is (= d22-s01-expanded (map t/expanded-brick-coords d22-s01)))))

(deftest xy-vals-test
  (testing "Returns only the xy positions of a brick"
    (is (= [#{[1 0] [1 1] [1 2]}
            #{[0 0] [1 0] [2 0]}
            #{[0 2] [1 2] [2 2]}
            #{[0 0] [0 1] [0 2]}
            #{[2 0] [2 1] [2 2]}
            #{[0 1] [1 1] [2 1]}
            #{[1 1]}]
           (map t/xy-vals d22-s01-expanded)))))

(deftest lowest-z-test
  (testing "Computes the lowest z position of a brick"
    (is (= [1 2 3 4 5 6 8] (map t/lowest-z d22-s01-expanded)))))

(deftest place-brick-test
  (testing "Places each brick in correct location in sample data"
    (is (= {:z-index {1 #{[1 0] [1 1] [1 2]}}
            :bricks [[[1 0 1] [1 1 1] [1 2 1]]]}
           (t/place-brick {:z-index {} :bricks []} (nth d22-s01-expanded 0))))

    (is (= {:z-index {1 #{[1 0] [1 1] [1 2]}
                      2 #{[0 0] [1 0] [2 0]}}
            :bricks [[[1 0 1] [1 1 1] [1 2 1]]
                     [[0 0 2] [1 0 2] [2 0 2]]]}
           (t/place-brick {:z-index {1 #{[1 0] [1 1] [1 2]}}
                           :bricks [[[1 0 1] [1 1 1] [1 2 1]]]}
                          (nth d22-s01-expanded 1))))
    (is (= {:z-index {1 #{[1 0] [1 1] [1 2]}
                      2 #{[2 2] [0 0] [1 0] [0 2] [2 0] [1 2]}}
            :bricks [[[1 0 1] [1 1 1] [1 2 1]]
                     [[0 0 2] [1 0 2] [2 0 2]]
                     [[0 2 2] [1 2 2] [2 2 2]]]}
           (t/place-brick {:z-index {1 #{[1 0] [1 1] [1 2]}
                                     2 #{[0 0] [1 0] [2 0]}}
                           :bricks [[[1 0 1] [1 1 1] [1 2 1]]
                                    [[0 0 2] [1 0 2] [2 0 2]]]}
                          (nth d22-s01-expanded 2))))))

(deftest place-bricks-test
  (testing "Correctly determines resting place/coords of each brick"
    (is (= [[[1 0 1] [1 1 1] [1 2 1]]
            [[0 0 2] [1 0 2] [2 0 2]]
            [[0 2 2] [1 2 2] [2 2 2]]
            [[0 0 3] [0 1 3] [0 2 3]]
            [[2 0 3] [2 1 3] [2 2 3]]
            [[0 1 4] [1 1 4] [2 1 4]]
            [[1 1 5] [1 1 6]]]
           (:bricks (t/place-bricks d22-s01-expanded))))))


(def day22-input (u/parse-puzzle-input t/parse 2023 22))

(t/place-brick {:z-index {} :bricks []} (nth d22-s01-expanded 0))
(t/place-brick {:z-index {1 #{[1 0] [1 1] [1 2]}}
                :bricks [[[1 0 1] [1 1 1] [1 2 1]]]}
               (nth d22-s01-expanded 1))


