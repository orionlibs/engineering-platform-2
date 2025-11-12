package com.dimi.project.permission;

import com.dimi.core.OrionEnumeration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Permission implements OrionEnumeration
{
    CREATE_PROJECT("Create Project"),
    EDIT_PROJECT("Edit Project"),
    DELETE_PROJECT("Delete Project"),
    VIEW_PROJECT("View Project"),
    CREATE_TASK("Create Task"),
    EDIT_TASK("Edit Task");
    private String name;


    Permission(String name)
    {
        this.name = name;
    }


    public static boolean valueExists(String other)
    {
        Permission[] values = values();
        for(Permission value : values)
        {
            if(value.get().equals(other))
            {
                return true;
            }
        }
        return false;
    }


    public static Permission getEnumForValue(String other)
    {
        Permission[] values = values();
        for(Permission value : values)
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
        Arrays.asList(Permission.values()).forEach(value -> list.add(value.get()));
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
        return other instanceof Permission;
    }
}
