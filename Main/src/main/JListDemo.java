package main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.IOException;
import java.util.List;  
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.UnknownHostException;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import org.xbill.DNS.Name;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.ZoneTransferException;
import org.xbill.DNS.ZoneTransferIn;

public class JListDemo extends JFrame {

	JList list;
	String[] listColorNames = { "black", "blue", "green", "yellow",
			"white" };
	Color[] listColorValues = { Color.BLACK, Color.BLUE, Color.GREEN,
			Color.YELLOW, Color.WHITE };
	Container contentpane;
	public JListDemo() {
		super("List Source Demo");
		contentpane = getContentPane();
		contentpane.setLayout(new FlowLayout());
		list = new JList(listColorNames);
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentpane.add(new JScrollPane(list));
		list.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				contentpane.setBackground(listColorValues[list
						.getSelectedIndex()]);
			}
		});
		setSize(200, 200);
		setVisible(true);
	}
	public static void main(String[] args) {
		JListDemo test = new JListDemo();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}