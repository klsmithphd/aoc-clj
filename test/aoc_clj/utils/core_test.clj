(ns aoc-clj.utils.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]))

(deftest split-at-blankline
  (testing "Demonstration of split-at-blankline"
    (is (= [["Chunk 1" "Some values"]
            ["Chunk 2" "Some other values"]]
           (u/split-at-blankline ["Chunk 1"
                                  "Some values"
                                  ""
                                  "Chunk 2"
                                  "Some other values"])))))

(deftest str->vec-test
  (testing "Demonstration of str->vec, convert a string into a vec of values"
    (is (= [1 2 3] (u/str->vec "1,2,3")))))

(deftest fmap-test
  (testing "Demonstration of fmap: applying a function to vals of a map"
    (is (= {:a 2 :b 3 :c 4} (u/fmap inc {:a 1 :b 2 :c 3})))))

(deftest kmap-test
  (testing "Demonstration of kmap: applying a function to keys of a map"
    (is (= {1 :a 2 :b 3 :c} (u/kmap inc {0 :a 1 :b 2 :c})))))

(deftest max-val-test
  (testing "Demonstration of max-val: find the key corresponding to the largest value"
    (is (= :c (u/max-val {:a 1 :b 4 :c 9})))))

(deftest min-val-test
  (testing "Demonstration of max-val: find the key corresponding to the smallest value"
    (is (= :a (u/min-val {:a 1 :b 4 :c 9})))))

(deftest without-keys-test
  (testing "Demonstration of without-keys: returning a map excluding keys. See `select-keys`"
    (is (= {:a 1 :b 2} (u/without-keys {:a 1 :b 2} [])))
    (is (= {:b 2 :c 3} (u/without-keys {:a 1 :b 2 :c 3 :d 4} [:a :d])))))

(deftest invert-map-test
  (testing "Demonstration of invert-map: swaps the keys and vals of a 1:1 map"
    (is (= {0 :a 1 :b 2 :c} (u/invert-map {:a 0 :b 1 :c 2})))))

(deftest keys-in-test
  (testing "Demonstration of keys-in: finds all key-paths in a nested map"
    (is (= [[:a] [:b] [:c] [:d] [:d :e] [:d :f] [:d :f :g] [:d :f :g :h]]
           (u/keys-in {:a 1 :b 2 :c 3 :d {:e 4 :f {:g {:h 5}}}})))))

(deftest rotate-test
  (testing "Demonstration of rotate: return a seq of same size, but with elements rotated by n"
    (is (= '(0 1 2 3 4) (u/rotate 0 (range 5))))
    (is (= '(1 2 3 4 0) (u/rotate 1 (range 5))))
    (is (= '(2 3 4 0 1) (u/rotate 2 (range 5))))
    (is (= '(3 4 0 1 2) (u/rotate 3 (range 5))))
    (is (= '(4 0 1 2 3) (u/rotate 4 (range 5))))
    (is (= '(0 1 2 3 4) (u/rotate 5 (range 5))))
    (is (= '(4 0 1 2 3) (u/rotate -1 (range 5))))
    (is (= '(3 4 0 1 2) (u/rotate -2 (range 5))))
    (is (= '(2 3 4 0 1) (u/rotate -3 (range 5))))
    (is (= '(1 2 3 4 0) (u/rotate -4 (range 5))))))

(deftest index-of-test
  (testing "Demonstration of index-of: find index in seq of first occurrence "
    (is (= 3 (u/index-of 8 [1 2 4 8 16])))
    (is (nil? (u/index-of 8 [1 3 9 27 81])))
    (is (= 1 (u/index-of :b [:a :b :b :b :b :b])))))

(deftest count-if-test
  (testing "Demonstration of count-if: find the number of elements of coll that satisfy pred"
    (is (= 5 (u/count-if (range 10) odd?)))))

(deftest rev-range-test
  (testing "Demonstration of rev-range: returns a countdown range"
    (is (= '(4 3 2 1 0)
           (u/rev-range 5)
           (reverse (range 5))))
    (is (= '(9 8 7 6 5)
           (u/rev-range 5 10)
           (reverse (range 5 10))))))

(deftest transpose-test
  (testing "Demonstration of transpose: returns the transpose of a vector
            of vectors"
    (is (= [[0 1]
            [2 3]
            [4 5]]
           (u/transpose [[0 2 4]
                         [1 3 5]])))
    (is (= [[0 1 2]
            [3 4 5]
            [6 7 8]]
           (u/transpose [[0 3 6]
                         [1 4 7]
                         [2 5 8]])))))

(deftest converge-test
  (testing "Demonstration of converge: returns a lazy seq of repeatedly
            applying a function to itself until there are no further
            changes"
    (is (= [128 64 32 16 8 4 2 1 0]
           (u/converge #(quot % 2) 128)))))