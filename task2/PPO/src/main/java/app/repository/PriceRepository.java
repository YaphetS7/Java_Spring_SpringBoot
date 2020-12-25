package app.repository;

import app.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;


@Repository("priceRepository")
public class PriceRepository implements IRestRepository<Price> {


    private static String selectQuery = "SELECT id, product_id, price " +
            "FROM Prices " +
            "ORDER BY id";

    private static String selectByIdQuery = "SELECT id, product_id, price " +
            "FROM Prices " +
            "WHERE id = ?";

    private static String insertQuery = "INSERT INTO Prices (product_id, price) " +
            "VALUES (?, ?) " +
            "RETURNING id, product_id, price";

    private static String updateQuery = "UPDATE Prices " +
            "SET product_id = ?, price = ? " +
            "WHERE id = ? " +
            "RETURNING id, product_id, price";

    private static String deleteQuery = "DELETE FROM Prices " +
            "WHERE id = ? " +
            "RETURNING id, product_id, price";

    @Autowired
    protected JdbcOperations jdbcOperations;

    @Override
    public Price[] select() {
        ArrayList<Price> values = new ArrayList<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);

        while (rowSet.next()) {
            values.add(new Price(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3)
            ));
        }

        Price[] result = new Price[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Price select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);

        if (!rowSet.next()) {
            return null;
        }

        return new Price(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3)
        );
    }

    @Override
    public Price insert(Price entity) {
        Object[] params = new Object[] { entity.getProductId(), entity.getPrice()};
        int[] types = new int[] {Types.INTEGER, Types.INTEGER};

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);

        if (rowSet.next()) {
            return new Price(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3)
            );
        }

        return null;
    }

    @Override
    public Price update(Integer id, Price entity) {
        Object[] params = new Object[] { entity.getProductId(), entity.getPrice(), id };
        int[] types = new int[] {Types.INTEGER, Types.INTEGER, Types.INTEGER};

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);

        if (rowSet.next()) {
            return new Price(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3)
            );
        }
        return null;
    }

    @Override
    public Price delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);

        if (rowSet.next()) {
            return new Price(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3)
            );
        }
        return null;
    }
}
