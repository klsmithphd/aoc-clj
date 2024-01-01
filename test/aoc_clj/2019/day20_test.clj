(ns aoc-clj.2019.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.graph :as g :refer [edges]]
            [aoc-clj.2019.day20 :as t]))

(def d20-s00
  ["         A           "
   "         A           "
   "  #######.#########  "
   "  #######.........#  "
   "  #######.#######.#  "
   "  #######.#######.#  "
   "  #######.#######.#  "
   "  #####  B    ###.#  "
   "BC...##  C    ###.#  "
   "  ##.##       ###.#  "
   "  ##...DE  F  ###.#  "
   "  #####    G  ###.#  "
   "  #########.#####.#  "
   "DE..#######...###.#  "
   "  #.#########.###.#  "
   "FG..#########.....#  "
   "  ###########.#####  "
   "             Z       "
   "             Z       "])

(def d20-s01
  ["                   A               "
   "                   A               "
   "  #################.#############  "
   "  #.#...#...................#.#.#  "
   "  #.#.#.###.###.###.#########.#.#  "
   "  #.#.#.......#...#.....#.#.#...#  "
   "  #.#########.###.#####.#.#.###.#  "
   "  #.............#.#.....#.......#  "
   "  ###.###########.###.#####.#.#.#  "
   "  #.....#        A   C    #.#.#.#  "
   "  #######        S   P    #####.#  "
   "  #.#...#                 #......VT"
   "  #.#.#.#                 #.#####  "
   "  #...#.#               YN....#.#  "
   "  #.###.#                 #####.#  "
   "DI....#.#                 #.....#  "
   "  #####.#                 #.###.#  "
   "ZZ......#               QG....#..AS"
   "  ###.###                 #######  "
   "JO..#.#.#                 #.....#  "
   "  #.#.#.#                 ###.#.#  "
   "  #...#..DI             BU....#..LF"
   "  #####.#                 #.#####  "
   "YN......#               VT..#....QG"
   "  #.###.#                 #.###.#  "
   "  #.#...#                 #.....#  "
   "  ###.###    J L     J    #.#.###  "
   "  #.....#    O F     P    #.#...#  "
   "  #.###.#####.#.#####.#####.###.#  "
   "  #...#.#.#...#.....#.....#.#...#  "
   "  #.#####.###.###.#.#.#########.#  "
   "  #...#.#.....#...#.#.#.#.....#.#  "
   "  #.###.#####.###.###.#.#.#######  "
   "  #.#.........#...#.............#  "
   "  #########.###.###.#############  "
   "           B   J   C               "
   "           U   P   P               "])

(deftest label-locations-test
  (testing "Can find the maze coordinates of all of the labels"
    (is (= {[7 0] ["AA" :outer],
            [0 6] ["BC" :outer], [0 11] ["DE" :outer], [0 13] ["FG" :outer],
            [7 4] ["BC" :inner],
            [4 8] ["DE" :inner],
            [9 10] ["FG" :inner],
            [11 14] ["ZZ" :outer]}
           (t/label-locations d20-s00)))
    (is (= {[17 0] ["AA" :outer]
            [0 13] ["DI" :outer], [0 17] ["JO" :outer], [0 21] ["YN" :outer],
            [9 32] ["BU" :outer], [13 32] ["JP" :outer], [17 32] ["CP" :outer],
            [30 9] ["VT" :outer], [30 15] ["AS" :outer], [30 19] ["LF" :outer], [30 21] ["QG" :outer],
            [15 6] ["AS" :inner], [19 6] ["CP" :inner],
            [6 19] ["DI" :inner]
            [11 26] ["JO" :inner], [13 26] ["LF" :inner], [19 26] ["JP" :inner],
            [24 11] ["YN" :inner], [24 15] ["QG" :inner], [24 19] ["BU" :inner], [24 21] ["VT" :inner],
            [0 15] ["ZZ" :outer]}
           (t/label-locations d20-s01)))))


(deftest neighbor-coords-test
  (testing "Neighbor lookup follows portals"
    (let [graph (:graph (t/maze-with-portals (t/load-maze d20-s00)))]
      (is (= [[7 1] [0 6]] (edges graph [7 4])))
      (is (= [[0 6] [0 11]] (edges graph [4 8])))
      (is (= [[0 11] [9 10]] (edges graph [0 13]))))))

(deftest shortest-path-test
  (testing "Can find the shortest path when using portals"
    (is (= 23 (t/solve-maze d20-s00)))
    (is (= 58 (t/solve-maze d20-s01)))))

(def d20-s02
  ["             Z L X W       C                 "
   "             Z P Q B       K                 "
   "  ###########.#.#.#.#######.###############  "
   "  #...#.......#.#.......#.#.......#.#.#...#  "
   "  ###.#.#.#.#.#.#.#.###.#.#.#######.#.#.###  "
   "  #.#...#.#.#...#.#.#...#...#...#.#.......#  "
   "  #.###.#######.###.###.#.###.###.#.#######  "
   "  #...#.......#.#...#...#.............#...#  "
   "  #.#########.#######.#.#######.#######.###  "
   "  #...#.#    F       R I       Z    #.#.#.#  "
   "  #.###.#    D       E C       H    #.#.#.#  "
   "  #.#...#                           #...#.#  "
   "  #.###.#                           #.###.#  "
   "  #.#....OA                       WB..#.#..ZH"
   "  #.###.#                           #.#.#.#  "
   "CJ......#                           #.....#  "
   "  #######                           #######  "
   "  #.#....CK                         #......IC"
   "  #.###.#                           #.###.#  "
   "  #.....#                           #...#.#  "
   "  ###.###                           #.#.#.#  "
   "XF....#.#                         RF..#.#.#  "
   "  #####.#                           #######  "
   "  #......CJ                       NM..#...#  "
   "  ###.#.#                           #.###.#  "
   "RE....#.#                           #......RF"
   "  ###.###        X   X       L      #.#.#.#  "
   "  #.....#        F   Q       P      #.#.#.#  "
   "  ###.###########.###.#######.#########.###  "
   "  #.....#...#.....#.......#...#.....#.#...#  "
   "  #####.#.###.#######.#######.###.###.#.#.#  "
   "  #.......#.......#.#.#.#.#...#...#...#.#.#  "
   "  #####.###.#####.#.#.#.#.###.###.#.###.###  "
   "  #.......#.....#.#...#...............#...#  "
   "  #############.#.#.###.###################  "
   "               A O F   N                     "
   "               A A D   M                     "])

(deftest shortest-path-3d-test
  (testing "Can find the shortest path through the recursive maze"
    (is (= 26 (t/solve-recursive-maze d20-s00)))
    (is (= 396 (t/solve-recursive-maze d20-s02)))))

(def day20-input (u/parse-puzzle-input t/parse 2019 20))

(deftest day20-part1-soln-test
  (testing "Can reproduce the solution for part1"
    (is (= 578 (t/day20-part1-soln day20-input)))))

(deftest day20-part2-soln-test
  (testing "Can reproduce the solution for part2"
    (is (= 6592 (t/day20-part2-soln day20-input)))))
