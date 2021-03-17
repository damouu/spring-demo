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
                success {
                    emailext body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                            recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                            subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
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