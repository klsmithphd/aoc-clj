(ns aoc-clj.2022.day22
  "Solution to https://adventofcode.com/2022/day/22"
  (:require [aoc-clj.utils.core :as u]))

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
     :start (first (sort (filter #(= 1 (second %)) (map first points))))
     :h-zones (horizontal-zones points)
     :v-zones (vertical-zones points)}))

(defn num-or-string
  [s]
  (if (number? (read-string s))
    (read-string s)
    s))

(defn parse-path
  [s]
  (map num-or-string (re-seq #"\d+|[LR]" s)))

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

(defn zone-match
  [[a b] [[mn mx] [vmin vmax]]]
  (when (<= mn a mx)
    (if (< b vmin)
      vmax
      vmin)))

(defn lookup
  [zones pos]
  (first (filter some? (map #(zone-match pos %) zones))))

(defn wrap-around
  [{:keys [h-zones v-zones]} facing [x y]]
  (if (or (= facing :L) (= facing :R))
    [(lookup h-zones [y x]) y]
    [x (lookup v-zones [x y])]))

(defn next-pos
  [facing [x y]]
  (case facing
    :U [x (dec y)]
    :R [(inc x) y]
    :D [x (inc y)]
    :L [(dec x) y]))

(defn wall?
  [{:keys [grid]} pos]
  (= :wall (grid pos)))

(defn next-cell
  [{:keys [grid] :as themap} {:keys [facing pos]}]
  (let [test-pos (next-pos facing pos)]
    (if (grid test-pos)
      test-pos
      (wrap-around themap facing test-pos))))

(def turn-right {:U :R
                 :R :D
                 :D :L
                 :L :U})
(def turn-left  {:U :L
                 :L :D
                 :D :R
                 :R :U})

(defn turn
  [state dir]
  (update state :facing (case dir
                          "R" turn-right
                          "L" turn-left)))

(defn walk
  [themap state dist]
  (loop [s state cnt dist]
    (let [test-pos (next-cell themap s)]
      (if (or (zero? cnt) (wall? themap test-pos))
        s
        (recur (assoc s :pos test-pos) (dec cnt))))))

(defn apply-cmd
  [themap state cmd]
  (if (number? cmd)
    (walk themap state cmd)
    (turn state cmd)))

(defn follow-path
  [{:keys [path start] :as themap}]
  (reduce (partial apply-cmd themap) {:pos start :facing :R} path))

(def facing-value
  {:R 0
   :D 1
   :L 2
   :U 3})

(defn final-password
  [{:keys [pos facing]}]
  (let [[x y] pos]
    (+ (* 1000 y) (* 4 x) (facing-value facing))))

(defn day22-part1-soln
  []
  (final-password (follow-path day22-input)))