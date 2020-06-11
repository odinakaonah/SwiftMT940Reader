pipeline {
   agent any

   stages {
      stage('Hello') {
         steps {
            echo 'Hello World'
         }
      }
      stage('Welcome') {
         steps {
            echo "You're welcome to my Jenkins Tutorial"
         }
      }

      stage('PowerShell') {
         steps {
            powershell label: '', script: 'Write-Output "Welcome to Powershell"'
         }
      }
   }

}
