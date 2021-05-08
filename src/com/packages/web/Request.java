package com.packages.web;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;

class Keywords{
    HashMap<String, Boolean> offense = new HashMap<>();
    public Keywords() throws IOException{
        //M: folder with all the keywords files
        File folder = new File("src/com/packages/web/keywords");
        if(!folder.exists()) {
            System.out.println("Couldn't open keywords folder");
            return;
        }
        //M: iterating through a folder
        for (final var file : folder.listFiles()) {
            //M: Filling each keyword "database" with keywords from corresponding files
            if(file.getName().equals("offense.txt"))
                offense = fillMap(new BufferedReader(new FileReader(file)));
        }
    }
    //M: Fills HashMap with values from provided buffer
    private HashMap<String, Boolean> fillMap(BufferedReader br) throws IOException {
        HashMap<String, Boolean> hashMap = new HashMap<>();

        String[] keywords = br.readLine().split(",");
        for (final var word : keywords) {
            hashMap.put(word, true);
        }
        return hashMap;
    }
    //M: Check if a keyword belongs to offense
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
            for(final var word : user_keywords){
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
        for(var obj : array){
            System.out.println(((JSONObject)obj).get("count"));
        }
    }
}
