pipeline {
    agent any
        stages {
            stage('Clean') {
                steps {
                        withMaven(maven: 'Maven') {
                                    sh "mvn clean"
                        }
                }
            }
            stage('Validate') {
                steps {
                        withMaven(maven: 'Maven') {
                                         sh "mvn validate"
                        }
                }
            }
            stage('Test') {
                steps {
                    withMaven(maven: 'Maven') {
                    sh "mvn test"
                    }
                }
            }
            stage('Install') {
                steps {
                    withMaven(maven: 'Maven') {
                    sh "mvn install"
                  }
               }
            }
            stage('Push to DockerHub') {
                when { branch 'main' }
                    steps {
                        sh 'mvn install'
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