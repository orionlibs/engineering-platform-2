package com.dimi.core;

public enum Profile implements OrionEnumeration
{
    TESTING_PROFILE("testing"),
    LOCALHOST_PROFILE("localhost"),
    DEV_PROFILE("dev"),
    PRODUCTION_PROFILE("production");
    private String name;


    private Profile(String name)
    {
        setName(name);
    }


    public static boolean valueExists(String other)
    {
        Profile[] values = values();
        for(Profile value : values)
        {
            if(value.get().equals(other))
            {
                return true;
            }
        }
        return false;
    }


    public static Profile getEnumForValue(String other)
    {
        Profile[] values = values();
        for(Profile value : values)
        {
            if(value.get().equals(other))
            {
                return value;
            }
        }
        return null;
    }


    @Override
    public String get()
    {
        return getName();
    }


    public String getName()
    {
        return this.name;
    }


    void setName(String name)
    {
        this.name = name;
    }


    @Override
    public boolean is(OrionEnumeration other)
    {
        return this == other;
    }


    @Override
    public boolean isNot(OrionEnumeration other)
    {
        return other instanceof Profile && this != other;
    }
}