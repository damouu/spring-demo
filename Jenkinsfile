pipeline {
    agent any
    tools {
            maven 'Maven'
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