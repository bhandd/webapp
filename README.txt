# bhand.se Backend Infrastructure & CI/CD

This repository hosts the Spring Boot backend for bhand.se. It features a fully automated DevSecOps pipeline, from local development to a secured production environment.

## 🖥️ The Website
The first page (Home) of the web application.
![Homepage](webapp/index.png)

## 🏗️ System Architecture
The following diagram illustrates the high-level architecture, including traffic routing through Cloudflare and Nginx, and the containerized runtime environment.

![System Architecture](system%20architecture.png)

### Security & Hardening
* **Infrastructure:** Hosted on a Linux Home Server with **SSH Hardening** (Password login disabled, Public Key Authentication only).
* **Traffic Routing:** External traffic is proxied through **Cloudflare** for IP masking and SSL, then managed by a local **Nginx Service** as a Reverse Proxy.
* **Network Verification:** The server is configured to listen only on necessary ports.

![Network Listening Status](Documentation/ubuntu-server/netstatListen.png)

---

## 🚀 CI/CD Pipeline
The deployment process is fully automated using GitHub Actions and Watchtower.

![CI/CD Pipeline Overview](webapp/CICD-github%20Actions/CICD-pipeline%20.drawio.png)

### Documentation Structure
Overview of the project's documentation and asset organization.
![File Structure](webapp/filestructure.png)

### Step 1: Development & Git Push
Code changes are pushed to the main branch, triggering the automated workflow.
![Git Push Console](webapp/CICD-github%20Actions/git%20pushMarked.png)

### Step 2: GitHub Actions Workflow
The `ci.yml` workflow automates the build and security checks.
![CI Workflow Config](webapp/CICD-github%20Actions/ci.yml.png)
![Workflow History](webapp/CICD-github%20Actions/github%20actions%20updateMarked.png)

### Step 3: Docker Hub Registry
Successfully built images are stored in a Docker Hub repository.
![Docker Hub Registry](webapp/CICD-github%20Actions/DockerHub1.png)
![Docker Update Evidence](webapp/CICD-github%20Actions/Docker%20update.png)

### Step 4: Automated Production Deployment
**Watchtower** polls the registry and restarts the container when a new image is detected.
![Watchtower Logs](webapp/CICD-github%20Actions/watchtower%20updateMarked.png)

---

## 📊 Monitoring & Observability
Real-time monitoring of application health and server resources.

### Monitoring Configuration
* **Docker Compose:** orchestration of the monitoring stack.
* **Prometheus:** configured to scrape metrics from the Spring Boot Actuator.

![Docker Compose Monitoring](Documentation/monitoring/docker-compose-yaml.png)
![Prometheus Config](Documentation/monitoring/prometheus/prometheus-yml.png)

### Visualization
Custom dashboards provide insights into container status and performance.
![Grafana Dashboard](Documentation/monitoring/grafana/grafana.png)

### Server Resource Usage
![Docker PS Status](Documentation/ubuntu-server/docker_ps.png)
![Docker Stats](Documentation/ubuntu-server/docker_stats.png)

---

## 🛠 Run the container

1. build:
docker build -t my-webpage .
2. run:

docker run -d --rm --name my-webpage -p 8080:8080 -e ZOHO_MAIL_PASSWORD="ZOHO_MAIL_PASSWORD" my-webpage

3. stop and delete container
ctrl+c

the ZOHO_MAIL_PASSWORD is located in your zoho account settings under app passwords
