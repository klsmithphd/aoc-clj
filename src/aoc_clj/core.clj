(ns aoc-clj.core
  "Core command-line interface to AoC solutions"
  (:require [clojure.string :as str]
            [clojure.tools.cli :refer [parse-opts]]
            [aoc-clj.utils.core :as u])
  (:gen-class))

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

(defn- resolve-fn
  [ns fn-name]
  (requiring-resolve (symbol (str ns "/" fn-name))))

(defn- soln-fns
  [year day]
  (let [day-str (str "day"  (format "%02d" day))
        ns  (str "aoc-clj." year "." day-str)]
    {:parse (resolve-fn ns "parse")
     :part1 (resolve-fn ns "part1")
     :part2 (resolve-fn ns "part2")}))

(defn -main [& args]
  (let [{:keys [options arguments summary]} (parse-opts args cli-options)]
    (if (or (options :help)
            (< (count arguments) 2))
      (println (usage summary))
      (let [[year day]              (map read-string arguments)
            {:keys [input verbose]} options
            filename (if input input (u/default-puzzle-input-path year day))
            puzzle   (u/puzzle-input filename)
            {:keys [parse part1 part2]} (soln-fns year day)]
        (when verbose
          (println (format "Advent of Code %s, Day %s, using %s" year day filename))
          (println "\nPart 1 solution")
          (println (:doc (meta part1))))
        (println (part1 (parse puzzle)))
        (when verbose
          (println "\nPart 2 solution")
          (println (:doc (meta part2))))
        (println (part2 (parse puzzle)))))))