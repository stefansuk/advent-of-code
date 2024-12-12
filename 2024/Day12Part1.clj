(require '[clojure.string :as str])

(def input (slurp "2024/Day12Input"))
(def width (dec (count (re-find #".*\n" input))))
(def grid (vec (partitionv width (str/replace input #"\n" ""))))
(def height (count grid))
(def directions [[1 0] [0 1] [-1 0] [0 -1]])
(def visited (vec (repeat height (vec (repeat width false)))))

(defn navigate [x y]
  (def visited (assoc-in visited [y x] true))
  (let [ap (for [d directions
                 :when (and (not (get-in visited [(+ y (d 1)) (+ x (d 0))]))
                            (= (get-in grid [y x])
                               (get-in grid [(+ y (d 1)) (+ x (d 0))])))]
             (navigate (+ x (d 0)) (+ y (d 1))))
        a (reduce + (map first ap))
        p (reduce + (map second ap))
        perimeter (count (for [d directions
                               :when (not (= (get-in grid [y x])
                                             (get-in grid [(+ y (d 1)) (+ x (d 0))])))]
                           1))]
    [(+ a 1) (+ p perimeter)]))

(def total (reduce + (for [y (range height)
                           x (range width)
                           :when (not (get-in visited [y x]))
                           :let [[area perimeter] (navigate x y)]]
                       (* area perimeter))))
(println total)
