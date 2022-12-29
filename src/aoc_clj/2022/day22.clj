(ns aoc-clj.2022.day22
  "Solution to https://adventofcode.com/2022/day/22"
  (:require [aoc-clj.utils.core :as u]))

(def charmap {\  nil \. :open \# :wall})
(def facing-value {:R 0 :D 1 :L 2 :U 3})
(def turn-right {:U :R :R :D :D :L :L :U})
(def turn-left  {:U :L :L :D :D :R :R :U})

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
    {:facing facing :pos [(lookup h-zones [y x]) y]}
    {:facing facing :pos [x (lookup v-zones [x y])]}))

(defn sample-cube-wrap-around
  [_ facing [x y]]
  (case facing
    :U (cond
         (<= 1 x 4)   {:pos [(+ 9 (- 4 x)) 1]   :facing :D}
         (<= 5 x 8)   {:pos [9 (+ 1 (- x 5))]   :facing :R}
         (<= 9 x 12)  {:pos [(+ 4 (- 9 x)) 5]   :facing :D}
         (<= 13 x 16) {:pos [12 (+ 8 (- 13 x))] :facing :L})
    :D (cond
         (<= 1 x 4)   {:pos [(+ 9 (- 4 x)) 12]  :facing :U}
         (<= 5 x 8)   {:pos [9 (+ 9 (- 8 x))]   :facing :R}
         (<= 9 x 12)  {:pos [(+ 4 (- 9 x)) 8]   :facing :U}
         (<= 13 x 16) {:pos [1 (+ 5 (- 16 x))]  :facing :R})
    :L (cond
         (<= 1 y 4)   {:pos [(+ 8 (- y 4)) 5]   :facing :D}
         (<= 5 y 8)   {:pos [(+ 16 (- 5 y)) 12] :facing :U}
         (<= 9 y 12)  {:pos [(+ 5 (- 12 y)) 8]  :facing :U})
    :R (cond
         (<= 1 y 4)   {:pos [16 (+ 9 (- 4 y))]  :facing :L}
         (<= 5 y 8)   {:pos [(+ 16 (- 5 y)) 9]  :facing :D}
         (<= 9 y 12)  {:pos [(+ 1 (- 12 y)) 12] :facing :L})))

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
  [{:keys [grid wrap-fn] :as themap} {:keys [facing pos]}]
  (let [test-pos (next-pos facing pos)]
    (if (grid test-pos)
      {:facing facing :pos test-pos}
      (wrap-fn themap facing test-pos))))

(defn turn
  [state dir]
  (update state :facing (case dir
                          "R" turn-right
                          "L" turn-left)))

(defn walk
  [themap state dist]
  (loop [s state cnt dist]
    (let [{:keys [pos] :as new-s} (next-cell themap s)]
      (if (or (zero? cnt) (wall? themap pos))
        s
        (recur new-s (dec cnt))))))

(defn apply-cmd
  [themap state cmd]
  (if (number? cmd)
    (walk themap state cmd)
    (turn state cmd)))

(defn follow-path
  [{:keys [path start] :as themap}]
  (reduce (partial apply-cmd themap) {:pos start :facing :R} path))

(defn final-password
  [{:keys [pos facing]}]
  (let [[x y] pos]
    (+ (* 1000 y) (* 4 x) (facing-value facing))))

(defn cube-wrap-around
  [_ facing [x y]]
  ;; TODO write impl
  )

(defn day22-part1-soln
  []
  (final-password (follow-path (assoc day22-input :wrap-fn wrap-around))))

(defn day22-part2-soln
  []
  (final-password (follow-path (assoc day22-input :wrap-fn cube-wrap-around))))

