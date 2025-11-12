package com.dimi.user.authority;

import com.dimi.core.OrionEnumeration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum UserAuthority implements OrionEnumeration
{
    ANONYMOUS("Anonymous"),
    ADMINISTRATOR("Administrator"),
    USER("User"),
    ENGINEER("Engineer"),
    PROJECT_MANAGER("Project Manager");
    private String name;


    UserAuthority(String name)
    {
        this.name = name;
    }


    public static boolean valueExists(String other)
    {
        UserAuthority[] values = values();
        for(UserAuthority value : values)
        {
            if(value.get().equals(other))
            {
                return true;
            }
        }
        return false;
    }


    public static UserAuthority getEnumForValue(String other)
    {
        UserAuthority[] values = values();
        for(UserAuthority value : values)
        {
            if(value.get().equals(other))
            {
                return value;
            }
        }
        return null;
    }


    public static List<String> getAllValues()
    {
        List<String> list = new ArrayList<>();
        Arrays.asList(UserAuthority.values()).forEach(value -> list.add(value.get()));
        return list;
    }


    @Override
    public String get()
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
        return other instanceof UserAuthority;
    }
}
