(ns aoc-clj.2024.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.util.interface :as u]
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
   :walls #{[0 0] [0 1] [0 2] [0 3] [0 4] [0 5] [0 6] [0 7]
            [1 0] [1 7]
            [2 0] [2 1] [2 7]
            [3 0] [3 7]
            [4 0] [4 2] [4 7]
            [5 0] [5 7]
            [6 0] [6 7]
            [7 0] [7 1] [7 2] [7 3] [7 4] [7 5] [7 6] [7 7]}
   :boxes #{[1 3] [1 5]
            [2 4]
            [3 4]
            [4 4]
            [5 4]}
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

(def d15-s02-wider
  {:robot [3 10]
   :walls #{[0 0] [0 1] [0 2] [0 3] [0 4] [0 5] [0 6]
            [0 7] [0 8] [0 9] [0 10] [0 11] [0 12] [0 13]
            [1 0] [1 1] [1 8] [1 9] [1 12] [1 13]
            [2 0] [2 1] [2 12] [2 13]
            [3 0] [3 1] [3 12] [3 13]
            [4 0] [4 1] [4 12] [4 13]
            [5 0] [5 1] [5 12] [5 13]
            [6 0] [6 1] [6 2] [6 3] [6 4] [6 5] [6 6]
            [6 7] [6 8] [6 9] [6 10] [6 11] [6 12] [6 13]}
   :boxes #{[3 6] [3 8] [4 6]}
   :moves [:w :s :s :w :w :n :n :w :w :n :n]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d15-s00 (d15/parse d15-s00-raw)))))

(deftest part1-move-test
  (testing "Performs a move attempt by the robot and updates the state"
    ;; Can't move due to wall in the way
    (is (= d15-s00
           (d15/move :part1 d15-s00 :w)))

    ;; Robot moves up one
    (is (= (assoc d15-s00 :robot [1 2])
           (d15/move :part1 d15-s00 :n)))

    ;; Robot moves right one, pushing a box
    (is (= (-> d15-s00
               (assoc :robot [1 3])
               (update :boxes disj [1 3])
               (update :boxes conj [1 4]))
           (d15/move :part1 (assoc d15-s00 :robot [1 2]) :e)))

    ;; Robot moves right one, pushing two boxes
    (is (= (-> d15-s00
               (assoc :robot [1 4])
               (update :boxes disj [1 3])
               (update :boxes conj [1 6]))
           (d15/move :part1
                     (-> d15-s00
                         (assoc :robot [1 3])
                         (update :boxes disj [1 3])
                         (update :boxes conj [1 4]))
                     :e)))

    ;; Robot can't move further right
    (is (= (-> d15-s00
               (assoc :robot [1 4])
               (update :boxes disj [1 3])
               (update :boxes conj [1 6]))
           (d15/move :part1
                     (-> d15-s00
                         (assoc :robot [1 4])
                         (update :boxes disj [1 3])
                         (update :boxes conj [1 6]))
                     :e)))

    ;; Robot moves line of boxes down one
    (is (= (-> d15-s00
               (assoc :robot [2 4])
               (update :boxes disj [1 3])
               (update :boxes disj [2 4])
               (update :boxes conj [1 6])
               (update :boxes conj [6 4]))
           (d15/move :part1
                     (-> d15-s00
                         (assoc :robot [1 4])
                         (update :boxes disj [1 3])
                         (update :boxes conj [1 6]))
                     :s)))))

