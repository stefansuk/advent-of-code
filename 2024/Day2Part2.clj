(require '[clojure.string :as str])

(def input (slurp "2024/Day2Input"))
(def reports (map
               (fn [x] (map #(Integer/parseInt %) x))
               (map #(str/split % #"\s") (str/split-lines input))))

(defn safe? [diffs] (or
                      (every? identity (map #(<= 1 % 3) diffs))
                      (every? identity (map #(>= -1 % -3) diffs))))

(def differences (map
                   (fn [x] (map #(map - (butlast %) (rest %)) x))
                   (map (fn [x] (conj (map
                                        #(concat (subvec (vec x) 0 %) (subvec (vec x) (+ % 1)))
                                        (range (count x)))
                                      x))
                        reports)))
(def total (count (filter identity (map (fn [x] (some #(safe? %) x)) differences))))
(println total)
