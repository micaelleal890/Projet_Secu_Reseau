package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;  
import java.util.Date;  
import java.util.List;  
import java.util.logging.Level;
import java.util.logging.Logger;
import net.firefang.ip2c.Country;
import net.firefang.ip2c.IP2Country;
  
import org.jnetpcap.Pcap;  
import org.jnetpcap.PcapIf;  
import org.jnetpcap.packet.PcapPacket;  
import org.jnetpcap.packet.PcapPacketHandler;  
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
  
import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.Thread.sleep;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.xbill.DNS.Name;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.ZoneTransferException;
import org.xbill.DNS.ZoneTransferIn;

public class ClassicPcap extends PApplet {  
  
    /** 
     * Main startup method 
     *  
     * @param args 
     *          ignored 
     */  
    public static UnfoldingMap map;
    public static Integer monid=0;
    public static Double Latitude, Longitude;
    public static Boolean USIS=false, FRIS=false, RUIS=false, PTIS=false;
    public static ArrayList<String> mylist=new ArrayList<String>();
    //public static JListDemo test = new JListDemo();
	public void setup() {
		size(1200, 1200, P2D);

		map = new UnfoldingMap(this);
                map.setTweening(true);
		MapUtils.createDefaultEventDispatcher(this, map);
	}

	public void draw() {
		map.draw();
	}
    
        public static void DrawMe(Double Latitude,Double Longitude)
        {
                        Location tralalaLocation = new Location(Latitude, Longitude);
                        SimplePointMarker tralalaMarker = new SimplePointMarker(tralalaLocation);

                        // Add markers to the map
                        map.addMarkers(tralalaMarker);
        }
        
