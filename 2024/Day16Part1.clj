(require '[clojure.string :as str])

(def input (slurp "2024/Day16Input"))
(def width (dec (count (re-find #".*\n" input))))
(def grid (vec (partitionv width (str/replace input #"\n" ""))))
(def height (count grid))
(def directions [[1 0] [0 1] [-1 0] [0 -1]])
(def score Integer/MAX_VALUE)
(def start (first (for [row (range height)
                        col (range width)
                        :when (= \S (get-in grid [row col]))]
                    [col row])))

(defn navigate [g x y e s]
  (let [c (get-in grid [y x])]
    (when (or (char? c)
              (and (integer? c)
                   (<= s c)))
      (if (and (= (get-in g [y x]) \E)
               (<= s score))
        (def score s)
        (let [ds (for [d directions
                       :let [dx (+ x (d 0))
                             dy (+ y (d 1))]
                       :when (not= (get-in g [dy dx]) \#)]
                   d)
              o [(- (e 0)) (- (e 1))]]
          (cond
            (= (count ds) 1)
            (let [d (first ds)
                  dx (+ x (d 0))
                  dy (+ y (d 1))
                  sn (+ s (if (= d e)
                            1
                            1001))]
              (def grid (assoc-in grid [y x] s))
              (recur g dx dy d sn))
            (and (= (count ds) 2)
                 (some #(= % o) ds))
            (let [d (first (remove #(= % o) ds))
                  dx (+ x (d 0))
                  dy (+ y (d 1))
                  sn (+ s (if (= d e)
                            1
                            1001))]
              (def grid (assoc-in grid [y x] s))
              (recur g dx dy d sn))
            :else
            (doseq [d directions
                    :let [dx (+ x (d 0))
                          dy (+ y (d 1))
                          p (assoc-in g [y x] \#)
                          sn (+ s (if (= d e)
                                    1
                                    1001))]
                    :when (not= (get-in g [dy dx]) \#)]
              (navigate p dx dy d sn))))))))

(navigate grid (start 0) (start 1) (directions 0) 0)
(println score)
