(ns aoc-clj.assembunny-test
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

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= s00 (asmb/parse s00-raw)))))

(deftest execute-test
  (testing "Executes sample instructions"
    (is (= {:a 42, :b 0, :c 0, :d 0, :inst 6 :cmds s00}
           (asmb/execute asmb/init-state s00)))
    (is (= {:a 3 :b 0 :c 0 :d 0 :inst 7
            :cmds [["cpy" 2 :a]
                   ["tgl" :a]
                   ["tgl" :a]
                   ["inc" :a]
                   ["jnz" 1 :a]
                   ["dec" :a]
                   ["dec" :a]]}
           (asmb/execute asmb/init-state s01)))))
