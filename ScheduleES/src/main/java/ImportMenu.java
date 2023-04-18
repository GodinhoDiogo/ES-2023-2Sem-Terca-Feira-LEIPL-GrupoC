import org.apache.commons.io.FileUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(ImportMenu.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        // TODO: Handle selected local file
                        JOptionPane.showMessageDialog(null, "Selected file: " + selectedFile.getAbsolutePath(), "File selected", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (urlButton.isSelected()) {
                    String url = JOptionPane.showInputDialog(null, "Enter URL to import from:", "Import from URL", JOptionPane.PLAIN_MESSAGE);
                    if (url != null && !url.isEmpty()) {
                        try {
                            URL downloadUrl = new URL(url);
                            String fileName = downloadUrl.getFile();
                            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
                            File outputFile = new File(fileName);
                            FileUtils.copyURLToFile(downloadUrl, outputFile);
                            // TODO: Handle downloaded file
                            JOptionPane.showMessageDialog(null, "File downloaded to: " + outputFile.getAbsolutePath(), "Download complete", JOptionPane.INFORMATION_MESSAGE);
                        } catch (MalformedURLException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid URL", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Error downloading file", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
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

