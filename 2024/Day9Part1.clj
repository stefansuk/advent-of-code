(def input (slurp "2024/Day9Input"))
(def index 0)
(def disk (into {} (for [i (range (count input))
                         :let [length (Integer/parseInt (str (nth input i)))
                               _ (def index (+ index length))
                               id (if (= (mod i 2) 0)
                                    (quot i 2)
                                    nil)]]
                     [i {:id id, :index (- index length), :length length}])))
(def edisk (loop [sdisk disk start 1 end (- (count input) 1)]
             (if (>= start end)
               sdisk
               (let [sv (sdisk start)
                     ev (sdisk end)]
                 (cond
                   (> (sv :length) (ev :length))
                   (let [d (-> sdisk
                               (update-in [start :index] + (ev :length))
                               (update-in [start :length] - (ev :length))
                               (assoc-in [end :index] (sv :index)))]
                     (recur d start (- end 2)))
                   (< (sv :length) (ev :length))
                   (let [d (-> sdisk
                               (assoc-in [start :id] (ev :id))
                               (update-in [end :length] - (sv :length)))]
                     (recur d (+ start 2) end))
                   :else
                   (let [d (-> sdisk
                               (dissoc start)
                               (assoc-in [end :index] (sv :index)))]
                     (recur d (+ start 2) (- end 2))))))))
(def checksum (reduce + (for [[_ file] edisk
                         i (map #(+ % (file :index)) (range (file :length)))
                         :when (not (nil? (file :id)))]
                     (* i (file :id)))))
(println checksum)
