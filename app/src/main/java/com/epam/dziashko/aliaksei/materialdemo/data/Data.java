package com.epam.dziashko.aliaksei.materialdemo.data;

import java.util.ArrayList;
import java.util.Random;

public class Data {

    private static final String URL_FORMAT = "http://robohash.org/terminator-%d.png";

    public static String getImageUrl() {
        Random random = new Random();
        return String.format(URL_FORMAT, random.nextInt(50));
    }

    public static ArrayList<String> getImageList(int size) {
        ArrayList<String> list = new ArrayList<String>(size);
        for (int i = 0; i < size; i++) {
            list.add(getImageUrl());
        }
        return list;
    }
}
