package com.dimi.user.permission;

import com.dimi.core.OrionEnumeration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum UserPermission implements OrionEnumeration
{
    CREATE_USER("Create User"),
    EDIT_USER("Edit User"),
    DELETE_USER("Delete User");
    private String name;


    UserPermission(String name)
    {
        this.name = name;
    }


    public static boolean valueExists(String other)
    {
        UserPermission[] values = values();
        for(UserPermission value : values)
        {
            if(value.get().equals(other))
            {
                return true;
            }
        }
        return false;
    }


    public static UserPermission getEnumForValue(String other)
    {
        UserPermission[] values = values();
        for(UserPermission value : values)
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
        Arrays.asList(UserPermission.values()).forEach(value -> list.add(value.get()));
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
        return other instanceof UserPermission;
    }
}
