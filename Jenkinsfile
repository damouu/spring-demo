pipeline {
    agent any
        stages {
            stage('Push to DockerHub') {
                steps {
                    echo 'Deploying....'
                        script {
                            docker.withRegistry('https://index.docker.io/v1/','DockerHub') {
                            def damouImage = docker.build("damou/springdemo:${env.BUILD_ID}","-f ${dockerfile} ./Dockerfile")
                            damouImage.push()
                    }
                }
            }
        }
    }
}