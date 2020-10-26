package app.model;

public class User extends BaseModel{

    private String name;
    private String password;

    public User(int id, String name, String password){
        super(id);

        this.password = password;
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
