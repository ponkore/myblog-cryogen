(ns cryogen.server
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :as route]
            [ring.util.response :refer [redirect]]
            [ring.adapter.jetty :refer [run-jetty]]
            [cryogen-core.watcher :refer [start-watcher!]]
            [cryogen-core.plugins :refer [load-plugins]]
            [cryogen-core.compiler :refer [compile-assets-timed read-config]]))

(defn init []
  (load-plugins)
  (compile-assets-timed)
  (let [ignored-files (-> (read-config) :ignored-files)]
    (start-watcher! "resources/templates" ignored-files compile-assets-timed)))

(defn destroy []
  )

(defonce server (atom nil))

(defroutes handler
  (GET "/" [] (redirect (str (:blog-prefix (read-config)) "/index.html")))
  (route/resources "/")
  (route/not-found "Page not found"))

(defn start-server
  []
  (init)
  (when-not @server
    (reset! server (run-jetty handler {:port 3000 :join? false}))))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))
