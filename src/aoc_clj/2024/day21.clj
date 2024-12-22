(ns aoc-clj.2024.day21
  "Solution to https://adventofcode.com/2024/day/20"
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.graph :as graph :refer [Graph]]
            [aoc-clj.utils.core :as u]))

;; Constants
(def numeric-keypad
  {\A {\0 \<
       \3 \^}
   \0 {\A \>
       \2 \^}
   \1 {\4 \^
       \2 \>}
   \2 {\0 \v
       \1 \<
       \3 \>
       \5 \^}
   \3 {\A \v
       \2 \<
       \6 \^}
   \4 {\1 \v
       \5 \>
       \7 \^}
   \5 {\2 \v
       \4 \<
       \6 \>
       \8 \^}
   \6 {\3 \v
       \5 \<
       \9 \^}
   \7 {\4 \v
       \8 \>}
   \8 {\5 \v
       \7 \<
       \9 \>}
   \9 {\6 \v
       \8 \<}})

(def directional-keypad
  {\< {\v \>}
   \v {\< \<
       \^ \^
       \> \>}
   \^ {\A \>
       \v \v}
   \> {\v \<
       \A \^}
   \A {\^ \<
       \> \v}})

;; Records
(defrecord MoveGraph [keypad]
  Graph
  (vertices [_]     (keys keypad))
  (edges    [_ v]   (keys (keypad v)))
  (distance [_ _ _] 1))

;; Input parsing
(def parse identity)

;; Puzzle logic
(defn buttons->moves
  "For the given `keypad` graph, and a `path` of buttons to move through,
   returns the directional instructions required."
  [keypad path]
  (->> (partition 2 1 path)
       (map #(get-in keypad %))
       (filter some?)
       (apply str)))

(defn button-paths
  "For a given `keypad`, the `graph` representation of indivudal moves,
   and any two given buttons `a` and `b`, return the set of moves to
   get from `a` to `b`"
  [keypad graph [a b]]
  (->> (graph/all-shortest-paths true graph a (u/equals? b))
       (mapv #(buttons->moves keypad %))))

(defn keypad-paths
  "For a given `keypad`, constructs a lookup map of all possible moves
   to get from one button to another."
  [keypad]
  (let [graph (->MoveGraph keypad)
        pairs (->> (combo/cartesian-product (keys keypad) (keys keypad))
                   (map vec))]
    (->> (map #(vector % (button-paths keypad graph %)) pairs)
         (into {}))))

;; (def robot-moves
;;   {\A {\A "A"
;;        \0 "<A"
;;        \1 "^<<A"
;;        \2 "^<A"
;;        \3 "^A"
;;        \4 "^^<<A"
;;        \5 "^^<A"
;;        \6 "^^A"
;;        \7 "^^^<<A"
;;        \8 "^^^<A"
;;        \9 "^^^A"}
;;    \0 {\A ">A"
;;        \0 "A"
;;        \1 "^<A"
;;        \2 "^A"
;;        \3 "^>A"
;;        \4 "^^<A"
;;        \5 "^^A"
;;        \6 "^^>A"
;;        \7 "^^^<A"
;;        \8 "^^^A"
;;        \9 "^^^>A"}
;;    \1 {\A ">>vA"
;;        \0 ">vA"
;;        \1 "A"
;;        \2 ">A"
;;        \3 ">>A"
;;        \4 "^A"
;;        \5 "^>A"
;;        \6 "^>>A"
;;        \7 "^^A"
;;        \8 "^^>A"
;;        \9 "^^>>A"}
;;    \2 {\A ">vA"
;;        \0 "vA"
;;        \1 "<A"
;;        \2 "A"
;;        \3 ">A"
;;        \4 "^<A"
;;        \5 "^A"
;;        \6 "^>A"
;;        \7 "^^<A"
;;        \8 "^^A"
;;        \9 "^^>A"}
;;    \3 {\A "vA"
;;        \0 "v<A"
;;        \1 "<<A"
;;        \2 "<A"
;;        \3 "A"
;;        \4 "^<<A"
;;        \5 "^<A"
;;        \6 "^A"
;;        \7 "^^<<A"
;;        \8 "^^<A"
;;        \9 "^^A"}
;;    \4 {\A ">>vvA"
;;        \0 ">vvA"
;;        \1 "vA"
;;        \2 ">vA"
;;        \3 ">>vA"
;;        \4 "A"
;;        \5 ">A"
;;        \6 ">>A"
;;        \7 "^A"
;;        \8 "^>A"
;;        \9 "^>>A"}
;;    \5 {\A ">vvA"
;;        \0 "vvA"
;;        \1 "v<A"
;;        \2 "vA"
;;        \3 "v>A"
;;        \4 "<A"
;;        \5 "A"
;;        \6 ">A"
;;        \7 "^<A"
;;        \8 "^A"
;;        \9 "^>A"}
;;    \6 {\A "vvA"
;;        \0 "vv<A"
;;        \1 "v<<A"
;;        \2 "v<A"
;;        \3 "vA"
;;        \4 "<<A"
;;        \5 "<A"
;;        \6 "A"
;;        \7 "^<<A"
;;        \8 "^<A"
;;        \9 "^A"}
;;    \7 {\A ">>vvvA"
;;        \0 ">vvvA"
;;        \1 "vvA"
;;        \2 ">vvA"
;;        \3 ">>vvA"
;;        \4 "vA"
;;        \5 ">vA"
;;        \6 ">>vA"
;;        \7 "A"
;;        \8 ">A"
;;        \9 ">>A"}
;;    \8 {\A ">vvvA"
;;        \0 "vvvA"
;;        \1 "vvA<"
;;        \2 "vvA"
;;        \3 "vv>A"
;;        \4 "vA<"
;;        \5 "vA"
;;        \6 "v>A"
;;        \7 "A<"
;;        \8 "A"
;;        \9 ">A"}
;;    \9 {\A "vvvA"
;;        \0 "vvv<A"
;;        \1 "vv<<A"
;;        \2 "vv<A"
;;        \3 "vvA"
;;        \4 "v<<A"
;;        \5 "v<A"
;;        \6 "vA"
;;        \7 "<<A"
;;        \8 "<A"
;;        \9 "A"}})

;; (def remote-control-moves
;;   {\A {\A "A"
;;        \^ "<A"
;;        \> "vA"
;;        \v "v<A"
;;        \< "<v<A"}
;;    \< {\A ">>^A"
;;        \^ ">^A"
;;        \> ">>A"
;;        \v ">A"
;;        \< "A"}
;;    \> {\A "^A"
;;        \^ "^<A"
;;        \> "A"
;;        \v "<A"
;;        \< "<<A"}
;;    \v {\A ">^A"
;;        \^ "^A"
;;        \> ">A"
;;        \v "A"
;;        \< "<A"}
;;    \^ {\A ">A"
;;        \^ "A"
;;        \> ">vA"
;;        \v "vA"
;;        \< "v<A"}})

;; (defn move
;;   [move-map {:keys [moves pos]} dest]
;;   {:moves (into moves (get-in move-map [pos dest]))
;;    :pos   dest})

;; (defn directions
;;   [move-map coll]
;;   (->> (reduce (partial move move-map) {:moves [] :pos \A} coll)
;;        :moves
;;        (apply str)))

;; (def robot-dirs  (partial directions robot-moves))
;; (def remote-dirs (partial directions remote-control-moves))

;; (defn all-presses
;;   [code]
;;   (->> code
;;        robot-dirs
;;        remote-dirs
;;        remote-dirs))

;; (defn seq-length
;;   [code]
;;   (count (all-presses code)))