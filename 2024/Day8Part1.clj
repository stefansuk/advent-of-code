(require '[clojure.string :as str])

(def input (slurp "2024/Day8Input"))
(def width (dec (count (re-find #".*\n" input))))
(def grid (vec (partitionv width (str/replace input #"\n" ""))))
(def height (count grid))
(def antennas (for [row (range height)
                    col (range width)
                    :when (not= (get-in grid [row col]) \.)]
                [col row]))
(def total (count (filter #(and (>= (first %) 0)
                                (< (first %) width)
                                (>= (second %) 0)
                                (< (second %) height))
                          (set (apply concat (for [a antennas
                                                   b antennas
                                                   :when (and (not= a b)
                                                              (= (get-in grid [(a 1) (a 0)])
                                                                 (get-in grid [(b 1) (b 0)])))
                                                   :let [dx (- (b 0) (a 0))
                                                         dy (- (b 1) (a 1))]]
                                               [[(- (a 0) dx) (- (a 1) dy)]
                                                [(+ (b 0) dx) (+ (b 1) dy)]]))))))
(println total)
