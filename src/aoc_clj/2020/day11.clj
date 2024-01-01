(ns aoc-clj.2020.day11
  "Solution to https://adventofcode.com/2020/day/11"
  (:require [aoc-clj.utils.grid.mapgrid :as mapgrid]))

(defn parse
  [ascii-lines]
  (let [seat-mapping {\. :space
                      \# :occupied
                      \L :seat}]
    (mapgrid/ascii->MapGrid2D seat-mapping ascii-lines)))

(def dirs
  (->> (for [y (range -1 2)
             x (range -1 2)]
         [x y])
       (filter #(not= [0 0] %))))

(defn seats
  [grid]
  (map first (filter #(= :seat (val %)) grid)))

(defn adjacent
  [pos]
  (map #(mapv + pos %) dirs))

(defn adjacency
  [{:keys [grid]}]
  (let [seats (seats grid)]
    (zipmap seats (map adjacent seats))))

(defn rules
  [limit adjacencies grid pos]
  (let [seat      (grid pos)
        neighbors (map grid (adjacencies pos))]
    (case seat
      :seat (if (not (some #{:occupied} neighbors))
              :occupied
              :seat)
      :occupied (if (>= (count (filter #{:occupied} neighbors)) limit)
                  :seat
                  :occupied)
      seat)))

(defn apply-rules-until-static
  [limit adjacency {:keys [grid] :as seatmap}]
  (let [adjacencies (adjacency seatmap)
        seats       (keys adjacencies)
        apply-rules (partial rules limit adjacencies)]
    (loop [statemap grid]
      (let [nextmap (map (partial apply-rules statemap) seats)]
        (if (= nextmap (vals statemap))
          nextmap
          (recur (zipmap seats nextmap)))))))

(defn occupied-seats-when-static
  [input limit adjacency]
  (->> input
       (apply-rules-until-static limit adjacency)
       (filter #{:occupied})
       count))

(defn valid-pos?
  [height width [x y]]
  (and (>= x 0)
       (< x width)
       (>= y 0)
       (< y height)))

(defn sightline
  [height width pos dir]
  (take-while (partial valid-pos? height width)
              (drop 1 (iterate #(mapv + dir %) pos))))

(defn first-visible-seat
  [grid sightline]
  (->> sightline
       (filter #(= :seat (grid %)))
       first))

(defn first-visible-seats
  [{:keys [height width grid]} pos]
  (let [sightlines (->> (map (partial sightline height width pos) dirs)
                        (filter (complement empty?)))]
    (filter some? (map (partial first-visible-seat grid) sightlines))))

(defn visibility
  [{:keys [grid] :as seatmap}]
  (let [seats (seats grid)]
    (zipmap seats (map (partial first-visible-seats seatmap) seats))))

(defn day11-part1-soln
  [input]
  (occupied-seats-when-static input 4 adjacency))

(defn day11-part2-soln
  [input]
  (occupied-seats-when-static input 5 visibility))