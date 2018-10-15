pipeline {
  agent any
  stages {
    stage('Stage1') {
      steps {
        sh 'echo "Step 1"'
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
