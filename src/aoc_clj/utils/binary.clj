(ns aoc-clj.utils.binary)

(defn bitstr->int
  "Converts a bit string (e.g. '1101') to the corresponding integer (may be a long or bigint)"
  [s]
  (->> s (str "2r") read-string))

(defn int->bitstr
  "Converts any integer (or long or bigint) to a bit string"
  ([x]
   (.toString (biginteger x) 2))
  ([width x]
   (let [s (int->bitstr x)]
     (if (>= (count s) width)
       s
       (let [pad (apply str (repeat (- width (count s)) "0"))]
         (str pad s))))))