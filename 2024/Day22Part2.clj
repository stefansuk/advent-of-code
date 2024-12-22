(def input (slurp "2024/Day22Input"))
(def secrets (map #(Integer/parseInt %) (re-seq #"\d+" input)))

(defn evolve [n]
  (let [n (mod (bit-xor (* n 64) n) 16777216)
        n (mod (bit-xor (quot n 32) n) 16777216)
        n (mod (bit-xor (* n 2048) n) 16777216)]
    n))

(defn bananas [n]
  (into {}
        (reverse
          (let [s (map #(mod % 10)
                       (take 2001 (iterate evolve n)))]
            (map vector
                 (partitionv 4 1 (map - (rest s) s))
                 (drop 4 s))))))

(def total (apply max (let [p (map bananas secrets)]
                        (for [a (range -9 10)
                              b (range -9 10)
                              :when (< (abs (+ a b)) 10)
                              c (range -9 10)
                              :when (< (abs (+ b c)) 10)
                              :when (< (abs (+ a b c)) 10)
                              d (range -9 10)
                              :when (< (abs (+ c d)) 10)
                              :when (< (abs (+ b c d)) 10)
                              :when (< (abs (+ a b c d)) 10)]
                          (reduce + (map #(or (% [a b c d]) 0) p))))))
(println total)
