# Grid2D Protocol: Design Issues and Alternatives

## Background

Many AoC puzzles use a 2D grid (often represented as ASCII art) as input. A persistent tension
in the codebase is that ASCII art uses **screen coordinates** (y increases downward, y=0 at the
top row), while traditional math/geometry uses **math coordinates** (y increases upward, y=0 at
the bottom). This difference has been handled inconsistently over the years via a `:down` flag
at parse time, and it keeps causing problems.

## Current Design Problems

### 1. Coordinate convention is invisible after construction

The `:down` flag is applied at **parse time** and then silently forgotten. After building a grid,
there is no way to know which convention it uses. This means:

- `ascii->VecGrid2D` (default, `:down false`) reverses the input lines so y=0 is at the bottom
  (math convention). `ascii->MapGrid2D` does the same.
- `lists->MapGrid2D` has **no `:down` option at all** and assigns y=0 to the first row (screen
  convention) — inconsistent with the other constructors' defaults.
- `Grid2D->ascii` has its own `:down` option — the caller must remember and repeat the choice
  they made at construction time.

### 2. `pos-seq`/`positions` iteration order is counterintuitive under the default convention

Under math convention (default), `positions` generates y from 0 upward — which is
**bottom-to-top**. This is the wrong order for displaying or reasoning about a grid visually.
`val-seq` in `VecGrid2D` uses `(flatten v)`, which inherits this confusing ordering.

### 3. `neighbors-4`/`neighbors-8` include out-of-bounds positions (nil values)

The protocol methods return maps with `nil` for positions outside the grid. Callers must filter
nils themselves. There is no clear distinction between "no neighbor in that direction" and
"neighbor exists but has value nil."

### 4. Parallel, overlapping neighbor APIs at different abstraction levels

There are four ways to ask about neighbors, scattered across the same namespace:

- `neighbors-4` / `neighbors-8` (protocol) — returns `{pos → value}`
- `neighbor-data` — returns `[{:pos :val :heading}]` with direction info
- `adj-coords-2d` — returns just coordinates
- `neighbor-pos` / `neighbor-value` — works on raw maps with an explicit direction keyword

No single API covers all use cases, so callers pick whichever looks closest, leading to
inconsistency across solutions.

### 5. `neighbors-2d` is misplaced and broken for Grid2D objects

```clojure
(defn neighbors-2d [grid pos ...]
  (select-keys grid (adj-coords-2d pos ...)))
```

`select-keys` on a `MapGrid2D` record selects from its **Clojure record field keys**
(`:width`, `:height`, `:grid`), not grid position keys. This only works when a plain Clojure map
is passed directly — not a `MapGrid2D`. It is placed in the grid namespace implying it works with
the protocol, but it does not.

### 6. Protocol/helper redundancy

- `pos-seq` (protocol method) delegates immediately to `positions` (standalone function) — both
  are public with no meaningful distinction.
- `in-grid?` (protocol method) delegates immediately to `within-grid?` (standalone function) —
  same issue.

---

## Dependent Solution Files

The following solution files (and supporting utilities) depend on the grid protocol or grid
utility functions and will be affected by any migration.

### Core infrastructure

| File | Role |
|------|------|
| `src/aoc_clj/utils/grid.clj` | Protocol definition, cardinal offsets, navigation helpers, standalone utilities |
| `src/aoc_clj/utils/grid/vecgrid.clj` | `VecGrid2D` implementation (dense vector-of-vectors) |
| `src/aoc_clj/utils/grid/mapgrid.clj` | `MapGrid2D` implementation (sparse coordinate map) |
| `src/aoc_clj/utils/maze.clj` | `Maze` record; bridges grid utilities with graph/pathfinding |

### Test files

| File |
|------|
| `test/aoc_clj/utils/grid_test.clj` |
| `test/aoc_clj/utils/grid/vecgrid_test.clj` |
| `test/aoc_clj/utils/grid/mapgrid_test.clj` |

### Puzzle solutions

