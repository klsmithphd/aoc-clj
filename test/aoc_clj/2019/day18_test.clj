(ns aoc-clj.2019.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day18 :as t]))

(def d18-s00
  ["#########"
   "#b.A.@.a#"
   "#########"])

(def d18-s01
  ["########################"
   "#f.D.E.e.C.b.A.@.a.B.c.#"
   "######################.#"
   "#d.....................#"
   "########################"])

(def d18-s02
  ["########################"
   "#...............b.C.D.f#"
   "#.######################"
   "#.....@.a.B.c.d.A.e.F.g#"
   "########################"])

(def d18-s03
  ["#################"
   "#i.G..c...e..H.p#"
   "########.########"
   "#j.A..b...f..D.o#"
   "########@########"
   "#k.E..a...g..B.n#"
   "########.########"
   "#l.F..d...h..C.m#"
   "#################"])

(def d18-s04
  ["########################"
   "#@..............ac.GI.b#"
   "###d#e#f################"
   "###A#B#C################"
   "###g#h#i################"
   "########################"])

(deftest shortest-path-test
  (testing "Can find the shortest path to clear the maze"
    (is (= 8   (t/shortest-path (t/load-graph (t/load-maze d18-s00)))))
    (is (= 86  (t/shortest-path (t/load-graph (t/load-maze d18-s01)))))
    (is (= 132 (t/shortest-path (t/load-graph (t/load-maze d18-s02)))))
    (is (= 81  (t/shortest-path (t/load-graph (t/load-maze d18-s04)))))))

(def d18-s05
  ["#######"
   "#a.#Cd#"
   "##@#@##"
   "#######"
   "##@#@##"
   "#cB#Ab#"
   "#######"])

(def d18-s06
  ["###############"
   "#d.ABC.#.....a#"
   "######@#@######"
   "###############"
   "######@#@######"
   "#b.....#.....c#"
   "###############"])

(def d18-s07
  ["#############"
   "#DcBa.#.GhKl#"
   "#.###@#@#I###"
   "#e#d#####j#k#"
   "###C#@#@###J#"
   "#fEbA.#.FgHi#"
   "#############"])

(def d18-s08
  ["#############"
   "#g#f.D#..h#l#"
   "#F###e#E###.#"
   "#dCba@#@BcIJ#"
   "#############"
   "#nK.L@#@G...#"
   "#M###N#H###.#"
   "#o#m..#i#jk.#"
   "#############"])

(deftest shortest-robot-path-test
  (testing "Can find the shortest four-robot path to clear the maze"
    (is (= 8  (t/shortest-path (t/load-graph (t/load-maze d18-s05)))))
    (is (= 24 (t/shortest-path (t/load-graph (t/load-maze d18-s06)))))
    (is (= 32 (t/shortest-path (t/load-graph (t/load-maze d18-s07)))))
    (is (= 72 (t/shortest-path (t/load-graph (t/load-maze d18-s08)))))))


;; FIXME: Day 18 solutions are too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/18
(deftest ^:slow shortest-path-hard-test
  (testing "Finds the shortest path on the difficult d18-s4 case"
    (is (= 136 (t/shortest-path (t/load-graph (t/load-maze d18-s03)))))))

(def day18-input (u/parse-puzzle-input t/parse 2019 18))

(deftest ^:slow day18-part1-test
  (testing "Can reproduce the solution for part1"
    (is (= 7048 (t/part1 day18-input)))))

(deftest ^:slow day18-part2-test
  (testing "Can reproduce the solution for part2"
    (is (= 1836 (t/part2 day18-input)))))
