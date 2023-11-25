package com.example.appqrstore;

import java.util.ArrayList;

public class AppData {
    public static String Tag = "MAIN_LOG";
    public static Boolean ScannedNewCode = false;
    public static String ScannedCode = "НЕ ЗАДАН";
    public static ArrayList<CartElem> Cart = new ArrayList<CartElem>();
    public static CartElemAdapter Adapter;
}
