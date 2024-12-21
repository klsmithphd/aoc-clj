(ns aoc-clj.utils.graph-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.graph :as g :refer [without-vertex rewired-without-vertex ->MapGraph]]
            [aoc-clj.utils.core :as u]))

(def t1 (->MapGraph {:a {:b 1}
                     :b {:a 1 :c 2}
                     :c {:b 2 :d 3}
                     :d {:c 3 :e 1}
                     :e {:d 1}}))

(def t2 (->MapGraph {:a {:b 1}
                     :b {:a 1 :c 2 :f 4}
                     :c {:b 2 :d 3}
                     :d {:c 3 :e 1}
                     :e {:d 1}
                     :f {:b 4 :g 1}
                     :g {:f 1}}))

(def t3 (->MapGraph {:a {:b 7 :c 14 :d 9}
                     :b {:a 7 :d 10 :e 15}
                     :c {:a 14 :d 2 :f 9}
                     :d {:a 9 :b 10 :c 2 :e 11}
                     :e {:b 15 :d 11 :f 6}
                     :f {:c 9 :e 6}}))

(def t4 (->MapGraph {:a {:j 3}
                     :b {:j 2}
                     :c {:j 4}
                     :d {:j 6}
                     :j {:a 3 :b 2 :c 4 :d 6}}))

(deftest without-vertex-test
  (testing "Can return a new graph with a vertex (and its corresponding edges) removed"
    (is (= (->MapGraph {:b {:c 2}, :c {:b 2, :d 3}, :d {:c 3, :e 1}, :e {:d 1}})
           (without-vertex t1 :a)))))

(deftest rewired-without-vertex-test
  (testing "Can return a new graph with a vertex removed, preserving the transitive relationships"
    (is (= (->MapGraph {:a {:b 5 :c 7 :d 9}
                        :b {:a 5 :c 6 :d 8}
                        :c {:a 7 :b 6 :d 10}
                        :d {:a 9 :b 8 :c 10}})
           (rewired-without-vertex t4 :j)))))

(deftest pruned-test
  (testing "Can prune a graph of unnecessary branches"
    (is (= (g/pruned t2 #{:a :g})
           (->MapGraph {:a {:b 1}
                        :b {:a 1 :f 4}
                        :f {:b 4 :g 1}
                        :g {:f 1}})))))

(deftest single-path-test
  (testing "Can traverse a graph until its end or a junction is reached"
    (is (= [:a :b :c :d :e] (g/single-path t1 :a)))
    (is (= [:a :b]          (g/single-path t2 :a)))
    (is (= [:g :f :b]       (g/single-path t2 :g)))))

(deftest all-paths-test
  (testing "Can traverse a graph until its end or a junction is reached"
    (is (= [[:b :a] [:b :c :d :e] [:b :f :g]]
           (g/all-paths t2 :b)))
    (is (= [[:g :f :b]]
           (g/all-paths t2 :g)))))

(def t5 (->MapGraph {:a {:b 1.5 :e 2}
                     :b {:c 2}
                     :c {:d 3}
                     :d {:g 4}
                     :e {:f 3}
                     :f {:g 2}}))
(def h5 {:a 6.5 :b 4 :c 2 :d 4 :e 4.5 :f 2 :g 0})

(deftest dijkstra-test
  (testing "Can find the shortest path between two vertices"
    (is (= [:a :d :c :f] (g/shortest-path t3 :a #(= :f %))))
    (is (= [:a :e :f :g] (g/shortest-path t5 :a (u/equals? :g) h5)))))

(deftest all-paths-dfs-test
  (testing "Finds all of the paths from start to finish using a depth-first
            search"
    (is (= [[:a :b :f :g]] (g/all-paths-dfs t2 :a (u/equals? :g))))
    (is (= [[:a :b :c :d :g]
            [:a :e :f :g]] (g/all-paths-dfs t5 :a (u/equals? :g))))
    (is (= [[:a :b :d :c :f]
            [:a :b :d :e :f]
            [:a :b :e :d :c :f]
            [:a :b :e :f]
            [:a :c :d :b :e :f]
            [:a :c :d :e :f]
            [:a :c :f]
            [:a :d :b :e :f]
            [:a :d :c :f]
            [:a :d :e :f]] (g/all-paths-dfs t3 :a (u/equals? :f))))))

(deftest flood-fill-test
  (testing "Returns the set of all distinct vertices in a graph reachable from a
            starting vertex, up to an optional traversal count limit"
    (is (= #{:a}                   (g/flood-fill t5 :a :limit 0)))
    (is (= #{:a :b :e}             (g/flood-fill t5 :a :limit 1)))
    (is (= #{:a :b :e :c :f}       (g/flood-fill t5 :a :limit 2)))
    (is (= #{:a :b :e :c :f :d :g} (g/flood-fill t5 :a :limit 3)))
    (is (= #{:a :b :e :c :f :d :g} (g/flood-fill t5 :a)))))

