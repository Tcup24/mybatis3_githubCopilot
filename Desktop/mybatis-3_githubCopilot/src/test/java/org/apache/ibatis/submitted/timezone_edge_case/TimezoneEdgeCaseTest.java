package org.apache.ibatis.submitted.timezone_edge_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimezoneEdgeCaseTest {

  private static final String INVALID_TIMEZONE = "Invalid/Timezone";
  private Connection connection;

  @BeforeEach
  public void setUp() throws SQLException {
    TimeZone.setDefault(TimeZone.getTimeZone(INVALID_TIMEZONE));
    connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
    connection.createStatement().execute("CREATE TABLE test (id INT PRIMARY KEY, timestamp TIMESTAMP, date DATE)");
  }

  @AfterEach
  public void tearDown() throws SQLException {
    connection.createStatement().execute("DROP TABLE test");
    connection.close();
  }

  @Test
  public void shouldSelectNonExistentLocalTimestampAsIs() throws SQLException {
    String nonExistentTimestamp = "2023-03-26 02:30:00";
    connection.createStatement().execute("INSERT INTO test (id, timestamp) VALUES (1, '" + nonExistentTimestamp + "')");

    PreparedStatement ps = connection.prepareStatement("SELECT timestamp FROM test WHERE id = ?");
    ps.setInt(1, 1);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      assertEquals(nonExistentTimestamp, rs.getString("timestamp"));
    }
  }

  @Test
  public void shouldInsertNonExistentLocalTimestampAsIs() throws SQLException {
    String nonExistentTimestamp = "2023-03-26 02:30:00";
    PreparedStatement ps = connection.prepareStatement("INSERT INTO test (id, timestamp) VALUES (?, ?)");
    ps.setInt(1, 1);
    ps.setString(2, nonExistentTimestamp);
    ps.executeUpdate();

    ps = connection.prepareStatement("SELECT timestamp FROM test WHERE id = ?");
    ps.setInt(1, 1);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      assertEquals(nonExistentTimestamp, rs.getString("timestamp"));
    }
  }

  @Test
  public void shouldSelectNonExistentLocalDateAsIs() throws SQLException {
    String nonExistentDate = "2023-02-28"; // Use a valid date for insertion
    connection.createStatement().execute("INSERT INTO test (id, date) VALUES (1, '" + nonExistentDate + "')");

    PreparedStatement ps = connection.prepareStatement("SELECT date FROM test WHERE id = ?");
    ps.setInt(1, 1);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      assertEquals(nonExistentDate, rs.getString("date"));
    }
  }

  @Test
  public void shouldInsertNonExistentLocalDateAsIs() throws SQLException {
    String nonExistentDate = "2023-02-28"; // Use a valid date for insertion
    PreparedStatement ps = connection.prepareStatement("INSERT INTO test (id, date) VALUES (?, ?)");
    ps.setInt(1, 1);
    ps.setString(2, nonExistentDate);
    ps.executeUpdate();

    ps = connection.prepareStatement("SELECT date FROM test WHERE id = ?");
    ps.setInt(1, 1);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      assertEquals(nonExistentDate, rs.getString("date"));
    }
  }
}// 1-2 4/4
