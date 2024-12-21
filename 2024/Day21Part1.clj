(def input (slurp "2024/Day21Input"))
(def codes (mapv #(vector (% 0) (Integer/parseInt (% 1))) (re-seq #"(\d+)A" input)))
(def robots 3)
(def keypads [[\7 \8 \9]
              [\4 \5 \6]
              [\1 \2 \3]
              [\# \0 \A]
              [\# \^ \A]
              [\< \v \>]])
(def keys-map (into {} (for [ay (range 6)
                             ax (range 3)
                             :let [a (get-in keypads [ay ax])]
                             :when (not= a \#)
                             by (range 6)
                             :when (or (and (< ay 4) (< by 4))
                                       (and (> ay 3) (> by 3)))
                             bx (range 3)
                             :let [b (get-in keypads [by bx])]
                             :when (not= b \#)
                             :let [y (- by ay)
                                   x (- bx ax)
                                   v (cond
                                       (and (= ax 0) (= by 3))
                                       (str
                                         (apply str (repeat x \>))
                                         (apply str (repeat y \v))
                                         \A)
                                       (and (= ay 3) (= bx 0))
                                       (str
                                         (apply str (repeat (- y) \^))
                                         (apply str (repeat (- x) \<))
                                         \A)
                                       (and (= ay 4) (= bx 0))
                                       (str
                                         (apply str (repeat y \v))
                                         (apply str (repeat (- x) \<))
                                         \A)
                                       :else
                                       (str
                                         (when (< x 0) (apply str (repeat (- x) \<)))
                                         (when (> y 0) (apply str (repeat y \v)))
                                         (when (> x 0) (apply str (repeat x \>)))
                                         (when (< y 0) (apply str (repeat (- y) \^)))
                                         \A))]]
                         [(str a b) v])))

(defn translate [s]
  (->> s
       (str \A)
       (partition 2 1)
       (mapv #(apply str %))
       (mapv #(keys-map %))
       (apply str)))

(def total (reduce + (for [c codes]
                       (* (count (nth (iterate translate
                                               (c 0))
                                      robots))
                          (c 1)))))
(println total)
