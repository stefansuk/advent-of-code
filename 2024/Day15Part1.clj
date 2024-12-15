(require '[clojure.string :as str])

(def input (slurp "2024/Day15Input"))
(def sections (str/split input #"\n\n"))
(def width (dec (count (re-find #".*\n" (first sections)))))
(def grid (vec (partitionv width (str/replace (first sections) #"\n" ""))))
(def height (count grid))
(def movements (apply list (str/replace (second sections) #"\n" "")))
(def directions {\> [1 0], \v [0 1], \< [-1 0], \^ [0 -1]})
(def robot (first (for [row (range height)
                        col (range width)
                        :when (= \@ (get-in grid [row col]))]
                    [col row])))

(defn push [g x y d]
  (let [dx (+ x (d 0))
        dy (+ y (d 1))]
    (cond
      (= (get-in g [dy dx]) \#)
      false
      (= (get-in g [dy dx]) \.)
      (-> g
          (assoc-in [y x] \.)
          (assoc-in [dy dx] \O))
      :else
      (let [p (push g dx dy d)]
        (if p
          (-> p
              (assoc-in [y x] \.)
              (assoc-in [dy dx] \O))
          false)))))

(def end (loop [g grid
                x (first robot)
                y (second robot)
                d (directions (first movements))
                m (rest movements)]
           (if (nil? d)
             g
             (let [dx (+ x (d 0))
                   dy (+ y (d 1))]
               (cond
                 (= (get-in g [dy dx]) \#)
                 (recur g x y (directions (first m)) (rest m))
                 (= (get-in g [dy dx]) \.)
                 (-> g
                     (assoc-in [y x] \.)
                     (assoc-in [dy dx] \@)
                     (recur dx dy (directions (first m)) (rest m)))
                 :else
                 (let [p (push g dx dy d)]
                   (if p
                     (-> p
                         (assoc-in [y x] \.)
                         (assoc-in [dy dx] \@)
                         (recur dx dy (directions (first m)) (rest m)))
                     (recur g x y (directions (first m)) (rest m)))))))))

(def total (reduce + (for [y (range height)
                           x (range width)
                           :when (= (get-in end [y x]) \O)]
                       (+ (* 100 y) x))))
(println total)
