//package org.pictolearn.docker.servlet;
package router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import utils.LogTerminal;
import utils.ManageProperties;
import IpBalancingTypes.*;


//1) ANOTA A URL DO SERVLET (URL PRECEDENTE AO "ENDPOINT DO WEBAPP")
@WebServlet(urlPatterns = "/url-class-router/*", loadOnStartup = 1)
public class Router extends HttpServlet {

    private static final long serialVersionUID = 2787920473586060865L;

    //2) PORTA DO SERVIDOR "FINAL", INCORPORADA NA URL-TARGET DO SERVIDOR FINAL
    private final String port = ManageProperties.getInstance().getProp("portTargetServer");


    //3) URL PRECEDENTE AO ENDPOINT
    private final String urlClassRouter = ManageProperties.getInstance().getProp("urlClassRouter");

    //4) NOME DO CONTAINER DO SERVIDOR "FINAL" NO DOCKER-COMPOSE
    private final String webAppServiceNameFromCompose = ManageProperties.getInstance().getProp("webAppServiceNameFromCompose");

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        //5.1) GET - INTERCEPTA URI
        String uri = request.getRequestURI().toString();

        //5.2) GET - EXTRAI URL-ROUTER + ENDPOINT
        String path = request.getRequestURI().substring(request.getContextPath().length());

        //5.3) GET - SELECIONA HOSTNAME
        String endpoint = path.substring(path.indexOf(urlClassRouter) +
                urlClassRouter.length(), path.length());

        if (StringUtils.isEmpty(endpoint)) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("Invalid GET CALL");
            out.close();
            return;
        }

        //5.4) GET - GETA O IP RANDOMICO, RETORNADO DO HOSTNAME SELECIONADO
        String ipAddress = getIpAddress(response, webAppServiceNameFromCompose);

        if (ipAddress == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("No Hosts Found");
            return;
        }

        //5.5) GET - MONTA URL-TARGET(SERVIDOR FINAL)
        String url = "http://" + ipAddress + ":" + port + "/" + endpoint;

        //5.6) GET - ADICIONA NOVO HEADER(URL-TARGET) NO REQUEST
        response.addHeader("WEB-HOST-GET-SCALE", ipAddress);

        //5.7) GET - CONVERTE STRING DA URL-TARGET, EM URL-REAL
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        //5.7) GET - DISPATCH O REQUEST P/ URL USANDO SENDRESPONSE - MOSTRA O BODY DA REQUEST
        sendResponse(response, con);

        LogTerminal.showlogget(port, uri, path, endpoint, ipAddress, url);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        //6.1) POST - INTERCEPTA URI
        String uri = request.getRequestURI().toString();

        //6.2) POST - EXTRAI URL-ROUTER + ENDPOINT
        String path = request.getRequestURI().substring(request.getContextPath().length());

        //6.3) POST - SELECIONA HOSTNAME
        String endpoint = path.substring(path.indexOf(urlClassRouter) +
                urlClassRouter.length(), path.length());

        if (StringUtils.isEmpty(endpoint)) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("Invalid POST CALL empty URI");
            out.close();
            return;
        }

        //6.4) POST - GETA O IP RANDOMICO, RETORNADO DO HOSTNAME SELECIONADO
        String ipAddress = getIpAddress(response, webAppServiceNameFromCompose);

        if (ipAddress == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("No Hosts Found");
            return;
        }

        //6.5) POST - MONTA URL-TARGET(SERVIDOR FINAL)
        String urlMontada = "http://" + ipAddress + ":" + port + "/" + endpoint;

        //6.6) POST - ADICIONA NOVO HEADER(URL-TARGET) NO REQUEST
        response.addHeader("WEB-HOST-POST-SCALE", ipAddress);

        //6.7) POST - CONVERTE STRING DA URL-TARGET, EM URL-REAL
        URL obj = new URL(urlMontada);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        Enumeration<String> headerNames = request.getHeaderNames();
        Set<String> headers = new HashSet<>();
        while (headerNames.hasMoreElements()) {
            headers.add(headerNames.nextElement());
        }

        if (!CollectionUtils.isEmpty(headers)) {
            for (String header : headers) {
                String value = request.getHeader(header);
                con.setRequestProperty(header, value);
            }
        }

        //6.8) POST - SHOW THE REQUEST-BODY
        con.setDoOutput(true);
        String body = getBody(request);

        //6.9) POST - DISPATCH O REQUEST P/ URL USANDO SENDRESPONSE - MOSTRA O BODY DA REQUEST
        OutputStream wr = con.getOutputStream();
        wr.write(body.getBytes("UTF-8"));
        wr.flush();
        wr.close();
        sendResponse(response, con);

        LogTerminal.showlogpost(port, uri, path, endpoint, ipAddress, urlMontada);
        LogTerminal.showpostheader(headers, request);
    }

    public String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    private String getIpAddress(HttpServletResponse response, String webServerKeyWord) throws UnknownHostException, IOException {

        //CRIA LISTA QUE ABRIGARA OS IPS
        List<String> ipAddr = new ArrayList<>();

        //ARMAZENA IPS NA LISTA
        for (InetAddress addr : InetAddress.getAllByName(webServerKeyWord)) {
            ipAddr.add(addr.getHostAddress());
        }

        int size = ipAddr.size();
        if (size == 0) {
            System.out.println("Size less than 1");
            return null;
        }

        //INJETA A DEPENDENCIA "IP SELECTION METHOD"
        IpInt injectIpSelectionMethod = new IpSequential().getInstance();
//        IpInt injectIpSelectionMethod = new IpRandom();
        int position = injectIpSelectionMethod.getIp(ipAddr);

        //RETORNA O IP SELECIONADO RANDOMICAMENTE
        String ipAddrStr = ipAddr.get(position);

        LogTerminal.showlogip(position, ipAddrStr, ipAddr);

        return ipAddrStr;
    }

    private void sendResponse(HttpServletResponse response, HttpURLConnection con)
            throws ProtocolException, IOException {



        int responseCode = con.getResponseCode();

        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            Map<String, List<String>> headerFields = con.getHeaderFields();
            if (!headerFields.isEmpty()) {
                int contador = 0;
                for (String header : headerFields.keySet()) {
                    contador++;
                    Object value = headerFields.get(header);
                    response.addHeader(header, value.toString());

                    //ADICIONA ELEMENTOS SO RESPONSE-HEADER
                    //logger.debug(" SEND-RESPONSE -> {}) Add header RESP: {} | header value: {}", new Object[]{contador, header, value});
                }
            }

            String inputLine;
            StringBuffer stringOutput = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                stringOutput.append(inputLine);
            }

            LogTerminal.showlogresponse(headerFields, stringOutput);

            PrintWriter out = response.getWriter();
            out.println(stringOutput.toString());
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal Server Error");
        }
    }
}