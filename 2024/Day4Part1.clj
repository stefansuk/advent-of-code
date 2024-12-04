(require '[clojure.string :as str])

(def input (slurp "2024/Day4Input"))
(def word "XMAS")
(def width (dec (count (re-find #".*\n" input))))
(def grid (partition width (str/replace input #"\n" "")))
(def height (count grid))
(def directions [[0 1] [1 1] [1 0] [-1 1] [-1 0] [-1 -1] [0 -1] [1 -1]])
(def total (count (for [y (range height)
                        x (range width)
                        d directions
                        :when (let [w (dec (count word))]
                                (and (>= (+ x (* (first d) w)) 0)
                                     (< (+ x (* (first d) w)) width)
                                     (>= (+ y (* (last d) w)) 0)
                                     (< (+ y (* (last d) w)) height)))
                        :when (every? #(-> grid
                                           (nth (+ y (* (last d) %)))
                                           (nth (+ x (* (first d) %)))
                                           (= (nth word %)))
                                      (range (count word)))]
                    1)))
(println total)
