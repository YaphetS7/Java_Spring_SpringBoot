package app.repository;

import app.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;


@Repository("orderRepository")
public class OrderRepository implements IRestRepository<Order> {


    private static String selectQuery = "SELECT id, customer_id, product_id, quantity " +
            "FROM Orders " +
            "ORDER BY id";

    private static String selectByIdQuery = "SELECT id, customer_id, product_id, quantity " +
            "FROM Orders " +
            "WHERE id = ?";

    private static String insertQuery = "INSERT INTO Orders (customer_id, product_id, quantity) " +
            "VALUES (?, ?, ?) " +
            "RETURNING id, customer_id, product_id, quantity";

    private static String updateQuery = "UPDATE Orders " +
            "SET customer_id = ?, product_id = ?, quantity = ? " +
            "WHERE id = ? " +
            "RETURNING id, customer_id, product_id, quantity";

    private static String deleteQuery = "DELETE FROM Orders " +
            "WHERE id = ? " +
            "RETURNING id, customer_id, product_id, quantity";

    @Autowired
    protected JdbcOperations jdbcOperations;


    @Override
    public Order[] select() {
        ArrayList<Order> values = new ArrayList<Order>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);

        while (rowSet.next()) {
            values.add(new Order(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getInt(4)
            ));
        }

        Order[] result = new Order[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Order select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);

        if (!rowSet.next()) {
            return null;
        }

        return new Order(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getInt(4)
        );
    }

    @Override
    public Order insert(Order entity) {
        Object[] params = new Object[] { entity.getCustomerId(), entity.getProductId(), entity.getQuantity() };
        int[] types = new int[] {Types.INTEGER, Types.INTEGER, Types.INTEGER};

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);

        if (rowSet.next()) {
            return new Order(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getInt(4)
            );
        }

        return null;
    }

    @Override
    public Order update(Integer id, Order entity) {
        Object[] params = new Object[] { entity.getCustomerId(), entity.getProductId(), entity.getQuantity(), id };
        int[] types = new int[] {Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER};

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);

        if (rowSet.next()) {
            return new Order(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getInt(4)
            );
        }
        return null;
    }

    @Override
    public Order delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);

        if (rowSet.next()) {
            return new Order(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getInt(4)
            );
        }
        return null;
    }

}
