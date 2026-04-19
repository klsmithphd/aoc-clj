# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

This project is a compilation of my personally written solutions to each year's
[Advent of Code](https://adventofcode.com) coding puzzle challenges. My solutions have all been
written in [Clojure](https://clojure.org), a functional Lisp dialect that runs on the JVM.

The Advent of Code puzzles are released each year, starting on December 1st, and running for 25 days
(most recently 12 days). Each puzzle consists of two parts. In order to unlock Part 2, you must
submit a correct answer for Part 1, all via the Advent of Code website.

Over the years, I have developed a number of utilities and helpers, as many of the puzzles rely upon
the same types of structures, such as reading an ASCII representation of a 2D map. 

This repository contains all of my solutions to date, the library code, and an extensive test suite.

This repository pulls in an [inputs repository](https://github.com/klsmithphd/aoc-inputs), which contains
a copy of each of my personalized puzzle inputs for each year and day. When doing local development,
this is a git subtree within the `inputs` directory.

This repository is configured to run in a devcontainer so that no dependencies need end up on my laptop.

## Key constraints

- **Never solve the puzzles for me** Part of the fun of solving the puzzle is to design and implement a
solution myself. I never want assistance in solving the puzzle. I'm only looking for AI assistance in
improving the overall architecture and maintainability of this repository.

- **Test-driven development** I've come to embrace a TDD style for this project, where I write a failing
test first, make the test pass, then refactor. The AoC puzzles often have good sample inputs and outputs,
so the puzzles themselves often facilitate this flow. The implication for Claude is that you should
also make sure that the test suite runs.

- **Stay functional** I wrote these solutions in Clojure because I really enjoy the functional style
of programming. I find it to be clean and elegant. Whatever we work on refactoring together, I want
to preserve a functional style. If we need to drop into a more imperative style for performance reasons,
these should be clearly documented.