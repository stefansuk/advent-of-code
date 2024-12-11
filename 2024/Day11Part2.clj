(def input (slurp "2024/Day11Input"))
(def stones (map #(Integer/parseInt %) (re-seq #"\d+" input)))
(def stone-map {})

(defn split [n]
  (let [s (str n)
        m (quot (count s) 2)]
    [(Integer/parseInt (subs s 0 m))
     (Integer/parseInt (subs s m))]))

(defn blink [n i]
  (if (= i 0)
    1
    (if (get stone-map [n i])
      (get stone-map [n i])
      (let [d (count (str n))]
        (let [result (cond
                       (= n 0) (blink 1 (- i 1))
                       (= (mod d 2) 1) (blink (* n 2024) (- i 1))
                       :else (let [[p q] (split n)]
                               (+ (blink p (- i 1))
                                  (blink q (- i 1)))))]
          (def stone-map (assoc stone-map [n i] result))
          result)))))

(def total (reduce + (for [s stones]
                       (blink s 75))))
(println total)
