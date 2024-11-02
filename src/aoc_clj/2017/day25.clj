(ns aoc-clj.2017.day25
  "Solution to https://adventofcode.com/2017/day/25"
  (:require
   [aoc-clj.utils.core :as u]
   [clojure.string :as str]))

;; Constants
(def init-state
  {:tape {} :slot 0})

;; Input Parsing
(defn parse-header
  [[line1 line2]]
  {:start (subs line1 15 16)
   :steps (read-string (re-find #"\d+" line2))})

(defn last-word
  [line]
  (let [last-chars (last (str/split line #" "))
        len        (count last-chars)]
    (subs last-chars 0 (dec len))))

(defn parse-chunk
  [chunk]
  (let [[a b c d e f g h i] (map last-word chunk)]
    [a {(read-string b) {:write (read-string c)
                         :move (keyword d)
                         :nxt-state e}
        (read-string f) {:write (read-string g)
                         :move (keyword h)
                         :nxt-state i}}]))

(defn parse
  [input]
  (let [[header & body] (u/split-at-blankline input)]
    (into (parse-header header) (map parse-chunk body))))

;; Puzzle logic
(defn step
  [logic {:keys [tape state slot]}]
  (let [current-val (get tape slot 0)
        {:keys [write move nxt-state]} (get-in logic [state current-val])]
    ;; (println tape state slot write move nxt-state)
    {:tape (assoc tape slot write)
     :state nxt-state
     :slot (case move
             :right (inc slot)
             :left  (dec slot))}))

(defn execute
  [{:keys [start steps] :as logic}]
  (->> (assoc init-state :state start)
       (iterate #(step logic %))
       (drop steps)
       first))

(defn checksum
  [logic]
  (let [{:keys [tape]} (execute logic)]
    (reduce + (vals tape))))