
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImportMenu extends JFrame {
    private JRadioButton localFileButton;
    private JRadioButton urlButton;

    public ImportMenu() {
        super("Import Options");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create radio buttons
        localFileButton = new JRadioButton("Import from local file");
        urlButton = new JRadioButton("Import from URL");

        // Add radio buttons to a button group
        ButtonGroup importOptionGroup = new ButtonGroup();
        importOptionGroup.add(localFileButton);
        importOptionGroup.add(urlButton);

        // Create OK button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (localFileButton.isSelected()) {
                    // TODO: Handle local file import
                } else if (urlButton.isSelected()) {
                    // TODO: Handle URL import
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an import option", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create panel to hold radio buttons and OK button
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(localFileButton);
        panel.add(urlButton);
        panel.add(okButton);

        // Add panel to frame
        getContentPane().add(panel);

        setVisible(true);
    }
}

