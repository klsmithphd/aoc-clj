(ns aoc-clj.2024.day15-test
  (:require [clojure.test :refer [deftest testing is]]
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
  {:robot [2 2]
   :walls #{[0 0] [1 0] [2 0] [3 0] [4 0] [5 0] [6 0] [7 0]
            [0 1] [7 1]
            [0 2] [1 2] [7 2]
            [0 3] [7 3]
            [0 4] [2 4] [7 4]
            [0 5] [7 5]
            [0 6] [7 6]
            [0 7] [1 7] [2 7] [3 7] [4 7] [5 7] [6 7] [7 7]}
   :boxes #{[3 1] [5 1]
            [4 2]
            [4 3]
            [4 4]
            [4 5]}
   :moves [:w :n :n :e :e :e :s :s :w :s :e :e :s :w :w]})

(def d15-s00-finish
  (-> ["########"
       "#....OO#"
       "##.....#"
       "#.....O#"
       "#.#O@..#"
       "#...O..#"
       "#...O..#"
       "########"
       ""
       ""]
      d15/parse
      (u/without-keys [:moves])))

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
  (-> ["##########"
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
       ""]
      d15/parse
      (u/without-keys [:moves])))

(def d15-s02
  (d15/parse
   ["#######"
    "#...#.#"
    "#.....#"
    "#..OO@#"
    "#..O..#"
    "#.....#"
    "#######"
    ""
    "<vv<<^^<<^^"]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d15-s00 (d15/parse d15-s00-raw)))))

(deftest move-test
  (testing "Performs a move attempt by the robot and updates the state"
    ;; Can't move due to wall in the way
    (is (= d15-s00
           (d15/move :part1 d15-s00 :w)))

    ;; Robot moves up one
    (is (= (assoc d15-s00 :robot [2 1])
           (d15/move :part1 d15-s00 :n)))

    ;; Robot moves right one, pushing a box
    (is (= (-> d15-s00
               (assoc :robot [3 1])
               (update :boxes disj [3 1])
               (update :boxes conj [4 1]))
           (d15/move :part1 (assoc d15-s00 :robot [2 1]) :e)))

    ;; Robot moves right one, pushing two boxes
    (is (= (-> d15-s00
               (assoc :robot [4 1])
               (update :boxes disj [3 1])
               (update :boxes conj [6 1]))
           (d15/move :part1
                     (-> d15-s00
                         (assoc :robot [3 1])
                         (update :boxes disj [3 1])
                         (update :boxes conj [4 1]))
                     :e)))

    ;; Robot can't move further right
    (is (= (-> d15-s00
               (assoc :robot [4 1])
               (update :boxes disj [3 1])
               (update :boxes conj [6 1]))
           (d15/move :part1
                     (-> d15-s00
                         (assoc :robot [4 1])
                         (update :boxes disj [3 1])
                         (update :boxes conj [6 1]))
                     :e)))

    ;; Robot moves line of boxes down one
    (is (= (-> d15-s00
               (assoc :robot [4 2])
               (update :boxes disj [3 1])
               (update :boxes disj [4 2])
               (update :boxes conj [6 1])
               (update :boxes conj [4 6]))
           (d15/move :part1
                     (-> d15-s00
                         (assoc :robot [4 1])
                         (update :boxes disj [3 1])
                         (update :boxes conj [6 1]))
                     :s)))))

(deftest all-moves-test
  (testing "Performs all the moves correctly and returns the end state"
    (is (= d15-s00-finish (d15/all-moves :part1 d15-s00)))

    (is (= d15-s01-finish (d15/all-moves :part1 d15-s01)))))

(deftest box-gps-sum
  (testing "Computes the sum of the GPS coordinates of all the boxes"
    (is (= 2028  (d15/box-gps-sum (d15/all-moves :part1 d15-s00))))
    (is (= 10092 (d15/box-gps-sum (d15/all-moves :part1 d15-s01))))))

(def day15-input (u/parse-puzzle-input d15/parse 2024 15))

(deftest part1-test
  (testing "Reproduces the answer for day15, part1"
    (is (= 1478649 (d15/part1 day15-input)))))