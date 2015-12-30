(ns cryogen.core
  (:require [cryogen-core.compiler :refer [compile-assets-timed]]
            [cryogen-core.plugins :refer [load-plugins]]
            [cryogen.server :as srv]
            [ring.adapter.jetty :refer [run-jetty]]))

(defonce server (atom nil))

(defn -main []
  (load-plugins)
  (compile-assets-timed)
  (System/exit 0))

(defn start-server
  [& [port]]
  (when-not @server
    (let [port (if port (Integer/parseInt port) 3000)]
      (srv/init)
      (reset! server (run-jetty srv/handler {:port port :join? false})))))

(defn stop-server []
  (when @server
    (.stop @server)
    (srv/destroy)
    (reset! server nil)))
