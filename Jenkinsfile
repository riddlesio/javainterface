node {
    try {

        stage 'Prepare Build Environment'
        checkout scm

        def environment_image = docker.build('javainterface-build-environment')

        // create a persistent docker volume
        def persistent_volume_image_id = sh(script: /docker ps -a --format="{{.ID}} {{.Image}} {{.Names}} {{.Labels}}" | grep persistent_gradle | cut -d ' ' -f 2/, returnStdout: true).trim()
        if (persistent_volume_image_id != environment_image.id) {
            sh("docker rm persistent_gradle || echo 0")
        }
        if (persistent_volume_image_id == '' || persistent_volume_image_id != environment_image.id) {
            sh("docker create -v /.gradle --name persistent_gradle javainterface-build-environment /bin/true")
        }

        environment_image.inside("-v ${env.WORKSPACE}:/riddles --volumes-from persistent_gradle") {
            stage 'Test'
                sh("gradle test")

            stage 'Analyze source'
                sh("gradle sonarqube")

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