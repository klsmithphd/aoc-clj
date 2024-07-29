(ns aoc-clj.utils.intervals)

(defn fully-contained?
  "Return true if one of the ranges (determined by inclusive left/right bounds)
   is fully contained within the other"
  [[l1 r1] [l2 r2]]
  (or
   ;;     l1-r1
   ;; l2----------r2
   (<= l2 l1 r1 r2)
   ;; l1----------r1
   ;;     l2--r2   
   (<= l1 l2 r2 r1)))

(defn overlap?
  "Return true if one of the ranges (determined by inclusive left/right bounds)
   overlaps with the other"
  [[l1 r1] [l2 r2]]
  (or
   ;; l1-----r1
   ;;     l2------r2
   (<= l1 l2 r1 r2)
   ;;       l1-----r1
   ;; l2------r2
   (<= l2 l1 r2 r1)
   ;;     l1-r1
   ;; l2----------r2
   (<= l2 l1 r1 r2)
   ;; l1----------r1
   ;;     l2--r2   
   (<= l1 l2 r2 r1)))

(defn abut?
  "Return true if two intervals (with inclusive left/right bounds) 
   abut one another, i.e., they are situated consecutively such
   that the upper-end of one is exactly one less than the lower
   bound of the other."
  [[l1 r1] [l2 r2]]
  (or 
   ;; l1-----r1
   ;;          l2-----r2
   (= (inc r1) l2)
   ;; l2-----r2
   ;;          l1-----r1
   (= (inc r2) l1)
   ))

(defn contained?
  "Return true if the point `x` is contained within range (determined by
   inclusive left/right bounds)"
  [x [l r]]
  (<= l x r))

(defn in-intervals?
  "Returns true if the point `x` is contained within any of the `intervals`"
  [x intervals]
  (boolean (some #(contained? x %) intervals)))

(defn- combine
  "For two intervals, `iv1` and `iv2`, combine the intervals into one
   if they overlap. Otherwise return the pair back unmodified."
  [[[l1 r1 :as iv1] [l2 r2 :as iv2]]]
  (if (or (overlap? iv1 iv2) (abut? iv1 iv2))
    [[(min l1 l2) (max r1 r2)]]
    [iv1 iv2]))

(defn- combiner
  [acc nxt]
  (if (empty? acc)
    [nxt]
    (concat (butlast acc) (combine [(last acc) nxt]))))

(defn simplify
  "Simplifies the collection of `intervals` by collapsing overlapping
   intervals into a single range"
  [intervals]
  (reduce combiner [] (sort intervals)))