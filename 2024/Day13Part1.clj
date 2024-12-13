(require '[clojure.string :as str])

(def input (slurp "2024/Day13Input"))
(def values (->> (str/split input #"\n\n")
                 (mapv #(re-seq #"\d+" %))
                 (mapv (fn [x] (mapv #(Integer/parseInt %) x)))))
(def total (reduce + (for [v values
                           :let [a (/ (- (* (v 3) (v 4))
                                         (* (v 2) (v 5)))
                                      (- (* (v 0) (v 3))
                                         (* (v 1) (v 2))))]
                           :when (integer? a)
                           :let [b (/ (- (v 4)
                                         (* (v 0) a))
                                      (v 2))]]
                       (+ (* 3 a) b))))
(println total)
