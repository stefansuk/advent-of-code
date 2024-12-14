(require '[clojure.string :as str])

(def input (slurp "2024/Day14Input"))
(def robots (->> (str/split input #"\n")
                 (mapv #(re-seq #"-?\d+" %))
                 (map (fn [x] (mapv #(Integer/parseInt %) x)))))
(def width 101)
(def height 103)
(def total (loop [seconds 0]
             (let [rs (vec (for [r robots
                                 :let [x (mod (+ (r 0) (* (r 2) seconds)) width)
                                       y (mod (+ (r 1) (* (r 3) seconds)) height)]]
                             [x y]))]
               (if (some (fn [[x y]] (every? (fn [n] (some #(= % [(+ x n) y])
                                                           rs))
                                             (range 1 8)))
                         rs)
                 seconds
                 (recur (inc seconds))))))
(println total)
