(ns ^:eftest/synchronized aoc-clj.utils.digest-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.digest :as d]))

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