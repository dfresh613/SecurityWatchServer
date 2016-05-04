#!/bin/bash
weekly=false
always=false
function parseTime (){
	day=$(date +%a)
	hour=$(date +%H)
	if [[ "$day" == "Wed" || "$day" == "Mon" || "$day" == "Tue" || "$day" == "Thu" ]];then
		if [[ $hour -gt 9 && $hour -lt 17 ]];then
			echo "sending notice..."
			sendNotice
		else
			echo "Motion detected outside timeframe, not notifying"
		fi
	else
		echo "motion detected outside date range, not notifying"
	fi
}

function determineVars (){
	checkSchedule=$(cat /home/pi/scripts/notifyConfig | grep -c "NOTIFY_MOTION_SCHEDULE=on")
	checkAlways=$(cat /home/pi/scripts/notifyConfig |grep -c "NOTIFY_MOTION_ALWAYS=on")
	if [[ $checkSchedule == "1" ]];then
		weekly=true;
	fi
	if [[ $checkAlways  == "1" ]];then
		always=true;
	fi
}
function sendNotice (){
	numbers=( 5162414091 2018355857 )
	for number in "${numbers[@]}";do
	curl http://textbelt.com/text -d number="$number" -d message="Motion has been detected on picam(backdoor) connect to 173.50.161.111:8081 to view"
	done

}
determineVars
if [[ "$always" == true ]];then
	echo "Always var configured, sending notice"
	sendNotice
elif [[ "$weekly" == true ]];then
	parseTime
else
	echo "No vars configured, not notifying"
fi
