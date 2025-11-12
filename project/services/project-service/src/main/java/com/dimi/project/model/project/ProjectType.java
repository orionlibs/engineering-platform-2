package com.dimi.project.model.project;

import com.dimi.core.OrionEnumeration;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectType
{
    @NotNull(message = "Project type must be provided")
    private Type type;


    public enum Type implements OrionEnumeration
    {
        UNKNOWN("Unknown"),
        ENGINEERING("Engineering"),
        SOFTWARE("Software"),
        OTHER("Other");
        private String name;


        Type(String name)
        {
            this.name = name;
        }


        public static boolean valueExists(String other)
        {
            Type[] values = values();
            for(Type value : values)
            {
                if(value.get().equals(other))
                {
                    return true;
                }
            }
            return false;
        }


        public static Type getEnumForValue(String other)
        {
            Type[] values = values();
            for(Type value : values)
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
            Arrays.asList(Type.values()).forEach(value -> list.add(value.get()));
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
            return other instanceof Type;
        }
    }
}
