(ns aoc-clj.2023.day06)

(defn as-map
  [time dist]
  {:time time :dist dist})

(defn extract-numbers
  [s]
  (map read-string (re-seq #"\d+" s)))

(defn parse
  [input]
  (let [times (extract-numbers (first input))
        dists (extract-numbers (second input))]
    (map as-map times dists)))

(defn distance-options
  "The distance traveled by the boat is determined by how long you hold down
   the button. For each second the button is held, the boat will have a speed
   equal to that time in millimeters per second."
  [{:keys [time]}]
  (for [i (range 1 time)]
    (* (- time i) i)))

(defn winning-options-count
  "How many unique ways can you beat the current distance record"
  [{:keys [dist] :as race}]
  (->> (distance-options race)
       (filter #(> % dist))
       count))

(defn win-count-multiplied
  "Multiply the number of ways to win each of the races"
  [races]
  (reduce * (map winning-options-count races)))

(defn long-race
  "Reinterpret the original numbers as representing one long race by 
   concatenating all the numbers on a row"
  [races]
  (let [times (map :time races)
        dists (map :dist races)]
    {:time (read-string (apply str times))
     :dist (read-string (apply str dists))}))

(defn day06-part1-soln
  "Determine the number of ways you could beat the record in each race. 
   What do you get if you multiply these numbers together?"
  [input]
  (win-count-multiplied input))

(defn day06-part2-soln
  "How many ways can you beat the record in this one much longer race?"
  [input]
  (winning-options-count (long-race input)))

