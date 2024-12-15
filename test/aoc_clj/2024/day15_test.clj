(ns aoc-clj.2024.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.2024.day15 :as d15]))

(def d15-s00-raw
  ["########"
   "#..O.O.#"
   "##@.O..#"
   "#...O..#"
   "#.#.O..#"
   "#...O..#"
   "#......#"
   "########"
   ""
   "<^^>>>vv<v>>v<<"])

(def d15-s00
  {:grid
   (mg/->MapGrid2D
    8 8
    {[0 7] :wall [1 7] :wall [2 7] :wall [3 7] :wall [4 7] :wall [5 7] :wall [6 7] :wall [7 7] :wall
     [0 6] :wall [1 6] :space [2 6] :space [3 6] :box [4 6] :space [5 6] :box [6 6] :space [7 6] :wall
     [0 5] :wall [1 5] :wall [2 5] :robot [3 5] :space [4 5] :box [5 5] :space [6 5] :space [7 5] :wall
     [0 4] :wall [1 4] :space [2 4] :space [3 4] :space [4 4] :box [5 4] :space [6 4] :space [7 4] :wall
     [0 3] :wall [1 3] :space [2 3] :wall [3 3] :space [4 3] :box [5 3] :space [6 3] :space [7 3] :wall
     [0 2] :wall [1 2] :space [2 2] :space [3 2] :space [4 2] :box [5 2] :space [6 2] :space [7 2] :wall
     [0 1] :wall [1 1] :space [2 1] :space [3 1] :space [4 1] :space [5 1] :space [6 1] :space [7 1] :wall
     [0 0] :wall [1 0] :wall [2 0] :wall [3 0] :wall [4 0] :wall [5 0] :wall [6 0] :wall [7 0] :wall})
   :moves
   [:west :north :north :east :east :east :south :south :west :south :east
    :east :south :west :west]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d15-s00 (d15/parse d15-s00-raw)))))

(def d15-s01
  (d15/parse
   ["##########"
    "#..O..O.O#"
    "#......O.#"
    "#.OO..O.O#"
    "#..O@..O.#"
    "#O#..O...#"
    "#O..O..O.#"
    "#.OO.O.OO#"
    "#....O...#"
    "##########"
    ""
    "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^"
    "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v"
    "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<"
    "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^"
    "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><"
    "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^"
    ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^"
    "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>"
    "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>"
    "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^"]))
