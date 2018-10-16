pipeline {
  agent any
  stages {
    stage('Clone Repo') {
      steps {
        echo 'Cloning repo from github'
        git(url: 'https://github.com/spring-projects/spring-kafka', branch: 'master', poll: true)
        sleep 1
      }
    }
    stage('Build Project') {
      agent {
        docker {
          image 'gradle:latest'
          args '-v $PWD:/home/gradle/project -w /home/gradle/project'
        }
      }
      steps {
        echo 'building $PWD'
        sh 'pwd'
        sh 'ls /home/gradle/project'
        sh 'gradle build'
      }
    }
    stage('Testing') {
      steps {
        echo 'Testing'
      }
    }
    stage('Static Analysis') {
            agent {
              docker {
                image 'gradle:latest'
                args '-v $PWD:/home/gradle/project -w /home/gradle/project'
              }
            }
            steps {
              echo 'building'
              sh 'gradle pmdMain'
            }
    }
  }
}