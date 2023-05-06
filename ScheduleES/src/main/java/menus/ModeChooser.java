package menus;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class ModeChooser extends JFrame{
	private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JButton submitButton;
    
    public ModeChooser(){
        super("Radio Button Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));
        
        // Create radio buttons
        radioButton1 = new JRadioButton("Json To Csv");
        radioButton2 = new JRadioButton("Csv To Json");
        
        // Create button group so only one radio button can be selected
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        
        // Create submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedOption = radioButton1.isSelected() ? 1 : 2;
                new ImportMenu(selectedOption);
                dispose();
            }
        });
        
        // Add components to window
        add(radioButton1);
        add(radioButton2);
        add(submitButton);
        
        // Set window properties
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new ModeChooser();
    }
}
