# aoc-clj
Solutions to [Advent of Code](https://adventofcode.com) challenges 
implemented in Clojure

# Usage

## Prerequisites

This project uses [Leiningen](https://leiningen.org/) for project automation.
Please install it and a suitable Java runtime before proceeding.

## Running
You can execute any day's implemented solutions, either with my puzzle input 
files or you can provide your own.
```
lein run
```

For example, to execute the solution for the
[2022 Day 1](https://adventofcode.com/2022/day/1) puzzle, just execute
```
lein run 2022 1 -v
```
The `-v` is purely optional, but will help print what the values are supposed
to mean in the context of the puzzle narrative.

You can specify your own puzzle input file with the `-i` command-line option.


Full usage:
```
Usage: lein run [options] year day

Arguments
  year -- four digit year for the puzzle
  day  -- value between 1 and 25 representing the day's puzzle

Options:
  -i, --input PATH  Puzzle input file to use instead of default
  -v, --verbose     Verbose printing
  -h, --help        Display usage
```

# License
Copyright 2024 Ken Smith

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.