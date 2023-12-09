(ns aoc-clj.2023.day09)

(defn parse
  [input]
  (map #(read-string (str "[" % "]")) input))

(defn extrapolate
  [nums]
  (if (every? zero? nums)
    nums
    (let [newnums (mapv #(apply - (reverse %)) (partition 2 1 nums))
          to-add  (last (extrapolate newnums))]
      (conj nums (+ (last nums) to-add)))))

(defn extrapolation-sum
  [input]
  (->> (map extrapolate input)
       (map last)
       (reduce +)))

(defn day09-part1-soln
  [input]
  (extrapolation-sum input))