node {
	properties(
		[parameters(
		[choice(choices: 
		[
		'version/0.1', 
		'version/0.2', 
		'version/0.3', 
		'version/0.4', 
		'version/0.5'], 
	description: 'Which version of the app should I deploy? ', 
	name: 'Version'), 
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
					ssh myjenkins@${ENVIR} sudo yum install epel-release -y
					ssh myjenkins@${ENVIR} sudo yum install python-pip -y 
					ssh myjenkins@${ENVIR} sudo pip install Flask
					'''
		}
	}
}
	stage("Copy Artemis"){
		timestamps {
			ws {
				sh '''
					scp -r * myjenkins@${ENVIR}:/tmp
					'''
		}
	}
}
	stage("Run Artemis"){
		timestamps {
			ws {
				sh '''
					ssh myjenkins@${ENVIR} nohup python /tmp/artemis.py  &
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
}