pipeline {
  agent any
  stages {
    stage('Stage1') {
      parallel {
        stage('Stage1') {
          steps {
            sh 'echo "Step 1"'
          }
        }
        stage('Parallel') {
          steps {
            echo 'parallelo'
          }
        }
      }
    }
    stage('Stage2') {
      steps {
        echo 'Adios'
        sleep 1
        git(url: 'https://github.com/spring-projects/spring-kafka', branch: 'master', poll: true)
      }
    }
  }
}