(ns aoc-clj.year-2020.day11
  "Solution to https://adventofcode.com/2020/day/11"
  (:require [aoc-clj.grid.interface :as mapgrid]))

(defn parse
  [ascii-lines]
  (let [seat-mapping {\. :space
                      \# :occupied
                      \L :seat}]
    (mapgrid/ascii->MapGrid2D seat-mapping ascii-lines)))

(def dirs
  (->> (for [dr (range -1 2)
             dc (range -1 2)]
         [dr dc])
       (filter #(not= [0 0] %))))

(defn seats
  [grid]
  (map first (filter #(= :seat (val %)) grid)))

(defn adjacent
  [pos]
  (map #(mapv + pos %) dirs))

(defn adjacency
  [{:keys [grid-map]}]
  (let [seats (seats grid-map)]
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
  [limit adjacency {:keys [grid-map] :as seatmap}]
  (let [adjacencies (adjacency seatmap)
        seats       (keys adjacencies)
        apply-rules (partial rules limit adjacencies)]
    (loop [statemap grid-map]
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
  [height width [row col]]
  (and (>= row 0)
       (< row height)
       (>= col 0)
       (< col width)))

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
  [{:keys [height width grid-map]} pos]
  (let [sightlines (->> (map (partial sightline height width pos) dirs)
                        (filter (complement empty?)))]
    (filter some? (map (partial first-visible-seat grid-map) sightlines))))

(defn visibility
  [{:keys [grid-map] :as seatmap}]
  (let [seats (seats grid-map)]
    (zipmap seats (map (partial first-visible-seats seatmap) seats))))

(defn part1
  [input]
  (occupied-seats-when-static input 4 adjacency))

(defn part2
  [input]
  (occupied-seats-when-static input 5 visibility))