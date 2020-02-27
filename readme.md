. Build image: `mvn package -Pdocker`
. Run container: `mvn install -Pdocker`

docker build -t al/demo

curl -X POST -d '{"location" : {"city" : "c13", "region":"r","country":"c"},"sports":[{"name":"n2","cost": 2, "period": {"start":"2000-02-02", "end":"2000-03-27"}}]}' -H "Content-Type: application/json" http://localhost:8080/
curl -X POST -d '{"sportNames":["n2","n1","n3"], "periodStart":"2000-02-10","periodEnd":"2000-03-15"}' -H "Content-Type: application/json" http://localhost:8080/search
