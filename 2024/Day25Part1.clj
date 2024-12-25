(require '[clojure.string :as str])

(def input (slurp "2024/Day25Input"))
(def schematics (mapv #(str/replace % #"\n" "")
                      (str/split input #"\n\n")))
(def total (count (for [a (range (count schematics))
                        b (range a (count schematics))
                        :let [p (range (count (schematics a)))]
                        :when (every? #(not= (get-in schematics [a %])
                                             (get-in schematics [b %])
                                             \#)
                                      p)]
                    1)))
(println total)
