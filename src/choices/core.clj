(ns choices.core
(:require [clojure.set :as set]))

(defn person-to-scores
  "given a preson structure, produce their scores per choice"
  [[name score choices]]
  (into {} (for [c choices] [c score]))) 

(defn tally
  "total up scores per choice"
  [lst]
  (apply merge-with + lst))

(defn rank-choices
  "given choices with scores, produce ranked choices"
  [choice-scores]
  (->>
    choice-scores
    (into [])
    (sort-by second)
    reverse
    (map first)))

(defn pivot
  "flip a map to lists into a list of maps: [x [y z]] -> [{y x} {z x}]"
  [lst]
  (reduce
    (fn [m [k v]] (apply conj m (for [kk k] {kk [v]})))
    [] lst)
  )

(defn choices-to-people
  "given a list of people structures, produce a map of choice to names "
  [people]
  (->>
    people
    (map (juxt last first))
    pivot
    (apply merge-with concat)
    ))
