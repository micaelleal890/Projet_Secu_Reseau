## Projet_Secu_Reseau ##

Project made by Micael LEAL

Project of Network Security with A.Dulanoy

##Setup of the environment##

- Program written in Java
- NetBeans or Eclipse must installed and be run as administrator, otherwise you can't access on network card
- Run ClassicPcap.java + don't forget to set your current folder (mycurrentFolder) variable
- All other *.java files are related to the accomplishment of the project
- Project was done on Windows 8 environment

##UPDATE 28/01/2015##

- Generates a world wide map thanks to Unfolding Map v0.9.6 which proposes a world map in Java (Zoom-out feature might lag)
- Read coming packets through network card
- Filter IP-adress
- Get Country by IP-adress works with GeoIP from MaxMind
- IP to Country mapping works via text file found via current folder / countries.txt which works with GeoIP from MaxMind
- Sets a point to the according country
- Displays menu with incoming IP-adress and country

- Writes in a file all the incoming IP-adresses
##UPDATE 09/02/2015##

- IP to ASN mapping via command line (Special thanks to Mark Russinovich who could implement in Windows a "whois" feature)
- Saves all IP to ASN mappings in text files stored in current folder / IPtoASNLOGS / *.txt
- Double click on listbox gives the according ASN mapping

##UPCOMING FEATURE##

- Main.java can read *.pcap files and Sets a point to the according country on the map
- Read multiple pcap-files (interesting if you got attacked from different sources during different days)
  -> useful to find IP-destination from different malwares

##CURRENT ISSUES##

- SPEED OF APPLICATION COULD BE FASTER BUT DUE TO THE READ/WRITE FEATURES ON FILES, IT SLOW DOWNS THE APPLICATION