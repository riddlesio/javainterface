node {
    try {

        stage 'Prepare Build Environment'
        checkout scm

        def url
        def sonarBasicAuth
        def sonarServerUrl
        def environment_image = docker.build('javainterface-build-environment')

        environment_image.inside("-v ${env.WORKSPACE}:/riddles") {
            stage 'Test'
                sh("gradle test")

            stage 'Analyze source'
            withSonarQubeEnv('SonarQube') {
                sh("gradle sonarqube -i -Dsonar.branch=${env.BRANCH_NAME} -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_AUTH_TOKEN")
                def props = getProperties("target/sonar/report-task.txt")
                sonarServerUrl=props.getProperty('serverUrl')
                url = new URL(props.getProperty('ceTaskUrl'))
                sonarBasicAuth  = SONAR_AUTH_TOKEN + ":"
                sonarBasicAuth = sonarBasicAuth.getBytes().encodeBase64().toString()
            }

            stage("Quality Gate") {
                def ceTask
                timeout(time: 1, unit: 'MINUTES') {
                    waitUntil {
                        ceTask = jsonParse(url, sonarBasicAuth)
                        echo ceTask.toString()
                        return "SUCCESS".equals(ceTask["task"]["status"])
                    }
                }
                url = new URL(sonarServerUrl + "/api/qualitygates/project_status?analysisId=" + ceTask["task"]["analysisId"])
                def qualitygate = jsonParse(url, sonarBasicAuth)
                echo qualitygate.toString()
                if ("ERROR".equals(qualitygate["projectStatus"]["status"])) {
                    error "Quality Gate Failure"
                }
                echo "Quality Gate Success"
            }

            stage 'Build'
                sh("gradle build")
        }

        step([$class: 'XUnitBuilder',
              thresholds: [[$class: 'FailedThreshold', unstableThreshold: '1']],
              tools: [[$class: 'JUnitType', pattern: "**/build/test-results/*.xml"]]])

        slackSend (color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL}) for branch ${env.BRANCH_NAME}")

    } catch (e) {
        currentBuild.result = "FAILED"
        notifyFailed()
        throw e
    }
}

def notifyFailed() {
    slackSend (color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL}) for branch ${env.BRANCH_NAME}")
    emailext (
            subject: "[BUILD_FAILED][${env.JOB_NAME}][${env.BUILD_NUMBER}]",
            mimeType: "text/html",
            body: """<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
      <p>Check console output at <a href='${env.BUILD_URL}console'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
            recipientProviders: [[$class: 'DevelopersRecipientProvider']]
    )
}

// https://gist.github.com/vdupain/832964527b4b8d7d4c648169dae8c656

def Properties getProperties(filename) {
    def properties = new Properties()
    properties.load(new StringReader(readFile(filename)))
    return properties
}


@NonCPS
def jsonParse(text) {
    return new groovy.json.JsonSlurperClassic().parseText(text);
}

@NonCPS
def jsonParse(URL url, String basicAuth) {
    def conn = url.openConnection()
    conn.setRequestProperty( "Authorization", "Basic " + basicAuth )
    InputStream is = conn.getInputStream();
    def json = new groovy.json.JsonSlurperClassic().parseText(is.text);
    conn.disconnect();
    return json
}

// vim: set syntax=groovy cindent ts=2 sts=2 sw=2: