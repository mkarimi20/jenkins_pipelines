node {
    stage("pull repo"){
        echo "Hello"

    }
     stage("build image"){
         sh "packer version"
    }
     stage("send notification to slack"){
        echo "Hello"
    }
     stage("send email"){
        echo "Hello"
    }
}
