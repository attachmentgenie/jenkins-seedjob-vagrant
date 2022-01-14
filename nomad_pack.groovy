folder("platform")
pipelineJob('platform/hello-world') {
    definition {
        cps {
            script('''
pipeline {
    agent { label 'nomad' }
    environment {
        NOMAD_ADDR = 'http://nomad.service.consul:4646'
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
                script {
                    env = "theory"
                    sh "nomad-pack run hello_world --registry=attachmentgenie --var namespace=${env} --var consul_service_name=${env}"
                }
            }
        }
        stage('Deploy to Reality') {
            input{
                message "Reality Check!"
            }
            steps {
                withCredentials([vaultString(credentialsId: 'supersecret', variable: 'SUPERSECRET')]) {
                    script {
                        env = "reality"
                        sh "nomad-pack run hello_world --registry=attachmentgenie --var message=${SUPERSECRET} --var namespace=${env} --var consul_service_name=${env}"
                    }
                }
            }
        }
    }
}
        ''')
        }
    }
}