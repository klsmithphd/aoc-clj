(ns aoc-clj.2024.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day17 :as d17]))

(def d17-s00-raw
  ["Register A: 729"
   "Register B: 0"
   "Register C: 0"
   ""
   "Program: 0,1,5,4,3,0"])

(def d17-s00
  {:regs {:a 729 :b 0 :c 0}
   :prog [0 1 5 4 3 0]})

(def d17-s01
  {:regs {:a 0 :b 0 :c 9}
   :prog [2 6]})

(def d17-s02
  {:regs {:a 10 :b 0 :c 0}
   :prog [5 0 5 1 5 4]})

(def d17-s03
  {:regs {:a 2024 :b 0 :c 0}
   :prog [0 1 5 4 3 0]})

(def d17-s04
  {:regs {:a 0 :b 29 :c 0}
   :prog [1 7]})

(def d17-s05
  {:regs {:a 0 :b 2024 :c 43690}
   :prog [4 0]})

(def d17-s06
  {:regs {:a 2024 :b 0 :c 0}
   :prog [0 3 5 4 3 0]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d17-s00 (d17/parse d17-s00-raw)))))

(deftest execute-test
  (testing "Returns the expected result for the program"
    (is (= (-> (d17/init-state d17-s00)
               (assoc-in [:regs :a] 0)
               (assoc :out [4 6 3 5 6 3 5 2 1 0])
               (assoc :ip 6))
           (d17/execute d17-s00)))

    (is (= (-> (d17/init-state d17-s01)
               (assoc-in [:regs :b] 1)
               (assoc :ip 2))
           (d17/execute d17-s01)))

    (is (= (-> (d17/init-state d17-s02)
               (assoc :out [0 1 2])
               (assoc :ip 6))
           (d17/execute d17-s02)))

    (is (= (-> (d17/init-state d17-s03)
               (assoc-in [:regs :a] 0)
               (assoc :out [4 2 5 6 7 7 7 7 3 1 0])
               (assoc :ip 6))
           (d17/execute d17-s03)))

    (is (= (-> (d17/init-state d17-s04)
               (assoc-in [:regs :b] 26)
               (assoc :ip 2))
           (d17/execute d17-s04)))

    (is (= (-> (d17/init-state d17-s05)
               (assoc-in [:regs :b] 44354)
               (assoc :ip 2))
           (d17/execute d17-s05)))))

(deftest a-value-that-copies-test
  (testing "Finds the A register value that causes the program to copy itself
            into the output"
    (is (= 117440 (d17/a-value-that-copies d17-s06)))))

(def day17-input (u/parse-puzzle-input d17/parse 2024 17))

(deftest part1-test
  (testing "Reproduces the answer for day17, part1"
    (is (= "1,4,6,1,6,4,3,0,3" (d17/part1 day17-input)))))

;; (d17/a-value-that-copies day17-input)
;; (reduce + (map * (iterate #(* 8 %) 8)
;;                [0 3 5 4 3 0]))

;; (* 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8)

;; ;; (deftest part2-test
;; ;;   (testing "Reproduces the answer for day17, part2"
;; ;;     (is (= 117440 (d17/part2 day17-input)))))

;; day17-input

;; (defn test-exec
;;   [input a-reg]
;;   (:out (d17/execute (assoc-in input [:regs :a] a-reg))))

;; (defn a-value-that-copies
;;   [{:keys [prog] :as input}]
;;   (let [first-pass (->> (range 4096)
;;                         (filter #(= (take 2 prog) (take 2 (test-exec input %)))))]
;;     first-pass))

;; (map #(test-exec day17-input %) (a-value-that-copies day17-input))
;; (count *1)

;; (:prog day17-input)
;; (test-exec day17-input (+ 7032631133083 (* 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8)))
;; (bit-shift-left 2 12)

;; (->> (mapcat #(map (fn [x] (+ x (bit-shift-left % 10))) *1) (range 1024))
;;      (filter #(= [2 4]
;;                  (take 2 (:out (d17/execute (assoc-in day17-input [:regs :a] %)))))))

;; (def foo
;;   (->> (range 1024)
;;        (filter #(= [2 4]
;;                    (take 2 (:out (d17/execute (assoc-in day17-input [:regs :a] %))))))))
;; foo


;; (def bar
;;   (->> (mapcat #(map (fn [x] (+ x (bit-shift-left % 10))) foo) (range 8))
;;        (filter #(= [2 4 1]
;;                    (take 3 (:out (d17/execute (assoc-in day17-input [:regs :a] %))))))))
;; bar
;; (:out (d17/execute (assoc-in day17-input [:regs :a] 8093)))


;; (->> (mapcat #(map (fn [x] (+ x (bit-shift-left % 13))) bar) (range 8))
;;      (filter #(= [2 4 1]
;;                  (take 3 (:out (d17/execute (assoc-in day17-input [:regs :a] %)))))))


;; (def baz '(7032631133083 7032631133085 7032631137179 7032631137181 7032631141275 7032631141277 7032631137179 7032631137181 7032631141275 7032631141277 42217003221915 42217003221917 42217003226011 42217003226013 42217003230107 42217003230109 42217003226011 42217003226013 42217003230107 42217003230109 77401375310747 77401375310749 77401375314843 77401375314845 77401375318939 77401375318941 77401375314843 77401375314845 77401375318939 77401375318941 112585747399579 112585747399581 112585747403675 112585747403677 112585747407771 112585747407773 112585747403675 112585747403677 112585747407771 112585747407773 147770119488411 147770119488413 147770119492507 147770119492509 147770119496603 147770119496605 147770119492507 147770119492509 147770119496603 147770119496605 182954491577243 182954491577245 182954491581339 182954491581341 182954491585435 182954491585437 182954491581339 182954491581341 182954491585435 182954491585437 218138863666075 218138863666077 218138863670171 218138863670173 218138863674267 218138863674269 218138863670171 218138863670173 218138863674267 218138863674269 253323235754907 253323235754909 253323235759003 253323235759005 253323235763099 253323235763101 253323235759003 253323235759005 253323235763099 253323235763101))
;; (->> (mapcat #(map (fn [x] (+ x (bit-shift-left % 45))) baz) (range 1024))
;;      (filter #(= [2 4 1 7 7 5 1 7 4 6 0 3 5 5 3 0]
;;                  (:out (d17/execute (assoc-in day17-input [:regs :a] %))))))



;; (->> baz
;;      (filter #(= [2 4 1 7 7 5 1 7 4 6 0 3 5 5 3 0]
;;                  (:out (d17/execute (assoc-in day17-input [:regs :a] %))))))
;; (map #(:out (d17/execute (assoc-in day17-input [:regs :a] %))) (map #(+ 10 %) (take 10 baz)))

;; (count foo)
;; (count (mapcat #(map (fn [x] (+ x (bit-shift-left % 10))) foo) (range 8)))

;; (:out (d17/execute (assoc-in day17-input [:regs :a] 253323235763101)))

;; ;; 569982584554395 too high
;; (mod 569982584554395 (* 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 2))

;; 569982584554395


