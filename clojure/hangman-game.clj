(require '[clojure.string :as str])

(defn characters-template [word]
  (loop [word-list (str/split word #"") template []]
    (if (empty? word-list)
      template
      (let [word-list-first (first word-list) word-list-rest (rest word-list)]
        (cond
          (or (= word-list-first " ") (= word-list-first "-")) (recur word-list-rest (conj template "-"))
          :else (recur word-list-rest (conj template "__")))))))

(defn replace-at-index [l indexes new-element]
  (reduce #(assoc %1 %2 new-element) l indexes))

(defn index-of [l e]
  (let [l-indexes (take (count l) (range))]
    (filter #(= e (nth l %1)) l-indexes)))

(defn process-guess [word characters current-guess guesses mistakes]
  (let [g-indexes (index-of (str/split word #"") current-guess)]
    (cond
      (= 0 (count g-indexes)) [characters (conj guesses current-guess) (inc mistakes)]
      :else [(replace-at-index characters g-indexes current-guess) (conj guesses current-guess) mistakes])))

(defn check-word [characters word]
  (let [
    w (str/join "" (replace-at-index (str/split word #"") (index-of (str/split word #"") "-") " "))
    c-word (str/join "" (replace-at-index characters (index-of characters "-") " "))
  ]
    (= w c-word)))

(defn display-game [characters mistakes]
  (println (str (str/join " " characters) "\nmistakes: " mistakes)))

(defn run-game [word]
  (let [MAX-MISTAKES 6]
    (loop [
      characters (characters-template word)
      guesses []
      mistakes 0
    ]
      (display-game characters mistakes)
      (cond
        (> mistakes MAX-MISTAKES) false
        (check-word characters word) true
        :else (let [
                proc-guess (process-guess word characters (str (read)) guesses mistakes)
                new-characters (nth proc-guess 0)
                new-guesses (nth proc-guess 1)
                new-mistakes (nth proc-guess 2)]
                (recur new-characters new-guesses new-mistakes))))))

(defn -main []
  (let [victory (run-game "opa")]
    (cond
      (true? victory) (print "venceu")
      :else (print "perdeu"))))

(-main)
