folder("platform")
pipelineJob('platform/hello-world') {
  definition {
    cps {
      script('''
pipeline {
    agent { label 'nomad' }

    stages {
        stage('Build') { 
            steps { 
                sh 'whoami' 
            }
        }
        stage('Deploy to Theory'){
            input{
                message "Will it work in Theory?"
            }
            steps {
                sh 'nomad-pack -version'
            }
        }
        stage('Deploy to Reality') {
            input{
                message "Reality Check!"
            }
            steps {
                sh 'nomad-pack registry list'
            }
        }
    }
}
      ''')
      }
    }
}