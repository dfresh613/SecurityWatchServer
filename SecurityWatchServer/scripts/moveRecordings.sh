#!/bin/bash
function move () {
	timestamp=$(date +"%m-%d-%y")
	dir=/tmp/record/$timestamp/
	mkdir $dir
	mv /tmp/record/*.jpg $dir
	mv /tmp/record/*.avi $dir	
	cp /tmp/motion.log $dir
}
function copy (){
	cp  -r $dir /vmstore/userhome/securityCam/ 
}
function remount () {
	umount -lf /mocaps
	mount /mocaps
}
function clean () {
	rm -rf $dir
        cat /dev/null > /tmp/motion.log
}
move
copy
if [ $? -eq 0 ];then
	clean
else
	while [ ! $? -eq 0 ];do
		echo "Attempting to remount"
		remount
		sleep 10
		echo "waiting..."
		copy
	done
	clean
fi
