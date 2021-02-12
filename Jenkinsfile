pipeline {
    agent any
    tools {
            maven 'Maven 3.6.3'
            jdk 'JDKopen'
        }
        stages {
            stage("Unit Test") {
                steps {
                    echo "run les test"
                     sh 'mvn test'
                }
            }
            stage("DIT BISLEHMA") {
                steps {
                    echo "dededede"
                }
            }
        }
}