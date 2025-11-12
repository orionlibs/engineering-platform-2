package com.dimi.core;

public class Domain
{
    public static final String LOCALHOST;
    public static final String TESTING;
    public static final String DEV;
    public static final String PRODUCTION;

    static
    {
        LOCALHOST = "localhost";
        TESTING = "testing";
        DEV = "dev";
        PRODUCTION = "production";
    }

    private Domain()
    {
    }
}
