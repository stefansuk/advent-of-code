(def input (slurp "2024/Day1Input"))
(def lines (re-seq #"(\d+)\s+(\d+)" input))
(def lists (apply map vector (map (fn [x] (rest x)) lines)))
(def products (map (fn [x] (*
                             (#(Integer/parseInt %) x)
                             (get (frequencies (last lists)) x 0)))
                   (first lists)))
(def sum (apply + products))
(println sum)
