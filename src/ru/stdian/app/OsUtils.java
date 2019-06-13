package ru.stdian.app;

// from https://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java

public final class OsUtils
{
	private static String OS = null;
	public static String getOsName()
	{
		if(OS == null) OS = System.getProperty("os.name");
		return OS;
	}
	public static boolean isWindows()
	{
		return getOsName().startsWith("Windows");
	}
}
