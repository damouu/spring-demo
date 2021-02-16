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
            stage('Push to DockerHub') {
                when { branch 'main' }
                    steps {
                        script {
                            docker.withRegistry('https://index.docker.io/v1/','DockerHub') {
                            def damouImage = docker.build("damou/springdemo:master-${env.BUILD_ID}")
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