| File | Grid usage |
|------|------------|
| `src/aoc_clj/2017/day03.clj` | Spiral position calculation |
| `src/aoc_clj/2017/day22.clj` | Virus simulation on sparse grid |
| `src/aoc_clj/2018/day10.clj` | Rendering moving points to find a message |
| `src/aoc_clj/2018/day13.clj` | `MapGrid2D` for cart collision detection |
| `src/aoc_clj/2018/day18.clj` | Cellular automaton |
| `src/aoc_clj/2019/day11.clj` | `VecGrid2D` for painted hull output |
| `src/aoc_clj/2019/day17.clj` | `MapGrid2D`, `ascii->MapGrid2D`, `neighbors-2d` |
| `src/aoc_clj/2019/day20.clj` | Grid utilities for portal/maze navigation |
| `src/aoc_clj/2019/day24.clj` | Grid utilities for cellular automaton |
| `src/aoc_clj/2020/day03.clj` | `MapGrid2D`, `ascii->MapGrid2D` |
| `src/aoc_clj/2020/day11.clj` | `MapGrid2D`, `ascii->MapGrid2D` for seating simulation |
| `src/aoc_clj/2020/day17.clj` | `MapGrid2D`, `ascii->MapGrid2D` |
| `src/aoc_clj/2021/day09.clj` | Grid utilities for basin finding |
| `src/aoc_clj/2021/day20.clj` | `MapGrid2D`, `ascii->MapGrid2D` |
| `src/aoc_clj/2021/day25.clj` | `MapGrid2D`, `ascii->MapGrid2D` with `:down true` |
| `src/aoc_clj/2021/gridgraph.clj` | Grid-based graph utilities (shared helper) |
| `src/aoc_clj/2022/day12.clj` | `MapGrid2D` via `lists->MapGrid2D`, `neighbors-2d` for pathfinding |
| `src/aoc_clj/2022/day18.clj` | Grid utilities |
| `src/aoc_clj/2022/day23.clj` | `MapGrid2D`, `ascii->MapGrid2D` |
| `src/aoc_clj/2023/day10.clj` | `VecGrid2D`, `ascii->VecGrid2D`, `neighbors-4` |
| `src/aoc_clj/2023/day11.clj` | `VecGrid2D`, `ascii->VecGrid2D` for galaxy mapping |
| `src/aoc_clj/2023/day16.clj` | `ascii->VecGrid2D`, `value`, `width`, `height` |
| `src/aoc_clj/2023/day21.clj` | `neighbors-4`, `width`, `height`, `value` |
| `src/aoc_clj/2023/day23.clj` | `VecGrid2D`, `ascii->VecGrid2D`, `in-grid?` |
| `src/aoc_clj/2024/day04.clj` | `value` for grid lookups |
| `src/aoc_clj/2024/day06.clj` | Grid `value` function |
| `src/aoc_clj/2024/day08.clj` | `MapGrid2D`, grid navigation |
| `src/aoc_clj/2024/day10.clj` | `GridGraph` using `pos-seq`, `value`, `adj-coords-2d`; `ascii->VecGrid2D` |
| `src/aoc_clj/2024/day12.clj` | `adj-coords-2d` for region finding |
| `src/aoc_clj/2024/day15.clj` | `MapGrid2D`, `find-nodes`, `value` for robot movement |
| `src/aoc_clj/2024/day16.clj` | `MoveGraph` record using `pos-seq`, `value`, `forward`, `turn` |
| `src/aoc_clj/2024/day20.clj` | Grid utilities for racetrack shortcuts |
| `src/aoc_clj/2024/day25.clj` | `ascii->VecGrid2D` for lock/key parsing |

---

## Solutions Not Using Grid Utilities (But Should)

The following solution files contain directional or 2D coordinate logic that duplicates
functionality already in the grid namespace, but do not require it.

### Strong candidates — clearly redundant, straightforward to migrate

