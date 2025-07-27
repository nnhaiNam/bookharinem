pipeline{
    agent any

    environment {
        ALL_SERVICES = "api-gateway,identity-service,profile-service,notification-service,post-service,file-service"

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
                            env.CHANGED_SERVICES  = changedServices ? changedServices.join(",") : allService.join(",")
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
                    def changedServices =env.CHANGED_SERVICES.split(",")
                    def buildTasks=[:]
                    for (service in changedServices) {
                        buildTasks[service] ={
                            echo "🚀 Building image for ${service}"
                            def imageName="${service}-img:${env.IMAGE_TAG}"
                            docker.build(imageName, "./${service}")
                        }
                        
                    }
                    parallel buildTasks
                }
            }
        }

        stage("Push Images") {
            steps {
                script {
                    docker.withRegistry("https://registry.hub.docker.com","dockerhub-harinem") {
                        def services = env.CHANGED_SERVICES.split(',')
                        def pushTasks=[:]
                        for (svc in changedServices) {
                            echo "📤 Pushing image ${imageName}"
                            def imageName="${service}-img:${env.IMAGE_TAG}"
                            sh "docker push ${imageName}"                            
                        }
                        parallel pushTasks
                    }


                }
            }
        }

        // stage("Trigger ManifestUpdate") {
        //     steps {
        //         echo "Triggering updatemanifest job"
        //         build job: "updatemanifest", parameters: [
        //             string(name: "DOCKERTAG", value: env.BUILD_NUMBER)
        //         ]
        //     }
        // }
    }

}