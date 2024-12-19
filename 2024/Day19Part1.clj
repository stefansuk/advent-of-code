(require '[clojure.string :as str])

(def input (slurp "2024/Day19Input"))
(def patterns (str/split input #"\n\n"))
(def towels (str/split (first patterns) #", "))
(def designs (str/split-lines (second patterns)))

(defn match [design]
  (if (empty? design)
    true
    (some true? (for [t towels
                      :let [p (re-pattern (str \^ t))]]
                  (if (re-find p design)
                    (match (str/replace design p ""))
                    false)))))

(def total (count (for [d designs
                        :when (match d)]
                    d)))
(println total)
