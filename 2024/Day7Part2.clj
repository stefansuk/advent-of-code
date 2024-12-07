(require '[clojure.string :as str])

(def input (slurp "2024/Day7Input"))
(def lines (map (fn [x] (map #(Long/parseLong %) (re-seq #"\d+" x))) (str/split-lines input)))

(defn check? [y xs]
  (when (> (count xs) 0)
    (let [x (first xs)]
      (or (and (= (count xs) 1)
               (= y x))
          (let [sy (str y)
                sx (str x)]
            (and (str/ends-with? sy sx)
                 (let [s (subs sy 0 (- (count sy) (count sx)))]
                   (check? (Long/parseLong (if (str/blank? s) "0" s)) (rest xs)))))
          (and (= (mod y x) 0)
               (check? (quot y x) (rest xs)))
          (and (> y x)
               (check? (- y x) (rest xs)))))))

(def sum (reduce + (for [l lines
                         :let [y (first l)
                               xs (reverse (rest l))]
                         :when (check? y xs)]
                     y)))
(println sum)
