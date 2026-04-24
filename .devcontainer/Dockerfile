FROM clojure:temurin-25-tools-deps-bookworm

RUN apt-get update && \
    apt-get install -y git jq rlwrap curl && \
    rm -rf /var/lib/apt/lists/*
