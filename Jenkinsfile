def workspace;
node {
  stage ('Checkout') {
    checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/carlospatinos/spring-kafka-producer.git']]])
    workspace = pwd()
  }
  stage('Test') {
    echo 'Tests!!!'
    sh 'mvn clean'
  }
}