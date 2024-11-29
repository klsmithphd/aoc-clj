(ns aoc-clj.2018.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day15 :as d15]))

(def d15-s00-raw
  ["#########"
   "#G..G..G#"
   "#.......#"
   "#.......#"
   "#G..E..G#"
   "#.......#"
   "#.......#"
   "#G..G..G#"
   "#########"])

(def d15-s00
  {:walls #{[0 0] [1 0] [2 0] [3 0] [4 0] [5 0] [6 0] [7 0] [8 0]
            [0 1] [8 1]
            [0 2] [8 2]
            [0 3] [8 3]
            [0 4] [8 4]
            [0 5] [8 5]
            [0 6] [8 6]
            [0 7] [8 7]
            [0 8] [1 8] [2 8] [3 8] [4 8] [5 8] [6 8] [7 8] [8 8]}
   :units [{:pos [1 1] :type :goblin :hp 200}
           {:pos [4 1] :type :goblin :hp 200}
           {:pos [7 1] :type :goblin :hp 200}
           {:pos [1 4] :type :goblin :hp 200}
           {:pos [4 4] :type :elf    :hp 200}
           {:pos [7 4] :type :goblin :hp 200}
           {:pos [1 7] :type :goblin :hp 200}
           {:pos [4 7] :type :goblin :hp 200}
           {:pos [7 7] :type :goblin :hp 200}]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d15-s00 (d15/parse d15-s00-raw)))))

(def d15-s01
  (d15/parse
   ["#######"
    "#.G...#"
    "#...EG#"
    "#.#.#G#"
    "#..G#E#"
    "#.....#"
    "#######"]))

(def d15-s02
  (d15/parse
   ["#######"
    "#G..#E#"
    "#E#E.E#"
    "#G.##.#"
    "#...#E#"
    "#...E.#"
    "#######"]))

(def d15-s03
  (d15/parse
   ["#######"
    "#E..EG#"
    "#.#G.E#"
    "#E.##E#"
    "#G..#.#"
    "#..E#.#"
    "#######"]))

(def d15-s04
  (d15/parse
   ["#######"
    "#E.G#.#"
    "#.#G..#"
    "#G.#.G#"
    "#G..#.#"
    "#...E.#"
    "#######"]))

(def d15-s05
  (d15/parse
   ["#######"
    "#.E...#"
    "#.#..G#"
    "#.###.#"
    "#E#G#G#"
    "#...#G#"
    "#######"]))

(def d15-s06
  (d15/parse
   ["#########"
    "#G......#"
    "#.E.#...#"
    "#..##..G#"
    "#...##..#"
    "#...#...#"
    "#.G...G.#"
    "#.....G.#"
    "#########"]))