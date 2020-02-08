node {
    stage("pull repo"){
        git 'https://github.com/mkarimi20/Jenkins-terraform-EKS.git'
    }
     stage("Build Image"){
         sh "terraform version"
    }
     stage("Build Image"){
    }
     stage("Build Image"){
    }
}