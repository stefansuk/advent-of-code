(require '[clojure.string :as str])

(def input (slurp "2024/Day19Input"))
(def patterns (str/split input #"\n\n"))
(def towels (str/split (first patterns) #", "))
(def designs (str/split-lines (second patterns)))
(def v {})

(defn match [design]
  (if (contains? v design)
    (v design)
    (let [n (if (empty? design)
              1
              (reduce + (flatten (for [t towels
                                       :let [p (re-pattern (str \^ t))]]
                                   (if (re-find p design)
                                     (match (str/replace design p ""))
                                     0)))))]
      (def v (conj v {design n}))
      n)))

(def total (reduce + (for [d designs]
                       (match d))))
(println total)
