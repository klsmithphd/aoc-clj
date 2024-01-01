(ns aoc-clj.core
  (:require [clojure.string :as str]
            [clojure.tools.cli :refer [parse-opts]]
            [aoc-clj.utils.core :as u])
  (:gen-class))

(def cli-options
  [["-i" "--input PATH" "Puzzle input file"]
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

(defn -main [& args]
  (let [{:keys [options arguments summary]} (parse-opts args cli-options)]
    (if (or (options :help)
            (< (count arguments) 2))
      (println (usage summary))
      (let [[year day]              (map read-string arguments)
            {:keys [input]} options
            filename (if input input (u/default-puzzle-input-path year day))]
        (println filename)))))