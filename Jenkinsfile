pipeline {
    agent any
        stages {
            stage('JUnit test'){
                steps {
                 sh 'mvn test'
                 withMaven(maven: 'maven-latest'){
                        sh 'mvn test install'
                    }
                }
            }
            stage('Push to DockerHub') {
                steps {
                        script {
                            docker.withRegistry('https://index.docker.io/v1/','DockerHub') {
                            def damouImage = docker.build("damou/springdemo:${env.BUILD_ID}")
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
