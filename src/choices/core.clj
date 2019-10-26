(ns choices.core
(:require [clojure.set :as set]))

(defn person-to-scores [[name score choices]]
  (into {} (map #(-> [% score]) choices))) 

(defn tally [lst]
  (apply merge-with + lst))

(defn rank-choices [choice-scores]
  (->>
    choice-scores
    (into [])
    (sort-by second)
    reverse
    (map first)))

