	echo "Select an option:"
	echo " * 1: Opprett fil"
	echo " * 2: Slett fil"
	echo " * 3: Endre fil"
	echo " * 4: Exit"


ERR_MSG=""


	read SEL

	case $SEL in
		1) read SAL; > $SAL ;;
		2) read SAL; rm $SAL ;;
		3) read SAL; vim $SAL;;
		4) echo "Bye!"; exit ;;
		*) ERR_MSG="Please enter a valid option!"
	esac

clear

done
