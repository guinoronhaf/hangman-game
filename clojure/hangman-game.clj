(require '[clojure.string :as str])

(defn replace-at-index [l indexes new-element]
  (reduce #(assoc %1 %2 new-element) l))

(defn index-of [l e]
  (let [l-indexes (take (count l) (range))]
    (filter #(= e (nth l %1)) l-indexes)))

(defn check-word [word characters current-guess guesses mistakes]
  (let [g-indexes (index-of () current-guess)])
