node {
    stage("pull repo"){

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
