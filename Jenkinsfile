node {
    try {

        stage 'Prepare Build Environment'
        checkout scm

        def environment_image = docker.build('javainterface-build-environment')

        environment_image.inside("-v ${env.WORKSPACE}:/riddles") {
            stage 'Test'
                sh("gradle test")

            stage 'Analyze source'
                sh("gradle sonarqube -Dsonar.host.url=http://35.187.10.52:9000")

            stage 'Build'
                sh("gradle build")
        }

        step([$class: 'XUnitBuilder',
              thresholds: [[$class: 'FailedThreshold', unstableThreshold: '1']],
              tools: [[$class: 'JUnitType', pattern: "**/build/test-results/*.xml"]]])

    } catch (e) {
        currentBuild.result = "FAILED"
        notifyFailed()
        throw e
    }
}

def notifyFailed() {
    emailext (
            subject: "[BUILD_FAILED][${env.JOB_NAME}][${env.BUILD_NUMBER}]",
            mimeType: "text/html",
            body: """<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
      <p>Check console output at <a href='${env.BUILD_URL}console'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
            recipientProviders: [[$class: 'DevelopersRecipientProvider']]
    )
}
// vim: set syntax=groovy cindent ts=2 sts=2 sw=2: