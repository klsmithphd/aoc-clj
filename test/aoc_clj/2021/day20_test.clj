(ns aoc-clj.2021.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day20 :as t]))

(def day20-sample
  (t/parse
   ["..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#"
    ""
    "#..#."
    "#...."
    "##..#"
    "..#.."
    "..###"]))

(deftest illuminated-after-enhance-sample
  (testing "Computes the number of pixels lit up after 2 enhancements"
    (is (= 35   (t/illuminated (t/enhance-n-times day20-sample 2))))
    (is (= 3351 (t/illuminated (t/enhance-n-times day20-sample 50))))))

(def day20-input (u/parse-puzzle-input t/parse 2021 20))

(deftest part1-test
  (testing "Reproduces the answer for day20, part1"
    (is (= 5097 (t/part1 day20-input)))))

;; FIXME: 2021.day20 part 2 is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/10
(deftest ^:slow part2
  (testing "Reproduces the answer for day20, part2"
    (is (= 17987 (t/part2 day20-input)))))
