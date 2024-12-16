(require '[clojure.string :as str])

(def input (slurp "2024/Day16Input"))
(def width (dec (count (re-find #".*\n" input))))
(def grid (vec (partitionv width (str/replace input #"\n" ""))))
(def height (count grid))
(def directions [[1 0] [0 1] [-1 0] [0 -1]])
(def visited (vec (repeat height (vec (repeat width false)))))
(def score Integer/MAX_VALUE)
(def start (first (for [row (range height)
                        col (range width)
                        :when (= \S (get-in grid [row col]))]
                    [col row])))

(defn navigate [g x y e s u]
  (let [c (get-in grid [y x])
        v (assoc-in u [y x] true)]
    (when (or (char? c)
              (and (integer? c)
                   (<= s c)))
      (if (and (= (get-in g [y x]) \E)
               (<= s score))
        (do (if (= s score)
              (def visited (mapv (fn [a b] (mapv #(or %1 %2) a b)) visited v))
              (def visited v))
            (def score s))
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
              (recur g dx dy d sn v))
            (and (= (count ds) 2)
                 (some #(= % o) ds))
            (let [d (first (remove #(= % o) ds))
                  dx (+ x (d 0))
                  dy (+ y (d 1))
                  sn (+ s (if (= d e)
                            1
                            1001))]
              (def grid (assoc-in grid [y x] s))
              (recur g dx dy d sn v))
            :else
            (doseq [d directions
                    :let [dx (+ x (d 0))
                          dy (+ y (d 1))
                          p (assoc-in g [y x] \#)
                          sn (+ s (if (= d e)
                                    1
                                    1001))]
                    :when (not= (get-in g [dy dx]) \#)]
              (navigate p dx dy d sn v))))))))

(navigate grid (start 0) (start 1) (directions 0) 0 visited)
(def total (count (filter true? (flatten visited))))
(println total)
