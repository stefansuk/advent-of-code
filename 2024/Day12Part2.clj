(require '[clojure.string :as str])

(def input (slurp "2024/Day12Input"))
(def width (dec (count (re-find #".*\n" input))))
(def grid (vec (partitionv width (str/replace input #"\n" ""))))
(def height (count grid))
(def directions [[1 0] [0 1] [-1 0] [0 -1]])
(def corners [[[1 0] [0 1] [1 1]] [[0 1] [-1 0] [-1 1]] [[-1 0] [0 -1] [-1 -1]] [[0 -1] [1 0] [1 -1]]])
(def visited (vec (repeat height (vec (repeat width false)))))

(defn navigate [x y]
  (def visited (assoc-in visited [y x] true))
  (let [as (for [d directions
                 :when (and (not (get-in visited [(+ y (d 1)) (+ x (d 0))]))
                            (= (get-in grid [y x])
                               (get-in grid [(+ y (d 1)) (+ x (d 0))])))]
             (navigate (+ x (d 0)) (+ y (d 1))))
        a (reduce + (map first as))
        s (reduce + (map second as))
        sides (count (for [c corners
                           :when (let [n (get-in grid [y x])
                                       p (get-in grid [(+ y ((c 0) 1)) (+ x ((c 0) 0))])
                                       q (get-in grid [(+ y ((c 1) 1)) (+ x ((c 1) 0))])
                                       r (get-in grid [(+ y ((c 2) 1)) (+ x ((c 2) 0))])]
                                   (or (and (not= n p)
                                            (not= n q))
                                       (and (= n p)
                                            (= n q)
                                            (not= n r))))]
                       1))]
    [(+ a 1) (+ s sides)]))

(def total (reduce + (for [y (range height)
                           x (range width)
                           :when (not (get-in visited [y x]))
                           :let [[area sides] (navigate x y)]]
                       (* area sides))))
(println total)
