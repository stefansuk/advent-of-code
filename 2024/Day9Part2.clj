(def input (slurp "2024/Day9Input"))
(def index 0)
(def disk (into (sorted-map) (for [i (range (count input))
                                   :let [length (Integer/parseInt (str (nth input i)))
                                         _ (def index (+ index length))
                                         id (if (= (mod i 2) 0)
                                              (quot i 2)
                                              nil)]]
                               [i {:id id, :index (- index length), :length length}])))
(def edisk (loop [sdisk disk start 1 end (- (count input) 1)]
             (if (>= start end)
               sdisk
               (let [ev (sdisk end)
                     s (some #(when (and (>= ((second %) :length) (ev :length))
                                         (nil? ((second %) :id)))
                                %)
                             sdisk)
                     sk (when (not (nil? s)) (s 0))
                     sv (when (not (nil? s)) (s 1))]
                 (cond
                   (or (nil? s) (>= sk end))
                   (recur sdisk start (- end 2))
                   (> (sv :length) (ev :length))
                   (let [d (-> sdisk
                               (update-in [sk :index] + (ev :length))
                               (update-in [sk :length] - (ev :length))
                               (assoc-in [end :index] (sv :index)))]
                     (recur d start (- end 2)))
                   (= (sv :index) start)
                   (let [d (-> sdisk
                               (dissoc sk)
                               (assoc-in [end :index] (sv :index)))]
                     (recur d (+ start 2) (- end 2)))
                   :else
                   (let [d (-> sdisk
                               (dissoc sk)
                               (assoc-in [end :index] (sv :index)))]
                     (recur d start (- end 2))))))))
(def checksum (reduce + (for [[_ file] edisk
                              i (map #(+ % (file :index)) (range (file :length)))
                              :when (not (nil? (file :id)))]
                          (* i (file :id)))))
(println checksum)
