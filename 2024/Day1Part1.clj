(def input (slurp "2024/Day1Input"))
(def lines (re-seq #"(\d+)\s+(\d+)" input))
(def lists (apply map vector (map (fn [x] (rest x)) lines)))
(def distances (map abs (map -
                             (map #(Integer/parseInt %) (sort (first lists)))
                             (map #(Integer/parseInt %) (sort (last lists))))))
(def sum (apply + distances))
(println sum)
