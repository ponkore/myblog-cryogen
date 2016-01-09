(defproject cryogen "0.1.0"
            :description "Simple static site generator"
            :url "https://github.com/lacarmen/cryogen"
            :license {:name "Eclipse Public License"
                      :url "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [[org.clojure/clojure "1.7.0"]
                           [ring/ring-devel "1.4.0"]
                           [compojure "1.4.0"]
                           [ring-server "0.4.0"]
                           [com.darrinholst/sass-java "3.4.20.0" :exclusions [com.github.jnr/jffi com.github.jnr/jnr-x86asm]]
                           [cryogen-asciidoc "0.1.2"]
                           [cryogen-core "0.1.29-SNAPSHOT"]] ;; "0.1.28"
            :plugins [[lein-ring "0.9.6"]]
            :main cryogen.core
            :repl-options {
                :timeout 120000
            }
            :ring {:init cryogen.server/init
                   :destroy cryogen.server/destroy
                   :handler cryogen.server/handler})
