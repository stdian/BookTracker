package ru.stdian.app;

// from https://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java

final class OsUtils
{
	private static String OS = null;
	private static String getOsName()
	{
		if(OS == null) OS = System.getProperty("os.name");
		return OS;
	}
	public static boolean isWindows()
	{
		return getOsName().startsWith("Windows");
	}
}
