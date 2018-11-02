pipeline {
    agent { label 'centos' }
    stages {
        stage ('Checkout') {
            agent { docker 'gradle:4.5-jdk8-alpine' }
            steps {
                git 'https://github.com/vedhamurthy/jenkins_3_database_migration.git'
            }
        }
        stage('Build') {
            agent { docker 'gradle:4.5-jdk8-alpine' }
            steps {
                sh 'gradle clean build'
                junit 'build/test-results/**/TEST-*.xml'
                archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                stash includes: 'build/libs/*.jar', name: 'package'
            }
        }
        stage('Migration') {
            agent { docker 'gradle:4.5-jdk8-alpine' }
            steps {
                sh 'gradle migrateStaging'
            }
        }
        stage('Deploy') {
            steps {
                unstash 'package'
                sh 'scp build/libs/*.jar user@vedlatha1113.mylabserver.com:/opt/app/jenkins_3_database_migration.jar'
                sh "ssh user@vedlatha1113.mylabserver.com 'sudo systemctl restart jenkins_3_database_migration.service'"
            }
        }
    }
}
