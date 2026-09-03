package fixtures;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlInjectionFixture {

    private final Connection connection;

    public SqlInjectionFixture(Connection connection) {
        this.connection = connection;
    }

    public ResultSet searchAccounts(String userSuppliedName) throws SQLException {
        String query =
            "SELECT id, name FROM account WHERE name = '" + userSuppliedName + "'";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    public ResultSet searchContacts(String lastName) throws SQLException {
        String where = "last_name = '" + lastName + "'";
        String query = "SELECT id FROM contact WHERE " + where;
        return connection.createStatement().executeQuery(query);
    }

    public int deactivateByCity(String city) throws SQLException {
        String sql =
            "UPDATE account SET active = false WHERE billing_city = '" + city + "'";
        return connection.createStatement().executeUpdate(sql);
    }

    public boolean runStatusFilter(String status) throws SQLException {
        return connection.createStatement()
            .execute("SELECT id FROM cases WHERE status = '" + status + "'");
    }

    public ResultSet sortedAccounts(String orderColumn) throws SQLException {
        return connection.createStatement()
            .executeQuery("SELECT id, name FROM account ORDER BY " + orderColumn);
    }
}