(require '[clojure.string :as str])

(def input (slurp "2024/Day8Input"))
(def width (dec (count (re-find #".*\n" input))))
(def grid (vec (partitionv width (str/replace input #"\n" ""))))
(def height (count grid))
(def antennas (for [row (range height)
                    col (range width)
                    :when (not= (get-in grid [row col]) \.)]
                [col row]))
(def total (count (set (for [a antennas
                             b antennas
                             :when (and (not= a b)
                                        (= (get-in grid [(a 1) (a 0)])
                                           (get-in grid [(b 1) (b 0)])))
                             :let [dx (- (b 0) (a 0))
                                   dy (- (b 1) (a 1))
                                   x (if (< dx 0) (- width 1 (a 0)) (a 0))
                                   y (if (< dy 0) (- height 1 (a 1)) (a 1))
                                   lower (min (abs (quot x dx))
                                              (abs (quot y dy)))
                                   upper (+ 1 (min (abs (quot (- width 1 x) dx))
                                                   (abs (quot (- height 1 y) dy))))]
                             t (range (- lower) upper)]
                         [(+ (a 0) (* dx t)) (+ (a 1) (* dy t))]))))
(println total)
