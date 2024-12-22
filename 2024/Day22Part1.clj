(def input (slurp "2024/Day22Input"))
(def secrets (map #(Integer/parseInt %) (re-seq #"\d+" input)))

(defn evolve [n]
  (let [n (mod (bit-xor (* n 64) n) 16777216)
        n (mod (bit-xor (quot n 32) n) 16777216)
        n (mod (bit-xor (* n 2048) n) 16777216)]
    n))

(def total (reduce + (map #(nth (iterate evolve %)
                                2000)
                          secrets)))
(println total)
