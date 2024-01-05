(ns aoc-clj.2021.day17
  "Solution to https://adventofcode.com/2021/day/17")

(defn parse
  [input]
  (let [nums (map read-string (re-seq #"\-?\d+" (first input)))
        [xmin xmax ymin ymax] nums]
    {:xmin xmin
     :xmax xmax
     :ymin ymin
     :ymax ymax}))

(defn target?
  [{:keys [xmin xmax ymin ymax]} [x y]]
  (and (<= xmin x xmax) (<= ymin y ymax)))

(defn step
  [[[x y] [vx vy]]]
  [[(+ x vx) (+ y vy)]
   [(if (pos? vx) (dec vx) 0) (- vy 1)]])

(defn evolve
  [{:keys [ymin]} [vx vy]]
  (take-while
   #(>= (second (first %)) ymin)
   (iterate step [[0 0] [vx vy]])))

(defn hits-target?
  [target trajectory]
  (boolean (some (partial target? target)
                 (map first trajectory))))

(defn will-hit-target?
  [target vel]
  (hits-target? target (evolve target vel)))

(defn min-init-vx
  [{:keys [xmin]}]
  (int (/ (+ 1 (Math/sqrt (+ 1 (* 4 2 xmin)))) 2)))

(defn highest-trajectory
  [target vymax]
  (let [vx     (min-init-vx target)
        speeds (for [vy (range vymax)]
                 [vx vy])
        trajectories (map (partial evolve target) speeds)
        candidates (filter (partial hits-target? target) trajectories)
        heights (map (fn [c] [(second (first c))
                              (apply max (map (comp second first) c))]) candidates)]
    (apply max-key second heights)))

(defn highest-point
  [target]
  (second (highest-trajectory target 100)))

;; TODO this is a brute force approach, and could
;; be made considerably smarter by calculating the time
;; range that the projectile is in the target's x range 
;; for a given vx value, and then computing viable vys 
;; based on the time range
(defn viable-init-vels
  [target vymax]
  (let [vxmin (min-init-vx target)
        vxmax (inc (:xmax target))
        vymin (:ymin target)
        speeds (for [vx (range vxmin vxmax)
                     vy (range vymin vymax)]
                 [vx vy])
        trajectories (map (partial evolve target) speeds)
        candidates (filter (partial hits-target? target) trajectories)]
    (count candidates)))

(defn part1
  [input]
  (highest-point input))

(defn part2
  [input]
  (viable-init-vels input 100))