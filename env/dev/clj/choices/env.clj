(ns choices.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [choices.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[choices started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[choices has shut down successfully]=-"))
   :middleware wrap-dev})
