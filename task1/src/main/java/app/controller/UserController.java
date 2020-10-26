package app.controller;


import app.model.User;
import app.repository.UserRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


@Controller
public class UserController {

    @Autowired
    @Qualifier("userRepository")
    public UserRepository userRepository;

    public HashMap<String, User> cookies = new HashMap<>();

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    private RedirectView registerPost(@RequestParam(value = "login") String login,
                             @RequestParam(value = "password") String password) {

        RedirectView redirectView = new RedirectView();

        if (!checkFields(login, password)){
            redirectView.setUrl("/error");  //TODO: add error classifiers
            return redirectView;
        }

        User user = new User(0, login, password);

        boolean state = userRepository.register(user);

        if(state){
            redirectView.setUrl("/auth");
        }
        else{
            redirectView.setUrl("/error"); //TODO: add error classifiers
        }

        return redirectView;
    }

    public boolean checkFields(String login, String password){
        return (password.length() >= 4 && login.length() >= 4);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public RedirectView authPost(@RequestParam(value = "login") String login,
                                  @RequestParam(value = "password") String password,
                                  HttpServletResponse response) {


        User user = new User(0, login, password);

        boolean state = userRepository.authorize(user);
        RedirectView redirectView = new RedirectView();

        if(state){
            redirectView.setUrl("/user");

            String cookieVal = Hashing.sha1()
                    .hashString(login + password, StandardCharsets.UTF_8)
                    .toString();

            Cookie cookie = new Cookie("token", cookieVal);

            cookies.put(cookieVal, user);

            response.addCookie(cookie);
        }
        else{
            redirectView.setUrl("/error"); //TODO: add error classifiers
        }

        return redirectView;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public RedirectView addNote(@RequestParam(value = "note") String note,
                          @CookieValue(name = "token", defaultValue = "admin") String cookieValue){

        RedirectView redirectView = new RedirectView();


        if (cookies.containsKey(cookieValue)){

            User user = cookies.get(cookieValue);
            redirectView.setUrl("/user");

            userRepository.addNote(note, user);
        }
        else{
            redirectView.setUrl("/error");
        }

        return redirectView;
    }


    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorGet () {
        return "error"; //TODO: add error classifiers
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userGet () {
        return "user";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String authGet () {
        return "authorization";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGet () {
        return "register";
    }


    @RequestMapping(value = "/quit", method = RequestMethod.GET)
    public String quit (@CookieValue(name = "token", defaultValue = "admin") String cookieValue,
                        HttpServletResponse response) {

        RedirectView redirectView = new RedirectView();

        Cookie cookie = new Cookie("token", cookieValue);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        for (String c: cookies.keySet()) {
            if (c.equals(cookieValue)) {
                cookies.remove(c);
                break;
            }
        }

        return "authorization";
    }

}
