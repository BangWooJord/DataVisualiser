package com.packages.web;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;

class Keywords{
    HashMap<String, Boolean> offense = new HashMap<String, Boolean>();
    public Keywords() throws IOException{
        File folder = new File("D:\\JavaProjects\\DataVisualiser\\src\\com\\packages\\web\\keywords");
        if(!folder.exists()) {
            System.out.println("Couldn't open keywords folder");
            return;
        }
        for (final File file : folder.listFiles()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String[] keywords = br.readLine().split(",");
            for (final String word : keywords) {
                offense.put(word, true);
            }
        }
    }
    public boolean isOffence(String keyword){
        return offense.get(keyword) != null;
    }
}

//M: Class that's gonna fetch JSON from web
public class Request {
    private static final String link_base = "https://api.usa.gov/crime/fbi/sapi/";
    private static final String api_key = "txqbrGOTn0SSuaU69VVFdjS76Tlb8EOq76uPEexx";
    public static String buildRequest(String input){
        try {
            Keywords keywords = new Keywords();

            StringBuilder built_url = new StringBuilder(link_base);
            built_url.append("api/data/nibrs/");

            String[] user_keywords = input.split(" ");
            for(final String word : user_keywords){
                if(keywords.isOffence(word)){
                    built_url.append(word);
                    break;
                }
            }
            built_url.append("/offender/national/COUNT?api_key="+api_key);
            return built_url.toString();
        }catch(IOException exc){
            System.out.println("Keywords constructor threw an error: " +
                        exc.getMessage());
        }


        return null;
    }
    public static void Fetch(String input) throws IOException {
        String url = buildRequest(input);
        if(url == null) return;

        JSONObject json = Parser.readJsonFromUrl(url);
        JSONArray array = json.getJSONArray("results");
        for(Object obj : array){
            System.out.println(((JSONObject)obj).get("count"));
        }
    }
}
