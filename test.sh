sleep 15
if curl 'http://stack:8080/api/swagger-ui.html' | grep -q 'swagger'; then
  echo "Tests passed!"
  exit 0
else
  echo "Tests failed!"
  exit 1
fi