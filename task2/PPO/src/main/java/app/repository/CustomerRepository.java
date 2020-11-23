package app.repository;

import app.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;


@Repository("customerRepository")
public class CustomerRepository implements IRestRepository<Customer> {

    private static String selectQuery = "SELECT id, phone_number, name " +
            "FROM Customers " +
            "ORDER BY id";

    private static String selectByIdQuery = "SELECT id, phone_number, name " +
            "FROM Customers " +
            "WHERE id = ?";

    private static String insertQuery = "INSERT INTO Customers (phone_number, name) " +
            "VALUES (?, ?) " +
            "RETURNING id, phone_number, name";

    private static String updateQuery = "UPDATE Customers " +
            "SET phone_number = ?, name = ? " +
            "WHERE id = ? " +
            "RETURNING id, phone_number, name";

    private static String deleteQuery = "DELETE FROM Customers " +
            "WHERE id = ? " +
            "RETURNING id, phone_number, name";


    @Autowired
    protected JdbcOperations jdbcOperations;


    @Override
    public Customer[] select() {
        ArrayList<Customer> values = new ArrayList<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);

        while (rowSet.next()) {
            values.add(new Customer(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)
            ));
        }

        Customer[] result = new Customer[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Customer select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);

        if (!rowSet.next()) {
            return null;
        }

        return new Customer(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3)
        );
    }

    @Override
    public Customer insert(Customer entity) {
        Object[] params = new Object[] { entity.getPhoneNumber(), entity.getName()};
        int[] types = new int[] {Types.VARCHAR, Types.VARCHAR};

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);

        if (rowSet.next()) {
            return new Customer(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)
            );
        }

        return null;
    }

    @Override
    public Customer update(Integer id, Customer entity) {
        Object[] params = new Object[] { entity.getPhoneNumber(), entity.getName(), id};
        int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.INTEGER};


        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);

        if (rowSet.next()) {
            return new Customer(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)
            );
        }

        return null;
    }

    @Override
    public Customer delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);

        if (rowSet.next()) {
            return new Customer(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)
            );
        }

        return null;
    }
}
