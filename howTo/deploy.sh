#!/bin/bash

display_usage() { 
	echo "This script must be run with super-user privileges." 
	echo -e "\nUsage:\n$0 [war to deploy] [name you want for the deployed war] [plateforme[default=prod]] \n" 
} 

# if less than two arguments supplied, display usage 
	if [  $# -le 1 ] 
	then 
		display_usage
		exit 1
	fi 
 
# check whether user had supplied -h or --help . If yes display usage 
	if [[ ( $# == "--help") ||  $# == "-h" ]] 
	then 
		display_usage
		exit 0
	fi 
 
# display usage if the script is not run as root user 
	if [[ $USER != "root" ]]; then 
		echo "This script must be run as root!" 
		exit 1
	fi 

# get params
war=$1
warname=$2
platform=$3
: ${platform:="prod"}

base_path='/var/lib/'
base_webapps='/webapps/'
user=tomcat7.tomcat7
service=( "tomcat7-${platform}01" "tomcat7-${platform}02" )

for i in "${service[@]}"
do
	# stop service
	service $i stop
	# remove /webapps/*
	rm -rf $base_path$i$base_webapps*
	# cp new war to webapps
	cp $war $base_path$i$base_webapps$warname
	# make user owner of new war 
	chown $user $base_path$i$base_webapps$warname
	# start service
	service $i start
done


