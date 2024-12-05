(require '[clojure.string :as str])

(def input (slurp "2024/Day5Input"))
(def numbers (->> input
                 (#(str/split % #"\n\n"))
                 (map #(str/split % #"\n"))
                 (map (fn [x] (map #(str/split % #"\||,") x)))))
(def rules (set (first numbers)))
(def updates (second numbers))
(def sum (apply + (keep (fn [x] (when
                                  (every? #(contains? rules %)
                                          (partitionv 2 1 x))
                                  (Integer/parseInt (nth x (quot (count x) 2)))))
                        updates)))
(println sum)
