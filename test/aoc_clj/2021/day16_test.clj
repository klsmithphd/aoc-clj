(ns aoc-clj.2021.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day16 :as t]))

(def d16-s00 (t/parse ["D2FE28"]))
(def d16-s01 (t/parse ["38006F45291200"]))
(def d16-s02 (t/parse ["EE00D40C823060"]))

(def d16-s03 (t/parse ["8A004A801A8002F478"]))
(def d16-s04 (t/parse ["620080001611562C8802118E34"]))
(def d16-s05 (t/parse ["C0015000016115A2E0802F182340"]))
(def d16-s06 (t/parse ["A0016C880162017C3686B18A3D4780"]))

(def d16-s07 (t/parse ["C200B40A82"]))
(def d16-s08 (t/parse ["04005AC33890"]))
(def d16-s09 (t/parse ["880086C3E88112"]))
(def d16-s10 (t/parse ["CE00C43D881120"]))
(def d16-s11 (t/parse ["D8005AC2A8F0"]))
(def d16-s12 (t/parse ["F600BC2D8F"]))
(def d16-s13 (t/parse ["9C005AC2F8F0"]))
(def d16-s14 (t/parse ["9C0141080250320F1802104A08"]))

(deftest decode
  (testing "Can decode all of the samples"
    (is (= {:version 6, :type 4, :bits 21, :value 2021}
           (t/decode d16-s00)))
    (is (= {:version 1, :type 6, :bits 49 :length 27
            :subpackets [{:version 6, :type 4, :bits 11, :value 10}
                         {:version 2, :type 4, :bits 16, :value 20}]}
           (t/decode d16-s01)))
    (is (= {:version 7, :type 3, :bits 51, :count 3
            :subpackets [{:version 2, :type 4, :bits 11, :value 1}
                         {:version 4, :type 4, :bits 11, :value 2}
                         {:version 1, :type 4, :bits 11, :value 3}]}
           (t/decode d16-s02)))))

(deftest version-sum
  (testing "Computes the version code sum for all packets"
    (is (= 16 (t/version-sum 0 (t/decode d16-s03))))
    (is (= 12 (t/version-sum 0 (t/decode d16-s04))))
    (is (= 23 (t/version-sum 0 (t/decode d16-s05))))
    (is (= 31 (t/version-sum 0 (t/decode d16-s06))))))

(deftest apply-operator
  (testing "Applies the operator logic to compute a value"
    (is (= 3  (t/apply-operator (t/decode d16-s07))))
    (is (= 54 (t/apply-operator (t/decode d16-s08))))
    (is (= 7  (t/apply-operator (t/decode d16-s09))))
    (is (= 9  (t/apply-operator (t/decode d16-s10))))
    (is (= 1  (t/apply-operator (t/decode d16-s11))))
    (is (= 0  (t/apply-operator (t/decode d16-s12))))
    (is (= 0  (t/apply-operator (t/decode d16-s13))))
    (is (= 1  (t/apply-operator (t/decode d16-s14))))))

(def day16-input (u/parse-puzzle-input t/parse 2021 16))

(deftest day16-part1-soln
  (testing "Reproduces the answer for day16, part1"
    (is (= 981 (t/day16-part1-soln day16-input)))))

(deftest day16-part2-soln
  (testing "Reproduces the answer for day16, part2"
    (is (= 299227024091 (t/day16-part2-soln day16-input)))))
