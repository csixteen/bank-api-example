(ns bank.api.common
  (:require [clojure.string :as string]))


(defn sanitize-column
  "Removes the `<table_name>/` prefix from the result set that
  is sent by next.jdbc. Probably there is a way of retrieving a
  result set already without that prefix, but I haven't spent too
  much time investigating."
  [col]
  (let [col (str col)
        i (string/index-of col \/)]
    (if (nil? i)
      col
      (apply str (drop (+ i 1) col)))))


(defn sanitize-result
  "Creates a new result map based on sanitized keys."
  [res]
  (reduce-kv (fn [m k v] (assoc m (sanitize-column k) v))
             {}
             res))


(defn invalid-field-exception
  "Throws an exception when a field (parameter) has an invalid value."
  [field-name message]
  (throw (ex-info
           (format "Invalid field: %s" field-name)
           {:status-code 400
            :errors      {(keyword field-name) message}})))


(defn checkp
  "Generic check that throws an exception if `pred` is not true."
  [pred field message]
  (when-not pred
    (invalid-field-exception field message)))
