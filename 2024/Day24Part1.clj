(require '[clojure.string :as str])

(def input (slurp "2024/Day24Input"))
(def wires (str/split input #"\n\n"))
(def values (into {} (map #(vector (% 1)
                                   (Integer/parseInt (% 2)))
                          (re-seq #"(.+): (\d)" (wires 0)))))
(def gates (into {} (map #(vector (% 4) [(% 2) (% 1) (% 3)])
                         (re-seq #"(.+) (.+) (.+) -> (.+)" (wires 1)))))

(defn value [v]
  (if (contains? values v)
    (values v)
    (let [w (gates v)
          a (value (w 1))
          b (value (w 2))
          o (case (w 0)
              "AND" (bit-and a b)
              "OR" (bit-or a b)
              "XOR" (bit-xor a b))]
      (def values (assoc values v o))
      o)))

(dorun (map value (keys gates)))
(def total (reduce + (map #(bit-shift-left (values %)
                                           (Integer/parseInt (subs % 1)))
                          (filter #(= (first %) \z)
                                  (keys values)))))
(println total)
