(ns aoc-clj.intcode.interface
  (:require [potemkin :refer [import-vars]]
            [aoc-clj.intcode.core]))

(import-vars
 [aoc-clj.intcode.core
  ;; instruction decoding
  opcodes parameter-mode parse-instruction apply-inst
  ;; program execution
  intcode-exec out-seq last-out
  ;; ASCII I/O helpers
  cmd->ascii cmds->ascii read-ascii-output interactive-asciicode])
