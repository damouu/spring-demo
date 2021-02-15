pipeline {
    agent any
        stages {
            stage('JUnit test'){
                steps {
                 junit './src/test/java/com/example/demo/course/CourseServiceTest.java'
                }
            }
            stage('Push to DockerHub') {
                steps {
                        script {
                            docker.withRegistry('https://index.docker.io/v1/','DockerHub') {
                            def damouImage = docker.build("damou/springdemo:${env.BUILD_ID}")
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
