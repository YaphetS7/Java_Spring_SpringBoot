package app.model;

public class User extends BaseModel{

    private String login;
    private String password;

    public User(int id, String name, String password){
        super(id);

        this.password = password;
        this.login = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String name) {
        this.login = name;
    }
}
