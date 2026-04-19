# Grid2D Migration Plan: Adopting Row/Col Indexing

## Decision

We are adopting **Option D** from the design analysis in `grid_protocol_issues.md`:
replace `[x y]` coordinate pairs throughout the grid protocol with `[row col]` pairs.

- `row` increases downward (0 = top row of the input)
- `col` increases rightward (0 = leftmost column)
- This matches standard matrix indexing (NumPy, linear algebra) and is honest about
  what the data structure actually is

Cardinal direction offsets in `[row col]` space:
- `:n [-1 0]` (north = decrease row = move up the screen)
- `:e [0 1]`  (east  = increase col = move right)
- `:s [1 0]`  (south = increase row = move down the screen)
- `:w [0 -1]` (west  = decrease col = move left)

When a puzzle genuinely requires Cartesian geometry, the conversion is explicit at the
call site: `x = col`, `y = (height - 1 - row)`.

## Migration Strategy

Rather than refactoring the existing `Grid2D` protocol and its implementations in place,
we will use the **strangler fig** pattern:

1. Create a new parallel protocol and implementations alongside the existing ones
2. Migrate each consumer one at a time to the new implementation, verifying tests pass
   after every change
3. Once all consumers are migrated, delete the old protocol and implementations

This keeps the codebase in a fully working state at every step and avoids getting
stuck mid-migration if an unexpectedly tricky puzzle solution is encountered.

## Naming

The new protocol will be named `GridRC` (row/col) to avoid conflicts with the existing
`Grid2D` protocol during the migration period. Implementations will be `VecGridRC` and
`MapGridRC`. Once migration is complete and the old code is deleted, these can be renamed
back to `Grid2D`, `VecGrid2D`, and `MapGrid2D` if desired.

---

## Phase 0 — Create the new parallel implementation

Create three new files mirroring the existing structure:

### `src/aoc_clj/utils/grid/core.clj` (new protocol)

Mirror `grid.clj`, replacing all `[x y]` coordinate semantics with `[row col]`:

- `cardinal-offsets`: `{:n [-1 0] :e [0 1] :s [1 0] :w [0 -1]}`
- `extended-cardinal-offsets`: `{:ne [-1 1] :se [1 1] :sw [1 -1] :nw [-1 -1]}`
- `positions`: `(for [row (range h) col (range w)] [row col])` — top-to-bottom, naturally
- `within-grid?`: `(<= 0 row (dec height))` and `(<= 0 col (dec width))`
- `adj-coords-2d`: produce `[row col]` pairs in N E S W order
- `Grid->ascii`: remove the `:down` option entirely — top-to-bottom is always correct
- `mapgrid->vectors`: no y-reversal; iterate row min → max directly
- `interpolated`: `[r1 c1]` / `[r2 c2]` parameter naming
- Fix the `neighbors-2d` bug at the same time (currently uses `select-keys` against a
  Grid2D record, which dispatches on record field keys rather than grid position keys)

### `src/aoc_clj/utils/grid/vecgrid_rc.clj` (new VecGrid implementation)

Mirror `vecgrid.clj` with row/col semantics:

- `value [_ [row col]] (get-in v [row col])` — the `(-> % reverse vec)` hack in
  `neighbors-4`/`neighbors-8` disappears since `[row col]` indexes `v[row][col]` directly
- `ascii->VecGridRC`: drop the `(if down lines (reverse lines))` — lines stay in order;
  no `:down` option
- `area-sum` / `square-area-sum`: rename coordinate parameters from `ul-x`/`ul-y`/`lr-x`/`lr-y`
  to `ul-row`/`ul-col`/`lr-row`/`lr-col`

### `src/aoc_clj/utils/grid/mapgrid_rc.clj` (new MapGrid implementation)

Mirror `mapgrid.clj` with row/col semantics:

- `ascii->MapGridRC`: no y-reversal; coordinates are `[row col]` with row iterating
  `(range height)` top-to-bottom; no `:down` option
- `lists->MapGridRC`: `(for [row (range height) col (range width)] [row col])` — correct
  as-is, just renamed
- `slice`: fix the swapped coord selectors — currently `:col` dispatches to `first` and
  `:row` to `second`, which is wrong for `[row col]` pairs; swap them

Write tests for all three new files before moving to Phase 1.

---

## Phase 1 — Migrate puzzle solutions, Wave 1: already on screen convention

These files use `:down true` or `lists->MapGrid2D` and already store row 0 at the top.
They only need the coordinate pair ordering updated (`[x y]` → `[row col]`), swapping
the pair elements wherever coordinates are constructed or destructured.

Note: `2023/day23.clj` also depends on `maze.clj` and is therefore deferred to Phase 4.

