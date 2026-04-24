(ns aoc-clj.cli.core
  "Command-line interface to AoC solutions"
  (:require [clojure.string :as str]
            [clojure.tools.cli :refer [parse-opts]]
            [aoc-clj.util.interface :as u]
            [aoc-clj.year-2015.interface :as y2015]
            [aoc-clj.year-2016.interface :as y2016]
            [aoc-clj.year-2017.interface :as y2017]
            [aoc-clj.year-2018.interface :as y2018]
            [aoc-clj.year-2019.interface :as y2019]
            [aoc-clj.year-2020.interface :as y2020]
            [aoc-clj.year-2021.interface :as y2021]
            [aoc-clj.year-2022.interface :as y2022]
            [aoc-clj.year-2023.interface :as y2023]
            [aoc-clj.year-2024.interface :as y2024]
            [aoc-clj.year-2025.interface :as y2025]))

(def year->solution-fns
  {2015 y2015/solution-fns
   2016 y2016/solution-fns
   2017 y2017/solution-fns
   2018 y2018/solution-fns
   2019 y2019/solution-fns
   2020 y2020/solution-fns
   2021 y2021/solution-fns
   2022 y2022/solution-fns
   2023 y2023/solution-fns
   2024 y2024/solution-fns
   2025 y2025/solution-fns})

(def cli-options
  [["-i" "--input PATH" "Puzzle input file to use instead of default"]
   ["-v" "--verbose" "Verbose printing"]
   ["-h" "--help" "Display usage"]])

(defn usage
  [options-summary]
  (->>
   ["Usage: program-name [options] year day"
    ""
    "Arguments"
    "  year -- four digit year for the puzzle"
    "  day  -- value between 1 and 25 representing the day's puzzle"
    ""
    "Options:"
    options-summary]
   (str/join \newline)))

(defn- lookup-solution
  [year day]
  (when-let [fns-for-year (year->solution-fns year)]
    (fns-for-year day)))

(defn -main [& args]
  (let [{:keys [options arguments summary]} (parse-opts args cli-options)]
    (if (or (options :help)
            (< (count arguments) 2))
      (println (usage summary))
      (let [[year day]              (map read-string arguments)
            {:keys [input verbose]} options
            {:keys [parse part1 part2] :as soln} (lookup-solution year day)]
        (if-not soln
          (println (format "No solution found for year %s, day %s" year day))
          (let [filename (or input (u/default-puzzle-input-path year day))
                puzzle   (u/puzzle-input filename)
                parsed   (parse puzzle)]
            (when verbose
              (println (format "Advent of Code %s, Day %s, using %s" year day filename)))
            (when part1
              (when verbose
                (println "\nPart 1 solution")
                (println (:doc (meta part1))))
              (println (part1 parsed)))
            (when part2
              (when verbose
                (println "\nPart 2 solution")
                (println (:doc (meta part2))))
              (println (part2 parsed)))))))))
