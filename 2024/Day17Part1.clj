(require '[clojure.string :as str])

(def input (slurp "2024/Day17Input"))
(def numbers (map #(Integer/parseInt %) (re-seq #"\d+" input)))
(def registers (vec (take 3 numbers)))
(def instructions (vec (drop 3 numbers)))
(def pointer 0)
(def output [])

(defn combo [n]
  (if (<= n 3)
    n
    (registers (- n 4))))

(defn adv [n]
  (def registers (update registers 0 bit-shift-right (combo n))))

(defn bxl [n]
  (def registers (update registers 1 bit-xor n)))

(defn bst [n]
  (def registers (assoc registers 1 (mod (combo n) 8))))

(defn jnz [n]
  (when (not= (registers 0) 0)
    (def pointer (- n 2))))

(defn bxc []
  (def registers (update registers 1 bit-xor (registers 2))))

(defn out [n]
  (def output (conj output (mod (combo n) 8))))

(defn bdv [n]
  (def registers (assoc registers 1 (bit-shift-right (registers 0) (combo n)))))

(defn cdv [n]
  (def registers (assoc registers 2 (bit-shift-right (registers 0) (combo n)))))

(loop []
  (when (< pointer (count instructions))
   (let [opcode (instructions pointer)
        operand (instructions (inc pointer))]
    (case opcode
      0 (adv operand)
      1 (bxl operand)
      2 (bst operand)
      3 (jnz operand)
      4 (bxc)
      5 (out operand)
      6 (bdv operand)
      7 (cdv operand))
    (def pointer (+ pointer 2))
    (recur))))

(println (str/join "," output))
