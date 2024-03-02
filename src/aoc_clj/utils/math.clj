(ns aoc-clj.utils.math
  "Mathematical helper utilities")

(defn gcd
  "Greatest common divisor between a and b"
  [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn lcm
  "Least common multiple of a and b"
  ([a b]
   (/ (* a b) (gcd a b)))
  ([a b & others]
   (reduce lcm (lcm a b) others)))

(defn mod-inverse
  "Computes the multiplicative inverse of a, mod m"
  [m a]
  (loop [t 0 next-t 1 r m next-r a]
    (if (zero? next-r)
      (if (neg? t) (+ t m) t)
      (let [q (quot r next-r)]
        (recur next-t (- t (* q next-t)) next-r (- r (* q next-r)))))))

(defn mod-add
  "Computes a + b mod m"
  [m a b]
  (mod (+' a b) m))

(defn mod-sub
  "Computes a - b mod m"
  [m a b]
  (mod (-' a b) m))

(defn mod-mul
  "Computes a * b mod m"
  [m a b]
  (mod (*' a b) m))

(defn mod-sq
  "Computes a^2 mod m"
  [m a]
  (mod (*' a a) m))

(defn mod-div
  "Computes a / b mod m
   Note that b^-1 does not always exist for all b for all m (b and m must be co-prime)"
  [m a b]
  (if (= (mod a m) (mod b m))
    1
    (mod-mul m a (mod-inverse m b))))

(defn mod-pow
  "Computes a^n mod m"
  [m a n]
  (if (zero? n)
    1
    ;; Applies the square-and-multiply method to perform fast
    ;; exponentiation of an integer a to the power n modulo m, using
    ;; the binary representation of n
    (letfn [(sq-and-mul [acc bit]
              (if (= \0 bit)
                ;; If the next bit is a zero, return the square of acc (mod m)
                (mod-sq m acc)
                ;; If the next bit is a one, return the square of acc times a
                ;; (modulo m)
                (mod-mul m a (mod-sq m acc))))]
      (reduce sq-and-mul 1 (Long/toBinaryString n)))))

(defn mod-linear-comp
  "Takes two linear functions of the form f(x) = a1*x + b1 and g(x) = a2*x + b2 (both modulo m), and
   determines the coefficients of the composite function (g(f(x)))"
  ([_ x]
   x)
  ([m [a2 b2] [a1 b1]]
   [(mod-mul m a2 a1) (mod (+' b2 (*' a2 b1)) m)]))

(defn mod-linear-inverse
  "Coefficients of the inverse of a linear function of the form (f(x) = a*x + b modulo m"
  [m [a b]]
  (let [ainv (mod-inverse m a)]
    [ainv (mod-sub m 0 (mod-mul m b ainv))]))

(defn mod-geometric-sum
  "The sum of the geometric sequence 1 + a + a^2 + ... + a^n, with all operations modulo m
   Formula from https://stackoverflow.com/questions/42032824/geometric-series-modulus-operation"
  [m a n]
  (if (zero? n)
    1
    (let [a2 (mod-mul m a a)]
      (if (even? n)
        (mod-add m 1 (mod-mul m (mod-add m a a2) (mod-geometric-sum m a2 (/ (- n 2) 2))))
        (mod-mul m (mod-add m 1 a) (mod-geometric-sum m a2 (/ (- n 1) 2)))))))

(defn mod-linear-pow
  "Determines the coefficients of the a linear function composed on itself multiple times, i.e.,
   if f(x) = a*x + b, determines f^n(x) = f(f(f(f...(f(x))))) with n nestings
   
   [a b]^n = [a^n (a^(n-1) + a^(n-2) + ... + a^1 + 1)b]
   
   Should be equivalent to (first (drop n (iterate (partial mod-linear-comp m) [a b])))"
  [m n [a b]]
  [(mod-pow m a n) (mod-mul m b (mod-geometric-sum m a (dec n)))])