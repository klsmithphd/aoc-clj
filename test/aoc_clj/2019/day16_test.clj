(ns aoc-clj.2019.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day16 :as t]))

(deftest phase-test
  (testing "Correctly apply phases"
    (is (= ["12345678" "48226158" "34040438" "03415518" "01029498"]
           (->> (t/str->nums "12345678")
                (iterate t/phase)
                (take 5)
                (map t/nums->str))))
    (is (= "24176176"
           (t/nums->str
            (take 8
                  (t/run-phases
                   (t/str->nums "80871224585914546619083218645595") 100)))))
    (is (= "73745418"
           (t/nums->str
            (take 8
                  (t/run-phases
                   (t/str->nums "19617804207202209144916044189917") 100)))))
    (is (= "52432133"
           (t/nums->str
            (take 8
                  (t/run-phases
                   (t/str->nums "69317163492948606335995924319873") 100)))))))

(deftest real-signal-test
  (testing "Can compute handle the `real signal` challenge in part 2"
    (is (= "84462026"
           (t/nums->str
            (t/real-signal
             (t/str->nums "03036732577212944063491565474664") 100))))
    (is (= "78725270"
           (t/nums->str
            (t/real-signal
             (t/str->nums "02935109699940807407585447034323") 100))))
    (is (= "53553731"
           (t/nums->str
            (t/real-signal
             (t/str->nums "03081770884921959731165446850517") 100))))))

(def day16-input (u/parse-puzzle-input t/parse 2019 16))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= "70856418" (t/part1 day16-input)))))

;; FIXME: test is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/17
(deftest ^:slow part2-test
  (testing "Can reproduce the answer for part2"
    (is (= "87766336" (t/part2 day16-input)))))