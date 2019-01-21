**Building Docker Image**
* Build image: `mvn clean install dockerfile:build`
  *  The image name will be `teamkfc/order-service:latest`
  
**Running Image Locally**
* Run image: `docker run -p 8080:8080 --name orders --rm <DOCKER_ID>/order-service`
  * Doing a GET from `http://localhost:8080/orders/123/status` should return *delivered* 

**Pushing Docker Image to Docker Hub**
* Log into Docker Hub: `docker login`
  * Provide your docker id and password
* Push image: `docker push teamkfc/order-service:latest`

**Point your kubectl to kyma**
* Download the kubeconfig file from `https://console.sa-hackathon-07.cluster.extend.sap.cx/home/settings/organisation`
* in your terminal `export KUBECONFIG=~/Downloads/kubeconfig`

**Deploying to kyma**
* Delete the existing deployment: `kubectl delete -f https://raw.githubusercontent.com/kymaccv2hackathon2018/KFC/master/order-service/deployment.yml -n stage`
* Deploy to kyma: `kubectl apply -f https://raw.githubusercontent.com/kymaccv2hackathon2018/KFC/master/order-service/deployment.yml -n stage`
