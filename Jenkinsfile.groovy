node {
    properties([parameters([choice(choices: ['golden_ami', 'tower', 'elk', 'nagios', 'gitlab', 'nexus', 'vault'], description: 'What tool would you like to build ', name: 'TOOL_TO_PROVISION'), choice(choices: ['us-east-1', 'us-east-2', 'us-west-1', 'us-west-2'], description: 'choose a region', name: 'REGION_AMI')])])

    stage("pull repo"){
        git 'https://github.com/farrukh90/packer'

    }
     stage("build image"){
         sh "packer version"
         sh "packer build -var region=${REGION_AMI} tools/${TOOL_TO_PROVISION}.json"
    }
     stage("send notification to slack"){
        //slackSend channel: 'nagios_alerts', message: "${TOOL_TO_PROVISION} has been created"
    }
     stage("send email"){
        mail bcc: '', 
        body: 
        "Hi, Please see ${REGION_AMI} for your requested golden_AMI", 
cc: '', 
from: '', 
replyTo: '', 
subject: 'Golden ami has been build', 
to: "mkarimidevops@gmail.com"
    }
}
