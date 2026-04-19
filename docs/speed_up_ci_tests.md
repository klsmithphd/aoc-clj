# Speeding Up CI Tests

The full test suite (268 test files spanning 10+ years of Advent of Code solutions) takes several
minutes to run in CI. The goal is incremental testing: only run tests for code that actually changed.

The key challenge is the fan-out dependency from shared utility namespaces (`utils/graph`, `utils/grid`,
etc.) to puzzle solutions. A change to a utility could affect hundreds of test files across all years.

---

## Option 1: GitHub Actions path-based matrix jobs

Split CI into multiple jobs, each scoped to a directory path, using GitHub Actions `paths` filters:

```yaml
jobs:
  test-utils:
    if: contains(github.event.changes, 'src/aoc_clj/utils/')
    ...
  test-2024:
    if: contains(github.event.changes, 'src/aoc_clj/2024/')
    ...
```

**Pros:**
- No changes to project structure or tooling
- Simple to understand and maintain for the common case (working on one year)

**Cons:**
- Coarse granularity — can only filter at the directory level, not by namespace dependency
- Fan-out problem: a utils change must explicitly trigger all year jobs, which must be hardcoded
- Brittle as the project grows; the workflow file becomes a dependency manifest that can fall out of sync

**Verdict:** Low-effort but limited. Works well only if utils changes are rare and the explicit
fan-out triggers are kept up to date.

---

## Option 2: Custom git-diff → affected namespaces script (Babashka)

Write a script (Babashka is ideal) that:
1. Runs `git diff origin/main --name-only` to get changed files
2. Maps changed file paths to Clojure namespace names
3. Reads `(:require ...)` declarations to build a reverse dependency graph
4. Emits the set of test namespaces that need to run
5. CI invokes `lein test <those namespaces>`

Given the regular structure (`src/aoc_clj/2024/day01.clj` → `test/aoc_clj/2024/day01_test.clj`),
the file-to-namespace mapping is straightforward. The reverse dep graph for utils is the only
complex part, but it's stable since puzzle solutions never depend on each other.

**Pros:**
- No structural changes to the project
- Namespace-level granularity, not just directory-level
- Slots directly into the existing Leiningen + GitHub Actions setup

**Cons:**
- Bespoke tooling to maintain; essentially reimplements what Polylith does natively
- Reverse dep graph logic could drift if utils evolves significantly
- Doesn't benefit from the broader ecosystem of incremental build tooling

**Verdict:** Good pragmatic option if staying on Leiningen is a priority. A few hours of work,
but you're building and owning infrastructure that established tools already provide.

---

## Option 3: Kaocha

Kaocha is a richer test runner for Clojure with a Leiningen plugin, better output formatting,
and a `--watch` mode that reruns tests when files change on disk.

**Pros:**
- Drop-in for local development; `lein kaocha --watch` gives fast feedback while iterating
- Focus filters (`--focus some.ns-test`) are ergonomic
- Better test output than `lein test`

**Cons:**
- Watch mode is file-change-triggered, not git-diff-aware — irrelevant to CI
- Does not solve the incremental CI problem at all
- No understanding of the namespace dependency graph

**Verdict:** A genuine improvement for local development workflow, but does nothing for CI
incremental testing. Worth adding regardless, but not a solution to the core problem.

---

## Option 4: Polylith

[Polylith](https://polylith.gitbook.io/polylith) is a monorepo architecture for Clojure that
structures code into explicit `components` (library units) and `bases` (entry points). The
`poly test :changed` command natively tests only components affected by changes since the last
stable point (tracked via a git tag).

This is the principled version of Option 2 — the dependency graph and incremental test selection
are built-in and maintained by the tooling rather than hand-rolled.

**Migration shape for this project:**
- Each utility namespace (or logical group) becomes a component: `grid`, `graph`, `math`, etc.
- Each year's puzzle solutions could be one component per year, or one per day
- A `workspace.edn` describes the dependency graph between components
- `poly test :changed` runs only what's affected

**Pros:**
- Git-aware incremental testing is a first-class feature, not bolted on
- Explicit dependency graph is enforced by the tooling (circular deps are caught)
- Skills transfer directly to production Clojure monorepos
- Active ecosystem, good documentation

**Cons:**
- Significant structural migration: directory layout, namespace conventions, and build tooling all change
- Polylith's primary toolchain is `deps.edn` + `tools.build`; migrating from Leiningen is part of the work
- Per-day components would be ~250 components — likely too fine-grained; per-year is more practical
- The puzzle solutions don't actually have interdependencies, so Polylith's component isolation
  provides less value there than it would in a real production monorepo

**Verdict:** The most principled option and the one with the best skills transfer. The migration
is substantial but feasible with AI assistance. Recommended if the goal includes learning
production-grade Clojure monorepo patterns.

---

## Option 5: Bazel or Pants

Hermetic, language-agnostic incremental build systems with remote caching and fine-grained
dependency tracking at the file/target level.

- **Bazel**: Google's open-source build system; Clojure support via [`rules_clojure`](https://github.com/clojure-maven-plugin/rules_clojure) (community-maintained)
- **Pants v2**: Developed by Toolchain (now Semaphore); Clojure support is limited

**Pros:**
- True hermetic builds with remote caching — a changed file only rebuilds/retests its transitive dependents
- Language-agnostic: if the project ever spans multiple languages, Bazel handles it uniformly
- Remote caching means CI can reuse artifacts across branches and PRs

**Cons:**
- Clojure ecosystem support is immature; `rules_clojure` is not widely used and may lag behind
  Clojure/JVM releases
- Requires writing `BUILD` files for every target (namespace or namespace group)
- Steep learning curve; significant ongoing maintenance overhead
- Designed for large multi-team organizations, not a personal project
- The JVM startup cost that makes Clojure CI slow isn't addressed — Bazel caches compilation
  artifacts but each test shard still pays JVM startup

**Verdict:** Not recommended for this project. The ecosystem support is too immature,
the maintenance cost is high, and the complexity is mismatched to the project's scale.
Bazel shines in large polyglot monorepos with dedicated build engineering support.

---

## Summary

| Option | CI Incremental | Effort | Skills Transfer | Recommended? |
|---|---|---|---|---|
| Path-based matrix jobs | Coarse (directory) | Low | Low | Maybe, as a stopgap |
| Babashka script | Fine (namespace) | Medium | Low | If staying on Leiningen |
| Kaocha | No (local only) | Low | Low | Yes, but for local DX only |
| Polylith | Fine (component) | High | High | Yes, if open to migration |
| Bazel / Pants | Fine (target) | Very High | Medium | No |
