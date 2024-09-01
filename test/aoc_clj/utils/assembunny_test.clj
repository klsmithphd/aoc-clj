(ns aoc-clj.utils.assembunny-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.assembunny :as asmb]))

(def s00-raw
  ["cpy 41 a"
   "inc a"
   "inc a"
   "dec a"
   "jnz a 2"
   "dec a"])

(def s00
  [["cpy" 41 :a]
   ["inc" :a]
   ["inc" :a]
   ["dec" :a]
   ["jnz" :a 2]
   ["dec" :a]])

(def s01
  (asmb/parse
   ["cpy 2 a"
    "tgl a"
    "tgl a"
    "tgl a"
    "cpy 1 a"
    "dec a"
    "dec a"]))

(def s02
  (asmb/parse
   ["cpy 3 a"
    "out a"
    "dec a"
    "jnz a -2"]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= s00 (asmb/parse s00-raw)))))

(deftest execute-test
  (testing "Executes sample instructions"
    (is (= {:a 42, :b 0, :c 0, :d 0, :inst 6 :out [] :cmds s00}
           (asmb/execute asmb/init-state s00)))
    (is (= {:a 3 :b 0 :c 0 :d 0 :inst 7 :out []
            :cmds [["cpy" 2 :a]
                   ["tgl" :a]
                   ["tgl" :a]
                   ["inc" :a]
                   ["jnz" 1 :a]
                   ["dec" :a]
                   ["dec" :a]]}
           (asmb/execute asmb/init-state s01)))
    (is (= {:a 0 :b 0 :c 0 :d 0 :inst 4 :out [3 2 1] :cmds s02}
           (asmb/execute asmb/init-state s02)))))
