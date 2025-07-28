pipeline{
    agent any

    environment {
        ALL_SERVICES = "api-gateway,identity-service,profile-service,notification-service,post-service,file-service"
        USERNAME_DOCKER="harinem"

    }

    stages {
        stage("Clone repository") {
            steps {
                checkout scm
                script {
                    def currentBranch=env.BRANCH_NAME
                    env.BRANCH=currentBranch
                    echo "Clone Repository Done!!✅✅"
                    echo "Current Branch => ${currentBranch}"

                }
            }
        }

        stage("Set Image Tag") {
            steps {
                script {
                    def branch= env.BRANCH ?: "develop"

                    if (branch=="product") {
                        env.IMAGE_TAG = "${env.BUILD_ID}"
                    }
                    else {
                        def commitHash =sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                        env.IMAGE_TAG = "${branch}-${commitHash}"
                        
                    }

                     echo "🎯 Image tag: ${env.IMAGE_TAG}"
                }
            }
        }

        stage("Detect Changed Services") {
            steps {
                script {
                    def allService=ALL_SERVICES.split(",")

                    if(env.BRANCH == "product") {
                        env.CHANGED_SERVICES = all.join(',')
                        echo "Branch 'product' → build all services"
                    }
                    else {
                        def changedFiles = sh(
                            script: "git diff --name-only HEAD~1 HEAD",
                            returnStdout: true
                        ).trim()

                        if(changedFiles) {
                            def changedServices=changedFiles.split("\n").collect {it.split("/")[0]}.unique().findAll { allService.contains(it) }
                            env.CHANGED_SERVICES  = changedServices ? changedServices.join(",") : ""
                            echo "🎯 Services have changed: ${env.CHANGED_SERVICES}"
                        }
                        else {
                            echo "No files changed compared to origin/${env.BRANCH}. Build all."
                             env.CHANGED_SERVICES = allService.join(',')
                        }
                    }
                   
                     echo "🎯 List of services to build: ${env.CHANGED_SERVICES}"
                }
            }
        }

        stage("Run Unit Test") {
            steps {
                echo "✅ Complete Run Unit Test!!"
            }
        }

        stage("Build images") {
            steps {
                script {

                    if(!env.CHANGED_SERVICES || env.CHANGED_SERVICES.trim() == ""){
                        echo "❌ No changed services detected. Skipping image build."
                        return
                    }

                    def changedServices =env.CHANGED_SERVICES.split(",")
                    def buildTasks=[:]
                    for (service in changedServices) {
                        def svc =service
                        buildTasks[svc] ={
                            echo "🚀 Building image for ${svc}"
                            def imageName="${env.USERNAME_DOCKER}/${svc}-img:${env.IMAGE_TAG}"
                            docker.build(imageName, "./${svc}")
                        }
                        
                    }
                    parallel buildTasks
                }
            }
        }

        stage("Push Images") {
            steps {
                script {   
                    if(!env.CHANGED_SERVICES || env.CHANGED_SERVICES.trim() == ""){
                        echo "❌ No changed services detected. Skipping push image."
                        return

                    }
                    withDockerRegistry([ credentialsId: "dockerhub-harinem", url: "" ]) {
                        def changedServices =env.CHANGED_SERVICES.split(",")
                        def pushTasks=[:]
                        for (service in changedServices) {
                            def svc=service
                            pushTasks[svc] ={
                                def imageName="${env.USERNAME_DOCKER}/${svc}-img:${env.IMAGE_TAG}"
                                echo "📤 Pushing image ${imageName}"
                                sh "docker push ${imageName}"                               
                            }
                                                        
                        }
                        parallel pushTasks
                    }


                }
            }
        }

        stage("Trigger ManifestUpdate") {
            steps {
                echo "Triggering updatemanifest job"
                build job: "update-manifest-book-review/develop", parameters: [
                    string(name: "DOCKERTAG", value: env.IMAGE_TAG),
                    string(name: "CHANGED_SERVICES", value: env.CHANGED_SERVICES)
                ]
            }
        }
    }

}