FROM clojure:lein

RUN apt-get update && \
    apt-get install -y git jq rlwrap curl && \
    rm -rf /var/lib/apt/lists/*

# Install Clojure CLI (tools.deps) alongside Leiningen.
# Both coexist during the Polylith migration; Leiningen is removed in Phase 5.
RUN curl -fsSL -o linux-install.sh \
        https://github.com/clojure/brew-install/releases/latest/download/linux-install.sh && \
    chmod +x linux-install.sh && \
    ./linux-install.sh && \
    rm linux-install.sh
