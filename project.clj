(defproject choices "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring "1.7.1"]
                 [hiccup "1.0.5"]
                 [cheshire "5.9.0"]
                 [compojure "1.6.1"]
                 [liberator "0.15.3"]]
  :plugins [[lein-ring "0.12.5"]]

  :repl-options {:init-ns choices.core}
  :ring { :handler choices.core/handler }
)
