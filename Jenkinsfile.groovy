node {
    properties([parameters([
        choice(choices: [
            'golden_ami', 
            'tower', 
            'elk', 
            'nagiosxi', 
            'gitlab', 
            'nexus', 
            'vault',
            'artemisv1'
            ], 
            description: 'What tool would you like to build?', name: 'TOOL_TO_PROVISION'),
        text(defaultValue: 'dummy@gmail.com', description: 'Please provide email(s) for notifications. Use , for multiple emails', name: 'EMAIL_TO_SEND'),
        choice(choices: [
            'us-east-1', 
            'us-east-2', 
            'us-west-1', 
            'us-west-2',
            'eu-west-1',
            'eu-west-2'
            ], 
            description: 'Please choose a region', name: 'AMI_REGION')])])

    stage("pull repo"){
        git 'https://github.com/farrukh90/packer'

    }
     stage("build image"){
         sh "packer version"
         sh "packer build -var region=${AMI_REGION} tools/${TOOL_TO_PROVISION}.json"
    }
     stage("send notification to slack"){
        slackSend channel: 'nagios_alerts', message: "${TOOL_TO_PROVISION} has been created"
    }
     stage("send email"){
        mail bcc: '', 
        body: 
        "Hi, Please see ${AMI_REGION} for your requested golden_AMI", 
cc: '', 
from: '', 
replyTo: '', 
subject: 'Golden ami has been build', 
to: "${EMAIL_TO_SEND}"
    }
}
