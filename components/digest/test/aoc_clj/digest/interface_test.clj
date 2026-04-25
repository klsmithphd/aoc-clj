(ns aoc-clj.digest.interface-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.digest.interface :as d]))

(def s00 (byte-array (map byte [0 0  1 -128]))) ;; 0x000001ff
(def s01 (byte-array (map byte [0 0 16 -128]))) ;; 0x000010ff
(def s02 (byte-array (map byte [0 0  0 -128]))) ;; 0x000000ff

(def hello-str "5d41402abc4b2a76b9719d911017c592")
(def hello-bytes
  ;;Hex:5d 41 40 2a  bc 4b 2a  76  b9  71  9d   91 10 17  c5   92
  (->> [93 65 64 42 -68 75 42 118 -71 113 -99 -111 16 23 -59 -110]
       (map byte)
       byte-array))

(deftest md5-digest-test
  (testing "Computes the MD5 digest (as bytes)"
    (is (= (seq hello-bytes) (seq (d/md5-digest "hello"))))))

(deftest md5-str-test
  (testing "Computes the md5 digest string"
    (is (= hello-str (d/md5-str "hello")))))

(deftest five-zero-start?-test
  (testing "Determines whether the byte array starts with five zeros"
    (is (= true (d/five-zero-start? s00)))
    (is (= false (d/five-zero-start? s01)))))

(deftest six-zero-start?-test
  (testing "Determines whether the byte array starts with six zeros"
    (is (= true (d/six-zero-start? s02)))
    (is (= false (d/six-zero-start? s00)))))

(deftest find-first-int-test
  (testing "Finds the smallest non-negative int satisfying the predicate"
    (is (= 0     (d/find-first-int #(= 0 %))))
    (is (= 7     (d/find-first-int #(= 7 %))))
    (is (= 1024  (d/find-first-int #(= 1024 %)))))
  (testing "Honors a small batch-size (covers cross-round answers)"
    ;; batch-size of 10 with N cores means a round covers 10*N candidates;
    ;; an answer past the first round exercises the loop/recur path.
    (is (= 137   (d/find-first-int #(= 137 %) 10)))
    (is (= 1234  (d/find-first-int #(= 1234 %) 10))))
  (testing "Takes min when multiple threads find hits in the same round"
    ;; Predicate matches many candidates; smallest must win regardless of
    ;; which thread happens to find which.
    (is (= 50    (d/find-first-int #(<= 50 % 200) 10)))))
