# Grid2D Protocol: Design Issues

## Background

Many AoC puzzles use a 2D grid (often represented as ASCII art) as input. Over the years the
codebase accumulated several design problems in the grid protocol and its implementations. This
document tracks those issues and their status.

The largest issue — coordinate convention — has been resolved. The full plan and migration history
are in `grid_rowcol_migration.md`. In brief: the codebase now uses `[row col]` indexing uniformly,
with row 0 at the top and row increasing downward. The old `[x y]` pairs and the `:down` flag have
been eliminated. See that document for details on what changed and why.

---

## Resolved Issues

### ~~1. Coordinate convention is invisible after construction~~ ✓ resolved

The old `:down` flag was applied at parse time and then silently forgotten. `ascii->VecGrid2D` and
`ascii->MapGrid2D` reversed input lines by default (math convention), while `lists->MapGrid2D`
assigned row 0 to the first row (screen convention) — inconsistent. `Grid2D->ascii` had its own
`:down` option that the caller had to repeat.

**Resolution:** Migrated to `[row col]` indexing with row 0 always at the top. The `:down` flag
was eliminated entirely from all constructors and `Grid2D->ascii`.

### ~~2. `pos-seq`/`positions` iteration order counterintuitive under the default convention~~ ✓ resolved

Under the old math convention, `positions` generated y from 0 upward — bottom-to-top. `val-seq`
in `VecGrid2D` used `(flatten v)` and inherited the same confusing order.

**Resolution:** Resolved by the same migration. `positions` now iterates top-to-bottom, matching
reading order and display order.

### ~~5. `neighbors-2d` misplaced and broken for Grid2D objects~~ ✓ resolved

The old implementation used `select-keys` directly on a `MapGrid2D` record, which dispatches on
record field keys (`:width`, `:height`, `:grid-map`) rather than grid positions. It only worked
when a plain Clojure map was passed.

**Resolution:** Fixed during the migration. `neighbors-2d` now uses
`(zipmap locs (map (partial value grid) locs))`, which correctly calls the `value` protocol method
and works with any `Grid2D` implementation.

---

## Open Issues

### 3. `neighbors-4`/`neighbors-8` include out-of-bounds positions (nil values)

Both `MapGrid2D` and `VecGrid2D` implement these protocol methods by calling `get`/`get-in` over
the raw storage, which returns `nil` for positions outside the grid. The result is a map containing
entries like `{[-1 3] nil}`. Callers must filter nils themselves, and there is no way to
distinguish "no neighbor in that direction" (out of bounds) from "neighbor exists but has value
nil."

### 4. Parallel, overlapping neighbor APIs at different abstraction levels

There are now five ways to ask about neighbors, scattered across the same namespace:

- `neighbors-4` / `neighbors-8` (protocol) — returns `{pos → value}`, includes out-of-bounds nils
- `neighbors-2d` (standalone) — same shape, but calls `value` protocol correctly; not on protocol
- `neighbor-data` (standalone) — returns `[{:pos :val :heading}]` with direction info
- `adj-coords-2d` (standalone) — returns just coordinates
- `neighbor-pos` / `neighbor-value` (standalone) — works on raw maps with an explicit direction

No single API covers all use cases, so callers pick whichever looks closest, leading to
inconsistency across solutions. Issues 3 and 4 are best addressed together: a clean resolution
would consolidate toward a small number of well-defined functions that filter out-of-bounds results
by default.

### 6. Protocol/helper redundancy

- `pos-seq` (protocol method) delegates immediately to `positions` (standalone function) — both
  are public with no meaningful distinction.
- `in-grid?` (protocol method) delegates immediately to `within-grid?` (standalone function) —
  same issue.

---

## Solutions Not Using Grid Utilities (But Should)

The following solution files contain directional or 2D coordinate logic that duplicates
functionality already in the grid namespace.

### Strong candidates — clearly redundant, straightforward to migrate

| File | Redundant logic |
|------|----------------|
| `src/aoc_clj/2015/day03.clj` | Defines its own `dir-map` (`\^ [-1 0] \v [1 0] \> [0 1] \< [0 -1]`) — a character-keyed version of `cardinal-offsets`; already uses `v/vec-add` |
| `src/aoc_clj/2016/day17.clj` | Defines its own `directions` map mirroring `cardinal-offsets`; defines its own `in-grid?` duplicating `within-grid?` |
| `src/aoc_clj/2019/day03.clj` | Defines its own direction-to-offset mapping for `\U \D \R \L` wire path characters; manual coordinate sequence generation |
| `src/aoc_clj/2022/day22.clj` | Defines its own `next-pos` using `case` on `:U :R :D :L` — exactly `cardinal-offsets` + `v/vec-add`; manual ASCII grid parsing that could use `ascii->MapGrid2D` |

### Moderate candidates — partial benefit, some puzzle-specific logic remains

| File | Notes |
|------|-------|
| `src/aoc_clj/2015/day06.clj` | Dense 1000×1000 grid with bulk rectangular-range updates; could use `VecGrid2D` for storage but bulk-update pattern doesn't map cleanly to the protocol |
| `src/aoc_clj/2016/day02.clj` | 2D keypad navigation with manual coordinate arithmetic; small enough that benefit is limited but could use `cardinal-offsets` and `within-grid?` |
| `src/aoc_clj/2020/day12.clj` | Defines its own `forward` duplicating the grid utility; cardinal turns could use `turn`; Part 2 waypoint rotation is geometry-specific |
| `src/aoc_clj/2021/day13.clj` | Fold operations on a set of `[row col]` coordinates; could use `MapGrid2D` for storage but fold transformation logic is puzzle-specific |
| `src/aoc_clj/2022/day08.clj` | Operates on a 2D vector-of-vectors grid; could use `VecGrid2D` and `slice`, but the current row-scan approach is clean and the fit is imperfect |
| `src/aoc_clj/2022/day17.clj` | Tetris-like falling simulation; manual coordinate arithmetic could use `cardinal-offsets` but bounded-grid collision logic is puzzle-specific |
| `src/aoc_clj/2024/day14.clj` | Robots with `[row col]` positions on a wrap-around grid; could use grid for bounds but modular-arithmetic wrapping is not part of the protocol |
