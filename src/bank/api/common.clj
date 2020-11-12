(ns bank.api.common
  (:require [clojure.string :as string]))


(defn sanitize-column
  [col]
  (let [col (str col)
        i (string/index-of col \/)]
    (if (nil? i)
      col
      (apply str (drop (+ i 1) col)))))


(defn sanitize-result
  [res]
  (reduce-kv (fn [m k v] (assoc m (sanitize-column k) v))
             {}
             res))


(defn invalid-field-exception
  [field-name message]
  (throw (ex-info
           (format "Invalid field: %s" field-name)
           {:status-code 400
            :errors      {(keyword field-name) message}})))


(defn checkp
  [pred field message]
  (when-not pred
    (invalid-field-exception field message)))
