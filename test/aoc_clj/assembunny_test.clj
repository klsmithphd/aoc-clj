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
  [{:cmd "cpy" :x 41 :y :a}
   {:cmd "inc" :x :a}
   {:cmd "inc" :x :a}
   {:cmd "dec" :x :a}
   {:cmd "jnz" :x :a :y 2}
   {:cmd "dec" :x :a}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= s00 (asmb/parse s00-raw)))))

(def s00-init {:a 0 :b 0 :c 0 :d 0 :inst 0})

(deftest execute-test
  (testing "Executes sample instructions"
    (is (= {:a 42, :b 0, :c 0, :d 0, :inst 6}
           (asmb/execute s00-init s00)))))
