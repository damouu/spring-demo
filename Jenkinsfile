pipeline {
    agent any
        stages {
            stage('JUnit test'){
                steps {
                        withMaven(maven: 'Maven') {
                                    sh "mvn test"
                        }
                }
            }
            stage('Push to DockerHub not actually pushing') {
                steps {
                       echo "here it should publish to Docker Hub"
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
