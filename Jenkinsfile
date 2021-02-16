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
            stage('Move tar locale') {
                            steps {
                                sh "cp /var/lib/jenkins/.m2/repository/com/example/demo/0.0.1-SNAPSHOT/demo-0.0.1-SNAPSHOT.jar /home/mouad/IdeaProjects/spring-demo/target"
                           }
                        }
            stage('Push to DockerHub') {
                when { branch 'main' }
                    steps {
                        sh 'mvn install'
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