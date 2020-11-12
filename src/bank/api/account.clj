(ns bank.api.account
  "Account related route table and handlers."
  (:require [compojure
             [core :refer [defroutes GET POST]]]
            [honeysql
             [core :as sql]
             [helpers :refer :all :as h]]
            [next.jdbc :as jdbc]
            [ring.util.response :refer [response]]
            [bank.db :refer [db-connection]]
            [bank.api.common :as api]))


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
    (response (api/sanitize-result r))))


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
  ; TODO - make this check a bit more generic, since it's repeated already
  (api/checkp #(> amount 0) :amount "Deposit amount must be greater than 0 (zero)")
  (let [q (-> (h/update :accounts)
              (h/sset {:balance (sql/call :+ :balance amount)})
              (h/where [:= :id account_id])
              sql/format)
        r (jdbc/execute-one! @db-connection q)]
    (view-account account_id)))


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
  (api/checkp #(> amount 0) :amount "Withdraw amount must be greater than 0 (zero)")
  (let [r (jdbc/execute-one! @db-connection
                             ["CALL Withdraw_money(?, ?)" account_id amount])]
    (view-account account_id)))


;;;-------------------------------------------------
;;; Transfer money to another account
;;; - POST /api/account/:id/send
;;;-------------------------------------------------

(defn transfer-money
  [src_id dst_id amount]
  (api/checkp
    #(not= src_id dst_id)
    :dst_id
    "Source and destination accounts must be different.")
  (let [r (jdbc/execute-one! @db-connection
                             ["CALL Transfer_money(?,?,?)" src_id dst_id amount])]
    (view-account src_id)))


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
