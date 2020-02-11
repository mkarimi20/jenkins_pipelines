pipeline {
  agent any
  stages {
    stage('stage1') {
      parallel {
        stage('stage1') {
          steps {
            echo 'hello'
          }
        }

        stage('test') {
          steps {
            sh 'echo hello'
          }
        }

      }
    }

    stage('stage2') {
      steps {
        echo 'stage2'
      }
    }

    stage('stage3') {
      steps {
        echo 'step3'
      }
    }

  }
}