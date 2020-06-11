pipeline {
   agent any

   stages {
      stage('Hello') {
         steps {
            echo 'Hello World from Jenkinsfile'
         }
      }
      stage('Welcome') {
         steps {
            echo "You're welcome to my Jenkins Tutorial from jenkinsfile on github"
         }
      }

      stage('PowerShell') {
         steps {
            powershell label: '', script: 'Write-Output "Welcome to Powershell from jenkinsfile fro github"'
         }
      }
   }

}
