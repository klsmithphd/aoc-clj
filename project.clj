(defproject aoc-clj "0.1.0-SNAPSHOT"
  :description "Solutions to Advent of Code challenges implemented in Clojure"
  :url "https://github.com/Ken-2scientists/aoc-clj"
  :license {:name "Apache License, Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/data.priority-map "1.1.0"]
                 [org.clojure/math.combinatorics "0.1.6"]
                 [org.clojure/tools.cli "1.0.219"]
                 [cheshire "5.10.0"]
                 [clojure-lanterna "0.9.7"]
                 [manifold "0.2.4"]
                 [net.mikera/core.matrix "0.63.0"]
                 [net.mikera/vectorz-clj "0.48.0"]]
  :test-selectors {:default (complement :slow)
                   :slow :slow}
  :repl-options {:init-ns aoc-clj.core}
  :jvm-opts ["-Xmx4g"]
  :main aoc-clj.core)
