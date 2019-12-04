(ns choices.core
  (:require
   [clojure.set :as set]
   [hiccup.core :as h]
   [cheshire.core :as ch]
   [compojure.core :refer [defroutes ANY GET]]
   [ring.middleware.params :refer [wrap-params]]
   [liberator.core :refer [resource defresource] :as r]))

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

(defn distribute-keys
  "[[y z] x] -> [{y x} {z x}]"
  [lst]
  (reduce
   (fn [m [k v]] (apply conj m (for [kk k] {kk #{v}})))
   []
   lst))

(defn choices-to-people
  "given a list of people structures, produce a map of choice to names "
  [people]
  (->>
   people
   (map (juxt last first))
   distribute-keys
   (apply merge-with (comp set concat))))

(defn rank-choices-with-people
  "transform list of people and choices to ranked choices and people"
  [people]
  (let [ranked-choices (->>
                        people
                        (map person-to-scores)
                        tally
                        rank-choices)
        choices-people (choices-to-people people)]
    (map (juxt identity choices-people) ranked-choices)))

(defn do-home [request]
  {:status 200
   :headers {"Content-type" "text/html"}
   :body (h/html
           [:head
            [:link {:rel "stylesheet" :href "/style.css"}]]
           [:body
            [:h1 "home World"]])})

(defn html-with-head [body]
  (h/html
   [:head
    [:link {:rel "stylesheet" :href "/style.css"}]]))

(defn do-html [request]
  {:status 200
   :headers {"Content-type" "text/html"}
   :body (h/html
          [:head
           [:link {:rel "stylesheet" :href "/style.css"}]]
          [:body
           [:h1 "an h1"]
           [:table
            [:tbody
             (for [p request]
               [:tr
                [:td (key p)]
                [:td (val p)]])]]])})

(defn do-style [request]
  {:status 200
   :headers {"Content-type" "text/css"}
   :body "html, body { color: white; background-color: black;}"})

(defn not-found [request]
  {:status 404
   :body "Not Found"})

(defn handler [request]
  (cond
    (re-matches #"/style.css" (:uri request)) (do-style request)
    (re-matches #"/html.*" (:uri request)) (do-html request)
    (re-matches #"/" (:uri request)) (do-home request)
    (re-matches #"/resource" (:uri request)) (resource
                                               :available-media-types ["text/plain"]
                                              :handle-ok "hello resource")
    :else (not-found request)))
