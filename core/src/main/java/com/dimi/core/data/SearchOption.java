package com.dimi.core.data;

import com.dimi.core.OrionEnumeration;

public enum SearchOption implements OrionEnumeration
{
    EXACT_MATCH("Exact match"),
    STARTS_WITH("Starts with"),
    ENDS_WITH("Ends with");
    private String name;


    private SearchOption(String name)
    {
        setName(name);
    }


    public static boolean valueExists(String other)
    {
        SearchOption[] values = values();
        for(SearchOption value : values)
        {
            if(value.get().equals(other))
            {
                return true;
            }
        }
        return false;
    }


    public static SearchOption getEnumForValue(String other)
    {
        SearchOption[] values = values();
        for(SearchOption value : values)
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
        return other instanceof SearchOption && this != other;
    }
}
