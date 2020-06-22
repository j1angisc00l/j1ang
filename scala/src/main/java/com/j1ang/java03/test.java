package com.j1ang.java03;

/**
 * @author J1aNgis0coo1
 * @create 2020-05-25 21:13
 */
public class test {
    public static void main(String[] args) throws CloneNotSupportedException {
        User user = new User();
        user.clone();
    }
}

class User{
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}