| File | Current grid usage |
|------|--------------------|
| `src/aoc_clj/2018/day10.clj` | `Grid2D->ascii` with `:down true` |
| `src/aoc_clj/2021/day25.clj` | `ascii->MapGrid2D` with `:down true` |
| `src/aoc_clj/2022/day12.clj` | `lists->MapGrid2D` |
| `src/aoc_clj/2023/day11.clj` | `ascii->VecGrid2D` with `:down true` |
| `src/aoc_clj/2023/day16.clj` | `ascii->VecGrid2D` with `:down true` |
| `src/aoc_clj/2024/day15.clj` | `ascii->MapGrid2D` with `:down true` |

---

## Phase 2 — Migrate puzzle solutions, Wave 2: topologically indifferent

Solutions doing pure BFS, DFS, flood-fill, or cellular automata where no absolute
coordinate value carries directional meaning. The constructor change handles the storage
convention automatically; most require only pair-ordering updates where `[x y]` is
explicitly constructed or destructured.

Note: `2019/day15`, `2019/day18`, `2019/day20`, and `2021/day15` depend on `maze.clj`
or `gridgraph.clj` and are therefore deferred to Phase 4.

Work year by year (oldest to newest). Run the full test suite after each file.

Expected files (confirm as migration proceeds):
`2017/day22`, `2018/day13`, `2018/day18`, `2019/day24`, `2020/day03`, `2020/day11`,
`2020/day17`, `2021/day09`, `2021/day20`, `2022/day18`,
`2023/day10`, `2023/day21`, `2024/day04`, `2024/day06`, `2024/day08`, `2024/day10`,
`2024/day12`, `2024/day20`, `2024/day25`

---

## Phase 3 — Migrate puzzle solutions, Wave 3: direction-sensitive cases

These two files have logic that depends on the direction of y and require careful
inspection alongside the coordinate changes.

**`src/aoc_clj/2022/day23.clj`**
`open-dir` hardcodes `(inc y)` / `(dec y)` for `:n` / `:s` neighbors, inconsistent
with `cardinal-offsets`. After migration: `:n` checks `(dec row)`, `:s` checks `(inc row)`.
The diagonal neighbor checks in the same function need corresponding updates.

**`src/aoc_clj/2019/day11.clj`**
The robot movement functions (`turn-left-and-move`, `turn-right-and-move`) use
math-convention y-arithmetic, and `mapgrid->vectors` compensates with y-reversal at
render time. Both must be updated together: movement functions switch to `[row col]`
arithmetic, and the new `mapgrid->vectors` (without reversal) handles the rest.

---

## Phase 4 — Migrate dependent utilities and deferred solutions

Migrate the shared utilities first, then the puzzle solutions that depend on them.
Run the full test suite after each file.

**`src/aoc_clj/utils/maze.clj`**
Uses `grid/adj-coords-2d`, `cardinal-offsets`, and maintains an internal plain Clojure map
with `[x y]` coordinate keys. Update all coordinate pairs to `[row col]` and switch
requires to the new `core.clj` / `mapgrid_rc.clj`.

**`src/aoc_clj/2021/gridgraph.clj`**
Uses `grid/adj-coords-2d` and `[x y]`-keyed map lookups. Update similarly.

**Deferred puzzle solutions** (blocked on the above utilities):

| File | Blocked on |
|------|------------|
| `src/aoc_clj/2016/day24.clj` | `maze.clj` |
| `src/aoc_clj/2019/day15.clj` | `maze.clj` |
| `src/aoc_clj/2019/day18.clj` | `maze.clj` |
| `src/aoc_clj/2019/day20.clj` | `maze.clj` |
| `src/aoc_clj/2021/day15.clj` | `gridgraph.clj` |
| `src/aoc_clj/2023/day23.clj` | `maze.clj` |

---

## Phase 5 — Delete the old implementation

Once all consumers have been migrated and the test suite is fully green:

1. Delete `src/aoc_clj/utils/grid.clj`
2. Delete `src/aoc_clj/utils/grid/vecgrid.clj`
3. Delete `src/aoc_clj/utils/grid/mapgrid.clj`
4. Delete corresponding test files for the old implementations
5. Optionally rename `GridRC` → `Grid2D`, `VecGridRC` → `VecGrid2D`, `MapGridRC` →
   `MapGrid2D`, and the new source files back to their original names

---

## Out of scope for this migration

The following work is related but intentionally deferred to avoid scope creep:

- Migrating "strong candidate" solutions that should use grid utilities but currently
  don't (`2015/day03`, `2016/day17`, `2019/day03`, `2022/day22`) — see
  `grid_protocol_issues.md` for details
- Addressing other protocol design issues identified in `grid_protocol_issues.md`
  (neighbor API consolidation, `pos-seq`/`positions` redundancy, etc.)
