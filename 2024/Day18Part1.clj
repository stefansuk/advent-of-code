(def input (slurp "2024/Day18Input"))
(def coordinates (mapv (fn [x] (mapv #(Integer/parseInt %) (rest x))) (re-seq #"(\d+),(\d+)" input)))
(def grid (reduce #(assoc-in %1 [(%2 1) (%2 0)] \#)
                  (vec (repeat 71 (vec (repeat 71 \.))))
                  (take 1024 coordinates)))
(def directions [[1 0] [0 1] [-1 0] [0 -1]])
(def start [0 0])
(def end [70 70])
(def queue (conj clojure.lang.PersistentQueue/EMPTY [start 0]))
(def visited #{})
(def total (loop []
             (let [p (peek queue)
                   c (p 0)
                   x (c 0)
                   y (c 1)
                   n (p 1)]
               (if (= c end)
                 n
                 (do (doseq [d directions
                             :let [dx (+ x (d 0))
                                   dy (+ y (d 1))]
                             :when (and (not (contains? visited [dx dy]))
                                        (= (get-in grid [dy dx]) \.))]
                       (def queue (conj queue [[dx dy] (+ n 1)]))
                       (def visited (conj visited [dx dy])))
                     (def queue (pop queue))
                     (recur))))))
(println total)
