(ns datomic.sim.validations
  (use datomic.api))

(defn missing-multimethod-dispatch
  "Assuming values of attribute attr are keywords driving a multimethod mfn,
   return a collection of attr values that cannot be dispatched by mfn."
  [db attr mfn]
  (let [vs (->> (q '[:find ?kw
                     :in $ ?attr
                     :where
                     [_ ?attr ?v]
                     [?v :db/ident ?kw]]
                  db attr)
                (map first)
                (into #{}))]
    (remove #(get-method mfn %) vs)))

