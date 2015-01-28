package main;

import java.util.ArrayList;
import java.util.List;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket; 
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.JProtocol; 

public class CapturePacket{

public static void main(String[] args) throws Exception {
    List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with
    // NICs
    StringBuilder errbuf = new StringBuilder(); // For any error msgs


    int r = Pcap.findAllDevs(alldevs, errbuf);
    if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
        System.err.printf("Can't read list of devices, error is %s", errbuf
                .toString());
        return;
    }
    PcapIf device = (PcapIf) alldevs.get(1); // We know we have at least 1 device


    String ad = device.getHardwareAddress().toString();
    System.out.println("\nCurrently open adapter MAC:" + ad);

    int snaplen = 64 * 1024; // Capture all packets, no truncation
    int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
    int timeout = 10; //10*1000; // No timeout, non-interactive traffic
    final Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout,
            errbuf);
    if (pcap == null) {
        System.err.printf("Error while opening device for capture: "
                + errbuf.toString());
        return;
    }


    PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() 
     {
         long total_traffic = 0,count = 0;
         int i;


     public void nextPacket(PcapPacket packet, String user) {

         count += packet.getTotalSize();
         if( count>1048576 )
            {
                i++;
                total_traffic += count;
             System.out.println(i+"MB"+"\t total:"+total_traffic);
             count=count-1048576;

            }
     };
     };           
            pcap.loop(-1,jpacketHandler," ");  
            pcap.close(); 
            }

}