# bank-api

FIXME

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed. This is also assuming that you have a MySQL compatible (e.g. MariaDB) database available.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start the server:

```clojure
$ make server
```

## Endpoints

### Create a bank account

- `POST /account`
- Request Body parameters:
    - name (string)

Response:
```
{
  "account-number": 1,
  "name": "Foobar"
}
```

### View a bank account

- `GET /account/:id`

Response:
```
{
  "account-number": 1,
  "name": "Foobar",
  "balance": 0
}
```

### Deposit money to account

- `POST /account/:id/deposit`
- Request Body parameters:
    - amount (number)
- Pre-requisits: amount > 0

Response:
```
{
  "account-number": 1,
  "name": "Foobar",
  "balance": 100
}
```

## References
- [Private def in Clojure](https://stackoverflow.com/questions/20443545/private-def-in-clojure-clojurescript)

