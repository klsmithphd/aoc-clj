(ns aoc-clj.2020.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day04 :as t]))

(def d04-s00
  ["ecl:gry pid:860033327 eyr:2020 hcl:#fffffd"
   "byr:1937 iyr:2017 cid:147 hgt:183cm"
   ""
   "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884"
   "hcl:#cfa07d byr:1929"
   ""
   "hcl:#ae17e1 iyr:2013"
   "eyr:2024"
   "ecl:brn pid:760753108 byr:1931"
   "hgt:179cm"
   ""
   "hcl:#cfa07d eyr:2025 pid:166559648"
   "iyr:2011 ecl:brn hgt:59in"])

(def d04-s00-invalid
  ["eyr:1972 cid:100"
   "hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926"
   ""
   "iyr:2019"
   "hcl:#602927 eyr:1967 hgt:170cm"
   "ecl:grn pid:012533040 byr:1946"
   ""
   "hcl:dab227 iyr:2012"
   "ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277"
   ""
   "hgt:59cm ecl:zzz"
   "eyr:2038 hcl:74454a iyr:2023"
   "pid:3556412378 byr:2007"])

(def d04-s00-valid
  ["pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980"
   "hcl:#623a2f"
   ""
   "eyr:2029 ecl:blu cid:129 byr:1989"
   "iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm"
   ""
   "hcl:#888785"
   "hgt:164cm byr:2001 iyr:2015 cid:88"
   "pid:545766238 ecl:hzl"
   "eyr:2022"
   ""
   "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719"])

(deftest keys-valid
  (testing "Correctly determines that passports have the correct keys"
    (is (= '(true false true false)
           (map t/keys-valid? (t/parse d04-s00))))))

(deftest byr-valid
  (testing "Correctly determines that birth years are valid"
    (is (= true  (t/byr-valid? {:byr "2002"})))
    (is (= false (t/byr-valid? {:byr "1919"})))
    (is (= false (t/byr-valid? {:byr "2003"})))))

(deftest iyr-valid
  (testing "Correctly determines that issue years are valid"
    (is (= true  (t/iyr-valid? {:iyr "2010"})))
    (is (= false (t/iyr-valid? {:iyr "2009"})))
    (is (= false (t/iyr-valid? {:iyr "2021"})))))

(deftest eyr-valid
  (testing "Correctly determines that expiration years are valid"
    (is (= true  (t/eyr-valid? {:eyr "2020"})))
    (is (= false (t/eyr-valid? {:eyr "2019"})))
    (is (= false (t/eyr-valid? {:eyr "2031"})))))

(deftest hgt-valid
  (testing "Correctly determines that heights are valid"
    (is (= true  (t/hgt-valid? {:hgt "60in"})))
    (is (= true  (t/hgt-valid? {:hgt "190cm"})))
    (is (= false (t/hgt-valid? {:hgt "190in"})))
    (is (= false (t/hgt-valid? {:hgt "190"})))))

(deftest hcl-valid
  (testing "Correctly determines that hair colors are valid"
    (is (= true  (t/hcl-valid? {:hcl "#123abc"})))
    (is (= false (t/hcl-valid? {:hcl "#123abz"})))
    (is (= false (t/hcl-valid? {:hcl "123abc"})))))

(deftest ecl-valid
  (testing "Correctly determines that eye colors are valid"
    (is (= true  (t/ecl-valid? {:ecl "amb"})))
    (is (= true  (t/ecl-valid? {:ecl "blu"})))
    (is (= true  (t/ecl-valid? {:ecl "brn"})))
    (is (= true  (t/ecl-valid? {:ecl "gry"})))
    (is (= true  (t/ecl-valid? {:ecl "grn"})))
    (is (= true  (t/ecl-valid? {:ecl "hzl"})))
    (is (= true  (t/ecl-valid? {:ecl "oth"})))
    (is (= false (t/ecl-valid? {:ecl "wat"})))))

(deftest pid-valid
  (testing "Correctly determines that passport ids are valid"
    (is (= true  (t/pid-valid? {:pid "000000001"})))
    (is (= false (t/pid-valid? {:pid "0123456789"})))))

(deftest all-valid
  (testing "All validators combined work correctly"
    (is (= '(true true true true)
           (map t/passport-valid? (t/parse d04-s00-valid))))
    (is (= '(false false false false)
           (map t/passport-valid? (t/parse d04-s00-invalid))))))

(def day04-input (u/parse-puzzle-input t/parse 2020 4))

(deftest part1-test
  (testing "Reproduces the answer for day04, part1"
    (is (= 192 (t/part1 day04-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day04, part2"
    (is (= 101 (t/part2 day04-input)))))