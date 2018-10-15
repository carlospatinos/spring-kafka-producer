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
          image 'maven:3-alpine'
          args '-v $PWD/.m2:/root/.m2'
        }
      }
      steps {
        echo 'building'
      }
    }
    stage('Testing') {
      steps {
        echo 'Testing'
      }
    }
    stage('Static Analysis') {
      steps {
        echo 'Static analysis'
      }
    }
  }
}