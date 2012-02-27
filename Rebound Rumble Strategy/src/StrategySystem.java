import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;



public class StrategySystem extends JFrame
{
	private int width, height;
	private File scoreFile;
	
	private Container container;
	private JScrollPane sPane;
	private JTable matchList;
	private JScrollPane jScroll;
	private JPanel controlPanel;
	
	private ArrayList<Match> matches;
	private ArrayList<Match> teamMatches;
	private int SELECTED_ROW;
	private String STRATEGY_STATE;
	
	private JButton refreshButton, teamViewButton;
	
	private DefaultTableModel model;
	private String[] information = {"Match #", "Team #", "H. Hoop 1", "H. Hoop 2", "H. Hoop 3", "T. Hoop 1", "T. Hoop 2", "T. Hoop 3", "Balanced", "Device"};
	private String[][] listData;
	
	public StrategySystem(File s)
	{
		STRATEGY_STATE = "ALL MATCHES";
		scoreFile = s;
		
		setTitle("Scouting Strategy System - All Matches");
		
		width = (int) ((7.0/9.0) * (Toolkit.getDefaultToolkit().getScreenSize().getWidth()));
		height = (int) ((8.0/9.0) * (Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		setSize(width, height);
		setResizable(false);
		setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2)-(width/2), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2)-(height/2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container = getContentPane();
		container.setLayout(new BorderLayout());
		
		
		//******************************************************************************************************
		//make table
		
		populateMatches();
		groupMatchList();
		
		container.add(jScroll, BorderLayout.CENTER);
		//******************************************************************************************************
		
		//******************************************************************************************************
		//make control options
		
		controlPanel = new JPanel();
		
		refreshButton = new JButton("Refresh All Matches");
		refreshButton.addActionListener(new RefreshList());
		controlPanel.add(refreshButton);
		
		teamViewButton = new JButton("View Team");
		teamViewButton.addActionListener(new TeamView());
		teamViewButton.setEnabled(false);
		controlPanel.add(teamViewButton);
		
		container.add(controlPanel, BorderLayout.NORTH);
		
		//******************************************************************************************************
		
		
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
	
	private void groupMatchList()
	{
		//DefaultTableModel model = null;
		
		model = new DefaultTableModel();
		model.setColumnIdentifiers(information);
		
		listData = new String[matches.size()][information.length];
		for(int i = 0; i < matches.size(); i++)
		{
			listData[i] = matches.get(i).getDataArray();
			model.addRow(listData[i]);
		}
		
		matchList = new JTable(model)
		{
			public boolean isCellEditable(int rowIndex, int vColIndex)
			{
				return false;
			}
		};
		
		matchList.getSelectionModel().addListSelectionListener(new RowListener());
		SELECTED_ROW = -1;
		jScroll = new JScrollPane(matchList);
	}
	
	
	
	private class RowListener implements ListSelectionListener
	{

		public void valueChanged(ListSelectionEvent e)
		{
			if("ALL MATCHES".equals(STRATEGY_STATE))
			{
				SELECTED_ROW = matchList.getSelectedRow();
				teamViewButton.setEnabled(true);
			}
		}
		
	}
	
	private class RefreshList implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			int j = model.getRowCount();
			for(int i = 0; i < j; i++)
			{
				model.removeRow(0);
			}
			
			populateMatches();
			
			listData = new String[matches.size()][information.length];
			for(int i = 0; i < matches.size(); i++)
			{
				listData[i] = matches.get(i).getDataArray();
				model.addRow(listData[i]);
			}
			
			model.fireTableDataChanged();
			
			SELECTED_ROW = -1;
			teamViewButton.setEnabled(false);
			
			setTitle("Scouting Strategy System - All Matches");
			STRATEGY_STATE = "ALL MATCHES";
		}
	}

	private class TeamView implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			int t = matches.get(SELECTED_ROW).teamNum;
			
			teamMatches = new ArrayList<Match>();
			
			for(int i = 0; i < matches.size(); i++)
			{
				if(t == matches.get(i).teamNum)
					teamMatches.add(matches.get(i));
			}
			
			int j = model.getRowCount();
			for(int i = 0; i < j; i++)
			{
				model.removeRow(0);
			}
			
			int hHoop1T = 0, hHoop2T = 0, hHoop3T = 0, tHoop1T = 0, tHoop2T = 0, tHoop3T = 0, balanT = 0;
			
			listData = new String[teamMatches.size()][information.length];
			for(int i = 0; i < teamMatches.size(); i++)
			{
				Match m = teamMatches.get(i);
				listData[i] = m.getDataArray();
				model.addRow(listData[i]);
				
				hHoop1T += m.hHoop1;
				hHoop2T += m.hHoop2;
				hHoop3T += m.hHoop3;
				tHoop1T += m.tHoop1;
				tHoop2T += m.tHoop2;
				tHoop3T += m.tHoop3;
				
				if(m.balance)
					balanT += 1;
			}
			
			model.addRow(new String[10]);
			
			String[] average = {"Average", Integer.toString(t),
								Double.toString((double) hHoop1T / teamMatches.size()), Double.toString((double) hHoop2T / teamMatches.size()), Double.toString((double) hHoop3T / teamMatches.size()),
								Double.toString((double) tHoop1T / teamMatches.size()), Double.toString((double) tHoop2T / teamMatches.size()), Double.toString((double) tHoop3T / teamMatches.size()),
								Double.toString((double) balanT / teamMatches.size()), ""};
			
			model.addRow(average);
			
			model.fireTableDataChanged();
			
			SELECTED_ROW = -1;
			teamViewButton.setEnabled(false);
			
			setTitle("Scouting Strategy System - Team " + t);
			
			STRATEGY_STATE = "TEAM MATCHES";
		}
		
	}
}
