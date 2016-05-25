(defproject uci-test "0.1.0"
 :dependencies [[org.clojure/clojure "1.8.0"]
                [org.clojure/data.csv "0.1.3"]
                [ubergraph "0.2.1"]
                [org.apache.commons/commons-math3 "3.6.1"]]
 :plugins [[lein-gorilla "0.3.6"]]
 :jvm-opts ["-Xmx4G"]
)
