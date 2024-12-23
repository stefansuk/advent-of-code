(require '[clojure.string :as str]
         '[clojure.set :as set])

(def input (slurp "2024/Day23Input"))
(def connections (mapv #(vec (rest %)) (re-seq #"(\w+)-(\w+)" input)))
(def neighbours (into (sorted-map)
                      (map (fn [c] (vector c (into (sorted-set)
                                                   (keep #(cond
                                                            (= (% 0) c) (% 1)
                                                            (= (% 1) c) (% 0)
                                                            :else nil)
                                                         connections))))
                           (into #{} (concat (mapv first connections)
                                             (mapv second connections))))))

(defn group [c ns p]
  (if (= (count ns) 0)
    p
    (apply max-key count
           (conj (map #(group % (set/intersection ns (neighbours %))
                              (conj p %))
                      (filter #(> 0 (compare c %))
                              ns))
                 ""))))

(def password (str/join "," (apply max-key count
                                   (map #(group % (neighbours %) [%])
                                        (keys neighbours)))))
(println password)
