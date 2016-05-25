(ns uci-test.core
  (:require [ubergraph.core :as uber :refer :all]
            [ubergraph.alg :refer :all]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

; Modified from from mike fikes's ds-exp/clustering project.
(defn load-graph-uci-file [file skip-header? skip max-size max-id save-memory?]
  (let [numeric_id_to_id (fn [numeric_id]  (keyword (str "n" numeric_id)))
        filecontents
          (take (or max-size Long/MAX_VALUE)
              (drop (or skip 0)
                  ((if skip-header?
                   rest
                   identity)
                   (csv/read-csv (io/reader (io/resource file))))))]
      (for [[id_1 id_2 cmp_fname_c1 cmp_fname_c2 cmp_lname_c1 cmp_lname_c2 cmp_sex cmp_bd cmp_bm cmp_by cmp_plz is_match] filecontents
          :let [num_1 (Integer/parseInt id_1)
                num_2 (Integer/parseInt id_2)
                nid_1 (numeric_id_to_id id_1)
                nid_2 (numeric_id_to_id id_2)
                r [[nid_1 {:l id_1 }]
                    [nid_2 {:l id_2 }]
                    ]]
            :when (and max-id (<= num_1 max-id) (<= num_2 max-id))
            ]
        (if save-memory?
          (conj r [nid_1 nid_2])
          (conj r [nid_1 nid_2 {:f [cmp_fname_c1 cmp_fname_c2 cmp_lname_c1 cmp_lname_c2 cmp_sex cmp_bd cmp_bm cmp_by cmp_plz]
                     :p (#(if (= "TRUE" %) 1.0 0.0) is_match)}])
          ))))


(defn load-graph-uci
  "Fettermania"
  ([file_prefix count skip-header? skip max-size max-id save-memory?]
   (apply graph
     (apply concat
          (apply concat
            (map  #(load-graph-uci-file (str file_prefix % ".csv") skip-header? skip max-size max-id save-memory?)
                          (range 1 (inc count))))))))

(defn evaluate-uci-data
  "Return graph loaded from UCI data set, and prints statistics on it."
  ([]
   (evaluate-uci-data "uci/block_" 10 99 false))
  ([path_prefix file_count max-id save-memory?]
   (let [uci-subgraph (load-graph-uci path_prefix file_count true 0 nil max-id save-memory?)
         components (ubergraph.alg/connected-components uci-subgraph)]
     (println "Max node id allowed: " max-id)
     (println "Total nodes: " (count (uber/nodes uci-subgraph)))
     (println "Total edges: " (/ (count (uber/edges uci-subgraph)) 2))
     (println "Total connected components: " (count components))
     uci-subgraph
     )
    )
  )


