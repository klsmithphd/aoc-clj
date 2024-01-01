(ns aoc-clj.2020.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day24 :as t]))

(def d24-s00
  (t/parse
   ["sesenwnenenewseeswwswswwnenewsewsw"
    "neeenesenwnwwswnenewnwwsewnenwseswesw"
    "seswneswswsenwwnwse"
    "nwnwneseeswswnenewneswwnewseswneseene"
    "swweswneswnenwsewnwneneseenw"
    "eesenwseswswnenwswnwnwsewwnwsene"
    "sewnenenenesenwsewnenwwwse"
    "wenwwweseeeweswwwnwwe"
    "wsweesenenewnwwnwsenewsenwwsesesenwne"
    "neeswseenwwswnwswswnw"
    "nenwswwsewswnenenewsenwsenwnesesenew"
    "enewnwewneswsewnwswenweswnenwsenwsw"
    "sweneswneswneneenwnewenewwneswswnese"
    "swwesenesewenwneswnwwneseswwne"
    "enesenwswwswneneswsenwnewswseenwsese"
    "wnwnesenesenenwwnenwsewesewsesesew"
    "nenewswnwewswnenesenwnesewesw"
    "eneswnwswnwsenenwnwnwwseeswneewsenese"
    "neswnwewnwnwseenwseesewsenwsweewe"
    "wseweeenwnesenwwwswnew "]))

(deftest parser
  (testing "The parser tokenizes the strings correctly"
    (is '("se" "se" "nw" "ne" "ne" "ne" "w" "se" "e" "sw" "w" "sw" "sw" "w" "ne" "ne" "w" "se" "w" "sw")
        (t/parse-line "sesenwnenenewseeswwswswwnenewsewsw"))
    (is '("w" "se" "w" "e" "e" "e" "nw" "ne" "se" "nw" "w" "w" "sw" "ne" "w")
        (t/parse-line "wseweeenwnesenwwwswnew"))))

(deftest identify-black-tiles
  (is (= 10 (count (t/black-tiles d24-s00)))))

(deftest evolution
  (testing "Can correctly apply the neighbor rules to evolve the tiles"
    (is (= 15 (count (t/black-tiles-on-day d24-s00 1))))
    (is (= 12 (count (t/black-tiles-on-day d24-s00 2))))
    (is (= 25 (count (t/black-tiles-on-day d24-s00 3))))
    (is (= 14 (count (t/black-tiles-on-day d24-s00 4))))
    (is (= 23 (count (t/black-tiles-on-day d24-s00 5))))
    (is (= 28 (count (t/black-tiles-on-day d24-s00 6))))
    (is (= 41 (count (t/black-tiles-on-day d24-s00 7))))
    (is (= 37 (count (t/black-tiles-on-day d24-s00 8))))
    (is (= 49 (count (t/black-tiles-on-day d24-s00 9))))
    (is (= 37 (count (t/black-tiles-on-day d24-s00 10))))))

(def day24-input (u/parse-puzzle-input t/parse 2020 24))

(deftest day24-part1-soln
  (testing "Reproduces the answer for day24, part1"
    (is (= 436 (t/day24-part1-soln day24-input)))))

;; FIXME: 2020.day24 part 2 too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/14
(deftest ^:slow day24-part2-soln
  (testing "Reproduces the answer for day24, part2"
    (is (= 4133 (t/day24-part2-soln day24-input)))))