(ns cryogen.server
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :as route]
            [ring.util.response :refer [redirect]]
            [cryogen-core.watcher :refer [start-watcher!]]
            [cryogen-core.plugins :refer [load-plugins]]
            [cryogen-core.compiler :refer [compile-assets-timed read-config]]))

(defonce plugin-loaded (atom nil))

(defn init []
  (when-not @plugin-loaded
    (load-plugins)
    (reset! plugin-loaded true))
  (compile-assets-timed)
  (let [ignored-files (-> (read-config) :ignored-files)]
    (start-watcher! "resources/templates" ignored-files compile-assets-timed)))

(defn destroy []
  )

(defroutes handler
  (GET "/" [] (redirect (str (:blog-prefix (read-config)) "/index.html")))
  (route/resources "/")
  (route/not-found "Page not found"))