(deftest box-chain-test
  (testing "Returns the chain of boxes that might be moved together"
    (is (= [[3 8] [3 6]]
           (d15/box-chain :part2 #{[3 6] [3 8] [4 6]} :w [0 -1] [3 10])))

    (is (= [[4 6] [3 7] [3 5]]
           (d15/box-chain :part2 #{[3 5] [3 7] [4 6]} :n [-1 0] [5 7])))

    (is (= [[6 8]]
           (d15/box-chain
            :part2
            #{[5 10] [3 4] [5 2] [7 6] [4 5] [8 10] [3 6] [6 14]
              [6 8] [7 16] [1 12] [7 4] [3 16] [7 10] [4 14]
              [1 6] [3 12] [2 14] [1 16] [6 2] [7 14]} :e [0 1] [6 7])))))

(deftest part2-move-test
  (testing "Performs a move attempt by the robot and updates the state"
    ;; Robot moves left, shifting two boxes to the left
    (is (= (-> d15-s02-wider
               (assoc :robot [3 9])
               (assoc :boxes #{[3 5] [3 7] [4 6]}))
           (d15/move :part2 d15-s02-wider :w)))

    ;; Skip to robot pushing up on lowest box, pushing three boxes up
    (is (= (-> d15-s02-wider
               (assoc :robot [4 7])
               (assoc :boxes #{[2 5] [2 7] [3 6]}))
           (d15/move
            :part2
            (-> d15-s02-wider
                (assoc :robot [5 7])
                (assoc :boxes #{[3 5] [3 7] [4 6]}))
            :n)))

    ;; Robot can't push the group of three boxes further up
    (is (= (-> d15-s02-wider
               (assoc :robot [4 7])
               (assoc :boxes #{[2 5] [2 7] [3 6]}))
           (d15/move
            :part2
            (-> d15-s02-wider
                (assoc :robot [4 7])
                (assoc :boxes #{[2 5] [2 7] [3 6]}))
            :n)))

    ;; Robot moves around the lowest box and moves up below the upper-left box
    (is (= (-> d15-s02-wider
               (assoc :robot [3 5])
               (assoc :boxes #{[2 5] [2 7] [3 6]}))
           (d15/move
            :part2
            (-> d15-s02-wider
                (assoc :robot [4 5])
                (assoc :boxes #{[2 5] [2 7] [3 6]}))
            :n)))

    ;; Robot pushes the upper-left box up by 1
    (is (= (-> d15-s02-wider
               (assoc :robot [2 5])
               (assoc :boxes #{[1 5] [2 7] [3 6]}))
           (d15/move
            :part2
            (-> d15-s02-wider
                (assoc :robot [3 5])
                (assoc :boxes #{[2 5] [2 7] [3 6]}))
            :n)))

    ;; Can't push any further up because there's a wall behind the box
    (is (= (-> d15-s02-wider
               (assoc :robot [2 5])
               (assoc :boxes #{[1 5] [2 7] [3 6]}))
           (d15/move
            :part2
            (-> d15-s02-wider
                (assoc :robot [2 5])
                (assoc :boxes #{[1 5] [2 7] [3 6]}))
            :n)))))


(deftest all-moves-test
  (testing "Performs all the moves correctly and returns the end state"
    (is (= d15-s00-finish (d15/all-moves :part1 d15-s00)))
    (is (= d15-s01-finish (d15/all-moves :part1 d15-s01)))

    (is (= (u/without-keys
            (-> d15-s02-wider
                (assoc :robot [2 5])
                (assoc :boxes #{[1 5] [2 7] [3 6]}))
            [:moves])
           (d15/all-moves :part2 d15-s02-wider)))

    (is (= (u/without-keys
            (-> (d15/widen-input d15-s01)
                (assoc :robot [7 4])
                (assoc :boxes #{[1 2] [2 2] [3 2] [4 2]
                                [1 11] [1 14] [1 16]
                                [2 15]
                                [3 12] [3 14] [3 16]
                                [4 10] [4 16]
                                [5 12]
                                [6 4]
                                [7 11] [7 14] [7 16]
                                [8 8] [8 10] [8 14]}))
            [:moves])
           (d15/all-moves :part2 (d15/widen-input d15-s01))))))

(deftest box-gps-sum
  (testing "Computes the sum of the GPS coordinates of all the boxes"
    (is (= 2028  (d15/box-gps-sum (d15/all-moves :part1 d15-s00))))
    (is (= 10092 (d15/box-gps-sum (d15/all-moves :part1 d15-s01))))

    (is (= 9021  (d15/box-gps-sum (d15/all-moves :part2 (d15/widen-input d15-s01)))))))

(deftest widen-input-test
  (testing "Constructs a new double-wide layout"
    (is (= d15-s02-wider (d15/widen-input d15-s02)))))

(def day15-input (u/parse-puzzle-input d15/parse 2024 15))

(deftest part1-test
  (testing "Reproduces the answer for day15, part1"
    (is (= 1478649 (d15/part1 day15-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day15, part2"
    (is (= 1495455 (d15/part2 day15-input)))))
