(ns choices.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[choices started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[choices has shut down successfully]=-"))
   :middleware identity})
