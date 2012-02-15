import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;



public class StrategySystem extends JFrame
{
	private int width, height;
	private File scoreFile;
	
	private Container container;
	private JScrollPane sPane;
	private JList matchList;
	
	private ArrayList<Match> matches;
	
	public StrategySystem(File s)
	{
		scoreFile = s;
		
		setTitle("Scouting Strategy System");
		
		width = (int) ((7.0/9.0) * (Toolkit.getDefaultToolkit().getScreenSize().getWidth()));
		height = (int) ((8.0/9.0) * (Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		setSize(width, height);
		setResizable(false);
		setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2)-(width/2), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2)-(height/2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container = getContentPane();
		container.setLayout(new BorderLayout());
		
		populateMatches();
		
		matchList = new JList();
		
		for(int i = 0; i < matches.size(); i++)
		{
			
		}
		
		
		setVisible(true);
	}
	
	private void populateMatches()
	{
		try
		{
			matches = new ArrayList<Match>();
			
			if(scoreFile.exists() && scoreFile.canRead())
			{
				
				BufferedReader reader = new BufferedReader(new FileReader(scoreFile));
				String line = "";
				
				while((line = reader.readLine()) != null)
				{
					matches.add(new Match(line));
				}
			}
		}catch(FileNotFoundException e)
		{
				e.printStackTrace();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
