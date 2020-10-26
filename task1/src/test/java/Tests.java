import app.Application;
import app.controller.UserController;
import app.model.User;
import app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcOperations;


import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = Application.class)
public class Tests {

    @Autowired
    private UserController controller;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    private JdbcOperations jdbcOperations;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void fieldValid(){

        String login1 = "daniil";
        String password1 = "1221";

        assertTrue(controller.checkFields(login1, password1));

        String login2 = "maxim";
        String password2 = "awdaw21";

        assertTrue(controller.checkFields(login2, password2));

        String login3 = "max";
        String password3 = "awx211";

        assertFalse(controller.checkFields(login3, password3));

        String login4 = "maxim";
        String password4 = "121";

        assertFalse(controller.checkFields(login4, password4));

    }

    @Test
    public void isUserInDataBase(){

        User user1 = new User(0, "daniil", "1221");
        User user2 = new User(0, "1s2", "awd1221");

        assertTrue(userRepository.isUser(user1));

        assertFalse(userRepository.isUser(user2));

    }

    @Test
    public void dataBaseConnectionCheck(){
        assertThat(jdbcOperations).isNotNull();
    }

    @Test
    public void addTest(){
        assertTrue(userRepository.register(new User(0, "awdwadwadwad2", "awdwad21")));
    }

    @Test
    public void logOutTest(){

        String cookie = Hashing.sha1()
                .hashString("daniil" + "1221",  StandardCharsets.UTF_8)
                .toString();


        controller.cookies.put(cookie, new User(0, "awd", "wad"));
        controller.cookies.remove(cookie);

        assertFalse(controller.cookies.containsKey(cookie));
    }
}
