# aoc-clj
![Workflow Status](https://github.com/klsmithphd/aoc-clj/actions/workflows/clojure.yml/badge.svg)

Solutions to [Advent of Code](https://adventofcode.com) challenges 
implemented in Clojure

# About

[Advent of Code](https://adventofcode.com) has been an annual gift to programmers
since 2015. It takes place for 25 days every December. A new coding challenge
is released every day (like an 
[advent calendar](https://en.wikipedia.org/wiki/Advent_calendar)) and consists
of two parts. You don't *necessarily* need to write code to solve the challenge,
but most of the time, you'll want to.

This repository contains my collection of Advent of Code solutions
implemented in [Clojure](https://clojure.org/). Clojure is a dynamic,
functional programming language implemented on top of the Java Virtual
Machine (JVM) as a LISP dialect. I was first introduced to Clojure in 2016
and it has remained my favorite programming language of all time since then.

While this is not a professional project by any means, I've tried over time
to improve the readability, performance, and testability of my solutions
as a means of "sharpening the saw". I've implemented quite a few helper 
namespaces (think "modules" or "packages" in other languages) that help 
solve common patterns of problems that tend to pop up again and again.

# Structure

This project is organized as a [Polylith](https://polylith.gitbook.io/polylith)
workspace, built on `deps.edn` + `tools.build` + `poly`. Utility code and each
year's puzzle solutions live under `components/`; the CLI entry point lives in
`bases/cli`; the deployable is assembled in `projects/aoc-clj`. See
[docs/polylith_migration_plan.md](docs/polylith_migration_plan.md) for the
full architecture.

# Usage

## Prerequisites

This project uses the [Clojure CLI](https://clojure.org/guides/install_clojure)
(`tools.deps`) and [Polylith](https://polylith.gitbook.io/polylith). Please
install a suitable Java runtime and the Clojure CLI before proceeding. This
project is also set up to run within a devcontainer, so if you have Docker
installed locally, you don't need to install anything on your host system.

For my own testing, I have a private repository of each day's puzzle input files.

With a fresh git checkout, `cd` into `inputs` and issue the following commands
to pull down the inputs submodule:

```
git submodule init
git submodule update
```

## Running

You can execute any day's implemented solutions, either with my puzzle input
files or you can provide your own.

```
clojure -M:aoc
```

For example, to execute the solution for the
[2022 Day 1](https://adventofcode.com/2022/day/1) puzzle, just execute

```
clojure -M:aoc 2022 1 -v
```

The `-v` is purely optional, but will help print what the values are supposed
to mean in the context of the puzzle narrative.

You can specify your own puzzle input file with the `-i` command-line option.

Full usage:

```
Usage: program-name [options] year day

Arguments
  year -- four digit year for the puzzle
  day  -- value between 1 and 25 representing the day's puzzle

Options:
  -i, --input PATH  Puzzle input file to use instead of default
  -v, --verbose     Verbose printing
  -h, --help        Display usage
```

## Testing

Run the full test suite (excluding tests tagged `^:slow`):

```
clojure -M:poly test :project with:default
```

To include slow tests, use `with:slow` in place of `with:default`. Polylith
tests only components affected since the `stable-main` tag, so normal
incremental runs complete quickly; the first run after a fresh clone has no
tag and runs everything via the CI fallback.

# License
Copyright &copy; 2025 Ken Smith

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
