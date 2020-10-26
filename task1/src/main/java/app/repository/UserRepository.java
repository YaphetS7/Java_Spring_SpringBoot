package app.repository;


import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository("userRepository")
public class UserRepository implements IRestRepository<User>{


    private static String selectQuery = "SELECT id, login, password " +
            "FROM Users";


    private static String insertQuery = "INSERT INTO Users (login, password) " +
            "VALUES (?, ?) " +
            "RETURNING id, login, password";

    private static String insertNoteQuery = "INSERT INTO Notes (user_login, note) " +
            "VALUES (?, ?) " +
            "RETURNING id, user_login, note";

    @Autowired
    protected JdbcOperations jdbcOperations;


    @Override
    public User[] select() {
        ArrayList<User> values = new ArrayList<User>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);

        while (rowSet.next()) {
            values.add(new User(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)
            ));
        }

        User[] result = new User[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public User insert(User entity) {
        Object[] params = new Object[] {entity.getName(), entity.getPassword()};
        int[] types = new int[] {Types.VARCHAR, Types.VARCHAR};

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);

        if (rowSet.next()) {
            return new User(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)
            );
        }

        return null;
    }

    public boolean register(User user){

        boolean check = isUser(user);

        if (!check){
            insert(user);
        }

        return (!check);
    }

    public boolean isUser(User user){
        User[] users = select();

        boolean check = false;

        for (User u: users) {
            check = check || u.getName().equals(user.getName());
        }

        return check;
    }

    public boolean authorize(User user){
        User[] users = select();

        boolean check = false;

        for (User u: users) {
            check = check || (u.getName().equals(user.getName()) && u.getPassword().equals(user.getPassword()));
        }

        return check;
    }

    public void addNote(String note, User user){

        Object[] params = new Object[] {user.getName(), note};
        int[] types = new int[] {Types.VARCHAR, Types.VARCHAR};

        jdbcOperations.queryForRowSet(insertNoteQuery, params, types);

    }

}
