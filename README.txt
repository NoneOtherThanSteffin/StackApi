Please change the MySQL and Postgres URL to valid DB Urls in application.yml

Run sh stack_app.sh to run the script which will run the maven build, docker build and then deploy it to local kubernetes.
In the end of the script it will expose a PORT(NodePort) of the Host Machine to interact with the kubernetes service


http://localhost:NODEPORT/api/swagger-ui.html