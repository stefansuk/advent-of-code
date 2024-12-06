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
(def zeroes (vec (repeat height (vec (repeat width 0)))))
(def path (assoc-in zeroes [(c 1) (c 0)] 1))
(def total (count (for [row (range height)
                        col (range width)
                        :let [g (assoc-in grid [row col] \#)]
                        :when (loop [p path
                                     x (get c 0)
                                     y (get c 1)
                                     d directions
                                     n 1]
                                (let [dx (get-in d [0 0])
                                      dy (get-in d [0 1])]
                                  (if (and (>= (+ x dx) 0)
                                           (>= (+ y dy) 0)
                                           (< (+ x dx) width)
                                           (< (+ y dy) height))
                                    (if (= 0 (bit-and n (get-in p [(+ y dy) (+ x dx)])))
                                      (if (= \# (get-in g [(+ y dy) (+ x dx)]))
                                        (recur p
                                               x
                                               y
                                               (into (vec (rest d)) [(first d)])
                                               (if (= n 8) 1 (bit-shift-left n 1)))
                                        (recur (assoc-in p [(+ y dy) (+ x dx)] (+ n (get-in p [(+ y dy) (+ x dx)])))
                                               (+ x dx)
                                               (+ y dy)
                                               d
                                               n))
                                      true)
                                    false)))]
                    1)))
(println total)
