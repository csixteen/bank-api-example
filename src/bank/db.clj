(ns bank.db
  "Bank App database definition."
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [bank.config :as config]))


(def ^:private db-connection-details
  {:dbtype      "mysql"
   :classname   "org.mariadb.jdbc.Driver"
   :subprotocol "mysql"
   :host        (config/get-config :db-host)
   :port        (config/get-config :db-port)
   :dbname      (config/get-config :db-name)
   :user        (config/get-config :db-user)
   :password    (config/get-config :db-password)})


(def db-connection
  (delay
    (-> db-connection-details
        (jdbc/get-datasource)
        (jdbc/with-options {:builder-fn rs/as-unqualified-lower-maps})
        (jdbc/get-connection))))
