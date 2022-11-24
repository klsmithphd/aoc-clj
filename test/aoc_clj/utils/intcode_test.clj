(ns aoc-clj.utils.intcode-test
  (:require [clojure.test :refer [deftest testing is]]
            [manifold.stream :as s]
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

(def s6     [1002 4 3 4 33])
(def s6-out [1002 4 3 4 99])

(deftest addmul-ops-test
  (testing "Add and multiply ops work as intended"
    (is (=  s1-it1 (:intcode (intcode/apply-inst {:intcode s1 :iptr 0}))))
    (is (=  s1-it2 (:intcode (intcode/apply-inst {:intcode s1-it1 :iptr 4}))))))

(deftest parse-instruction-test
  (testing "Can parse the instruction integer"
    (is (= (select-keys (intcode/parse-instruction 1002) [:width :modes])
           {:width 4 :modes [:position :immediate :position]}))
    (is (= (select-keys (intcode/parse-instruction 1) [:width :modes])
           {:width 4 :modes [:position :position :position]}))
    (is (= (select-keys (intcode/parse-instruction 103) [:width :modes])
           {:width 2 :modes [:immediate]}))))

(deftest intcode-ex-test
  (testing "Intcode execution results in correct final intcode state"
    (is (= s2-out (:intcode (intcode/intcode-exec s2))))
    (is (= s3-out (:intcode (intcode/intcode-exec s3))))
    (is (= s4-out (:intcode (intcode/intcode-exec s4))))
    (is (= s5-out (:intcode (intcode/intcode-exec s5))))
    (is (= s6-out (:intcode (intcode/intcode-exec s6))))))

(deftest equals-test
  (testing "Equals operator produces the correct output"
    ;; Using position mode, consider whether the input is equal to 8; output 1 (if it is).
    (is (= 1 (intcode/last-out (intcode/intcode-exec [3 9 8 9 10 9 4 9 99 -1 8] [8]))))
    ;; Using position mode, consider whether the input is equal to 8; output 0 (if it is not).
    (is (= 0 (intcode/last-out (intcode/intcode-exec [3 9 8 9 10 9 4 9 99 -1 8] [5]))))
    ;; Using immediate mode, consider whether the input is less than 8; output 1 (if it is).
    (is (= 1 (intcode/last-out (intcode/intcode-exec [3 3 1108 -1 8 3 4 3 99] [8]))))
    ;; Using immediate mode, consider whether the input is less than 8; output 0 (if it is not).
    (is (= 0 (intcode/last-out (intcode/intcode-exec [3 3 1108 -1 8 3 4 3 99] [4]))))))

(deftest less-than-test
  (testing "Less-than operator produces the correct output"
    ;; Using position mode, consider whether the input is less than 8; output 1 (if it is).
    (is (= 1 (intcode/last-out (intcode/intcode-exec [3 9 7 9 10 9 4 9 99 -1 8] [7]))))
    ;; Using position mode, consider whether the input is less than 8; output 0 (if it is not).
    (is (= 0 (intcode/last-out (intcode/intcode-exec [3 9 7 9 10 9 4 9 99 -1 8] [9]))))
    ;; Using immediate mode, consider whether the input is less than 8; output 1 (if it is).
    (is (= 1 (intcode/last-out (intcode/intcode-exec [3 3 1107 -1 8 3 4 3 99] [6]))))
    ;; Using immediate mode, consider whether the input is less than 8; output 0 (if it is not).
    (is (= 0 (intcode/last-out (intcode/intcode-exec [3 3 1107 -1 8 3 4 3 99] [12]))))))

(deftest jump-test
  (testing "Jump operators produce the correct output"
    ;; output 0 if the input was zero or 1 if the input was non-zero:
    (is (= 0 (intcode/last-out (intcode/intcode-exec [3 12 6 12 15 1 13 14 13 4 13 99 -1 0 1 9] [0]))))
    (is (= 1 (intcode/last-out (intcode/intcode-exec [3 12 6 12 15 1 13 14 13 4 13 99 -1 0 1 9] [8]))))
    (is (= 0 (intcode/last-out (intcode/intcode-exec [3 3 1105 -1 9 1101 0 0 12 4 12 99 1] [0]))))
    (is (= 1 (intcode/last-out (intcode/intcode-exec [3 3 1105 -1 9 1101 0 0 12 4 12 99 1] [-5]))))))


(def s7 [3 21 1008 21 8 20 1005 20 22 107 8 21 20 1006 20 31
         1106 0 36 98 0 0 1002 21 125 20 4 20 1105 1 46 104
         999 1105 1 46 1101 1000 1 20 4 20 1105 1 46 98 99])

(deftest day05-complex-test
  (testing "Correct behavior from more complex scenario from day05"
    ;; The program will then output 999 if the input value is below 8
    (is (=  999 (intcode/last-out (intcode/intcode-exec s7 [7]))))
    ;; output 1000 if the input value is equal to 8
    (is (= 1000 (intcode/last-out (intcode/intcode-exec s7 [8]))))
    ;; or output 1001 if the input value is greater than 8
    (is (= 1001 (intcode/last-out (intcode/intcode-exec s7 [9]))))))

(deftest stream-input-test
  (testing "Can handle streaming inputs"
    (is (= 999
           (let [in (s/stream)
                 _  (s/put! in 7)]
             (intcode/last-out (intcode/intcode-exec s7 in)))))))