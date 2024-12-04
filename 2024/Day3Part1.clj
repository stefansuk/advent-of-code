(def input (slurp "2024/Day3Input"))
(def sum (apply + (map #(*
                          (Integer/parseInt (get % 1))
                          (Integer/parseInt (get % 2)))
                       (re-seq #"mul\((\d+),(\d+)\)" input))))
(println sum)
