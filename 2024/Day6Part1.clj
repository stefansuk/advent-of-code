(require '[clojure.string :as str])

(def input (slurp "2024/Day6Input"))
(def width (dec (count (re-find #".*\n" input))))
(def grid (vec (partitionv width (str/replace input #"\n" ""))))
(def height (count grid))
(def directions [[0 -1] [1 0] [0 1] [-1 0]])
(def c (first (for [row (range height)
                    col (range width)
                    :when (= \^ (get-in grid [row col]))]
                [col row])))
(def path (assoc grid (c 1) (assoc (grid (c 1)) (c 0) \X)))
(def visited (loop [p path
                    x (get c 0)
                    y (get c 1)
                    d directions]
               (let [dx (get-in d [0 0])
                     dy (get-in d [0 1])]
                 (if (and (>= (+ x dx) 0)
                          (>= (+ y dy) 0)
                          (< (+ x dx) width)
                          (< (+ y dy) height))
                   (if (= \# (get-in grid [(+ y dy) (+ x dx)]))
                     (recur p
                            x
                            y
                            (into (vec (rest d)) [(first d)]))
                     (recur (assoc p (+ y dy) (assoc (p (+ y dy)) (+ x dx) \X))
                            (+ x dx)
                            (+ y dy)
                            d))
                   p))))
(def total (count (filter #(= \X %) (flatten visited))))
(println total)
