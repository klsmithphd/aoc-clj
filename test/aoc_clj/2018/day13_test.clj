(ns aoc-clj.2018.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day13 :as d13]))


(def d13-s00-raw
  ["/->-\\        "
   "|   |  /----\\"
   "| /-+--+-\\  |"
   "| | |  | v  |"
   "\\-+-/  \\-+--/"
   "  \\------/   "])

(def d13-s00
  (mg/->MapGrid2D 13 6
                  {[0 0] :curve-45
                   [1 0] :h
                   [2 0] :cart-r
                   [3 0] :h
                   [4 0] :curve-135
                   [0 1] :v
                   [4 1] :v
                   [7 1] :curve-45
                   [8 1] :h
                   [9 1] :h
                   [10 1] :h
                   [11 1] :h
                   [12 1] :curve-135
                   [0 2] :v
                   [2 2] :curve-45
                   [3 2] :h
                   [4 2] :intersection
                   [5 2] :h
                   [6 2] :h
                   [7 2] :intersection
                   [8 2] :h
                   [9 2] :curve-135
                   [12 2] :v
                   [0 3] :v
                   [2 3] :v
                   [4 3] :v
                   [7 3] :v
                   [9 3] :cart-d
                   [12 3] :v
                   [0 4] :curve-135
                   [1 4] :h
                   [2 4] :intersection
                   [3 4] :h
                   [4 4] :curve-45
                   [7 4] :curve-135
                   [8 4] :h
                   [9 4] :intersection
                   [10 4] :h
                   [11 4] :h
                   [12 4] :curve-45
                   [2 5] :curve-135
                   [3 5] :h
                   [4 5] :h
                   [5 5] :h
                   [6 5] :h
                   [7 5] :h
                   [8 5] :h
                   [9 5] :curve-45}))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d13-s00 (d13/parse d13-s00-raw)))))

(deftest carts-test
  (testing "Finds the carts in the map"
    (is (= #{{:pos [9 3] :heading :n :int-cnt 0}
             {:pos [2 0] :heading :e :int-cnt 0}}
           (set (d13/carts d13-s00))))))

(deftest tick-test
  (testing "Updates the cart state by one tick"
    (is (= [{:pos [3 0] :heading :e :int-cnt 0}
            {:pos [9 4] :heading :e :int-cnt 1}]
           (d13/tick d13-s00
                     [{:pos [2 0] :heading :e :int-cnt 0}
                      {:pos [9 3] :heading :n :int-cnt 0}])))

    (is (= [{:pos [4 0] :heading :n :int-cnt 0}
            {:pos [10 4] :heading :e :int-cnt 1}]
           (d13/tick d13-s00
                     [{:pos [3 0] :heading :e :int-cnt 0}
                      {:pos [9 4] :heading :e :int-cnt 1}])))

    (is (= [{:pos [4 1] :heading :n :int-cnt 0}
            {:pos [11 4] :heading :e :int-cnt 1}]
           (d13/tick d13-s00
                     [{:pos [4 0] :heading :n :int-cnt 0}
                      {:pos [10 4] :heading :e :int-cnt 1}])))

    (is (= [{:pos [4 2] :heading :e :int-cnt 1}
            {:pos [12 4] :heading :s :int-cnt 1}]
           (d13/tick d13-s00
                     [{:pos [4 1] :heading :n :int-cnt 0}
                      {:pos [11 4] :heading :e :int-cnt 1}])))

    (is (= [{:pos [5 2] :heading :e :int-cnt 1}
            {:pos [12 3] :heading :s :int-cnt 1}]
           (d13/tick d13-s00
                     [{:pos [4 2] :heading :e :int-cnt 1}
                      {:pos [12 4] :heading :s :int-cnt 1}])))

    (is (= [{:pos [6 2] :heading :e :int-cnt 1}
            {:pos [12 2] :heading :s :int-cnt 1}]
           (d13/tick d13-s00
                     [{:pos [5 2] :heading :e :int-cnt 1}
                      {:pos [12 3] :heading :s :int-cnt 1}])))

    (is (= [{:pos [7 2] :heading :e :int-cnt 2}
            {:pos [12 1] :heading :w :int-cnt 1}]
           (d13/tick d13-s00
                     [{:pos [6 2] :heading :e :int-cnt 1}
                      {:pos [12 2] :heading :s :int-cnt 1}])))

    (is (= [{:pos [8 2] :heading :e :int-cnt 2}
            {:pos [11 1] :heading :w :int-cnt 1}]
           (d13/tick d13-s00
                     [{:pos [7 2] :heading :e :int-cnt 2}
                      {:pos [12 1] :heading :w :int-cnt 1}])))

    (is (= [{:pos [9 4] :heading :w :int-cnt 3}
            {:pos [8 1] :heading :w :int-cnt 1}]
           (d13/tick d13-s00
                     [{:pos [9 3] :heading :n :int-cnt 2}
                      {:pos [9 1] :heading :w :int-cnt 1}])))))

(deftest first-crash-test
  (testing "Finds the location of the first crash"
    (is (= "7,3" (d13/first-crash d13-s00)))))

(def day13-input (u/parse-puzzle-input d13/parse 2018 13))

(deftest part1-test
  (testing "Reproduces the answer for day13, part1"
    (is (= "112,69" (d13/part1 day13-input)))))