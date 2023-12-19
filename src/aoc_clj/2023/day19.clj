(ns aoc-clj.2023.day19
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-rule
  [rule-str]
  (if (nil? (str/index-of rule-str ":"))
    [(keyword rule-str)]
    (let [value (subs rule-str 0 1)
          oper  (subs rule-str 1 2)
          [num outcome] (str/split (subs rule-str 2) #":")]
      [(keyword outcome) value oper (read-string num)])))

(defn parse-workflow
  [workflow-str]
  (let [[id rules]    (str/split workflow-str #"\{")
        trimmed-rules (subs rules 0 (dec (count rules)))]
    [(keyword id) (map parse-rule (str/split trimmed-rules #","))]))

(defn parse-parts
  [input-str]
  (mapv read-string (re-seq #"\d+" input-str)))

(defn parse
  [input]
  (let [[workflows inputs] (u/split-at-blankline input)]
    {:workflows (into {} (map parse-workflow workflows))
     :parts     (map parse-parts inputs)}))

(defn cond->str
  "Construct a cond clause as a string for each of the non-terminal 
   workflow rules"
  [[outcome value oper num]]
  (str "(" oper " " value " " num ")" " " outcome))

(defn workflow->fn-str
  "Create a valid clojure function definition as a string based on the
   rules structure"
  [rules]
  (let [opening ["(fn [[x m a s]]"
                 "(cond"]
        body    (into opening (map cond->str (butlast rules)))
        closing (into body [(str ":else " (first (last rules)) "))")])]
    (str/join " " closing)))

(defn workflow->fn
  "Create clojure functions for each of the workflow rules structures"
  [rules]
  (eval (read-string (workflow->fn-str rules))))

(defn functionized-input
  "Augments the input data structure with a `workflow-fns` key that
   has the compiled workflow logic"
  [{:keys [workflows] :as input}]
  (assoc input :workflow-fns (u/fmap workflow->fn workflows)))

(defn apply-workflows
  "Executes the workflow functions against the part ratings data
   to determine whether it is accepted (`:A`) or rejected (`:R`)"
  [{:keys [workflow-fns]} part]
  (loop [workflow (get workflow-fns :in)]
    (let [outcome (workflow part)]
      (if (or (= :A outcome) (= :R outcome))
        outcome
        (recur (get workflow-fns outcome))))))

(defn accepted-parts
  "Returns the parts data for the items that are accepted by the workflows"
  [{:keys [parts] :as input}]
  (let [input-fns (functionized-input input)]
    (filter #(= :A (apply-workflows input-fns %)) parts)))

(defn accepted-parts-sum
  "Computes the sum of all the ratings values for all the parts that
   are accepted by the workflow"
  [input]
  (->> (accepted-parts input)
       (map (partial reduce +))
       (reduce +)))


(def negate-comparisons
  {"<" ">="
   ">" "<="})

(defn conditions
  [other-conds [out val op num]]
  (if (empty? other-conds)
    [[out [[val op num]]]]
    (let [last-conds (second (peek other-conds))]
      (conj other-conds
            [out (vec (filter
                       some?
                       (conj (vec (drop-last last-conds))
                             (update (peek last-conds) 1 negate-comparisons)
                             (when val [val op num]))))]))))

(defn explicit-conditions
  "Unrolls the conditional logic so that there's an explicit statement
   of the conditions that must be true to reach another node in the 
   workflow graph"
  [{:keys [workflows]}]
  (u/fmap #(reduce conditions [] %) workflows))

(defn accepted-search
  "Searches the rules space to find paths that reach an accepted state"
  [rules paths [nxt-node conditions]]
  (if (= :A nxt-node)
    [(into paths conditions)]
    (->> (rules nxt-node)
         (mapcat #(accepted-search rules (into paths conditions) %)))))

(defn all-accepted-paths
  "Returns all mutually disjoint rule sets that lead to an accepted outcome"
  [input]
  (let [rules (explicit-conditions input)]
    (mapcat #(accepted-search rules [] %) (rules :in))))

(def default-ranges
  {"x" {:min 1 :max 4000}
   "m" {:min 1 :max 4000}
   "a" {:min 1 :max 4000}
   "s" {:min 1 :max 4000}})

(defn update-range-limits
  [ranges [rating op num]]
  (if (or (= op "<") (= op "<="))
    (assoc-in ranges [rating :max] (if (= op "<") (dec num) num))
    (assoc-in ranges [rating :min] (if (= op ">") (inc num) num))))

(defn options
  [{:keys [min max]}]
  (inc (- max min)))

(defn range-limits
  [conditions]
  (->> conditions
       (reduce update-range-limits default-ranges)
       vals
       (map options)
       (reduce *)))

(defn all-accepted-count
  [input]
  (->> (all-accepted-paths input)
       (map range-limits)
       (reduce +)))

(defn day19-part1-soln
  "Sort through all of the parts you've been given; what do you get if you 
   add together all of the rating numbers for all of the parts that 
   ultimately get accepted?"
  [input]
  (accepted-parts-sum input))

(defn day19-part2-soln
  "Consider only your list of workflows; the list of part ratings that the 
   Elves wanted you to sort is no longer relevant. How many distinct 
   combinations of ratings will be accepted by the Elves' workflows?"
  [input]
  (all-accepted-count input))