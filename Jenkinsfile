pipeline {
    agent any
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
            post
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}