import java.io.File;

import javax.swing.JFileChooser;


public class AccessScoreFile
{
	private File file;
	private JFileChooser fileChooser;
	
	public AccessScoreFile()
	{
		select();
	}
	
	public void select()
	{
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select Score File");
		fileChooser.showDialog(null, "Okay");
		file = fileChooser.getSelectedFile();
	}
	
	public File getScoreFile()
	{
		return file;
	}
}
