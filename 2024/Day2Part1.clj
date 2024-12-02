(require '[clojure.string :as str])

(def input (slurp "2024/Day2Input"))
(def reports (map
               (fn [x] (map #(Integer/parseInt %) x))
               (map #(str/split % #"\s") (str/split-lines input))))

(defn safe? [diffs] (or
                      (every? identity (map #(<= 1 % 3) diffs))
                      (every? identity (map #(>= -1 % -3) diffs))))

(def differences (map #(map - (butlast %) (rest %)) reports))
(def total (count (filter identity (map safe? differences))))
(println total)
