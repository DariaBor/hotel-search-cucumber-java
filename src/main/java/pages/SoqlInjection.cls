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

    // Finding 1: concatenation into Statement.executeQuery
    public ResultSet searchAccounts(String userSuppliedName) throws SQLException {
        String query =
            "SELECT id, name FROM account WHERE name = '" + userSuppliedName + "'";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    // Finding 2: tainted value in a WHERE clause built across statements
    public ResultSet searchContacts(String lastName) throws SQLException {
        String where = "last_name = '" + lastName + "'";
        String query = "SELECT id FROM contact WHERE " + where;
        return connection.createStatement().executeQuery(query);
    }

    // Finding 3: injection into an UPDATE via executeUpdate
    public int deactivateByCity(String city) throws SQLException {
        String sql =
            "UPDATE account SET active = false WHERE billing_city = '" + city + "'";
        return connection.createStatement().executeUpdate(sql);
    }

    // Finding 4: injection into execute()
    public boolean runStatusFilter(String status) throws SQLException {
        return connection.createStatement()
            .execute("SELECT id FROM cases WHERE status = '" + status + "'");
    }

    // Finding 5: tainted ORDER BY — not fixable by quote-escaping, common false-negative test
    public ResultSet sortedAccounts(String orderColumn) throws SQLException {
        return connection.createStatement()
            .executeQuery("SELECT id, name FROM account ORDER BY " + orderColumn);
    }
}