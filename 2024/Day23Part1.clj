(def input (slurp "2024/Day23Input"))
(def connections (mapv #(vec (rest %)) (re-seq #"(\w+)-(\w+)" input)))
(def total (count (for [a (range (count connections))
                        :let [ca (connections a)]
                        b (range (+ a 1) (count connections))
                        :let [cb (connections b)
                              ab (set (concat ca cb))]
                        :when (and (= (count ab) 3)
                                   (some #(= (first %) \t) ab))
                        c (range (+ b 1) (count connections))
                        :let [cc (connections c)]
                        :when (and (contains? ab (cc 0))
                                   (contains? ab (cc 1)))]
                    [ca cb cc])))
(println total)
