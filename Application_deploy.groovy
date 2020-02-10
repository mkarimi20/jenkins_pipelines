	properties(
		[parameters(
		[choice(choices: 
		[
		'version/0.1', 
		'version/0.2', 
		'version/0.3', 
		'version/0.4', 
		'version/0.5',
        'version/0.6',
        'version/0.7',
        'version/0.8',
        'version/0.9'], 
	description: 'Which version of the app should I deploy? ', 
	name: 'Version'), 
    text(defaultValue: 'dummy@gmail.com', description: 'Please provide email(s) for notifications. Use , for multiple emails', name: 'EMAIL_TO_SEND'),
	choice(choices: 
	[
		'bastion.ops-work.net', 
		'qa1.acirrustech.com', 
		'stage1.acirrustech.com', 
		'prod1.acirrustech.com'], 
	description: 'Please provide an environment to build the application', 
	name: 'ENVIR')])])


	stage("Stage1"){
		timestamps {
			ws {
				checkout([$class: 'GitSCM', branches: [[name: '${Version}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/fuchicorp/artemis.git']]])
		}
	}
}
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
        body: "Hello, Your AMI is ready in some Thanks", 
        cc: '', 
        from: 'karimi.m20@gmail.com', 
        replyTo: '', 
        subject: "mo has been built", 
        to: "${EMAIL_TO_SEND}"
    }
}