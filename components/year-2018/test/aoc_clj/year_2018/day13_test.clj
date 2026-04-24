(ns aoc-clj.year-2018.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.grid.interface :as mg]
            [aoc-clj.util.interface :as u]
            [aoc-clj.year-2018.day13 :as d13]))


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
                  {[0 0] :curve-45
                   [0 1] :h
                   [0 2] :cart-r
                   [0 3] :h
                   [0 4] :curve-135
                   [1 0] :v
                   [1 4] :v
                   [1 7] :curve-45
                   [1 8] :h
                   [1 9] :h
                   [1 10] :h
                   [1 11] :h
                   [1 12] :curve-135
                   [2 0] :v
                   [2 2] :curve-45
                   [2 3] :h
                   [2 4] :intersection
                   [2 5] :h
                   [2 6] :h
                   [2 7] :intersection
                   [2 8] :h
                   [2 9] :curve-135
                   [2 12] :v
                   [3 0] :v
                   [3 2] :v
                   [3 4] :v
                   [3 7] :v
                   [3 9] :cart-d
                   [3 12] :v
                   [4 0] :curve-135
                   [4 1] :h
                   [4 2] :intersection
                   [4 3] :h
                   [4 4] :curve-45
                   [4 7] :curve-135
                   [4 8] :h
                   [4 9] :intersection
                   [4 10] :h
                   [4 11] :h
                   [4 12] :curve-45
                   [5 2] :curve-135
                   [5 3] :h
                   [5 4] :h
                   [5 5] :h
                   [5 6] :h
                   [5 7] :h
                   [5 8] :h
                   [5 9] :curve-45}))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d13-s00 (d13/parse d13-s00-raw)))))

(deftest carts-test
  (testing "Finds the carts in the map"
    (is (= #{{:pos [3 9] :heading :s :int-cnt 0}
             {:pos [0 2] :heading :e :int-cnt 0}}
           (d13/carts d13-s00)))))

(deftest cart-order
  (testing "Places carts in the correct order"
    (is (= [{:pos [0 2]} {:pos [3 9]}]
           (d13/cart-order [{:pos [3 9]} {:pos [0 2]}])))

    (is (= [{:pos [3 0]} {:pos [4 2]} {:pos [4 3]}]
           (d13/cart-order [{:pos [4 3]} {:pos [3 0]} {:pos [4 2]}])))))

(deftest tick-part1-test
  (testing "Updates the cart state by one tick"
    (is (= #{{:pos [4 9] :heading :e :int-cnt 1}
             {:pos [0 3] :heading :e :int-cnt 0}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [3 9] :heading :s :int-cnt 0}
                      {:pos [0 2] :heading :e :int-cnt 0}})))))

    (is (= #{{:pos [4 10] :heading :e :int-cnt 1}
             {:pos [0 4] :heading :s :int-cnt 0}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [4 9] :heading :e :int-cnt 1}
                      {:pos [0 3] :heading :e :int-cnt 0}})))))

    (is (= #{{:pos [4 11] :heading :e :int-cnt 1}
             {:pos [1 4] :heading :s :int-cnt 0}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [4 10] :heading :e :int-cnt 1}
                      {:pos [0 4] :heading :s :int-cnt 0}})))))

    (is (= #{{:pos [4 12] :heading :n :int-cnt 1}
             {:pos [2 4] :heading :e :int-cnt 1}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [4 11] :heading :e :int-cnt 1}
                      {:pos [1 4] :heading :s :int-cnt 0}})))))

    (is (= #{{:pos [3 12] :heading :n :int-cnt 1}
             {:pos [2 5] :heading :e :int-cnt 1}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [4 12] :heading :n :int-cnt 1}
                      {:pos [2 4] :heading :e :int-cnt 1}})))))

    (is (= #{{:pos [2 12] :heading :n :int-cnt 1}
             {:pos [2 6] :heading :e :int-cnt 1}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [3 12] :heading :n :int-cnt 1}
                      {:pos [2 5] :heading :e :int-cnt 1}})))))

    (is (= #{{:pos [1 12] :heading :w :int-cnt 1}
             {:pos [2 7] :heading :e :int-cnt 2}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [2 12] :heading :n :int-cnt 1}
                      {:pos [2 6] :heading :e :int-cnt 1}})))))

    (is (= #{{:pos [1 11] :heading :w :int-cnt 1}
             {:pos [2 8] :heading :e :int-cnt 2}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [1 12] :heading :w :int-cnt 1}
                      {:pos [2 7] :heading :e :int-cnt 2}})))))

    (is (= #{{:pos [1 10] :heading :w :int-cnt 1}
             {:pos [2 9] :heading :s :int-cnt 2}}
           (:carts
            (d13/tick-part1
             (assoc d13-s00 :carts
                    #{{:pos [1 11] :heading :w :int-cnt 1}
                      {:pos [2 8] :heading :e :int-cnt 2}})))))))

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
