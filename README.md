# uci-test

This is starting to experiment with the UCI record linkage comparison data.

- contact: fettermania@gmail.com
- TODO:
    - Cache results of reading files in.
    - Figure out missing edge weight strategy (steal from ds-exp/clustering)
    - Start basic machine learning
    
# Usage

(TODO: Put data in github)
- Snag the data set from https://archive.ics.uci.edu/ml/datasets/Record+Linkage+Comparison+Patterns
- Extract all block_1.csv through block_10.csv and move under "resources/uci/"


In REPL, require the namespace:

    (require '[uci-test.core :as uci]
             '[ubergraph.core :as uber])
 
Visualize very small subgraph, nodes 99 and below.  Note, every call to evaluate-uci-data takes a while to read in all files (TODO cache):

    (def mygraph (uci/evaluate-uci-data)) 
    (uber/viz-graph mygraph {:auto-label true})

Get stats on a much bigger subgraph.

    (def mygraph (uci/evaluate-uci-data "uci/block_" 10 10000 true))

- Arguments are: 
    - file prefix under resource directory (defaults to recommended above)
    - file count (counts 1 to 10 by default)
    - max node id to include in subgraph (max of 99999 kills memory)
    - whether to "save-memory?" by ditching edge attributes (features and "prob connected")
       
       

## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
