pipeline {
    agent any
        stages {
            stage('Run on q/a branch'){
                when { branch 'q/a' }
                steps {
                    echo 'Hi from q/a branch !'
                }
            }
            stage('JUnit test'){
                steps {
                        withMaven(maven: 'Maven') {
                                    sh "mvn test"
                        }
                }
                post {
                       success {
                                 echo "the test passed successfully ;)"
                                }
                       failure {
                                 echo "something went wrong :("
                                }
                     }
            }
            stage('Push to DockerHub not actually pushing to the repository') {
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