| File | Redundant logic |
|------|----------------|
| `src/aoc_clj/2015/day03.clj` | Defines its own `dir-map` (`\^ [0 1] \v [0 -1] \> [1 0] \< [-1 0]`) — a character-keyed version of `cardinal-offsets`; already uses `v/vec-add` |
| `src/aoc_clj/2016/day17.clj` | Defines its own `directions` map (`"U" [0 -1] "D" [0 1] ...`) mirroring `cardinal-offsets`; defines its own `in-grid?` duplicating `within-grid?` |
| `src/aoc_clj/2019/day03.clj` | Defines its own direction-to-offset mapping for `\U \D \R \L` wire path characters; manual coordinate sequence generation |
| `src/aoc_clj/2022/day22.clj` | Defines its own `next-pos` using `case` on `:U :R :D :L` — exactly `cardinal-offsets` + `v/vec-add`; manual ASCII grid parsing into a coordinate map that could use `ascii->MapGrid2D` |

### Moderate candidates — partial benefit, some puzzle-specific logic remains

| File | Notes |
|------|-------|
| `src/aoc_clj/2015/day06.clj` | Operates on a dense 1000×1000 grid of lights with bulk rectangular-range updates; could use `VecGrid2D` for storage but the bulk-update pattern doesn't map cleanly to the protocol |
| `src/aoc_clj/2016/day02.clj` | 2D keypad navigation with manual [x y] arithmetic; small enough that benefit is limited but could use `cardinal-offsets` and `within-grid?` |
| `src/aoc_clj/2020/day12.clj` | Defines its own `forward` function duplicating the grid `forward` utility; cardinal direction turns could use `turn`; the Part 2 waypoint rotation is geometry-specific and wouldn't simplify |
| `src/aoc_clj/2021/day13.clj` | Fold operations on a set of [x y] coordinates; could use `MapGrid2D` for storage but the fold transformation logic is puzzle-specific |
| `src/aoc_clj/2022/day08.clj` | Operates on a 2D vector-of-vectors grid; uses `u/transpose` for column operations; could use `VecGrid2D` and `slice`, but the current row-scan approach is clean and the fit is imperfect |
| `src/aoc_clj/2022/day17.clj` | Tetris-like falling simulation with `:left :right :down` movement; manual coordinate arithmetic could use `cardinal-offsets` but bounded-grid collision logic is puzzle-specific |
| `src/aoc_clj/2024/day14.clj` | Robots with [x y] positions and velocities on a wrap-around grid; could use grid for bounds but the modular-arithmetic wrapping is not part of the protocol |

---

## Alternatives

### Option A — Standardize on screen coordinates everywhere (recommended)

Make y=0 = top row, y increases downward. This is how rows in a vector naturally index, and it
matches the natural reading order of every AoC ASCII puzzle input. Update `cardinal-offsets` to:

```clojure
(def cardinal-offsets
  {:n [0 -1]   ; north = up = decreasing y
   :e [1 0]
   :s [0 1]    ; south = down = increasing y
   :w [-1 0]})
```

Eliminate the `:down` flag entirely — constructors always use screen convention. `pos-seq`
iteration becomes top-to-bottom, matching display order. `Grid2D->ascii` no longer needs a
`:down` option.

**Cost:** existing solutions using the default math convention (the majority) need their
`cardinal-offsets` assumptions for `:n`/`:s` reviewed, and any explicit y-comparisons updated.
However, the coordinate direction only affects solutions that navigate cardinally or compare
absolute y values — many solutions are unaffected.

### Option B — Standardize on math coordinates everywhere

Keep y=0 at bottom, fix `lists->MapGrid2D` to also reverse, and document this clearly.
`:n [0 1]` stays intuitive for people with a math background.

**Cost:** reversal at parse time is a permanent overhead; `pos-seq` iteration remains
bottom-to-top, which is unnatural for display. Easy to forget the reversal when adding new
constructors.

### Option C — Encode the convention in the type

Add a `:convention :math` or `:convention :screen` field to the grid records. Operations like
`Grid2D->ascii` read it automatically. Constructors set it explicitly.

**Cost:** more complex; does not eliminate the dual-convention problem, just makes it visible.
Operating in two conventions simultaneously remains a potential source of bugs.

---

## Recommendation

**Option A (screen coordinates)** produces the cleanest outcome: no reversal logic anywhere,
natural top-to-bottom iteration order, consistent constructors with no `:down` flag, and a
simpler `Grid2D->ascii`. The migration cost is real but bounded — the vast majority of solutions
use relative navigation (move north/south/east/west) and the direction of y only matters when
explicitly comparing or displaying absolute coordinates.
