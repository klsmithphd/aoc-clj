(ns aoc-clj.2023.day20
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [[l r] (str/split line #" -> ")]
    [(if (= "broadcaster" l) l (subs l 1))
     {:type (cond (= "broadcaster" l) :broadcast
                  (str/starts-with? l "%") :flip-flop
                  (str/starts-with? l "&") :conjunction)
      :dest (str/split r #", ")}]))

(defn parse
  [input]
  (into {} (map parse-line input)))

(defn conj-inputs
  "Returns the input modules that send signals into the given conjunction module"
  [modules conj-module]
  (map key (filter #(some #{conj-module} (:dest (val %))) modules)))

(defn state-setup
  "For each non-broadcast module, return the initial state of that module
   
   Flip-flop modules are initially off.
   Conjunction modules initially default to remembering a low pulse for each
   input"
  [modules [id {:keys [type]}]]
  (case type
    :flip-flop   [id :off]
    :conjunction [id (into {} (map vector (conj-inputs modules id) (repeat :low)))]
    nil))

(defn initial-circuit-state
  "Computes the initial state of the circuit based on the type of all the
   modules and their connections"
  [modules]
  (into {} (remove nil? (map #(state-setup modules %) modules))))

(defn new-pulses
  [dest from-id pulse]
  (map vector dest (repeat from-id) (repeat pulse)))

(defn process-broadcast-pulse
  "There is a single broadcast module (named broadcaster). 
   When it receives a pulse, it sends the same pulse to all of its 
   destination modules."
  [state _ _ pulse dest]
  (-> state
      (update :pulses #(into % (new-pulses dest :broadcast pulse)))
      (update-in [:pulse-history :buttons] inc)))

(def flip-state
  {:on :off :off :on})

(defn process-flip-flop-pulse
  "Flip-flop modules (prefix %) are either on or off; they are initially off. 
   If a flip-flop module receives a high pulse, it is ignored and nothing 
   happens. However, if a flip-flop module receives a low pulse, 
   it flips between on and off. 
   If it was off, it turns on and sends a high pulse. 
   If it was on, it turns off and sends a low pulse."
  [{:keys [circuit-state] :as state} to-id _ pulse dest]
  (if (= :high pulse)
    state
    (-> state
        (update :pulses #(into % (if (= :on (circuit-state to-id))
                                   (new-pulses dest to-id :low)
                                   (new-pulses dest to-id :high))))
        (update-in [:circuit-state to-id] flip-state))))

(defn process-conjunction-pulse
  "Conjunction modules (prefix &) remember the type of the most recent 
   pulse received from each of their connected input modules; 
   they initially default to remembering a low pulse for each input. 
   When a pulse is received, the conjunction module first updates its 
   memory for that input. 
   Then, if it remembers high pulses for all inputs, it sends a low pulse; 
   otherwise, it sends a high pulse."
  [{:keys [circuit-state] :as state} to-id from-id pulse dest]
  (let [new-circuit-state (assoc-in circuit-state [to-id from-id] pulse)]
    (-> state
        (assoc :circuit-state new-circuit-state)
        (update :pulses #(into % (if (every?
                                      (u/equals? :high)
                                      (vals (new-circuit-state to-id)))
                                   (new-pulses dest to-id :low)
                                   (new-pulses dest to-id :high)))))))

(defn process-output-pulse
  "A pulse that isn't sent to one of the known modules must be an
   output pulse. Update the state to count how many signals were sent
   to rx during this cycle"
  [state to-id _ pulse _]
  (update-in state [to-id pulse] inc))

(defn type->update-fn
  [type]
  (case type
    :broadcast process-broadcast-pulse
    :flip-flop process-flip-flop-pulse
    :conjunction process-conjunction-pulse
    process-output-pulse))

(defn process-pulse
  [{:keys [modules pulses] :as state}]
  (let [[to-id from-id pulse] (peek pulses)
        {:keys [type dest]}  (get modules to-id)]
    (-> state
        (update :pulses pop)
        ((type->update-fn type) to-id from-id pulse dest)
        (update-in [:pulse-history pulse] inc))))

(def button-press-pulse ["broadcaster" "button" :low])
(def pulse-history-init {:low 0 :high 0 :buttons 0})
(def pulses-init (conj clojure.lang.PersistentQueue/EMPTY button-press-pulse))
(defn init-state
  [modules]
  {:modules       modules
   :circuit-state (initial-circuit-state modules)
   :pulses        pulses-init
   :pulse-history pulse-history-init
   "output"        {:high 0 :low 0}
   "rx"            {:high 0 :low 0}})

(defn reset-rx
  [state]
  (if (= {:high 0 :low 1} (state "rx"))
    state
    (assoc state "rx" {:high 0 :low 0})))

(defn process-pulses
  [init-state]
  (loop [state init-state]
    (if (not (peek (:pulses state)))
      (reset-rx state)
      (recur (process-pulse state)))))

(defn button-press
  [state]
  (update state :pulses conj button-press-pulse))

(defn pulses-until-cycle
  [modules]
  (let [init               (init-state modules)
        init-circuit-state (:circuit-state init)]
    (loop [state (process-pulses init)]
      (if (= init-circuit-state (:circuit-state state))
        (:pulse-history state)
        (recur (-> state button-press process-pulses))))))

(defn after-n-buttons
  [modules n]
  (let [init (init-state modules)]
    (if (zero? n)
      init
      (loop [countdown (dec n)
             state     (process-pulses init)]
        (if (<= countdown 0)
          state
          (recur (dec countdown)
                 (-> state button-press process-pulses)))))))

(defn pulses-after-1000-brute-force
  [modules]
  (let [{:keys [low high]} (:pulse-history (after-n-buttons modules 1000))]
    (* low high)))

(defn pulses-after-1000
  [modules]
  (let [{:keys [low high buttons]} (pulses-until-cycle modules)
        units     (quot 1000 buttons)
        remainder (rem 1000 buttons)
        rest-sim  (:pulse-history (after-n-buttons modules remainder))]
    (* (+ (* low units)  (:low rest-sim))
       (+ (* high units) (:high rest-sim)))))

(defn presses-until-single-rx-low-pulse
  [modules]
  (loop [state (process-pulses (init-state modules))]
    (if (= {:high 0 :low 1} (get state "rx"))
      (get-in state [:pulse-history :buttons])
      (recur (-> state button-press process-pulses)))))

(defn day20-part1-soln
  [input]
  (pulses-after-1000-brute-force input))

(defn day20-part2-soln
  [input]
  (presses-until-single-rx-low-pulse input))