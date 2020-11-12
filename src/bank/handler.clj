(ns bank.handler
  "Top-level Bank App Ring handler."
  (:require [ring.middleware
             [json :refer [wrap-json-body wrap-json-response]]
             [keyword-params :refer [wrap-keyword-params]]
             [params :refer [wrap-params]]]
            [bank.routes :as routes]))


(def app
  "Primary entry point to the Ring HTTP server."
  (-> routes/routes
      wrap-json-body
      wrap-json-response
      wrap-keyword-params
      wrap-params))
