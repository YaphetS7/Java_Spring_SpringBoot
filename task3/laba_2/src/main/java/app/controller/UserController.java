package app.controller;
import app.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("users")
public class UserController {

    private static final String SERVER = "http://host.docker.internal";
    private static final String PORT = ":8090/";
    private static final String REG = "reg";
    private static final String AUTH = "auth";
    private static final String REQUEST = "request";
    private static final String cookieName = "token";

    @PostMapping("auth")
    @ResponseBody
    public String auth(@RequestBody User user, HttpServletResponse response) throws IOException {

        String cookie = connectAuth(SERVER+PORT+AUTH, user);

        if(!cookie.equals("-1")){
            response.addCookie(new Cookie(cookieName, cookie));
        }

        return cookie;
    }


    @PostMapping("reg")
    @ResponseBody
    public String reg(@RequestBody User user, HttpServletResponse response) throws IOException {

        String cookie = connectReg(SERVER+PORT+REG, user);

        if(!cookie.equals("-3"))
            response.addCookie(new Cookie(cookieName, cookie));

        return cookie;
    }

    @PostMapping("request")
    @ResponseBody
    public String request(@CookieValue(name = cookieName, defaultValue = "admin") String cookie) throws IOException {


        System.out.println(cookie);
        return connectRequest(SERVER+PORT+REQUEST, cookie);
    }


    private String connectAuth(String address, User user) throws IOException {

        String ans;

        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/text");
        con.setDoOutput(true);

        String jsonInputString = String.format("{ \"login\": \"%s\", \"password\": \"%s\"}",
                user.getLogin(), user.getPassword());

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }


        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            String response = "";
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response += (responseLine.trim());
            }
            System.out.println(response);
            ans = response;
        }


        return ans;
    }

    private String connectRequest(String address, String cookie) throws IOException {
        String ans;

        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "plain/text; utf-8");
        con.setRequestProperty("Accept", "application/text");
        con.setDoOutput(true);

        String jsonInputString = String.format(cookie);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }


        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            String response = "";
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response += (responseLine.trim());
            }
            System.out.println(response);
            ans = response;
        }


        return ans;
    }

    private String connectReg(String URL, User user) throws IOException {

        String ans;

        URL url = new URL(URL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/text");
        con.setDoOutput(true);

        String jsonInputString = String.format("{ \"login\": \"%s\", \"password\": \"%s\"}",
                user.getLogin(), user.getPassword());

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }


        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            String response = "";
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response += (responseLine.trim());
            }
            System.out.println(response);
            ans = response;
        }



        return ans;
    }





}
