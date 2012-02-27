public class Match
{
	
	public int matchNum;
	public int teamNum;
	public int hHoop1, hHoop2, hHoop3;
	public int tHoop1, tHoop2, tHoop3;
	public boolean balance;
	public String device;
	
	public Match(String data)
	{
		matchNum = Integer.parseInt(data.substring(0, data.indexOf(" ")));
		data = data.substring(data.indexOf(" ") + 1);
		
		teamNum = Integer.parseInt(data.substring(0, data.indexOf(" ")));
		data = data.substring(data.indexOf(" ") + 1);
		
		hHoop1 = Integer.parseInt(data.substring(0, data.indexOf(" ")));
		data = data.substring(data.indexOf(" ") + 1);
		
		hHoop2 = Integer.parseInt(data.substring(0, data.indexOf(" ")));
		data = data.substring(data.indexOf(" ") + 1);
		
		hHoop3 = Integer.parseInt(data.substring(0, data.indexOf(" ")));
		data = data.substring(data.indexOf(" ") + 1);
		
		tHoop1 = Integer.parseInt(data.substring(0, data.indexOf(" ")));
		data = data.substring(data.indexOf(" ") + 1);
		
		tHoop2 = Integer.parseInt(data.substring(0, data.indexOf(" ")));
		data = data.substring(data.indexOf(" ") + 1);
		
		tHoop3 = Integer.parseInt(data.substring(0, data.indexOf(" ")));
		data = data.substring(data.indexOf(" ") + 1);
		
		balance = Boolean.parseBoolean(data.substring(0, data.indexOf(" ")));
		data = data.substring(data.indexOf(" ") + 1);
		
		device = data.substring(0);
		
		//System.out.println(toString());
	}
	
	public String toString()
	{
		String output = matchNum + " " + teamNum + " " + hHoop1 + " " + hHoop2 + " " + hHoop3 + " "
								 + tHoop1 + " " + tHoop2 + " " + tHoop3 + " " + Boolean.toString(balance) + " " + device;
		return output;
	}
	
	public String toString(String delim)
	{
		String output = matchNum + delim + teamNum + delim + hHoop1 + delim + hHoop2 + delim + hHoop3 + delim
								 + tHoop1 + delim + tHoop2 + delim + tHoop3 + delim + Boolean.toString(balance) + delim + device;
		return output;
	}
	
	public String[] getDataArray()
	{
		String[] output = {Integer.toString(matchNum), Integer.toString(teamNum), Integer.toString(hHoop1), Integer.toString(hHoop2), Integer.toString(hHoop3), Integer.toString(tHoop1), Integer.toString(tHoop2), Integer.toString(tHoop3), Boolean.toString(balance), device};
		return output;
	}
	
	public String getDevice()
	{
		return device;
	}
}
