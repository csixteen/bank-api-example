(ns bank.config
  (:require [clojure.string :as string]
            [environ.core :as environ]))


(def ^:private app-defaults
  {:db-host     "goliath"
   :db-port     3306
   :db-name     "bank"})


(defn get-config
  "Takes a keyword or a string and retrieves its corresponding
  configuration value. I'm taking advantage of `environ` library here,
  which will search in this order:

  1. Environment variables
  2. Java system properties

  If none of those sources returns anything, then the value will be
  retrieved from `app-defaults`."
  [k]
  (let [k (keyword k)
        v (k environ/env)]
    (if (not (string/blank? v))
      v
      (k app-defaults))))
