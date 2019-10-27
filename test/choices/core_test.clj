(ns choices.core-test
  (:require [clojure.test :refer :all]
            [choices.core :refer :all]))

(deftest test-person-to-scores
  (testing "person-to-scores"
    (is (=
         {"c1" 1.0
          "c2" 1.0}
         (person-to-scores ["name" 1.0 ["c1" "c2"]])))
    (is (=
         {"c1" 0.5
          "c2" 0.5}
         (person-to-scores ["name" 0.5 ["c1" "c2"]])))))

(deftest test-tally
  (testing "tally"
    (is (=
         {"c1" 1.5, "c2" 1.0, "c3" 0.5}
         (tally [{"c1" 1.0, "c2" 1.0} {"c1" 0.5, "c3" 0.5}])))))

(deftest test-rank-choices
  (testing "rank-choices"
    (is (=
         ["c1" "c2" "c3"]
         (rank-choices {"c2" 1.0, "c3" 0.5, "c1" 1.5})))))

(deftest test-choices-to-people
  (testing "choices to people"
    (is (=
         {"c1" ["p1"], "c2" ["p1" "p2"], "c3" ["p2"]}
         (choices-to-people
          [["p1" 1.0 ["c1" "c2"]]
           ["p2" 1.5 ["c2" "c3"]]])))))

(deftest test-distribute-keys
  (testing "distribute a list of keys into a map"
    (is (=
          [{"c1" ["p1"]} {"c2" ["p1"]} {"c1" ["p2"]}]
          (distribute-keys [["p1" ["c1" "c2"]] ["p2" ["c1"]]])))))

(deftest test-integrated
  (testing "person to ranked choices"
    (is (=
         ["c2" "c3" "c1"]
         (->> [["p1" 1.0 ["c1" "c2"]]
               ["p2" 1.5 ["c2" "c3"]]]
              (map person-to-scores)
              tally
              rank-choices)))))
