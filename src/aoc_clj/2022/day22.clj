(ns aoc-clj.2022.day22
  "Solution to https://adventofcode.com/2022/day/22"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]))

(def charmap {\  nil \. :open \# :wall})

(defn parse-line
  [y s]
  (filter #(some? (second %))
          (map vector
               (for [x (range (count s))] [(inc x) (inc y)])
               (map charmap s))))

(defn bounds
  [dir lines]
  (let [[minx miny] (-> lines first first)
        [maxx maxy] (-> lines last last)]
    (case dir
      :horiz [[miny maxy] [minx maxx]]
      :vert  [[minx maxx] [miny maxy]])))

(def dir-lookup
  {:horiz second
   :vert  first})

(defn zones
  [dir points]
  (->> points
       (map first)
       (group-by (dir-lookup dir))
       sort
       (map second)
       (partition-by count)
       (map (partial bounds dir))))

(def horizontal-zones (partial zones :horiz))
(def vertical-zones   (partial zones :vert))

(defn parse-map
  [s]
  (let [points (apply concat (map-indexed parse-line s))]
    {:grid (into {} points)
     :h-zones (horizontal-zones points)
     :v-zones (vertical-zones points)}))

(defn parse-path
  [s]
  (map read-string (re-seq #"\d+|[LR]" s)))

(defn parse
  [input]
  (let [[a b] (u/split-at-blankline input)]
    (assoc (parse-map a)
           :path (parse-path (first b)))))

(def d22-s01
  (parse
   ["        ...#"
    "        .#.."
    "        #..."
    "        ...."
    "...#.......#"
    "........#..."
    "..#....#...."
    "..........#."
    "        ...#...."
    "        .....#.."
    "        .#......"
    "        ......#."
    ""
    "10R5L5R10L4R5L5"]))

(def day22-input (parse (u/puzzle-input "2022/day22-input.txt")))
