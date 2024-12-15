(require '[clojure.string :as str])

(def input (slurp "2024/Day15Input"))
(def sections (str/split input #"\n\n"))
(def width (* 2 (dec (count (re-find #".*\n" (first sections))))))
(def grid (vec (partitionv width (apply concat (map #(case %
                                                       \# "##"
                                                       \O "[]"
                                                       \. ".."
                                                       \@ "@.")
                                                    (str/replace (first sections) #"\n" ""))))))
(def height (count grid))
(def movements (apply list (str/replace (second sections) #"\n" "")))
(def directions {\> [1 0], \v [0 1], \< [-1 0], \^ [0 -1]})
(def robot (first (for [row (range height)
                        col (range width)
                        :when (= \@ (get-in grid [row col]))]
                    [col row])))

(defn push [g x y d b]
  (let [dx (+ x (d 0))
        dy (+ y (d 1))
        c (get-in g [y x])
        s (if (= c \[) 1 -1)]
    (cond
      (= (get-in g [dy dx]) \#)
      false
      (= (get-in g [dy dx]) \.)
      (if (and b (= (d 0) 0))
        (let [p (push g (+ x s) y d false)]
          (if p
            (-> p
                (assoc-in [y x] \.)
                (assoc-in [dy dx] c))
            false))
        (-> g
            (assoc-in [y x] \.)
            (assoc-in [dy dx] c)))
      :else
      (let [p (push g dx dy d true)]
        (if p
          (if (and b (= (d 0) 0))
            (let [q (push p (+ x s) y d false)]
              (if q
                (-> q
                    (assoc-in [y x] \.)
                    (assoc-in [dy dx] c))
                false))
            (-> p
                (assoc-in [y x] \.)
                (assoc-in [dy dx] c)))
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
                 (let [p (push g dx dy d true)]
                   (if p
                     (-> p
                         (assoc-in [y x] \.)
                         (assoc-in [dy dx] \@)
                         (recur dx dy (directions (first m)) (rest m)))
                     (recur g x y (directions (first m)) (rest m)))))))))

(def total (reduce + (for [y (range height)
                           x (range width)
                           :when (= (get-in end [y x]) \[)]
                       (+ (* 100 y) x))))
(println total)
