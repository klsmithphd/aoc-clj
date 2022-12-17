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