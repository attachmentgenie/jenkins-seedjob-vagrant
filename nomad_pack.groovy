folder("platform")
pipelineJob('platform/hello-world') {
  definition {
    cps {
      script('''

pipeline {
    agent { label 'nomad' }
    environment {
        NOMAD_ADDR = 'http://192.168.1.10:4646'
    }
    stages {
        stage('Prepare') { 
            steps { 
                sh 'nomad-pack registry add attachmentgenie github.com/attachmentgenie/pack-registry'
            }
        }
        stage('Deploy to Theory'){
            input{
                message "Will it work in Theory?"
            }
            steps {
                sh 'nomad-pack run hello_world --registry=attachmentgenie --var namespace=theory --var datacenters=[\\\\"lab\\\\"]'
            }
        }
        stage('Deploy to Reality') {
            input{
                message "Reality Check!"
            }
            steps {
                sh 'nomad-pack run hello_world --registry=attachmentgenie --var namespace=reality --var datacenters=[\\\\"lab\\\\"]'
            }
        }
    }
}
      ''')
      }
    }
}