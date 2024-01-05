(ns aoc-clj.2023.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day13 :as t]))

(def d13-s00
  ["#.##..##."
   "..#.##.#."
   "##......#"
   "##......#"
   "..#.##.#."
   "..##..##."
   "#.#.##.#."])

(def d13-s01
  ["#...##..#"
   "#....#..#"
   "..##..###"
   "#####.##."
   "#####.##."
   "..##..###"
   "#....#..#"])

(def d13-s02
  ["####....####.#..."
   "#.#.#..#.#.#.#..."
   "...#....#....#.##"
   ".#.#.####.#.###.."
   "#.##.##.##.##.#.."
   "####....#######.."
   ".##########.##..."
   "...##..##....####"
   "##...##...##.####"
   ".##########..####"
   ".#.##..##.#..#.##"
   ".#........#.#.###"
   ".##.####.##.#..##"])

(deftest mirror-pos-test
  (testing "Returns the position of the reflection point"
    (is (= {:type :vertical :pos 5}   (t/mirror-pos 0 d13-s00)))
    (is (= {:type :horizontal :pos 4} (t/mirror-pos 0 d13-s01)))
    (is (= {:type :vertical :pos 16}  (t/mirror-pos 0 d13-s02)))

    (is (= {:type :horizontal :pos 3} (t/mirror-pos 1 d13-s00)))
    (is (= {:type :horizontal :pos 1} (t/mirror-pos 1 d13-s01)))))

(deftest summarize-test
  (testing "Computes the summarized sum"
    (is (= 405 (t/summarize 0 [d13-s00 d13-s01])))
    (is (= 400 (t/summarize 1 [d13-s00 d13-s01])))))

(def day13-input (u/parse-puzzle-input t/parse 2023 13))

(deftest part1-test
  (testing "Reproduces the answer for day13, part1"
    (is (= 33195 (t/part1 day13-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day13, part2"
    (is (= 31836 (t/part2 day13-input)))))
