### **TagFinder - A Containerized Web Application**

#### **Overview**
TagFinder is a containerized web application designed to display, upload, and manage images stored in an **Amazon S3 bucket**. The application is built for high availability, automatic scaling, and integrates with **AWS Fargate, ECR, and ECS**.

A **CI/CD pipeline** using **GitHub Actions** automates the build and deployment of the application’s Docker image to **Amazon ECR** whenever new changes are pushed.

---

### **🛠️ Features**
1. **Homepage with an Image Dashboard** – Fetches and displays images stored in S3  
2. **Upload Functionality** – Allows users to upload new images to S3  
3. **Pagination** – Efficiently loads large datasets from the backend  
3. **Automated CI/CD Pipeline** – Uses **GitHub Actions** to build, tag, and push a Docker image to Amazon ECR  

---

### **🖥️ Tech Stack**
- ThymeLeaf - For interface
- SpringBoot
- Amazon S3
- Docker
- GitHub Actions

---

### **🚀 Deployment Workflow**
1. **Code Push to GitHub** → Triggers GitHub Actions
2. **GitHub Actions Workflow**:
    - Build a Docker image
    - Tag it
    - Push the image to **Amazon ECR**
3. **ECS Deployment Pipeline**:
    - The new image is picked up by **AWS CodeDeploy**
    - Performs **Blue/Green Deployment** to ECS
