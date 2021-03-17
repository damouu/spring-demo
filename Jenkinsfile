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
                always {
                    echo "Send Email"
                    emailext body: 'A Test EMail', recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Test'
                }
                failure {
                    emailext body: 'Check console output at $BUILD_URL to view the results. \n\n ${CHANGES} \n\n -------------------------------------------------- \n${BUILD_LOG, maxLines=100, escapeHtml=false}',
                            to: "${EMAIL_TO}",
                            subject: 'Build failed in Jenkins: $PROJECT_NAME - #$BUILD_NUMBER'
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