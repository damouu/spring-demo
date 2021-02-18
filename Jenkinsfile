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
            stage('DockerHub Main') {
                when { branch 'main' }
                    steps {
                       sh 'mvn install'
                       sh "cp /var/lib/jenkins/.m2/repository/com/example/demo/0.0.1-SNAPSHOT/demo-0.0.1-SNAPSHOT.jar /home/mouad/IdeaProjects/spring-demo/target/"
                        script {
                            docker.withRegistry('https://index.docker.io/v1/','DockerHub') {
                            def damouImage = docker.build("damou/springdemo:${env.BUILD_ID}").push()
                                }
                            }
                        }
                        post {
                                success {
                                    sh "rm -rf /home/mouad/IdeaProjects/spring-demo/target/demo-0.0.1-SNAPSHOT.jar"
                                    sh "rm -rf /var/lib/jenkins/.m2/repository/com/example/demo/0.0.1-SNAPSHOT/"
                                }
                                failure{
                                    echo "error when trying to publishing the image"
                    }
                }
            }
            stage('DockerHub RESTEasy') {
                when { branch 'RESTeasy' }
                    steps {
                        sh 'mvn install'
                        sh "cp /var/lib/jenkins/.m2/repository/com/example/demo/0.0.1-SNAPSHOT/demo-0.0.1-SNAPSHOT.jar /home/mouad/IdeaProjects/spring-demo/target/"
                            script {
                                docker.withRegistry('https://index.docker.io/v1/','DockerHub') {
                                def damouImage = docker.build("damou/springdemo-resteasy:${env.BUILD_ID}").push()
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
