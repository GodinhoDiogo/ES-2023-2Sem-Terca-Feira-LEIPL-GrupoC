package menus;

/**

A utility class that provides a static method to open a file chooser dialog for saving a file and return the selected
file's absolute path as a string.
*/
import javax.swing.*;
import java.io.*;

public class FileChooserTest {

	/**
	 * Displays a file chooser dialog for the user to select a directory to save a file to. Returns the selected
	 * directory's absolute path as a string.
	 *
	 * @return the selected directory's absolute path as a string, or null if the user cancels the dialog
	 */
	public static String saveFile() {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Save File As");
	    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	    int userSelection = fileChooser.showSaveDialog(null);
	    System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
	    if (userSelection == JFileChooser.APPROVE_OPTION) {
	        File saveLocation = fileChooser.getSelectedFile();
	        return saveLocation.getAbsolutePath();

	    } else {
	        return null;
	    }
	}
}