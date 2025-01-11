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

(def d13-s01
  (d13/parse
   ["/>-<\\  "
    "|   |  "
    "| /<+-\\"
    "| | | v"
    "\\>+</ |"
    "  |   ^"
    "  \\<->/"]))

(def d13-s00
  (mg/->MapGrid2D 13 6
                  {[0 5] :curve-45
                   [1 5] :h
                   [2 5] :cart-r
                   [3 5] :h
                   [4 5] :curve-135
                   [0 4] :v
                   [4 4] :v
                   [7 4] :curve-45
                   [8 4] :h
                   [9 4] :h
                   [10 4] :h
                   [11 4] :h
                   [12 4] :curve-135
                   [0 3] :v
                   [2 3] :curve-45
                   [3 3] :h
                   [4 3] :intersection
                   [5 3] :h
                   [6 3] :h
                   [7 3] :intersection
                   [8 3] :h
                   [9 3] :curve-135
                   [12 3] :v
                   [0 2] :v
                   [2 2] :v
                   [4 2] :v
                   [7 2] :v
                   [9 2] :cart-d
                   [12 2] :v
                   [0 1] :curve-135
                   [1 1] :h
                   [2 1] :intersection
                   [3 1] :h
                   [4 1] :curve-45
                   [7 1] :curve-135
                   [8 1] :h
                   [9 1] :intersection
                   [10 1] :h
                   [11 1] :h
                   [12 1] :curve-45
                   [2 0] :curve-135
                   [3 0] :h
                   [4 0] :h
                   [5 0] :h
                   [6 0] :h
                   [7 0] :h
                   [8 0] :h
                   [9 0] :curve-45}))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d13-s00 (d13/parse d13-s00-raw)))))

(deftest carts-test
  (testing "Finds the carts in the map"
    (is (= #{{:pos [9 2] :heading :s :int-cnt 0}
             {:pos [2 5] :heading :e :int-cnt 0}}
           (d13/carts d13-s00)))))

(deftest cart-order
  (testing "Places carts in the correct order"
    (is (= [{:pos [2 5]} {:pos [9 2]}]
           (d13/cart-order [{:pos [9 2]} {:pos [2 5]}])))

    (is (= [{:pos [0 2]} {:pos [2 1]} {:pos [3 1]}]
           (d13/cart-order [{:pos [3 1]} {:pos [0 2]} {:pos [2 1]}])))))

(deftest tick-part1-test
  (testing "Updates the cart state by one tick"
    (is (= #{{:pos [9 1] :heading :e :int-cnt 1}
             {:pos [3 5] :heading :e :int-cnt 0}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [9 2] :heading :s :int-cnt 0}
                      {:pos [2 5] :heading :e :int-cnt 0}})))))

    (is (= #{{:pos [10 1] :heading :e :int-cnt 1}
             {:pos [4 5] :heading :s :int-cnt 0}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [9 1] :heading :e :int-cnt 1}
                      {:pos [3 5] :heading :e :int-cnt 0}})))))

    (is (= #{{:pos [11 1] :heading :e :int-cnt 1}
             {:pos [4 4] :heading :s :int-cnt 0}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [10 1] :heading :e :int-cnt 1}
                      {:pos [4 5] :heading :s :int-cnt 0}})))))

    (is (= #{{:pos [12 1] :heading :n :int-cnt 1}
             {:pos [4 3] :heading :e :int-cnt 1}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [11 1] :heading :e :int-cnt 1}
                      {:pos [4 4] :heading :s :int-cnt 0}})))))

    (is (= #{{:pos [12 2] :heading :n :int-cnt 1}
             {:pos [5 3] :heading :e :int-cnt 1}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [12 1] :heading :n :int-cnt 1}
                      {:pos [4 3] :heading :e :int-cnt 1}})))))

    (is (= #{{:pos [12 3] :heading :n :int-cnt 1}
             {:pos [6 3] :heading :e :int-cnt 1}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [12 2] :heading :n :int-cnt 1}
                      {:pos [5 3] :heading :e :int-cnt 1}})))))

    (is (= #{{:pos [12 4] :heading :w :int-cnt 1}
             {:pos [7 3] :heading :e :int-cnt 2}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [12 3] :heading :n :int-cnt 1}
                      {:pos [6 3] :heading :e :int-cnt 1}})))))

    (is (= #{{:pos [11 4] :heading :w :int-cnt 1}
             {:pos [8 3] :heading :e :int-cnt 2}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [12 4] :heading :w :int-cnt 1}
                      {:pos [7 3] :heading :e :int-cnt 2}})))))

    (is (= #{{:pos [10 4] :heading :w :int-cnt 1}
             {:pos [9 3] :heading :s :int-cnt 2}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [11 4] :heading :w :int-cnt 1}
                      {:pos [8 3] :heading :e :int-cnt 2}})))))))

(deftest first-crash-test
  (testing "Finds the location of the first crash"
    (is (= "7,3" (d13/first-crash d13-s00)))))

(deftest last-cart-test
  (testing "Returns the coordinate of the last cart after crashed carts are all removed"
    (is (= "6,4" (d13/last-cart d13-s01)))))

(def day13-input (u/parse-puzzle-input d13/parse 2018 13))

(deftest part1-test
  (testing "Reproduces the answer for day13, part1"
    (is (= "118,112" (d13/part1 day13-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day13, part2"
    (is (= "50,21" (d13/part2 day13-input)))))