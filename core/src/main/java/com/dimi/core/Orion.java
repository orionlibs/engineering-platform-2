package com.dimi.core;

public abstract class Orion
{
    public static String systemProfileMode = "localhost";
    public static String domainName = "localhost";


    private Orion()
    {
    }


    public static boolean isTesting()
    {
        return Profile.TESTING_PROFILE.get().equals(systemProfileMode);
    }


    public static boolean isNotTesting()
    {
        return !isTesting();
    }


    public static boolean isLocalhost()
    {
        return Profile.LOCALHOST_PROFILE.get().equals(systemProfileMode);
    }


    public static boolean isNotLocalhost()
    {
        return !isLocalhost();
    }


    public static boolean isDev()
    {
        return Profile.DEV_PROFILE.get().equals(systemProfileMode);
    }


    public static boolean isNotDev()
    {
        return !isDev();
    }


    public static boolean isProduction()
    {
        return Profile.PRODUCTION_PROFILE.get().equals(systemProfileMode);
    }


    public static boolean isNotProduction()
    {
        return !isProduction();
    }
}
