(defproject bank "0.1.0-SNAPSHOT"
  :description "An API for managing Bank accounts"
  :url "https://github.com/csixteen/bank-api-example"
  :min-lein-version "2.0.0"

  :aliases
  {"ring"  ["with-profile" "+ring" "ring"]}

  :dependencies
  [[org.clojure/clojure                  "1.10.0"]
   [compojure                            "1.6.1"]
   [environ                              "1.2.0"]
   [honeysql                             "1.0.444"]
   [org.mariadb.jdbc/mariadb-java-client "2.6.2"]
   [ring/ring-defaults                   "0.3.2"]
   [ring/ring-json                       "0.5.0"]
   [seancorfield/next.jdbc               "1.1.613"]]

  :profiles
  {:ring
   [{:plugins
     [[lein-ring "0.12.5" :exclusions [org.clojure/clojure]]]
     
     :ring
     {:handler bank.handler/app
      :reload-paths ["src"]}}]

   :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
