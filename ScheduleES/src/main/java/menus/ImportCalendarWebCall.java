package menus;

import java.util.List;
import javax.swing.*;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;

import com.google.ical.compat.jodatime.DateTimeIterator;
import com.google.ical.compat.jodatime.DateTimeIteratorFactory;

import java.util.Scanner;
import java.util.TimeZone;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.io.TimezoneInfo;
import biweekly.io.scribe.property.ICalPropertyScribe;
import biweekly.property.DateStart;
import biweekly.property.ICalProperty;
import biweekly.property.Summary;
import biweekly.util.ICalDate;
import modules.Schedule;
import modules.ScheduleList;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class ImportCalendarWebCall extends JFrame {
	private JTextField textField;

	public ImportCalendarWebCall() {
		super("Import Calendar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		// Create text field
		textField = new JTextField(20);

		// Create button
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText();
				try {
					lerHorario(text);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});

		// Add components to window
		add(textField);
		add(submitButton);

		// Set window properties
		setSize(300, 150);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void lerHorario(String uri) throws IOException {
		System.out.println("Vou ler o url");
		URL url = new URL(uri.replace("webcal", "https"));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder content = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine).append("\n");
		}
		in.close();
		con.disconnect();
		ScheduleList ss = ScheduleList.fromWebcalString(content.toString());
	}

	public static void main(String[] args) {
		new ImportCalendarWebCall();
	}

}
