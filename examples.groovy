folder("examples")

job('examples/whoami') {
    description('Barebones job, to test if the nomad integration is setup properly, will output the current user.')
    label('mgmt')
    steps {
        shell('whoami')
    }
}

pipelineJob('examples/hello-world') {
    description('This job shows how to use nomad-pack and vault to deploy nomad applications, will deploy a hello world app.')
    definition {
        cps {
            script('''
pipeline {
    agent { label 'mgmt' }
    stages {
        stage('Prepare') { 
            steps { 
                sh 'nomad-pack registry add attachmentgenie github.com/attachmentgenie/nomad-pack-attachmentgenie-registry'
            }
        }
        stage('Will it work in Theory?'){
            steps {
                script {
                    env = "theory"
                    sh "nomad-pack run example --registry=attachmentgenie --var namespace=${env}"
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
                        sh "nomad-pack run example --registry=attachmentgenie --var message='we should not leak secrets, but here are: ${SUPERSECRET}' --var namespace=${env}"
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
