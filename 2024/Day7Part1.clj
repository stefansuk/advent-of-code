(require '[clojure.string :as str])

(def input (slurp "2024/Day7Input"))
(def lines (map (fn [x] (map #(Long/parseLong %) (re-seq #"\d+" x))) (str/split-lines input)))

(defn check? [m n]
  (when (> (count n) 0)
    (or (and (= (count n) 1)
             (= m (first n)))
        (and (= (mod m (first n)) 0)
             (check? (quot m (first n)) (rest n)))
        (and (> m (first n))
             (check? (- m (first n)) (rest n))))))

(def sum (reduce + (for [l lines
                         :let [m (first l)
                               n (reverse (rest l))]
                         :when (check? m n)]
                     m)))
(println sum)
