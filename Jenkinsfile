pipeline {
    agent any
    parameters {
        string(name: 'VERSION', defaultValue: '', description: 'version to deploy on prod')
        choice(name: 'VERSION', choices: ['1.0', '1.1', '1.2'], description: '')
        booleanParam(name: 'executeDeploy', defaultValue: true, description: 'build a new image and publish it to DockerHub repository.')
    }
    tools {
        maven 'Maven'
        jdk 'JDK'
    }
    stages {
        stage('Clean') {
            steps {
                sh "mvn clean"
            }
        }
        stage('Validate') {
            steps {
                sh "mvn validate"
            }
        }
        stage('Test') {
            steps {
                sh "mvn test"
            }
        }
        stage('Install') {
            steps {
                sh "mvn install"
            }
        }
        stage('DockerHub RESTEasy') {
            when {
                expression {
                    params.executeDeploy
                }
            }
            steps {
                sh 'mvn install'
                sh "cp /var/lib/jenkins/.m2/repository/com/example/demo/0.0.1-SNAPSHOT/demo-0.0.1-SNAPSHOT.jar /home/mouad/IdeaProjects/spring-demo/target/"
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'DockerHub') {
                        def damouImage = docker.build("damou/springdemo").push("latest")
                    }
                }
            }
            post {
                success {
                    sh "rm -rf /home/mouad/IdeaProjects/spring-demo/target/demo-0.0.1-SNAPSHOT.jar"
                    sh "rm -rf /var/lib/jenkins/.m2/repository/com/example/demo/0.0.1-SNAPSHOT/"
                }
                failure {
                    echo "error when trying to publishing the image"
                }
            }
        }
    }
}