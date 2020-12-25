package app.controller;

import app.Cache;
import app.model.User;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {


    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;


    @PostMapping(value = "auth")
    @ResponseBody
    public String auth(@RequestBody User user)  {

        boolean state = searchDB(user);

        if(state){
            Cache.add(user);
            return user.getLogin() + user.getPassword();
        }
        else{
            return "-1";
        }
    }

    @PostMapping(value = "request")
    @ResponseBody
    public String request(@RequestBody String cookie){

        if(Cache.in(cookie)) {
            return "Hello World";
        }
        else{
            return "Go out!";
        }
    }


    @PostMapping("reg")
    @ResponseBody
    public String reg(@RequestBody User user){

        boolean state = userRepository.register(user);

        if(state){
            Cache.add(user);
            return user.getLogin() + user.getPassword();
        }
        else {
            return "-3";
        }
    }


    private boolean searchDB(User user){

        return userRepository.authorize(user);
    }

}
