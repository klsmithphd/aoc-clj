(ns aoc-clj.2022.day22-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day22 :as t]))

(def d22-s00
  (t/parse
   ["        ...#"
    "        .#.."
    "        #..."
    "        ...."
    "...#.......#"
    "........#..."
    "..#....#...."
    "..........#."
    "        ...#...."
    "        .....#.."
    "        .#......"
    "        ......#."
    ""
    "10R5L5R10L4R5L5"]))

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= (select-keys d22-s00 [:start :h-zones :v-zones :path])
           {;; The start point is the upper-leftmost non-empty cell
            :start [9 1]
            ;; Horizontal zones are broken down to indicate areas
            ;; of discontinuity. 
            ;; From y = 1 to 4, x-values are 9-12
            ;; From y = 5 to 8, x-values are 1-12
            ;; From y = 9 to 12, x-values are 9-16
            :h-zones '([[1 4] [9 12]] [[5 8] [1 12]] [[9 12] [9 16]])
            ;; Vertical zones are similar
            ;; From x = 1 to 8, y-values are 5-8
            ;; From x = 9 to 12, y-values are 1-12
            ;; From x = 13 to 16, y-values 9-12
            :v-zones '([[1 8] [5 8]] [[9 12] [1 12]] [[13 16] [9 12]])
            :path '(10 "R" 5 "L" 5 "R" 10 "L" 4 "R" 5 "L" 5)}))))

(defn sample-cube-wrap-around
  "This cube wrap-around logic is specific to the flattened cube
   layout in the sample data (`d22-s01`)"
  [_ facing [x y]]
  (case facing
    :U (cond
         (<= 1 x 4)   {:pos [(+ 9 (- 4 x)) 1]   :facing :D}
         (<= 5 x 8)   {:pos [9 (+ 1 (- x 5))]   :facing :R}
         (<= 9 x 12)  {:pos [(+ 4 (- 9 x)) 5]   :facing :D}
         (<= 13 x 16) {:pos [12 (+ 8 (- 13 x))] :facing :L})
    :D (cond
         (<= 1 x 4)   {:pos [(+ 9 (- 4 x)) 12]  :facing :U}
         (<= 5 x 8)   {:pos [9 (+ 9 (- 8 x))]   :facing :R}
         (<= 9 x 12)  {:pos [(+ 4 (- 9 x)) 8]   :facing :U}
         (<= 13 x 16) {:pos [1 (+ 5 (- 16 x))]  :facing :R})
    :L (cond
         (<= 1 y 4)   {:pos [(+ 8 (- y 4)) 5]   :facing :D}
         (<= 5 y 8)   {:pos [(+ 16 (- 5 y)) 12] :facing :U}
         (<= 9 y 12)  {:pos [(+ 5 (- 12 y)) 8]  :facing :U})
    :R (cond
         (<= 1 y 4)   {:pos [16 (+ 9 (- 4 y))]  :facing :L}
         (<= 5 y 8)   {:pos [(+ 16 (- 5 y)) 9]  :facing :D}
         (<= 9 y 12)  {:pos [(+ 1 (- 12 y)) 12] :facing :L})))


(deftest wrap-around-test
  (testing "Computes the wrap-around positions correctly"
    (is (= [9 1]  (:pos (t/wrap-around d22-s00 :R [13 1]))))
    (is (= [12 1] (:pos (t/wrap-around d22-s00 :L [8 1]))))
    (is (= [9 12] (:pos (t/wrap-around d22-s00 :U [9 0]))))
    (is (= [12 5] (:pos (t/wrap-around d22-s00 :L [0 5]))))))

(deftest follow-path-test
  (testing "Follows the path and arrives at the correct final position/orientation"
    (is (= {:pos [8 6] :facing :R}
           (t/follow-path (assoc d22-s00 :wrap-fn t/wrap-around))))))

(deftest final-password-test
  (testing "Computes the final password given the final position/orientation"
    (is (= 6032 (t/final-password
                 (t/follow-path (assoc d22-s00 :wrap-fn t/wrap-around)))))
    (is (= 5031 (t/final-password
                 (t/follow-path (assoc d22-s00 :wrap-fn sample-cube-wrap-around)))))))

(def day22-input (u/parse-puzzle-input t/parse 2022 22))

(deftest day22-part1-soln
  (testing "Reproduces the answer for day22, part1"
    (is (= 1428 (t/day22-part1-soln day22-input)))))

(deftest day22-part2-soln
  (testing "Reproduces the answer for day22, part2"
    (is (= 142380 (t/day22-part2-soln day22-input)))))