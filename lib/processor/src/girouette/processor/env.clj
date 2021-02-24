(ns girouette.processor.env)

;; Used for storing the configuration data, accessible
;; from anywhere in the tool, from any thread.
(def config (atom {}))
