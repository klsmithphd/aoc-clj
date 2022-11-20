(ns aoc-clj.utils.intcode-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.intcode :as intcode]))

(def s1     [1 9 10  3 2 3 11 0 99 30 40 50])
(def s1-it1 [1 9 10 70 2 3 11 0 99 30 40 50])
(def s1-it2 [3500 9 10 70 2 3 11 0 99 30 40 50])

(def s2     [1 0 0 0 99])
(def s2-out [2 0 0 0 99])

(def s3     [2 3 0 3 99])
(def s3-out [2 3 0 6 99])

(def s4     [2 4 4 5 99 0])
(def s4-out [2 4 4 5 99 9801])

(def s5     [1 1 1 4 99 5 6 0 99])
(def s5-out [30 1 1 4  2 5 6 0 99])

(deftest addmul-ops-test
  (testing "Add and multiply ops work as intended"
    (is (= s1-it1 (intcode/apply-inst s1 0)))
    (is (= s1-it2 (intcode/apply-inst s1-it1 4)))))

(deftest intcode-ex-test
  (testing "Intcode execution results in correct final intcode state"
    (is (= s2-out (intcode/intcode-exec s2)))
    (is (= s3-out (intcode/intcode-exec s3)))
    (is (= s4-out (intcode/intcode-exec s4)))
    (is (= s5-out (intcode/intcode-exec s5)))))

