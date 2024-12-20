(require '[clojure.string :as str])

(def input (slurp "2024/Day20Input"))
(def width (dec (count (re-find #".*\n" input))))
(def grid (vec (partitionv width (str/replace input #"\n" ""))))
(def height (count grid))
(def directions [[1 0] [0 1] [-1 0] [0 -1]])
(def distance 20)
(def start (first (for [row (range height)
                        col (range width)
                        :when (= \S (get-in grid [row col]))]
                    [col row])))
(loop [x (start 0)
       y (start 1)
       n 0]
  (def grid (assoc-in grid [y x] n))
  (let [[cx cy] (first (for [d directions
                             :let [dx (+ x (d 0))
                                   dy (+ y (d 1))]
                             :when (or (= (get-in grid [dy dx]) \.)
                                       (= (get-in grid [dy dx]) \E))]
                         [dx dy]))]
    (when (some? cx)
      (recur cx cy (+ n 1)))))
(def total (count (for [y (range height)
                        x (range width)
                        :let [n (get-in grid [y x])]
                        :when (integer? n)
                        cy (range (- distance) (+ distance 1))
                        cx (range (- distance) (+ distance 1))
                        :let [dx (+ x cx)
                              dy (+ y cy)
                              d (+ (abs cx) (abs cy))
                              m (get-in grid [dy dx])]
                        :when (and (<= d distance)
                                   (integer? m))
                        :let [s (- m n d)]
                        :when (>= s 100)]
                    s)))
(println total)
