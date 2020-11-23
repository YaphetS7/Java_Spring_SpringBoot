package app.repository;

import app.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;


@Repository("productRepository")
public class ProductRepository implements IRestRepository<Product> {

    private static String selectQuery = "SELECT id, name, description " +
            "FROM Products " +
            "ORDER BY id";

    private static String selectByIdQuery = "SELECT id, name, description " +
            "FROM Products " +
            "WHERE id = ?";

    private static String insertQuery = "INSERT INTO Products (name, description) " +
            "VALUES (?, ?) " +
            "RETURNING id, name, description";

    private static String updateQuery = "UPDATE Products " +
            "SET name = ?, description = ? " +
            "WHERE id = ? " +
            "RETURNING id, name, description";

    private static String deleteQuery = "DELETE FROM Products " +
            "WHERE id = ? " +
            "RETURNING id, name, description";


    @Autowired
    protected JdbcOperations jdbcOperations;


    @Override
    public Product[] select() {
        ArrayList<Product> values = new ArrayList<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);

        while (rowSet.next()) {
            values.add(new Product(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)
            ));
        }

        Product[] result = new Product[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Product select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);

        if (!rowSet.next()) {
            return null;
        }

        return new Product(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3)
        );
    }

    @Override
    public Product insert(Product entity) {
        Object[] params = new Object[] { entity.getName(), entity.getDescription()};
        int[] types = new int[] {Types.VARCHAR, Types.VARCHAR};

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);

        if (rowSet.next()) {
            return new Product(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)
            );
        }

        return null;
    }

    @Override
    public Product update(Integer id, Product entity) {
        Object[] params = new Object[] { entity.getName(), entity.getDescription(), id};
        int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.INTEGER};

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);

        if (rowSet.next()) {
            return new Product(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)
            );
        }

        return null;
    }

    @Override
    public Product delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);

        if (rowSet.next()) {
            return new Product(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3)
            );
        }

        return null;
    }
}
