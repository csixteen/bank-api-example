(ns bank.routes
  "Main Bank App route table.
  For future reference: https://github.com/weavejester/compojure/wiki/Routes-In-Detail"
 (:require [compojure
             [core :refer [context defroutes GET]]
             [route :as route]]
           [bank.api.routes :as api]))


(defroutes routes
  ;; ^/$
  (GET "/" [] (str "Hello, world!"))
  ;; ^/api/health$
  (GET "/api/health" [] {:status 200, :body {:status "ok"}})
  ;; ^/api -> All other /api endpoints
  (context "/api" [] (fn [& args] (apply api/routes args)))
  ;; Anything else
  (route/not-found "Page not found"))
