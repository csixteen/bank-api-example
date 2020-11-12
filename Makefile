.PHONY: server

server:
	@lein ring server-headless 8000

.PHONY: test
test:
	curl \
		--header "Content-Type: application/json" \
		--request POST \
		--data '{"name":"Pedro"}' \
		http://localhost:8000/api/account/
