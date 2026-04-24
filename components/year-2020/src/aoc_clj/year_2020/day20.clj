(ns aoc-clj.year-2020.day20
  "Solution to https://adventofcode.com/2020/day/20"
  (:require [clojure.string :as str]
            [aoc-clj.grid.interface :as mapgrid]
            [aoc-clj.util.interface :as u]))

(def charmap  {\. 0 \# 1})
(def sea-monster-pattern
  (->> (mapgrid/ascii->MapGrid2D
        charmap
        ["                  # "
         "#    ##    ##    ###"
         " #  #  #  #  #  #   "])
       :grid-map
       (filter (comp some? second))
       (map first)))

(defn parse-tile
  [tile-str]
  (let [[header grid] (str/split tile-str #":\n")
        tile-id (read-string (subs header 5 9))]
    [tile-id (mapgrid/ascii->MapGrid2D
              charmap
              (str/split grid #"\n"))]))

(defn intermediate-parse
  [input]
  (->> (str/split input #"\n\n")
       (map parse-tile)
       (into {})))

(defn parse
  [input]
  (->> input
       (str/join "\n")
       intermediate-parse))

(defn edge
  [grid edge-indices]
  (str/join (map grid edge-indices)))

(defn edge-hash
  [edge]
  (if (>= (compare edge (str/reverse edge)) 0)
    edge
    (str/reverse edge)))

(defn tile-edges
  [[tile-id {:keys [width height grid-map]}]]
  (let [edge-coords [(map vector (repeat 0)           (range width))
                     (map vector (repeat (dec height)) (range width))
                     (map vector (range height)        (repeat 0))
                     (map vector (range height)        (repeat (dec width)))]]
    [tile-id (zipmap '(:n :s :w :e) (map (partial edge grid-map) edge-coords))]))

(defn tile-edge-map
  [tiles]
  (into {} (map tile-edges tiles)))

(defn index-edge
  [[tile-id edges]]
  (map (fn [[edge-id edge]]
         [(edge-hash edge) [tile-id edge-id]]) edges))

(defn matching-edges
  [tile-edge-map]
  (let [half-matches (->> tile-edge-map
                          (mapcat index-edge)
                          (group-by first)
                          (filter #(= 2 (count (val %))))
                          (u/fmap (partial map second))
                          vals)
        all-matches (concat half-matches (mapv (comp vec reverse) half-matches))
        edge-map    (->> (map (fn [[[a b] [c d]]] [a [b [c d]]]) all-matches)
                         (group-by first)
                         (u/fmap #(into {} (mapv second %))))]
    edge-map))

(defn corners
  [matching-edges]
  (let [match-counts (u/fmap count matching-edges)]
    (->> (filter #(= 2 (second %)) match-counts)
         (map first))))

(def opposite-dir {:n :s :s :n :e :w :w :e})
(defn orthogonal-dir
  [edge-matches [tile dir]]
  (let [options (keys (edge-matches tile))
        orth-dirs (case dir
                    :n #{:e :w}
                    :s #{:e :w}
                    :e #{:n :s}
                    :w #{:n :s})]
    [tile (some orth-dirs options)]))

(defn next-tile
  [edge-matches [tile dir]]
  (get-in edge-matches [tile (opposite-dir dir)]))

(defn tile-row
  [edge-matches [start dir]]
  (take-while some? (iterate (partial next-tile edge-matches) [start (opposite-dir dir)])))

(defn tile-positions
  [edge-matches]
  (let [start        (first (corners edge-matches))
        start-dir    (first (keys (edge-matches start)))
        edge         (map (partial orthogonal-dir edge-matches)
                          (tile-row edge-matches [start start-dir]))
        rows         (count edge)
        ordered-tiles (->> (mapcat (partial tile-row edge-matches) edge)
                           (map first))
        cols         (/ (count ordered-tiles) rows)]
    {:width cols
     :height rows
     :grid-map (zipmap (for [row (range rows) col (range cols)] [row col])
                       ordered-tiles)}))

(def neighbors {[-1 0] :n
                [0 1] :e
                [1 0] :s
                [0 -1] :w})

(defn desired-edge
  [grid valid-locs [pos tile]]
  (let [n-pos  (->> (map (fn [[loc dir]]
                           [(map + pos loc) dir]) neighbors)
                    (filter (comp valid-locs first)))]
    {tile (into {} (mapv (fn [[loc dir]]
                           [dir [(grid loc) (opposite-dir dir)]])
                         n-pos))}))

(defn desired-edges
  [{:keys [grid-map]}]
  (let [valid-locs (set (keys grid-map))]
    (apply merge (map (partial desired-edge grid-map valid-locs) grid-map))))

(defn fliph-edge
  [edges]
  (u/fmap #(if (#{:e :w} %)
             (opposite-dir %)
             %) edges))

(defn flipv-edge
  [edges]
  (u/fmap #(if (#{:n :s} %)
             (opposite-dir %)
             %) edges))

(defn rotate-edge
  [edges]
  (u/fmap {:n :e :e :s :s :w :w :n} edges))

(defn orient
  [current-edges desired-edges]
  (let [tiles (keys current-edges)
        deltas (->> (map (comp set vector)
                         (map current-edges tiles)
                         (map desired-edges tiles))
                    (filter #(> (count %) 1)))
        delta-count (count deltas)]
    (case delta-count
      0 [:no-op]
      1 (if (= #{:e :w} (first deltas))
          [:fliph]
          [:flipv])
      (case (first deltas)
        #{:e :w} [:fliph (orient (fliph-edge current-edges) desired-edges)]
        #{:n :s} [:flipv (orient (flipv-edge current-edges) desired-edges)]
        [:rotate (orient (rotate-edge current-edges) desired-edges)]))))

(defn simplify-edges
  [edges]
  (u/invert-map (u/fmap first edges)))

(defn tile-orientations
  [current desired]
  (let [c (u/fmap simplify-edges current)
        d (u/fmap simplify-edges desired)
        tiles (keys d)]
    (zipmap tiles
            (map (comp flatten orient) (map c tiles) (map d tiles)))))

(defn is-edge?
  [width height [row col]]
  (or (= 0 row)
      (= 0 col)
      (= (dec height) row)
      (= (dec width) col)))

(defn trim-edge
  [{:keys [width height grid-map]}]
  (let [is-not-edge? (complement (partial is-edge? width height))]
    {:width (- width 2)
     :height (- height 2)
     :grid-map (into {} (filter (comp is-not-edge? key) grid-map))}))

(defn fliph
  [{:keys [width grid-map] :as tile}]
  (assoc tile :grid-map (u/kmap (fn [[row col]] [row (- (dec width) col)]) grid-map)))

(defn flipv
  [{:keys [height grid-map] :as tile}]
  (assoc tile :grid-map (u/kmap (fn [[row col]] [(- (dec height) row) col]) grid-map)))

(defn rotate
  [{:keys [width height grid-map]}]
  (let [mapping (fn [[row col]] [col (- (dec height) row)])]
    {:width height
     :height width
     :grid-map (u/kmap mapping grid-map)}))

(defn oriented
  [tile command]
  (case command
    :no-op tile
    :fliph (fliph tile)
    :flipv (flipv tile)
    :rotate (rotate tile)))

(defn corrected-tile
  [tiles orientations [[shift-row shift-col] tile-id]]
  (let [tile     (get tiles tile-id)
        commands (get orientations tile-id)]
    (->>
     (reduce oriented tile commands)
     trim-edge
     :grid-map
     (u/kmap (fn [[row col]]
               [(+ (* 8 shift-row) row -1)
                (+ (* 8 shift-col) col -1)])))))

(defn reassembled-image
  [tiles]
  (let [edge-matches  (matching-edges (tile-edge-map tiles))
        positions     (tile-positions edge-matches)
        desired-edges (desired-edges positions)
        orientations  (tile-orientations edge-matches desired-edges)
        do-everything (partial corrected-tile tiles orientations)
        width         (* 8 (:width positions))
        height        (* 8 (:height positions))]
    {:height height
     :width  width
     :grid-map (apply merge (map do-everything (:grid-map positions)))}))

(def all-orientation-cmds
  [[:no-op]
   [:rotate]
   [:rotate :rotate]
   [:rotate :rotate :rotate]
   [:fliph]
   [:fliph :rotate]
   [:fliph :rotate :rotate]
   [:fliph :rotate :rotate :rotate]])

(defn all-orientations
  [tile]
  (map (partial reduce oriented tile) all-orientation-cmds))

(defn is-sea-monster?
  [grid pos]
  (let [positions (map (partial map + pos) sea-monster-pattern)]
    (every? #(= 1 %) (map grid positions))))

(defn sea-monster-count
  [{:keys [width height grid-map]}]
  (->> (filter (partial is-sea-monster? grid-map)
               (for [row (range (- height 3))
                     col (range (- width 20))]
                 [row col]))
       count))

(defn sea-roughness
  [tiles]
  (let [image (reassembled-image tiles)
        ones  (count (filter #(= 1 (val %)) (:grid-map image)))
        sea-monsters (apply max (map sea-monster-count (all-orientations image)))]
    (- ones (* sea-monsters (count sea-monster-pattern)))))

(defn part1
  [input]
  (->> input
       tile-edge-map
       matching-edges
       corners
       (reduce *)))

(defn part2
  [input]
  (sea-roughness input))
