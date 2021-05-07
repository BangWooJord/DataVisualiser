package com.packages.web;

import org.json.JSONObject;
import java.io.IOException;

//M: Class that's gonna fetch JSON from web
public class Request {
    public static void Fetch(String url) throws IOException {
        JSONObject json = Parser.readJsonFromUrl(url);
        System.out.println(json.get("results"));
    }
}
