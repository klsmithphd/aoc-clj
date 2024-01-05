(ns aoc-clj.2021.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day18 :as t]))

(def d18-s00-in  [[[[[9,8],1],2],3],4])
(def d18-s00-out [[[[0,9],2],3],4])

(def d18-s02-in  [7,[6,[5,[4,[3,2]]]]])
(def d18-s02-out [7,[6,[5,[7,0]]]])

(def d18-s03-in  [[6,[5,[4,[3,2]]]],1])
(def d18-s03-out [[6,[5,[7,0]]],3])

(def d18-s04-in  [[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]])
(def d18-s04-out [[3,[2,[8,0]]],[9,[5,[7,0]]]])

(def d18-s05-in  [[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]])
(def d18-s05-out [[[[0,7],4],[[7,8],[6,0]]],[8,1]])

(def d18-s06-in [[1,1]
                 [2,2]
                 [3,3]
                 [4,4]])
(def d18-s06-out [[[[1,1],[2,2]],[3,3]],[4,4]])

(def d18-s07-in [[1,1]
                 [2,2]
                 [3,3]
                 [4,4]
                 [5,5]])
(def d18-s07-out [[[[3,0],[5,3]],[4,4]],[5,5]])

(def d18-s08-in [[1,1]
                 [2,2]
                 [3,3]
                 [4,4]
                 [5,5]
                 [6,6]])
(def d18-s08-out [[[[5,0],[7,4]],[5,5]],[6,6]])

(def d18-s09-in [[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
                 [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
                 [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
                 [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
                 [7,[5,[[3,8],[1,4]]]]
                 [[2,[2,2]],[8,[8,1]]]
                 [2,9]
                 [1,[[[9,3],9],[[9,0],[0,7]]]]
                 [[[5,[7,4]],7],1]
                 [[[[4,2],2],6],[8,7]]])
(def d18-s09-out [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]])

(def d18-s10-in [[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
                 [[[5,[2,8]],4],[5,[[9,9],0]]]
                 [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
                 [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
                 [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
                 [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
                 [[[[5,4],[7,7]],8],[[8,3],8]]
                 [[9,3],[[9,9],[6,[4,9]]]]
                 [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
                 [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]])
(def d18-s10-out [[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]])

(deftest number-reduce
  (testing "Correctly applies number reduction logic for exploding/splitting"
    (is (= d18-s00-out (t/number-reduce d18-s00-in)))
    (is (= d18-s02-out (t/number-reduce d18-s02-in)))
    (is (= d18-s03-out (t/number-reduce d18-s03-in)))
    (is (= d18-s04-out (t/number-reduce d18-s04-in)))
    (is (= d18-s05-out (t/number-reduce d18-s05-in)))))

(deftest addition
  (testing "Snailfish addition, including reduction steps works correctly on s0 data"
    (is (= d18-s06-out (reduce t/snailfish-add d18-s06-in)))
    (is (= d18-s07-out (reduce t/snailfish-add d18-s07-in)))
    (is (= d18-s08-out (reduce t/snailfish-add d18-s08-in)))
    (is (= d18-s09-out (reduce t/snailfish-add d18-s09-in)))
    (is (= d18-s10-out (reduce t/snailfish-add d18-s10-in)))))

(deftest magnitude
  (testing "Computes the recursive magnitude of a number"
    (is (=   29 (t/magnitude [9 1])))
    (is (=   21 (t/magnitude [1 9])))
    (is (= 1384 (t/magnitude [[[[0,7],4],[[7,8],[6,0]]],[8,1]])))
    (is (=  445 (t/magnitude [[[[1,1],[2,2]],[3,3]],[4,4]])))
    (is (=  791 (t/magnitude [[[[3,0],[5,3]],[4,4]],[5,5]])))
    (is (= 1137 (t/magnitude [[[[5,0],[7,4]],[5,5]],[6,6]])))
    (is (= 3488 (t/magnitude [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]])))
    (is (= 4140 (t/magnitude d18-s10-out)))))

(deftest largest-magnitude-sum
  (testing "Finds the largest magnitude sum in the s0 data"
    (is (= 3993 (t/largest-magnitude-sum d18-s10-in)))))

(def day18-input (u/parse-puzzle-input t/parse 2021 18))

(deftest part1-test
  (testing "Reproduces the answer for day18, part1"
    (is (= 3647 (t/part1 day18-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day18, part2"
    (is (= 4600 (t/part2 day18-input)))))
