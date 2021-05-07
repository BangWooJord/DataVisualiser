package com;

import com.packages.gui.*;
import com.packages.web.*;

import java.io.*;

public class Main {
    public static void main(String[] args){
        MainWindow mainWindow = new MainWindow("Pogchamp visualiser", 800, 600);

        try {
            Request.Fetch("https://api.usa.gov/crime/fbi/sapi/api/nibrs/burglary/offender/national/age?api_key=txqbrGOTn0SSuaU69VVFdjS76Tlb8EOq76uPEexx");
        }catch (IOException ioException){
            System.out.println("Request fetch caught an error: " + ioException.getMessage());
        }
    }
}
