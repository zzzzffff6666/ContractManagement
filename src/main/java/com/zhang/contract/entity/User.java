package com.zhang.contract.entity;

import java.util.List;

public class User {
    private Integer id;
    private String name;
    private String password;

    private List<Integer> functions;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFunctions(List<Integer> f) {
        functions = f;
    }

    public boolean hasRight(int index) {
        if (functions == null) return false;
        return functions.contains(index);
    }

    public List<Integer> getFunctions() {
        return functions;
    }
}
