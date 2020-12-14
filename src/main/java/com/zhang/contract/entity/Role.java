package com.zhang.contract.entity;

import java.util.*;

public class Role {
    private Integer id;
    private String name;
    private String description;
    private String functions;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", functions='" + functions + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    public List<Integer> getFunctionList() {
        if (functions != null) {
            String[] fs = functions.split("\\s+");
            List<Integer> nums = new ArrayList<Integer>();
            for (String f : fs) {
                nums.add(Integer.parseInt(f));
            }
            return nums;
        } else return null;
    }
}
