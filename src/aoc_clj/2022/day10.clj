(ns aoc-clj.2022.day10
  "Solution to https://adventofcode.com/2022/day/10"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;;;; Input parsing

(defn parse-line
  [line]
  (if (= "noop" line)
    ::noop
    (read-string (subs line 5))))

(defn parse
  [input]
  (map parse-line input))

(def day10-input (u/parse-puzzle-input parse 2022 10))

;;;; Puzzle logic

(defn op->change
  "addx V takes two cycles to complete. After two cycles, the X register
   is increased by the value V. (V can be negative.)
   noop takes one cycle to complete. It has no other effect."
  [op]
  (if (= ::noop op)
    [0]
    [0 op]))

(defn collapse
  "Convert the input instructions into the timed changes they will make."
  [input]
  (mapcat op->change input))

(defn register-values
  "Starting with the X register with the value 1, return a seq of the 
   X register values at each tick of the CPU"
  [input]
  (reductions + 1 (collapse input)))

(defn sampled-signal-strength-sums
  "consider the signal strength (the cycle number multiplied by the 
   value of the X register) during the 20th cycle and every 40 cycles 
   after that (that is, during the 20th, 60th, 100th, 140th, 180th, 
   and 220th cycles)"
  [input]
  (->> (register-values input)
       (drop 19)
       (take-nth 40)
       (map * [20 60 100 140 180 220])
       (reduce +)))

(defn light
  "Determine whether to light up the screen at the pixel location
   given the value of the X register"
  [pixel register]
  (if (<= (abs (- register pixel)) 1)
    "#"
    "."))

(defn screen
  "Return a string representation of the CRT screen based on the 
   programmed instructions"
  [input]
  (->>
   (map light
        (flatten (repeat 6 (range 40)))
        (register-values input))
   (partition 40)
   (map str/join)
   (str/join "\n")))

;;;; Puzzle solutions

(defn day10-part1-soln
  "Find the signal strength during the 20th, 60th, 100th, 140th, 180th, 
   and 220th cycles. What is the sum of these six signal strengths?"
  []
  (sampled-signal-strength-sums day10-input))

(comment
  (print (screen day10-input))
  "####  ##  #### #  # ####  ##  #    ###  
   #    #  #    # #  #    # #  # #    #  # 
   ###  #      #  #  #   #  #  # #    #  # 
   #    #     #   #  #  #   #### #    ###  
   #    #  # #    #  # #    #  # #    # #  
   ####  ##  ####  ##  #### #  # #### #  #")

(defn day10-part2-soln
  "Render the image given by your program. 
   What eight capital letters appear on your CRT?"
  []
  "ECZUZALR")
