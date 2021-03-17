def groovy
pipeline {
    agent any
    parameters {
        string(name: 'tagImage', defaultValue: 'latest', description: 'add a specific tag to the image')
        booleanParam(name: 'executeDeploy', defaultValue: true, description: 'build a new image and publish it to DockerHub repository.')
    }
    tools {
        maven 'Maven'
        jdk 'JDK'
    }
    stages {
        stage('Initialize') {
            steps {
                script {
                    groovy = load "script.groovy"
                }
            }
        }
        stage('Clean') {
            steps {
                script {
                    groovy.clean()
                }
            }
        }
        stage('Validate') {
            steps {
                script {
                    groovy.validate()
                }
            }
        }
        stage('Compile') {
            steps {
                script {
                    groovy.compile()
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    groovy.test()
                }
            }
            post {
                failure {
                    echo "send email again"
                    emailext body: 'A Test EMail', recipientProviders: [['mouadsehbaoui@gmail.com']], subject: 'Test', to: 'mouadsehbaoui@gmail.com'
                }
            }
        }
        stage('Deploy') {
            when {
                expression {
                    params.executeDeploy
                }
            }
            steps {
                script {
                    groovy.buildAppDocker()
                }
            }
            post {
                always {
                    emailext body: 'A DEDE EMail', recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Test'
                }
                success {
                    script {
                        groovy.deleteJarFile()
                    }
                }
                failure {
                    script {
                        groovy.errorPostBuildAppDocker()
                    }
                }
            }
        }
    }
}