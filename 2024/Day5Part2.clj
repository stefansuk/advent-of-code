(require '[clojure.string :as str])

(def input (slurp "2024/Day5Input"))
(def numbers (->> input
                  (#(str/split % #"\n\n"))
                  (map #(str/split % #"\n"))
                  (map (fn [x] (map #(str/split % #"\||,") x)))))
(def rules (set (first numbers)))
(def updates (second numbers))
(def sum (apply + (keep (fn [x] (loop [y x]
                                  (let [n (some #(when
                                                   (not (contains? rules (second %)))
                                                   (first %))
                                                (map-indexed vector (partitionv 2 1 y)))]
                                    (if (nil? n)
                                      (when (not= x y)
                                        (Integer/parseInt (nth y (quot (count y) 2))))
                                      (recur (vec (flatten [(subvec y 0 n)
                                                            (reverse (subvec y n (+ n 2)))
                                                            (subvec y (+ n 2))])))))))
                        updates)))
(println sum)
