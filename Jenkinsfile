pipeline {
    agent { dockerfile true }
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Unit Tests') {
            steps {
                  echo "$GIT_BRANCH"
            }
        }
        stage('Deploy to DockerHub') {
            steps {
                echo 'Deploying....'
                    script {
                         docker.withRegistry('https://index.docker.io/v1/','DockerHub') {
                         def damouImage = docker.build("damou/springdemo:FILSDEEEEEEPUTE")
                         damouImage.push()
                    }
            }
        }
    }
}