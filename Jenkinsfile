pipeline {
  agent any
  parameters {
    string(name: 'tomcat_dev', defaultValue: '18.223.124.227', description: 'servidor de QA')
    string(name: 'tomcat_prod', defaultValue: '18.188.45.204', description: 'servidor de PROD')
  }
  
  triggers {
    pollSCM('* * * * *')
  }

stages{
        stage('Build'){
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                    echo 'Now Archiving...'
                    archiveArtifacts artifacts: '**/target/*.war'
                }
            }
        }
    }
}
