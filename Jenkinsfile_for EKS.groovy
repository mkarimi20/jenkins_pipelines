node {
    stage("pull repo"){
        git 'https://github.com/mkarimi20/Jenkins-terraform-EKS.git'
    }
       stage("Download Terraform"){
        sh "terraform version"
        sh "wget https://releases.hashicorp.com/terraform/0.12.19/terraform_0.12.19_linux_amd64.zip"
        sh "unzip terraform_0.12.19_linux_amd64.zip"
        sh "./terraform version"
        sh "chmod +x terraform.*"
        sh "mv terraform.* terraform-12"
        sh "mv terraform-12 /sbin/"
    }

     stage("Build Image"){
         sh "source setenv.sh configurations/dev/us-east-1/dev.tfvars"
    }
     stage("Build Image"){
    }
}