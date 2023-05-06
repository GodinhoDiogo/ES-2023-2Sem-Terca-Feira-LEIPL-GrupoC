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
import biweekly.property.ICalProperty;
import biweekly.util.ICalDate;
import modules.Schedule;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
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
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                try {
					lerHorario(text);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
            content.append(in.readLine());
        }
        in.close();
        con.disconnect();
        List<Schedule> schedules = createScheduleList(content.toString());
    }
    public List<Schedule> createScheduleList(String input) {
        List<Schedule> scheduleList = new ArrayList<>();
        //String[] events = input.split("END:VEVENT");
        String[] events = input.split("\\\\n");
        for (String event : events) {
            String[] lines = event.split("\\n");
            Schedule schedule = new Schedule();
            
            System.out.println(event);
//            for(String l : lines) {
//            	System.out.println(l);
//            }
//            for (String line : lines) {
//                String[] keyValue = line.split(":", 2);
//                for(String s : keyValue) {
//                	System.out.println("key value: " + s);
//                }
//                String key = keyValue[0];
//                String value = keyValue.length > 1 ? keyValue[1] : "";
//
//                switch (key) {
////                    case "SUMMARY":
////                        schedule.setSummary(value);
////                        break;
////                    case "DESCRIPTION":
////                        schedule.setDescription(value);
////                        break;
////                    case "LOCATION":
////                        schedule.setLocation(value);
////                        break;
//                    case "DTSTART":
//                        
//                        schedule.setHorarioFimAula(value);
//                        break;
//                    case "DTEND":
//                        
//                        schedule.setHorarioInicioAula(value);
//                        break;
//                }
//            }
//            if (!schedule.isEmpty()) {
//                scheduleList.add(schedule);
//                System.out.println("got a valid schedule");
//                System.out.println(schedule);
//            }
        }

        return scheduleList;
    }
        


    
    public static void main(String[] args) {
        new ImportCalendarWebCall();
    }
    
}

