./mvnw clean install
docker build . -t stack_app
kubectl delete deployment stack-api
kubectl apply -f deployment.yml
kubectl delete service stack-api
kubectl expose deployment/stack-api --type="NodePort" --port 8080
kubectl describe service stack-api