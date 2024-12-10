(require '[clojure.string :as str])

(def input (slurp "2024/Day10Input"))
(def width (dec (count (re-find #".*\n" input))))
(def grid (vec (mapv (fn [x] (mapv #(Integer/parseInt (str %)) x))
                     (partitionv width (str/replace input #"\n" "")))))
(def height (count grid))
(def directions [[1 0] [0 1] [-1 0] [0 -1]])
(def trailheads (for [row (range height)
                      col (range width)
                      :when (= (get-in grid [row col]) 0)]
                  [col row]))

(defn score [x y]
  (if (= (get-in grid [y x]) 9)
    1
    (reduce +
            (for [d directions
                  :when (= (get-in grid [(+ y (d 1)) (+ x (d 0))])
                           (+ (get-in grid [y x]) 1))]
              (score (+ x (d 0)) (+ y (d 1)))))))

(def sum (reduce + (for [t trailheads]
                     (score (t 0) (t 1)))))
(println sum)
