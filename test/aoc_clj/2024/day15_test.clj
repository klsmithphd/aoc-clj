(ns aoc-clj.2024.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.core :as u]
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
   [:w :n :n :e :e :e :s :s :w :s :e :e :s :w :w]})

(def d15-s00-finish
  (d15/parse
   ["########"
    "#....OO#"
    "##.....#"
    "#.....O#"
    "#.#O@..#"
    "#...O..#"
    "#...O..#"
    "########"
    ""
    ""]))

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

(def d15-s01-finish
  (d15/parse
   ["##########"
    "#.O.O.OOO#"
    "#........#"
    "#OO......#"
    "#OO@.....#"
    "#O#.....O#"
    "#O.....OO#"
    "#O.....OO#"
    "#OO....OO#"
    "##########"
    ""
    ""]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d15-s00 (d15/parse d15-s00-raw)))))

(deftest move-test
  (testing "Performs a move attempt by the robot and updates the state"
    ;; Can't move due to wall in the way
    (is (= {:grid (:grid d15-s00) :robot-pos [2 5]}
           (d15/move {:grid (:grid d15-s00) :robot-pos [2 5]} :w)))

    ;; Robot moves up one
    (is (= {:grid (-> (:grid d15-s00)
                      (assoc-in [:grid [2 5]] :space)
                      (assoc-in [:grid [2 6]] :robot))
            :robot-pos [2 6]}
           (d15/move {:grid (:grid d15-s00) :robot-pos [2 5]} :n)))

    ;; Robot moves right one
    (is (= {:grid (-> (:grid d15-s00)
                      (assoc-in [:grid [2 5]] :space)
                      (assoc-in [:grid [3 6]] :robot)
                      (assoc-in [:grid [4 6]] :box))
            :robot-pos [3 6]}
           (d15/move
            {:grid (-> (:grid d15-s00)
                       (assoc-in [:grid [2 5]] :space)
                       (assoc-in [:grid [2 6]] :robot))
             :robot-pos [2 6]}
            :e)))

    ;; Robot moves right one
    (is (= {:grid (-> (:grid d15-s00)
                      (assoc-in [:grid [2 5]] :space)
                      (assoc-in [:grid [3 6]] :space)
                      (assoc-in [:grid [4 6]] :robot)
                      (assoc-in [:grid [6 6]] :box))
            :robot-pos [4 6]}
           (d15/move
            {:grid (-> (:grid d15-s00)
                       (assoc-in [:grid [2 5]] :space)
                       (assoc-in [:grid [3 6]] :robot)
                       (assoc-in [:grid [4 6]] :box))
             :robot-pos [3 6]}
            :e)))

    ;; Robot can't move further right
    (is (= {:grid (-> (:grid d15-s00)
                      (assoc-in [:grid [2 5]] :space)
                      (assoc-in [:grid [3 6]] :space)
                      (assoc-in [:grid [4 6]] :robot)
                      (assoc-in [:grid [6 6]] :box))
            :robot-pos [4 6]}
           (d15/move
            {:grid (-> (:grid d15-s00)
                       (assoc-in [:grid [2 5]] :space)
                       (assoc-in [:grid [3 6]] :space)
                       (assoc-in [:grid [4 6]] :robot)
                       (assoc-in [:grid [6 6]] :box))
             :robot-pos [4 6]}
            :e)))

    ;; Robot moves line of boxes down one
    (is (= {:grid (-> (:grid d15-s00)
                      (assoc-in [:grid [2 5]] :space)
                      (assoc-in [:grid [3 6]] :space)
                      (assoc-in [:grid [4 6]] :space)
                      (assoc-in [:grid [4 5]] :robot)
                      (assoc-in [:grid [6 6]] :box)
                      (assoc-in [:grid [4 1]] :box))
            :robot-pos [4 5]}
           (d15/move
            {:grid (-> (:grid d15-s00)
                       (assoc-in [:grid [2 5]] :space)
                       (assoc-in [:grid [3 6]] :space)
                       (assoc-in [:grid [4 6]] :robot)
                       (assoc-in [:grid [6 6]] :box))
             :robot-pos [4 6]}
            :s)))))

(deftest all-moves-test
  (testing "Performs all the moves correctly and returns the end state"
    (is (= {:grid (:grid d15-s00-finish) :robot-pos [4 3]}
           (d15/all-moves d15-s00)))

    (is (= {:grid (:grid d15-s01-finish) :robot-pos [3 5]}
           (d15/all-moves d15-s01)))))

(deftest box-gps-sum
  (testing "Computes the sum of the GPS coordinates of all the boxes"
    (is (= 2028  (d15/box-gps-sum (d15/all-moves d15-s00))))
    (is (= 10092 (d15/box-gps-sum (d15/all-moves d15-s01))))))

(def day15-input (u/parse-puzzle-input d15/parse 2024 15))

(deftest part1-test
  (testing "Reproduces the answer for day15, part1"
    (is (= 1478649 (d15/part1 day15-input)))))