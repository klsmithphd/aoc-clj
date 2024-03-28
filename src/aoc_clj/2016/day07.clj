(ns aoc-clj.2016.day07
  "Solution to https://adventofcode.com/2016/day/7")

;; Input parsing
(defn parse-line
  [line]
  (let [nets (re-seq #"\w+" line)]
    {:supernets (take-nth 2 nets)
     :hypernets (take-nth 2 (rest nets))}))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn abba?
  "An ABBA is any four-character sequence which consists of a pair of
   two different characters followed by the reverse of that pair, 
   such as xyyx or abba"
  [s]
  (re-seq #"(.)(?!\1)(.)\2\1" s))

(defn supports-tls?
  "An IP supports TLS if it has an ABBA sequence among its supernets
   but not among its hypernets"
  [{:keys [supernets hypernets]}]
  (if (some abba? hypernets)
    false
    (boolean (some abba? supernets))))

(defn all-abas
  "Returns all ABA sequences.
   
   An ABA is any three-character sequence which consists of the same 
   character twice with a different character between them, 
   such as xyx or aba."
  [s]
  (re-seq #"(?=((.)(?!\2)(.)\2))" s))

(defn bab
  "Given an ABA sequence, construct a regex pattern to find an exact
   literal BAB sequence"
  [[_ _ a b]]
  (re-pattern (str b a b)))

(defn has-bab
  "Whether any of the hypernets matches the BAB regex"
  [hypernets bab]
  (some #(re-find bab %) hypernets))

(defn supports-ssl?
  "An IP supports SSL if it has an Area-Broadcast Accessor, or ABA, 
   anywhere in the supernet sequences (outside any square bracketed sections), 
   and a corresponding Byte Allocation Block, or BAB, 
   anywhere in the hypernet sequences."
  [{:keys [supernets hypernets]}]
  (let [babs (->> (mapcat all-abas supernets) (map bab))]
    (boolean (some #(has-bab hypernets %) babs))))

;; Puzzle solutions
(defn part1
  "How many IPs support TLS"
  [input]
  (count (filter supports-tls? input)))

(defn part2
  "How many IPs support SLS"
  [input]
  (count (filter supports-ssl? input)))
