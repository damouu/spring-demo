def clean() {
    sh "mvn clean"
}

def validate() {
    sh "mvn validate"
}

def compile() {
    sh "mvn compile"
}

def test() {
    sh "mvn test"
}

def buildAppDocker() {
    sh 'mvn install'
    sh "cp /var/lib/jenkins/.m2/repository/com/example/demo/0.0.1-SNAPSHOT/demo-0.0.1-SNAPSHOT.jar /home/mouad/IdeaProjects/spring-demo/target/"
    docker.withRegistry('https://index.docker.io/v1/', 'DockerHub') {
        def damouImage = docker.build("damou/springdemo").push(params.tagImage)
    }
    sh 'docker image prune -f'
}

def deleteJarFile() {
    sh "rm -rf /home/mouad/IdeaProjects/spring-demo/target/demo-0.0.1-SNAPSHOT.jar"
    sh "rm -rf /var/lib/jenkins/.m2/repository/com/example/demo/0.0.1-SNAPSHOT/"
}

def errorPostBuildAppDocker() {
    echo "error when trying to publishing the image"
}

return this