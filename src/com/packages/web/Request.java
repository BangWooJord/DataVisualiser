package com.packages.web;

import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.HashMap;

class Keywords{
    HashMap<String, String> keywordMap = new HashMap<>();
    public Keywords() throws IOException {
        //M: folder with all the keywords files
        File folder = new File("src/com/packages/web/keywords");
        if (!folder.exists()) {
            System.out.println("Couldn't open keywords folder");
            return;
        }
        //M: iterating through a folder
        for (final var file : folder.listFiles()) {
            //M: Filling each keyword "database" with keywords from corresponding files
            BufferedReader br = new BufferedReader(new FileReader(file));
            //M: removing file extension
            String pure_file_name = FilenameUtils.removeExtension(file.getName());
            //M: getting each keyword from file into an array
            String[] keywords = br.readLine().split(",");
            for (final var word : keywords) {
                keywordMap.put(word, pure_file_name);
            }
        }
    }
    //M: returns type of the input key
    public String getKeyType(String key){
            return keywordMap.get(key);
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
                if(keywords.getKeyType(word).equals("offense")){
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
