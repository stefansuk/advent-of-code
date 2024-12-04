(require '[clojure.string :as str])

(def input (slurp "2024/Day4Input"))
(def word "XMAS")
(def width (- (count (re-find #".*\n" input)) 1))
(def grid (partition width (str/replace input #"\n" "")))
(def height (count grid))
(def directions [[0 1] [1 1] [1 0] [-1 1] [-1 0] [-1 -1] [0 -1] [1 -1]])
(def total (count (for [y (range height)
                        x (range width)
                        d directions
                        :when (let [w (- (count word) 1)]
                                (and (>= (+ x (* (nth d 0) w)) 0)
                                     (< (+ x (* (nth d 0) w)) width)
                                     (>= (+ y (* (nth d 1) w)) 0)
                                     (< (+ y (* (nth d 1) w)) height)))
                        :when (every? #(-> grid
                                           (nth (+ y (* (nth d 1) %)))
                                           (nth (+ x (* (nth d 0) %)))
                                           (= (nth word %)))
                                      (range (count word)))]
                    1)))
(println total)
