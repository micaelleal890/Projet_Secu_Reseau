import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.jnetpcap.*;

public class sniffer
{
    public static void main(String[] args)
    {
        List<PcapIf> alldevs = new ArrayList<PcapIf>();
        StringBuilder errorbuf = new StringBuilder();

        //pobieranie listy urzadzen

        int r = Pcap.findAllDevs(alldevs, errorbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty())
        {
            System.err.println("Can't read list of devices, error is: " +errorbuf.toString());
            return;
        }

    }
}