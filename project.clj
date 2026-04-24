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
                 [data.deque "0.1.0"]
                 [manifold "0.2.4"]
                 [net.mikera/core.matrix "0.63.0"]
                 [net.mikera/vectorz-clj "0.48.0"]
                 [potemkin "0.4.7"]]
  :profiles {:dev {:plugins [[com.github.clj-kondo/lein-clj-kondo "2026.04.15"]]}}
  :source-paths ["src"
                 "components/util/src"
                 "components/binary/src"
                 "components/digest/src"
                 "components/math/src"
                 "components/vectors/src"
                 "components/geometry/src"
                 "components/intervals/src"
                 "components/grid/src"
                 "components/graph/src"
                 "components/maze/src"
                 "components/assembunny/src"
                 "components/intcode/src"
                 "components/year-2015/src"
                 "components/year-2016/src"
                 "components/year-2017/src"
                 "components/year-2018/src"
                 "components/year-2019/src"
                 "components/year-2020/src"
                 "components/year-2021/src"
                 "components/year-2022/src"
                 "components/year-2023/src"
                 "components/year-2024/src"
                 "components/year-2025/src"]
  :test-paths   ["test"
                 "components/util/test"
                 "components/binary/test"
                 "components/digest/test"
                 "components/math/test"
                 "components/vectors/test"
                 "components/geometry/test"
                 "components/intervals/test"
                 "components/grid/test"
                 "components/graph/test"
                 "components/maze/test"
                 "components/assembunny/test"
                 "components/intcode/test"
                 "components/year-2015/test"
                 "components/year-2016/test"
                 "components/year-2017/test"
                 "components/year-2018/test"
                 "components/year-2019/test"
                 "components/year-2020/test"
                 "components/year-2021/test"
                 "components/year-2022/test"
                 "components/year-2023/test"
                 "components/year-2024/test"
                 "components/year-2025/test"]
  :test-selectors {:default (complement :slow)
                   :slow :slow}
  :repl-options {:init-ns aoc-clj.core}
  :jvm-opts ["-Xmx4g"]
  :main aoc-clj.core)
