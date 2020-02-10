node {
	
    properties([[$class: 'JiraProjectProperty'], parameters([choice(choices: ['bastion.ops-work.net', 'bastion.ops-work.net1', 'bastion.ops-work.net2', 'bastion.ops-work.net4'], description: 'Please see select env. ', name: 'ENVIR'), choice(choices: ['version/0.1 ', 'version/0.2 ', 'version/0.3 ', 'version/0.4', 'version/0.5', 'version/0.6', 'version/0.7', 'version/0.8', 'version/0.9', 'version/0.10'], description: 'Please select the version', name: 'VERSION'), string(defaultValue: 'Dummy@gmail.com', description: 'Please added the email.', name: 'RECEPIANT', trim: false)])])
	stage("Install Prerequisites"){
		timestamps {
			ws{
				sh '''
					ssh centos@${ENVIR} sudo yum install epel-release -y
					ssh centos@${ENVIR} sudo yum install python-pip -y 
					ssh centos@${ENVIR} sudo pip install Flask
					'''
		}
	}
}
	stage("Copy Artemis"){
		timestamps {
			ws {
				sh '''
					scp -r * centos@${ENVIR}:/tmp
					'''
		}
	}
}
	stage("Run Artemis"){
		timestamps {
			ws {
				sh '''
					ssh centos@${ENVIR} nohup python /tmp/artemis.py  &
					'''
		}
	}
}
	stage("Send slack notifications"){
		timestamps {
			ws {
				echo "Slack"
				//slackSend color: '#BADA55', message: 'Hello, World!'
			}
		}
	}
       stage("Send Email"){
        mail bcc: '', 
        body: "Hello, Your AMI is ready in nothing Thanks", 
        cc: '', 
        from: 'mkarimidevops@gmail.com', 
        replyTo: '', 
        subject: "somegood has been built", 
        to: "${RECEPIANT}"
    }
}
}