(ns aoc-clj.2023.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day05 :as t]))

(def d05-s01-raw
  ["seeds: 79 14 55 13"
   ""
   "seed-to-soil map:"
   "50 98 2"
   "52 50 48"
   ""
   "soil-to-fertilizer map:"
   "0 15 37"
   "37 52 2"
   "39 0 15"
   ""
   "fertilizer-to-water map:"
   "49 53 8"
   "0 11 42"
   "42 0 7"
   "57 7 4"
   ""
   "water-to-light map:"
   "88 18 7"
   "18 25 70"
   ""
   "light-to-temperature map:"
   "45 77 23"
   "81 45 19"
   "68 64 13"
   ""
   "temperature-to-humidity map:"
   "0 69 1"
   "1 0 69"
   ""
   "humidity-to-location map:"
   "60 56 37"
   "56 93 4"])

(def d05-s01
  {:seeds      [79 14 55 13]
   :range-maps [[[50 97 2]
                 [98 99 -48]]
                [[0 14 39]
                 [15 51 -15]
                 [52 53 -15]]
                [[0 6 42]
                 [7 10 50]
                 [11 52 -11]
                 [53 60 -4]]
                [[18 24 70]
                 [25 94 -7]]
                [[45 63 36]
                 [64 76 4]
                 [77 99 -32]]
                [[0 68 1]
                 [69 69 -69]]
                [[56 92 4]
                 [93 96 -37]]]})

(deftest parse-test
  (testing "Parses the input correctly"
    (is (= d05-s01 (t/parse d05-s01-raw)))))

(deftest apply-map-test
  (testing "Correctly maps a source number to a target number given a map"
    (is (= 0  (t/apply-range-map 0  (first (:range-maps d05-s01)))))
    (is (= 49 (t/apply-range-map 49 (first (:range-maps d05-s01)))))
    (is (= 52 (t/apply-range-map 50 (first (:range-maps d05-s01)))))
    (is (= 99 (t/apply-range-map 97 (first (:range-maps d05-s01)))))
    (is (= 50 (t/apply-range-map 98 (first (:range-maps d05-s01)))))
    (is (= 51 (t/apply-range-map 99 (first (:range-maps d05-s01)))))))

(deftest range-mappings-test
  (testing "Maps a seed number through each of the mappings"
    (is (= [79 81 81 81 74 78 78 82]
           (t/range-mappings 79 (:range-maps d05-s01))))
    (is (= [14 14 53 49 42 42 43 43]
           (t/range-mappings 14 (:range-maps d05-s01))))
    (is (= [55 57 57 53 46 82 82 86]
           (t/range-mappings 55 (:range-maps d05-s01))))
    (is (= [13 13 52 41 34 34 35 35]
           (t/range-mappings 13 (:range-maps d05-s01))))))

(deftest location-test
  (testing "Maps a seed to its location"
    (is (= 82 (t/location 79 (:range-maps d05-s01))))
    (is (= 43 (t/location 14 (:range-maps d05-s01))))
    (is (= 86 (t/location 55 (:range-maps d05-s01))))
    (is (= 35 (t/location 13 (:range-maps d05-s01))))))

(deftest lowest-location-test
  (testing "Finds the lowest location value"
    (is (= 35 (t/lowest-location d05-s01)))))

;; (deftest seed-ranges
;;   (testing "Converts the seed numbers into ranges to explore"
;;     (is (= [79 80 81 82 83 84 85 86 87 88 89 90 91 92
;;             55 56 57 58 59 60 61 62 63 64 65 66 67]
;;            (t/seed-ranges d05_s01)))))

(deftest range-lowest-location-test
  (testing "Finds the lowest location value among seed ranges"
    (is (= 46 (t/range-lowest-location d05-s01)))))

(def day05-input (u/parse-puzzle-input t/parse 2023 5))

(deftest day05-part1-soln
  (testing "Reproduces the answer for day05, part1"
    (is (= 323142486 (t/day05-part1-soln day05-input)))))

(deftest day05-part2-soln
  (testing "Reproduces the answer for day05, part2"
    (is (= 79874951 (t/day05-part2-soln day05-input)))))


;; (take 10 (t/day05-part2-soln day05-input))
;; (clojure.pprint/pprint (take 1 (:range-maps day05-input)))

;; (t/day05-part2-soln (update day05-input :range-maps (partial take 1)))

;; (reduce t/next-values
;;         (t/seed-ranges (:seeds day05-input))
;;         (take 1 (:range-maps day05-input)))

;; (t/seed-ranges (:seeds day05-input))
;; (t/intervals-to-propagate
;;  (t/seed-ranges (:seeds day05-input))
;;  (nth (:range-maps day05-input) 0))
;; (partition 2
;;            (map #(t/apply-range-map % (nth (:range-maps day05-input) 0))
;;                 (flatten (t/intervals-to-propagate
;;                           (t/seed-ranges (:seeds day05-input))
;;                           (nth (:range-maps day05-input) 0)))))

;; (clojure.pprint/pprint (sort (nth (:range-maps day05-input) 1)))
;; (def after-1 [[2637529854 2860924752] [3007537707 3511520873] [307349251 504732785] [3543757609 3820406008] [2296792159 2437803013] [116452725 121613257] [2246652813 2296420148] [762696372 890067009] [890067009 890067010] [890067010 923151448] [3960442213 4066309213] [1197133308 1235680073]])
;; (partition 2
;;            (map #(t/apply-range-map % (nth (:range-maps day05-input) 1))
;;                 (flatten (t/intervals-to-propagate
;;                           after-1
;;                           (nth (:range-maps day05-input) 1)))))
;; (clojure.pprint/pprint (:range-maps d05-s01))
;; (clojure.pprint/pprint (reductions t/next-values (t/seed-ranges (:seeds d05-s01)) (:range-maps d05-s01)))

;; (clojure.pprint/pprint
;;  (reductions t/next-values (t/seed-ranges (:seeds day05-input)) (:range-maps day05-input)))

;; 50184598
;; 595208874
;; 422846749