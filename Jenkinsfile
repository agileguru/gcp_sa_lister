
pipeline {
  agent {
    kubernetes {
        yamlFile 'podtemplate/jenkinstemplate.yaml'
        idleMinutes 10
    }
  }
  stages {
        stage('Build Artifact') {
            steps {
                container('alacrity') {
                    sh 'gsutil cp gs://devops-353009-configurations/maven/settings.xml /root/.m2/settings.xml'
                    sh './mvnw -s /root/.m2/settings.xml clean install'
                }
            }
        }
        stage('Deploy Artifact') {
           steps {
                container('alacrity') {
                    sh './mvnw -s /root/.m2/settings.xml clean deploy'
                }
            }
        }
    }
}