    public static void main(String[] args) { 
        final JPanel panelShop = new JPanel();
        final JFrame monframe = new JFrame();
	String fileName="C:/Users/Micael/Desktop/countries.txt";
	CSVFileReader x=new CSVFileReader(fileName);
	x.ReadFile();
        mylist=x.getFileValues();
        final ArrayList al = new ArrayList();
        try
        {
                    String filename= "C:\\\\Users\\\\Micael\\\\Documents\\\\NetBeansProjects\\\\Main\\\\src\\\\main\\\\Logs.txt";
                    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
                    fw.write("Welcome...");//appends the string to the file
                    fw.write(System.lineSeparator());
                    fw.write("MiLE map - potential network attacks targeting IP addresses in my home network.");//appends the string to the file
                    fw.write(System.lineSeparator());
                    fw.write("Don't hesitate to contact us if you have any question..");//appends the string to the file
                    fw.write(System.lineSeparator());
                    fw.write("Loading...");//appends the string to the file
                    fw.write(System.lineSeparator());
                    fw.close();
        }
        catch(IOException ioe)
        {
                System.err.println("IOException: " + ioe.getMessage());
        }
        al.add("Welcome...");
        al.add("MiLE map - potential network attacks targeting IP addresses in my home network.");
        al.add("Don't hesitate to contact us if you have any question.");
        al.add("Loading...");
        PApplet.main(new String[] { ClassicPcap.class.getName() });
        try {
            sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClassicPcap.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs  
        StringBuilder errbuf = new StringBuilder(); // For any error msgs  
  	final Tcp tcp = new Tcp();
        final Ip4 ip = new Ip4();
        /*************************************************************************** 
         * First get a list of devices on this system 
         **************************************************************************/  
        int r = Pcap.findAllDevs(alldevs, errbuf);  
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {  
            System.err.printf("Can't read list of devices, error is %s", errbuf  
                .toString());  
            return;  
        }  
  
        System.out.println("Network devices found:");  
  
        int i = 0;  
        for (PcapIf device : alldevs) {  
            String description =  
                (device.getDescription() != null) ? device.getDescription()  
                    : "No description available";  
            System.out.printf("#%d: %s [%s]\n", i++, device.getName(), description);  
        }  
  
        //Wifi set (5)
        PcapIf device = alldevs.get(5); // We know we have atleast 1 device  
        System.out  
            .printf("\nChoosing '%s' on your behalf:\n",  
                (device.getDescription() != null) ? device.getDescription()  
                    : device.getName());  
  
        /*************************************************************************** 
         * Second we open up the selected device 
         **************************************************************************/  
        int snaplen = 64 * 1024;           // Capture all packets, no trucation  
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets  
        int timeout = 1;           // 10 seconds in millis  
        Pcap pcap =  
            Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);  
  
        if (pcap == null) {  
            System.err.printf("Error while opening device for capture: "  
                + errbuf.toString());  
            return;  
        }  
  
        /*************************************************************************** 
         * Third we create a packet handler which will receive packets from the 
         * libpcap loop. 
         **************************************************************************/  
        PcapPacketHandler<String> jpacketHandler;  
        jpacketHandler = new PcapPacketHandler<String>() {  
            
            public void nextPacket(PcapPacket packet, String user) {
                if(packet.hasHeader(ip)){
                    packet.getHeader(tcp);
                    System.out.printf("Source IP::%s%n", FormatUtils.ip(ip.source()));
                }
                
                File file = new File("D:/ip2c/ip-to-country.bin");
                //System.out.println(file.getAbsolutePath());
                String iptest = FormatUtils.ip(ip.source()).toString();
                String currentmodify ;
                int caching1 = IP2Country.MEMORY_CACHE;
                IP2Country ip2c = null;
                try {
                    ip2c = new IP2Country(caching1);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                Country c = null;
                try {
                    c = ip2c.getCountry(iptest);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (c == null)	{
                    System.out.println("UNKNOWN");
                }
                else{
                    System.out.printf("Country ::%s%n",c.getName());
                    
                    //System.out.println(mylist.size());
                    for( int i=1;i<mylist.size();i++)
                    {
                        String currentItem = mylist.get(i).toString();
                        String[] temp;
                        String[] modifier;
                        /* delimiter */
                        String delimiter = ",";
                        String mycurrentIPDnS = iptest.toString();
                       
                        String[] tempDNS;
                        /* delimiter */
                        String delimiterDNS = "\\.";
                        /* given string will be split by the argument delimiter provided. */
                        tempDNS = mycurrentIPDnS.split(delimiterDNS);;
                        /* given string will be split by the argument delimiter provided. */
                        temp = currentItem.split(delimiter);
                        if(temp[0].toString().equals(c.getName()))
                        {
                             if (!(al.contains(iptest+":"+c.getName()))){
                                 
                                try
                                {
                                    String filename= "C:\\\\Users\\\\Micael\\\\Documents\\\\NetBeansProjects\\\\Main\\\\src\\\\main\\\\Logs.txt";
                                    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
                                    fw.write(iptest+":"+c.getName());//appends the string to the file
                                    fw.write(System.lineSeparator());
                                    fw.close();
                                    try {
                                        sleep(1);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(ClassicPcap.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //WHOIS executable
                                    //https://technet.microsoft.com/en-us/sysinternals/bb897435.aspx
                                }
                                catch(IOException ioe)
                                {
                                    System.err.println("IOException: " + ioe.getMessage());
                                }
                                String s = null;

                                try {

                                // run the Unix "ps -ef" command
                                    // using the Runtime exec method:
                                    Process p = Runtime.getRuntime().exec("C:\\Users\\Micael\\Documents\\NetBeansProjects\\Main\\src\\main\\whois.exe -v "+iptest);

                                    BufferedReader stdInput = new BufferedReader(new
                                         InputStreamReader(p.getInputStream()));

                                    BufferedReader stdError = new BufferedReader(new
                                         InputStreamReader(p.getErrorStream()));

                                    // read the output from the command
                                    System.out.println("Here is the standard output of the command:\n");
                                    while ((s = stdInput.readLine()) != null ) {
                                        System.out.println(s);
                                    }

                                    // read any errors from the attempted command
                                    System.out.println("Here is the standard error of the command (if any):\n");
                                    while ((s = stdError.readLine()) != null) {
                                        System.out.println(s);
                                    }
                                }
                                catch (IOException e) {
                                    System.out.println("exception happened - here's what I know: ");
                                    e.printStackTrace();
                                    System.exit(-1);
                                }
                                al.add(iptest+":"+c.getName());
                                DrawMe(Double.parseDouble(temp[1]),Double.parseDouble(temp[2]));
                                                     panelShop.removeAll();
                    JList list = new JList(al.toArray());
                    list.setLayoutOrientation(JList.VERTICAL);
                    list.setVisible(true);
                    JScrollPane menuScrollPane = new JScrollPane(list);
                    menuScrollPane.setMinimumSize(new Dimension(500, 800));
                    panelShop.add(menuScrollPane,BorderLayout.WEST);
                    monframe.add(panelShop);
                    monframe.setTitle("Incoming-IPAdress:");
                    monframe.pack();
                    monframe.setVisible(true);
                    try {
                        Hashtable env = new Hashtable();
                        env.put("java.naming.factory.initial","com.sun.jndi.dns.DnsContextFactory");

                        DirContext ctx = new InitialDirContext(env);
                        Attributes attrs = ctx.getAttributes(tempDNS[3].toString()+"."+tempDNS[2].toString()+"."+tempDNS[1].toString()+"."+tempDNS[0]+".in-addr.arpa",new String[] {"PTR"});
                        
                        for (NamingEnumeration ae = attrs.getAll();ae.hasMoreElements();) {
                            Attribute attr = (Attribute)ae.next();
                            String attrId = attr.getID();
                            for (Enumeration vals = attr.getAll();vals.hasMoreElements();
                            System.out.println(attrId + ": " + vals.nextElement()));
                        }

                        ctx.close();
                    }   

                    catch(Exception e) {
                        System.out.println("NO REVERSE DNS");
                    }
                             }
                        }

                    }
                    

                }
                System.out.printf("Received packet at %s caplen=%-4d len=%-4d %s\n",
                        new Date(packet.getCaptureHeader().timestampInMillis()),
                        packet.getCaptureHeader().caplen(),  // Length actually captured
                        packet.getCaptureHeader().wirelen(), // Original length
                        user                                 // User supplied object
                );
            }
        };
        pcap.loop(999999999, jpacketHandler, "");  
  
        /*************************************************************************** 
         * Last thing to do is close the pcap handle 
         **************************************************************************/  
        pcap.close();  
    } 
    
}  