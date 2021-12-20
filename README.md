Für den Start der Umgebung müssen docker, minikube und kubernetes installiert werden. Zusätzlich wurde eine lokale Registry für Docker verwendet 
```bash
docker run -d -p 5000:5000 --restart=always --name registry registry:2
```
1. Alle nötigen Images bauen und in die Registry pushen:
- product-ms
- category-ms
- hska-vis-legacy
- rproxy
    ```bash
    docker build -t localhost:5000/product-ms .
    ```

    ```bash
    docker push localhost:5000/product-ms
    ```
2. Minikube mit Docker starten
    ```bash
    eval $(minikube docker-env)
    ```

     ```bash
    minikube start
    ```
3. Istio einbinden (Wenn es bereits hier gemacht wird, dann müssen die einzelnen Pods nicht neugestartet werden)
     ```bash
    curl -L https://istio.io/downloadIstio | sh -
    ```

     ```bash
    export PATH=$PWD/bin:$PATH
    ```

     ```bash
    istioctl install --set profile=demo -y
    ```

     ```bash
    kubectl label namespace default istio-injection=enabled
    ```

     ```bash
    istioctl analyze
    ```

    ```bash
    kubectl apply -f samples/addons
    ```
    
     ```bash
    istioctl analyzekubectl rollout status deployment/kiali -n istio-system
    ```
 
     ```bash
   istioctl dashboard kiali
    ```
4. Deployments und Services erstellen
    ```bash
    kubectl apply -k ./k8s/
    ```
5. Reverse Proxy verfügbar machen
    ```bash
    minikube service rproxy
    ```