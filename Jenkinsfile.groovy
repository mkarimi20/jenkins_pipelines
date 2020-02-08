node {
    properties([parameters([choice(choices: ['us-east-1', 'us-east-2', 'us-west-1', 'us-west-2'], description: 'Please select region', name: 'REGION_AMI')])])
    stage("pull repo"){
        git 'https://github.com/farrukh90/packer'

    }
     stage("build image"){
         sh "packer version"
         //sh "packer build -var region=${REGION_AMI} tools/jenkins_example.json"
    }
     stage("send notification to slack"){
        slackSend channel: 'nagios_alerts', message: 'Golden_ami has been created'
    }
     stage("send email"){
        mail bcc: '', body: '''Hi, 
Please see ${REGION_AMI} for your requested golden_AMI''', cc: '', from: '', replyTo: '', subject: 'Golden ami has been build', to: 'mkarimidevops@gmail.com'
    }
}
