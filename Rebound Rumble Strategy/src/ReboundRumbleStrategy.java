
public class ReboundRumbleStrategy
{
	
	public static AccessScoreFile accessScoreFile;
	public static StrategySystem strategySystem;
	
	public static void main(String[] args)
	{
		
		accessScoreFile = new AccessScoreFile();
		
		strategySystem = new StrategySystem(accessScoreFile.getScoreFile());
	}

}
