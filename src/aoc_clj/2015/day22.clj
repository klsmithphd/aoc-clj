(ns aoc-clj.2015.day22
  "Solution to https://adventofcode.com/2015/day/22"
  (:require [aoc-clj.2015.day21 :as d21]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.graph :as g :refer [Graph]]))

;; Constants
(def spell-cost
  {:magic-missile 53
   :drain         73
   :shield        113
   :poison        173
   :recharge      229})
(def player-start {:hit-points 50 :mana 500 :armor 0})

;; Input parsing
;; Reuse the same parsing logic from Day 21
(def parse d21/parse)

;; Puzzle logic
(defn deduct-mana-cost
  [state spell]
  (update-in state [:player :mana] - (spell-cost spell)))

(defn cast-magic-missile
  "Magic Missile instantly does 4 damage"
  [state]
  (update-in state [:boss :hit-points] - 4))

(defn cast-drain
  "Drain instantly does 2 damage and heals you for two hit points"
  [state]
  (-> state
      (update-in [:player :hit-points] + 2)
      (update-in [:boss   :hit-points] - 2)))

(defn cast-shield
  "Shield starts an effect that lasts for 6 turns"
  [state]
  (-> state
      (assoc-in  [:effects :shield] 6)
      (update-in [:player :armor] + 7)))

(defn cast-poison
  "Poison starts an effect that lasts for 6 turns"
  [state]
  (assoc-in state [:effects :poison] 6))

(defn cast-recharge
  "Recharge starts an effect that lasts for 5 turns"
  [state]
  (assoc-in state [:effects :recharge] 5))

(def spells
  "Mapping of the spell keywords to their update fns"
  {:magic-missile cast-magic-missile
   :drain         cast-drain
   :shield        cast-shield
   :poison        cast-poison
   :recharge      cast-recharge})

(defn shield-effect
  "Shield increases armor by 7 while it is active"
  [state]
  ;; When timer = 1, Shield is expiring, so we decrement the armor value by 7
  (if (= 1 (get-in state [:effects :shield]))
    (update-in state [:player :armor] - 7)
    state))

(defn poison-effect
  "Poison deals the boss 3 damage for each turn while it is active"
  [state]
  (update-in state [:boss :hit-points] - 3))

(defn recharge-effect
  "Recharge gives you 101 new manage for each turn while it is active"
  [state]
  (update-in state [:player :mana] + 101))

(def effects
  "Mapping of the spell keywords to their effect update fns"
  {:shield   shield-effect
   :poison   poison-effect
   :recharge recharge-effect})

(defn update-effect-timer
  "If the effect is expiring (just as timer = 1), remove it from the
   active effects, otherwise, decrement the timer by 1"
  [state effect]
  (if (= 1 (get-in state [:effects effect]))
    (update state :effects dissoc effect)
    (update-in state [:effects effect] dec)))

(defn apply-effect
  "Update the state according to the active effect and update the effect timer"
  [state effect]
  (-> ((effects effect) state)
      (update-effect-timer effect)))

(defn apply-effects
  "Apply all active effects and return the updated game state"
  [{:keys [effects] :as state}]
  (reduce apply-effect state (keys effects)))

(defn cast-spell
  "Cast the given spell and return the updated game state"
  [state spell]
  (-> ((spells spell) state)
      (deduct-mana-cost spell)
      (assoc :last-spell spell)))

(defn boss-attack
  "Apply the boss's attack and return the updated game state"
  [{:keys [player boss] :as state}]
  (let [{:keys [damage]} boss
        {:keys [armor]}  player]
    (update-in state [:player :hit-points] - (max 1 (- damage armor)))))

(defn player-round
  "Player takes their round. If `hard?` is `True`, the round begins with
   the player automatically losing one hit point before anything else happens.
   
   As long as the player still has hit points, any active effects will do their
   thing and the player will cast a spell."
  ([state spell]
   (player-round false state spell))
  ([hard? state spell]
   (let [newstate (if hard?
                    (update-in state [:player :hit-points] dec)
                    state)]
     (if (pos? (get-in newstate [:player :hit-points]))
       (-> newstate
           apply-effects
           (cast-spell spell))
       newstate))))

(defn boss-round
  "Boss takes their round. Any active effects will do their thing, and if
   the boss still has hit points, they'll attack the player."
  [state]
  (let [newstate (apply-effects state)
        boss-points (get-in newstate [:boss :hit-points])]
    (if (pos? boss-points)
      (boss-attack newstate)
      newstate)))

(defn combat-round
  "Each combat round consists of the player taking a turn, and then the 
   boss taking a turn. Neither opponent will be able to attack if their hit
   points run out during their play."
  ([state spell]
   (combat-round false state spell))
  ([hard? state spell]
   (-> (player-round hard? state spell)
       boss-round)))

(defn player-wins?
  "True if the player has won (boss's hit points are zero while player's
   hit points are greater than zero)"
  [{:keys [player boss] :as state}]
  (and
   state
   (not (pos? (:hit-points boss)))
   (pos? (:hit-points player))))

(defn available-spells
  "At a given game state, returns the list of spells that the player could
   choose to cast at any given time."
  [{:keys [player effects]}]
  (if (<= (:hit-points player) 0)
    []
    (let [active-effects (map first (filter #(> (val %) 1) effects))]
      (->> (u/without-keys spell-cost active-effects)
           (filter #(>= (:mana player) (val %)))
           (map first)))))

;; We use Dijkstra's algorithm, where the nodes in the graph
;; are game states, and possible new game states are connected
;; by edges. The "distance" is the amount of mana spent to cast
;; a spell
(defrecord GameGraph [hard?]
  Graph
  (edges
    [_ state]
    (map #(combat-round hard? state %) (available-spells state)))

  (distance
    [_ _ state]
    (spell-cost (:last-spell state))))

(defn winning-spells
  "Find a sequence of spells that allows the player to win with the least
   mana spent."
  ([boss]
   (winning-spells false boss))
  ([hard? boss]
   (let [start {:player player-start
                :boss   boss
                :effects {}}]
     (->>
      (g/dijkstra (->GameGraph hard?) start player-wins? :limit 5000)
      (map :last-spell)
      (drop 1)))))


;; Puzzle solutions
(defn part1
  "Least amount of mana to spend to win the game"
  [input]
  (reduce + (map spell-cost (winning-spells input))))


(defn part2
  "Least amount of mana to spend to win the game in hard mode"
  [input]
  (reduce + (map spell-cost (winning-spells true input))))



