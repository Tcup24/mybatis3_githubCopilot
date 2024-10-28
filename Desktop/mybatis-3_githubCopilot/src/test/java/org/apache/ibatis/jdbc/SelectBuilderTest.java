package org.apache.ibatis.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.ibatis.jdbc.SelectBuilder;
import org.junit.jupiter.api.Test;

public class SelectBuilderTest {

  @Test
  public void shouldProduceExpectedSimpleSelectStatement() {
    SelectBuilder.BEGIN();
    SelectBuilder.SELECT("P.ID, P.FIRST_NAME, P.LAST_NAME");
    SelectBuilder.FROM("PERSON P");
    SelectBuilder.WHERE("P.ID = #{id}");
    SelectBuilder.WHERE("P.FIRST_NAME = #{firstName}");
    SelectBuilder.WHERE("P.LAST_NAME = #{lastName}");
    SelectBuilder.ORDER_BY("P.LAST_NAME");
    String sql = SelectBuilder.SQL();
    String expectedSql = "SELECT P.ID, P.FIRST_NAME, P.LAST_NAME\n" +
      "FROM PERSON P\n" +
      "WHERE (P.ID = #{id} AND P.FIRST_NAME = #{firstName} AND P.LAST_NAME = #{lastName})\n" +
      "ORDER BY P.LAST_NAME";
    assertEquals(expectedSql, sql);
  }

  @Test
  public void shouldProduceExpectedSimpleSelectStatementMissingFirstParam() {
    SelectBuilder.BEGIN();
    SelectBuilder.SELECT("P.ID, P.FIRST_NAME, P.LAST_NAME");
    SelectBuilder.FROM("PERSON P");
    SelectBuilder.WHERE("P.FIRST_NAME = #{firstName}");
    SelectBuilder.WHERE("P.LAST_NAME = #{lastName}");
    SelectBuilder.ORDER_BY("P.LAST_NAME");
    String sql = SelectBuilder.SQL();
    String expectedSql = "SELECT P.ID, P.FIRST_NAME, P.LAST_NAME\n" +
      "FROM PERSON P\n" +
      "WHERE (P.FIRST_NAME = #{firstName} AND P.LAST_NAME = #{lastName})\n" +
      "ORDER BY P.LAST_NAME";
    assertEquals(expectedSql, sql);
  }

  @Test
  public void shouldProduceExpectedSimpleSelectStatementMissingFirstTwoParams() {
    SelectBuilder.BEGIN();
    SelectBuilder.SELECT("P.ID, P.FIRST_NAME, P.LAST_NAME");
    SelectBuilder.FROM("PERSON P");
    SelectBuilder.WHERE("P.LAST_NAME = #{lastName}");
    SelectBuilder.ORDER_BY("P.LAST_NAME");
    String sql = SelectBuilder.SQL();
    String expectedSql = "SELECT P.ID, P.FIRST_NAME, P.LAST_NAME\n" +
      "FROM PERSON P\n" +
      "WHERE (P.LAST_NAME = #{lastName})\n" +
      "ORDER BY P.LAST_NAME";
    assertEquals(expectedSql, sql);
  }

  @Test
  public void shouldProduceExpectedSimpleSelectStatementMissingAllParams() {
    SelectBuilder.BEGIN();
    SelectBuilder.SELECT("P.ID, P.FIRST_NAME, P.LAST_NAME");
    SelectBuilder.FROM("PERSON P");
    SelectBuilder.ORDER_BY("P.LAST_NAME");
    String sql = SelectBuilder.SQL();
    String expectedSql = "SELECT P.ID, P.FIRST_NAME, P.LAST_NAME\n" +
      "FROM PERSON P\n" +
      "ORDER BY P.LAST_NAME";
    assertEquals(expectedSql, sql);
  }

  @Test
  public void shouldProduceExpectedComplexSelectStatement() {
    SelectBuilder.BEGIN();
    SelectBuilder.SELECT("P.ID, P.FIRST_NAME, P.LAST_NAME, A.ADDRESS");
    SelectBuilder.FROM("PERSON P");
    SelectBuilder.JOIN("ADDRESS A ON P.ID = A.PERSON_ID");
    SelectBuilder.WHERE("P.LAST_NAME = #{lastName}");
    SelectBuilder.GROUP_BY("P.ID, P.FIRST_NAME, P.LAST_NAME, A.ADDRESS");
    SelectBuilder.HAVING("COUNT(A.ID) > 1");
    SelectBuilder.ORDER_BY("P.LAST_NAME, P.FIRST_NAME");
    String sql = SelectBuilder.SQL();
    String expectedSql = "SELECT P.ID, P.FIRST_NAME, P.LAST_NAME, A.ADDRESS\n" +
      "FROM PERSON P\n" +
      "JOIN ADDRESS A ON P.ID = A.PERSON_ID\n" +
      "WHERE (P.LAST_NAME = #{lastName})\n" +
      "GROUP BY P.ID, P.FIRST_NAME, P.LAST_NAME, A.ADDRESS\n" +
      "HAVING (COUNT(A.ID) > 1)\n" +
      "ORDER BY P.LAST_NAME, P.FIRST_NAME";
    assertEquals(expectedSql, sql);
  }
} // 4 5/5
