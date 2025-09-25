# deploy-to-minikube.ps1
Write-Host "=== Despliegue en Minikube ===" -ForegroundColor Green

# 1. Iniciar Minikube
Write-Host "1. Iniciando Minikube..." -ForegroundColor Yellow
minikube start --driver=docker --memory=4096 --cpus=2

# 2. Configurar Docker para usar el daemon de Minikube
Write-Host "2. Configurando Docker environment..." -ForegroundColor Yellow
minikube docker-env | Invoke-Expression

# 3. Compilar la aplicación
Write-Host "3. Compilando aplicación..." -ForegroundColor Yellow
mvn clean package

# 4. Construir imagen Docker
Write-Host "4. Construyendo imagen Docker..." -ForegroundColor Yellow
docker build -t event-driven-app:latest .

# 5. Desplegar en Kubernetes
Write-Host "5. Desplegando en Kubernetes..." -ForegroundColor Yellow

# Aplicar configuraciones
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/rabbitmq-deployment.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml

# 6. Esperar a que los pods estén listos
Write-Host "6. Esperando a que los pods estén ready..." -ForegroundColor Yellow
kubectl wait --for=condition=ready pod -l app=event-driven-app --timeout=180s

# 7. Mostrar información del despliegue
Write-Host "7. Información del despliegue:" -ForegroundColor Green
kubectl get pods,svc,deploy

# 8. Obtener URL de la aplicación
Write-Host "8. URLs de acceso:" -ForegroundColor Cyan
$minikubeIp = minikube ip
Write-Host "🌐 Aplicación: http://$minikubeIp" -ForegroundColor Yellow
Write-Host "📊 RabbitMQ Management: http://$minikubeIp:15672" -ForegroundColor Yellow
Write-Host "📈 H2 Console: http://$minikubeIp/h2-console" -ForegroundColor Yellow

Write-Host "`n✅ Despliegue completado!" -ForegroundColor Green