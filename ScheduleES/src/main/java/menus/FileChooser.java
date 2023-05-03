package menus;

import javax.swing.*;
import java.io.*;

public class FileChooser {

    public static String saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save File As");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);
        System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File saveLocation = fileChooser.getSelectedFile();
            return saveLocation.getAbsolutePath();

           
        }else {
        	return null;
        }
    }
}

