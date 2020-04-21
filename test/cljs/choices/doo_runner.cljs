(ns choices.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [choices.core-test]))

(doo-tests 'choices.core-test)

