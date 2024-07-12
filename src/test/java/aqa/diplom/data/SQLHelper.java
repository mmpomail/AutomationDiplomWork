package aqa.diplom.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {

    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), System.getProperty("db.user"), System.getProperty("db.pass"));

    }

    public static void cleanDB() {
        try (Connection connect = getConnection()) {
            runner.execute(connect, "DELETE FROM credit_request_entity");
            runner.execute(connect, "DELETE FROM payment_entity");
            runner.execute(connect, "DELETE FROM order_entity ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getDebitCardStatus() {
        try (Connection connect = getConnection()) {
            String queryStatus = "SELECT status FROM payment_entity ORDER by created DESC LIMIT 1";
            return runner.query(connect, queryStatus, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
            return "Payment status not found";
        }
    }

    public static String getCreditCardStatus() {
        try (Connection connect = getConnection()) {
            String queryStatus = "SELECT status  FROM credit_request_entity ORDER by created DESC LIMIT 1";
            return runner.query(connect, queryStatus, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
            return "Credit status not found";
        }
    }































}
