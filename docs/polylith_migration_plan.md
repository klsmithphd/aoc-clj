# Polylith Migration Plan

## Decision

Migrate this project to [Polylith](https://polylith.gitbook.io/polylith), replacing
Leiningen with the `deps.edn` + `tools.build` + `poly` toolchain.

## Rationale

- **Primary goal — faster CI iteration.** `poly test :changed` is a first-class feature
  that computes affected components from a git-tracked stable point and runs only those
  tests. This is exactly the problem shape we have: shared utilities used by ~250 puzzle
  solution files.
- **Secondary goal — learning transferable architecture skills.** Polylith teaches explicit
  component boundaries, interface-vs-implementation discipline, and monorepo layout — all
  patterns that recur in production codebases regardless of language.
- **The planned visualization project provides a real second consumer** for the utility
  components. That's what makes Polylith's component model earn its keep here rather than
  being pure ceremony.
- **Modern Clojure tooling.** `deps.edn` + `tools.build` is where the ecosystem has been
  converging since 2018. Moving off Leiningen is a worthwhile side effect.

## Explicit non-goals

This migration changes architecture and build tooling **only**. Out of scope:

- **No functional changes.** Every solution produces the same output as before.
- **No algorithmic or performance refactors.** Code moves; it does not get rewritten.
- **No changes to the puzzle solutions themselves.** Day files are relocated but their
  logic is untouched.
- **No changes to the `inputs/` subtree.**
- **No test reorganization** beyond mirroring the component layout. Existing tests keep
  their assertions.

The test suite is the contract. If behavior drifts, the migration is wrong.

## Target workspace structure

```
aoc-clj/
├── bases/
│   └── cli/                     # thin wrapper around the existing -main
├── components/
│   ├── grid/                    # utility components
│   ├── graph/
│   ├── math/
│   ├── intervals/
│   ├── maze/
│   ├── geometry/
│   ├── vectors/
│   ├── binary/
│   ├── digest/
│   ├── assembunny/
│   ├── intcode/
│   ├── core-utils/              # currently `utils/core.clj` — scope TBD in Phase 0
│   ├── year-2015/               # year components
│   ├── year-2016/
│   ├── year-2017/
│   ├── year-2018/
│   ├── year-2019/
│   ├── year-2020/
│   ├── year-2021/
│   ├── year-2022/
│   ├── year-2023/
│   ├── year-2024/
│   └── year-2025/
├── projects/
│   └── aoc-clj/                 # deployable: cli base + all components
├── development/                 # REPL classpath — sees everything
├── inputs/                      # unchanged; existing git subtree
├── deps.edn                     # workspace root
└── workspace.edn                # polylith metadata
```

## Component breakdown

### Utility components

| Component  | Current source                                                | Notes                     |
| ---------- | ------------------------------------------------------------- | ------------------------- |
| grid       | `src/aoc_clj/utils/grid.clj` + `src/aoc_clj/utils/grid/`      | Package with subdirs      |
| graph      | `src/aoc_clj/utils/graph.clj`                                 |                           |
| math       | `src/aoc_clj/utils/math.clj`                                  |                           |
| intervals  | `src/aoc_clj/utils/intervals.clj`                             |                           |
| maze       | `src/aoc_clj/utils/maze.clj`                                  |                           |
| geometry   | `src/aoc_clj/utils/geometry.clj`                              |                           |
| vectors    | `src/aoc_clj/utils/vectors.clj`                               |                           |
| binary     | `src/aoc_clj/utils/binary.clj`                                |                           |
| digest     | `src/aoc_clj/utils/digest.clj`                                |                           |
| assembunny | `src/aoc_clj/utils/assembunny.clj`                            |                           |
| intcode    | `src/aoc_clj/utils/intcode.clj` + `src/aoc_clj/utils/intcode/` | Package with subdirs      |
| core-utils | `src/aoc_clj/utils/core.clj`                                  | Scope decision in Phase 0 |

### Year components

`year-2015` through `year-2025` — one per existing `src/aoc_clj/YYYY/` directory.

### Base

`cli` — wraps the current `aoc-clj.core/-main` entry point.

### Project

`aoc-clj` — the one deployable, composing the `cli` base with every component.

## Namespace renames

Polylith requires component code under `<top-ns>.<component-name>.*` with a mandatory
`interface` namespace. This forces mechanical renames:

- `aoc-clj.utils.grid`            → `aoc-clj.grid.interface`
- `aoc-clj.utils.grid.<helper>`   → `aoc-clj.grid.<helper>` (implementation, private)
- `aoc-clj.2024.day01`            → `aoc-clj.year-2024.day01` (Polylith component names
  cannot start with a digit; year directories get the `year-` prefix)

The interface namespace can use `potemkin/import-vars` (or an equivalent `def`
re-export pattern) to preserve public API surface without duplicating function
definitions. The rename itself is mechanical and scriptable.

## Preserving current test semantics

`project.clj` currently defines:

```clojure
:test-selectors {:default (complement :slow)
                 :slow :slow}
```

The `:slow` test exclusion must be preserved. Polylith passes args through to the
underlying test runner, so metadata-based selection (`^:slow` on `deftest` forms)
continues to work. Phase 4 will confirm the default test invocation excludes slow
tests; the CI workflow explicitly excludes them.

## Migration sequence

Each phase leaves the test suite green as its exit criterion. Work happens on a
`polylith-migration` branch merged to `main` only after all phases are complete and
CI is green. No phase is allowed to change solution behavior — if a test that was
passing before a phase fails after, the move is wrong, not the test.

### Phase 0 — Workspace scaffolding

- Install `clojure` CLI and `poly` CLI (update devcontainer as needed)
- Create skeleton `deps.edn`, `workspace.edn`, `development/` config
- Run `poly info` — confirm the empty workspace is recognized
- Resolve the `utils/core.clj` question: merge into an existing utility component
  or stand up as its own `core-utils` component
- Existing `src/` and `test/` trees untouched; `lein test` still works alongside

**Exit criterion:** `poly info` succeeds; `lein test` still passes.

### Phase 1 — Utility components (one at a time, bottom-up)

Order follows the dependency graph, leaves first. Rough order (verify in Phase 0
using `clj-kondo` analysis):
`binary` → `digest` → `math` → `vectors` → `geometry` → `intervals` → `grid` →
`graph` → `maze` → `assembunny` → `intcode` → `core-utils`

For each component:

1. `poly create c name:<name>`
2. Move source files into `components/<name>/src/aoc_clj/<name>/`
3. Create `interface.clj` re-exporting the public API
4. Move tests into `components/<name>/test/aoc_clj/<name>/`
5. Update all `(:require ...)` forms in consumer code to the new interface namespace
6. Run `poly test :dev :project` (full sweep) — confirm nothing regressed
7. Commit

**Exit criterion (per component):** full test suite passes after the component move.

### Phase 2 — Year components (one year at a time)

Years are independent (no cross-year requires), so chronological order is fine.

For each year:

1. `poly create c name:year-YYYY`
2. Move `src/aoc_clj/YYYY/*` → `components/year-YYYY/src/aoc_clj/year_YYYY/`
   (note: file-system underscore vs. namespace hyphen)
3. Update `ns` forms and internal requires (`aoc-clj.YYYY.dayNN` →
   `aoc-clj.year-YYYY.dayNN`)
4. Move tests
5. Declare utility component dependencies in `components/year-YYYY/deps.edn`
6. Run `poly test :dev :project`
7. Commit

**Exit criterion (per year):** that year's tests pass; all prior years' tests still pass.

### Phase 3 — CLI base and project

1. `poly create b name:cli`
2. Move `-main` and dispatch logic from `src/aoc_clj/core.clj` →
   `bases/cli/src/aoc_clj/cli/core.clj`
3. `poly create p name:aoc-clj`
4. Configure the project to include the `cli` base and every component
5. Spot-check the CLI against a few known days — output must match pre-migration

**Exit criterion:** `clojure -M:aoc-clj <args>` reproduces the behavior of the
previous `lein run` invocation for a sampled set of days.

### Phase 4 — CI migration

Replace the Leiningen-based workflow with Polylith-aware CI. Details in the next
section.

**Exit criterion:** CI green on the migration branch; `poly test :project` correctly
identifies affected components on a test PR.

### Phase 5 — Remove Leiningen

1. Delete `project.clj`
2. Remove the now-empty old `src/` and `test/` trees
3. Update `CLAUDE.md` and `README.md` to describe the `deps.edn` / `poly` workflow
4. Update devcontainer config to provision `clojure` + `poly` instead of `lein`

**Exit criterion:** a fresh devcontainer rebuild can run `poly test :project`
successfully from a clean state.

## GitHub Actions changes

Current workflow: `.github/workflows/clojure.yml` runs `lein deps && lein test`
inside the devcontainer. Two changes are needed: (1) swap the commands, and (2) add
stable-tag management so `poly test :changed` has a reference point.

### Updated test workflow

```yaml
name: Clojure CI

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout (GitHub)
        uses: actions/checkout@v4
        with:
          submodules: true
          token: ${{ secrets.AOC_INPUTS_TOKEN }}
          fetch-depth: 0            # poly needs history to find stable-main

      - name: Build and run Dev Container tasks
        uses: devcontainers/ci@v0.3
        with:
          runCmd: |
            # Resolve deps (populates ~/.m2 cache)
            clojure -P
            # Incremental test — affected components only, since stable-main
            poly test :project
```

### Stable-tag workflow

Polylith compares `HEAD` against a git tag (default `stable-<branch>`) to decide
which components are affected. The tag is a moving marker on the last known-green
commit on `main`. Add a second workflow that advances the tag after a successful
test run on `main`:

```yaml
name: Tag stable

on:
  workflow_run:
    workflows: ["Clojure CI"]
    branches: [main]
    types: [completed]

jobs:
  tag:
    if: ${{ github.event.workflow_run.conclusion == 'success' }}
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
        with:
          ref: ${{ github.event.workflow_run.head_sha }}
          fetch-depth: 0

      - name: Move stable-main tag
        run: |
          git config user.name  "github-actions[bot]"
          git config user.email "github-actions[bot]@users.noreply.github.com"
          git tag -f stable-main ${{ github.event.workflow_run.head_sha }}
          git push -f origin stable-main
```

Force-pushing a tag is intentional here: `stable-main` is a moving marker, not an
immutable release.

### First-run behavior

On the first CI run after the migration merges, no `stable-main` tag exists yet.
`poly test :project` falls back to running everything — equivalent to the current
full-suite behavior. Once the tag is created, subsequent PRs get incremental
selection.

### Slow-test selector

The current `:slow` exclusion must be preserved. In the Polylith world, this is
handled at the test runner layer via metadata. Phase 4 confirms the invocation
excludes `^:slow` tests by default, matching pre-migration behavior.

## Risks and mitigations

| Risk | Mitigation |
| --- | --- |
| Namespace rename introduces typos across hundreds of files | Use `clj-kondo --analysis` to get an authoritative list of all require forms; script the rename; verify test suite at each phase |
| Interface layer accidentally drops a public var | `potemkin/import-vars` + test suite coverage catches this; consumers fail at compile time if a var is missing |
| Devcontainer drift between local and CI | Pin `poly` and `clojure` CLI versions explicitly; update devcontainer base image in same PR as Phase 5 |
| First CI run on main is slow (no stable tag) | Expected, one-time cost; documented above |
| Polylith tooling bug blocks migration mid-phase | Keep Leiningen operational through Phase 4; the branch can be abandoned without affecting `main` |
