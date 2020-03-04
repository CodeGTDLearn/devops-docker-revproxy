package main.java.router.utils;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public class LogTerminal {

    private static void dividers(boolean opt) {
        if (opt == true) {
//            System.out.println("\n");
            System.out.println("---------------------------------------------------------------------------------\n");

        } else {
            System.out.println("\n---------------------------------------------------------------------------------");
//            System.out.println("\n");
        }
    }

    public static void showlogg(String port, String uri, String path, String endpoint, String ipAddress, String url, String verb) {
        dividers(true);
        System.out.println("VERB: " + verb);
        System.out.println("   -URI: " + uri);
        System.out.println("   -REQUEST: " + path);
        System.out.println("   -ENDPOINT: " + endpoint);
        System.out.println("   -IPADDRESS: " + uri);
        System.out.println("   -URL (ipAddress + port + endpoint): " + url);
    }

    public static void showlogpost(String port, String uri, String path, String endpoint, String ipAddress, String url) {
        dividers(true);
        System.out.println("VERB POST:");
        System.out.println("   -URI: " + uri);
        System.out.println("   -REQUEST: " + path);
        System.out.println("   -ENDPOINT: " + endpoint);
        System.out.println("   -IPADDRESS: " + uri);
        System.out.println("   -URL (ipAddress + port + endpoint): " + url);
    }

    public static void showlogipposition(String ipMethod, int position) {
        dividers(true);
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("   -" + ipMethod + "|Position: " + position);

    }

    public static void showlogip(int position, String ipAddrStr, List<String> ipAddr) {
        dividers(true);
        System.out.println("IP SELECTION:");
        int counter = 1;
        for (String addr : ipAddr) {
            System.out.println("   -HOSTNAME 0" + counter + ": " + addr);
            counter++;
        }
        System.out.println("   -TOTAL HOSTS: 0" + ipAddr.size());
        System.out.println("   -POSITION: 0" + position);
        System.out.println("   -SELECTED: " + ipAddrStr);
        dividers(false);
    }

    public static void showlogresponse(Map<String, List<String>> headerFields, StringBuffer stringOutput) {
        dividers(true);
        System.out.println("RESPONSE - ADDING HEADERS:");
        if (!headerFields.isEmpty()) {
            int contador = 0;
            for (String header : headerFields.keySet()) {
                contador++;
                Object value = headerFields.get(header);
                System.out.println("   -0" + contador + ": " + header + "|" + value);
            }
            ;
        }
        //System.out.println("RESPONSE-BODY: {}" + stringOutput.toString());
    }

    public static void showpostheader(Set<String> headers, HttpServletRequest request) {
        dividers(true);
        System.out.println("POST - ADDING HEADERS:");
        int contador = 0;
        for (String header : headers) {
            String value = request.getHeader(header);
            contador++;
            System.out.println("   -" + header.toUpperCase() + ": " + value);
        }
    }
}