pipeline {
    agent any
        stages {
            stage('Push to DockerHub') {
                steps {
                        script {
                            docker.withRegistry('https://index.docker.io/v1/','DockerHub') {
                            def damouImage = docker.build("damou/springdemo:master-${env.BUILD_ID}")
                            damouImage.push()
                    }
                }
            }
            post {
                    success {
                        echo "image published successfully"
                    }
                    failure{
                        echo "error when trying to publishing the image"
                    }
            }
        }
    }
}