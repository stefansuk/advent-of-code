(require '[clojure.string :as str])

(def input (slurp "2024/Day19Input"))
(def patterns (str/split input #"\n\n"))
(def towels (str/split (first patterns) #", "))
(def designs (str/split-lines (second patterns)))
(def combinations {})

(defn match [design]
  (if (contains? combinations design)
    (combinations design)
    (let [n (if (empty? design)
              1
              (reduce + (flatten (for [t towels
                                       :let [p (re-pattern (str \^ t))]]
                                   (if (re-find p design)
                                     (match (str/replace design p ""))
                                     0)))))]
      (def combinations (conj combinations {design n}))
      n)))

(def total (reduce + (for [d designs]
                       (match d))))
(println total)
