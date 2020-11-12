(ns bank.api.routes
  (:require [compojure
             [core :refer [context defroutes]]]
            [bank.api
             [account :as account]]))


(defroutes routes
  (context "/account" [] (fn [& args] (apply account/routes args))))
