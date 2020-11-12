(ns bank.api.account
  "Account related route table and handlers."
  (:require [clojure.string :as string]
            [compojure
             [core :refer [defroutes GET POST]]]
            [honeysql
             [core :as sql]
             [helpers :refer :all :as h]]
            [next.jdbc :as jdbc]
            [ring.util.response :refer [response]]
            [bank.db :refer [db-connection]]))


(defn- sanitize-column
  [col]
  (let [col (str col)
        i (string/index-of col \/)]
    (if (nil? i)
      col
      (apply str (drop (+ i 1) col)))))

(defn- sanitize-result
  [res]
  (reduce-kv (fn [m k v] (assoc m (sanitize-column k) v))
             {}
             res))


;;;-------------------------------------------------
;;; Creates a new Bank account
;;; - POST /api/account
;;;   Body parameters: {
;;;       name  str
;;;   }
;;;-------------------------------------------------

(defn create-account
  [name]
  (let [q (-> (h/insert-into :accounts)
              (h/columns :name)
              (h/values [[name]])
              sql/format)
        r (jdbc/execute-one! @db-connection q {:return-keys true})]
    (response
      {:account-number (:insert_id r)
       :name name})))


;;;-------------------------------------------------
;;; View Bank account
;;; - GET /api/account/:id
;;;-------------------------------------------------

(defn view-account
  [id]
  (let [q (-> (h/select :*)
              (h/from :accounts)
              (h/where [:= :id id])
              sql/format)
        r (jdbc/execute-one! @db-connection q)]
    (response (sanitize-result r))))


;;;-------------------------------------------------
;;; Deposit money to an account
;;; - POST /api/account/:id/deposit
;;;   Body parameters: {
;;;       account_id int
;;;       amount     int
;;;   }
;;;-------------------------------------------------

(defn make-deposit
  [account_id amount]
  (response {:account_id account_id, :amount amount}))


;;;-------------------------------------------------
;;; Withdraw money from account
;;; - POST /api/account/:id/withdraw
;;;   Body parameters: {
;;;       account_id int
;;;       amount     int
;;;   }
;;;-------------------------------------------------

(defn withdraw-money
  [account_id amount]
  (response {:account_id account_id, :amount amount}))


;;;-------------------------------------------------
;;; Transfer money to another account
;;; - POST /api/account/:id/send
;;;-------------------------------------------------

(defn transfer-money
  [src_id dst_id amount]
  (response {:from_account src_id
             :to_account dst_id
             :amount amount}))


;;;+----------------------------------+
;;; `Account` route table definition
;;;+----------------------------------+

(defroutes routes
  (POST "/" 
        [:as {body :body}]
        (create-account (get body "name")))

  (GET  "/:id"
       [id]
       (view-account id))

  (POST "/:id/deposit"
        [id :as {body :body}]
        (make-deposit id (get body "amount")))

  (POST "/:id/withdraw"
        [id :as {body :body}]
        (withdraw-money id (get body "amount")))

  (POST "/:id/send"
        [id :as {body :body}]
        (println (keys body))
        (transfer-money id
                        (get body "account-number")
                        (get body "amount"))))
