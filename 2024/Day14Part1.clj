(require '[clojure.string :as str])

(def input (slurp "2024/Day14Input"))
(def robots (->> (str/split input #"\n")
                 (mapv #(re-seq #"-?\d+" %))
                 (map (fn [x] (mapv #(Integer/parseInt %) x)))))
(def width 101)
(def height 103)
(def seconds 100)
(def total (apply * (apply map + (for [r robots
                                       :let [x (mod (+ (r 0) (* (r 2) seconds)) width)
                                             y (mod (+ (r 1) (* (r 3) seconds)) height)
                                             v (quot width 2)
                                             h (quot height 2)
                                             nw (and (< x v) (< y h))
                                             ne (and (> x v) (< y h))
                                             sw (and (< x v) (> y h))
                                             se (and (> x v) (> y h))]]
                                   [(if nw 1 0) (if ne 1 0) (if sw 1 0) (if se 1 0)]))))
(println total)
