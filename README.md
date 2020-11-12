# bank-api

Clojure exercise to create a simple RESTful API to manage bank accounts.

# Dependencies

You will need [Leiningen][] 2.0.0 or above installed. This is also assuming that you have a MySQL compatible (e.g. MariaDB) database available.

[leiningen]: https://github.com/technomancy/leiningen

# Running

To start the server:

```clojure
$ export DB_USER=foobar
$ export DB_PASSWORD=secure_password
$ make server
```

## Configuration

Database configuration can be hardcoded on `bank/config.clj` for the sake of simplicity:

```clojure
(def ^:private app-defaults
  {:db-host     "amazing_database"
   :db-port     3306
   :db-user     "foobar"
   :db-password "secure_password"
   :db-name     "bank"})
```

You can also export those configuration values as environment variables:

```
export DB_USER=foobar
export DB_PASSWORD=secure_password
```

# Endpoints

### Create a bank account

- `POST /api/account`
- Request Body parameters:
    - **name** (string)

Response:
```
{
  "account-number": 1,
  "name": "Foobar"
}
```

### View a bank account

- `GET /api/account/:id`

Response:
```
{
  "account-number": 1,
  "name": "Foobar",
  "balance": 0
}
```

### Deposit money to account

- `POST /api/account/:id/deposit`
- Request Body parameters:
    - **amount** (number)
- Pre-requisits:
    - **amount** > 0

Response:
```
{
  "account-number": 1,
  "name": "Foobar",
  "balance": 100
}
```

### Withdraw money from account

- `POST /api/account/:id/withdraw`
- Request Body parameters:
    - **amount** (number)
- Pre-requisits:
    - **amount** > 0
    - account balance >= **amount**

Response:
```
{
  "account-number": 1,
  "name": "Foobar",
  "balance": 95
}
```

### Transfer money between accounts

- `POST /api/account/:id/send`
- Request Body parameters:
    - **amount** (number)
    - **account-number** (number)
- Pre-requisits:
    - **amount** > 0
    - source account balane >= **amount**

Response:
```
{
  "account-number": 1,
  "name": "Foobar",
  "balance": 37.50
}
```

### Retrieve account audit log (TODO)

- `GET /api/account/:id/audit`

# TODO
- Account audit log endpoint.
- Test the code.
- Use proper map destructuring on the endpoint handlers to extract the request body parameters.
- Document the code a bit better.
- Find a way to remove the table namespace from the result set returned by `next.jdbc`.

# References
- [Private def in Clojure](https://stackoverflow.com/questions/20443545/private-def-in-clojure-clojurescript)
- [next.jdbc](https://github.com/seancorfield/next-jdbc)
- [HoneySQL](https://github.com/seancorfield/honeysql)
- [Ring](https://github.com/ring-clojure/ring)
- [Compojure](https://github.com/weavejester/compojure)
- [Lein Ring](https://github.com/weavejester/lein-ring)
- [Clojure Getting Started](https://clojure.org/guides/getting_started)
- [Clojure from the ground-up](https://aphyr.com/posts/301-clojure-from-the-ground-up-welcome)
