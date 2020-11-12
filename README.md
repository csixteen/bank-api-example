# bank-api

Clojure exercise to create a simple RESTful API to manage bank accounts.

# Dependencies

You will need [Leiningen][] 2.0.0 or above installed. This is also assuming that you have a MySQL compatible (e.g. MariaDB) database available.

[leiningen]: https://github.com/technomancy/leiningen

# Running

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

# TODO
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
