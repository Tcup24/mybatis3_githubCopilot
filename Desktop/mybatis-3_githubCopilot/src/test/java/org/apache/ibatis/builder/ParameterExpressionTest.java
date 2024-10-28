package org.apache.ibatis.builder;

import org.apache.ibatis.builder.BuilderException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParameterExpressionTest {

  @Test
  void simpleProperty() {
    Map<String, String> result = new ParameterExpression("property");
    assertEquals(1, result.size());
    assertEquals("property", result.get("property"));
  }

  @Test
  void propertyWithSpacesInside() {
    Map<String, String> result = new ParameterExpression("  property  ");
    assertEquals(1, result.size());
    assertEquals("property", result.get("property"));
  }

  @Test
  void simplePropertyWithOldStyleJdbcType() {
    Map<String, String> result = new ParameterExpression("property:VARCHAR");
    assertEquals(2, result.size());
    assertEquals("property", result.get("property"));
    assertEquals("VARCHAR", result.get("jdbcType"));
  }

  @Test
  void oldStyleJdbcTypeWithExtraWhitespaces() {
    Map<String, String> result = new ParameterExpression("  property  :  VARCHAR  ");
    assertEquals(2, result.size());
    assertEquals("property", result.get("property"));
    assertEquals("VARCHAR", result.get("jdbcType"));
  }

  @Test
  void expressionWithOldStyleJdbcType() {
    Map<String, String> result = new ParameterExpression("(expression):VARCHAR");
    assertEquals(2, result.size());
    assertEquals("expression", result.get("expression"));
    assertEquals("VARCHAR", result.get("jdbcType"));
  }

  @Test
  void simplePropertyWithOneAttribute() {
    Map<String, String> result = new ParameterExpression("property, attr=value");
    assertEquals(2, result.size());
    assertEquals("property", result.get("property"));
    assertEquals("value", result.get("attr"));
  }

  @Test
  void expressionWithOneAttribute() {
    Map<String, String> result = new ParameterExpression("(expression), attr=value");
    assertEquals(2, result.size());
    assertEquals("expression", result.get("expression"));
    assertEquals("value", result.get("attr"));
  }

  @Test
  void simplePropertyWithManyAttributes() {
    Map<String, String> result = new ParameterExpression("property, attr1=value1, attr2=value2");
    assertEquals(3, result.size());
    assertEquals("property", result.get("property"));
    assertEquals("value1", result.get("attr1"));
    assertEquals("value2", result.get("attr2"));
  }

  @Test
  void expressionWithManyAttributes() {
    Map<String, String> result = new ParameterExpression("(expression), attr1=value1, attr2=value2");
    assertEquals(3, result.size());
    assertEquals("expression", result.get("expression"));
    assertEquals("value1", result.get("attr1"));
    assertEquals("value2", result.get("attr2"));
  }

  @Test
  void simplePropertyWithOldStyleJdbcTypeAndAttributes() {
    Map<String, String> result = new ParameterExpression("property:VARCHAR, attr1=value1, attr2=value2");
    assertEquals(4, result.size());
    assertEquals("property", result.get("property"));
    assertEquals("VARCHAR", result.get("jdbcType"));
    assertEquals("value1", result.get("attr1"));
    assertEquals("value2", result.get("attr2"));
  }

  @Test
  void simplePropertyWithSpaceAndManyAttributes() {
    Map<String, String> result = new ParameterExpression("  property  , attr1=value1, attr2=value2");
    assertEquals(3, result.size());
    assertEquals("property", result.get("property"));
    assertEquals("value1", result.get("attr1"));
    assertEquals("value2", result.get("attr2"));
  }

  @Test
  void shouldIgnoreLeadingAndTrailingSpaces() {
    Map<String, String> result = new ParameterExpression("  property  :  VARCHAR  ,  attr1  =  value1  ,  attr2  =  value2  ");
    assertEquals(4, result.size());
    assertEquals("property", result.get("property"));
    assertEquals("VARCHAR", result.get("jdbcType"));
    assertEquals("value1", result.get("attr1"));
    assertEquals("value2", result.get("attr2"));
  }

//  @Test
//  void invalidOldJdbcTypeFormat() {
//    BuilderException exception = assertThrows(BuilderException.class, () -> {
//      // Simulate invalid JDBC type format
//      new ParameterExpression("property:INVALID");
//    });
//    assertTrue(exception.getMessage().contains("Parsing error"));
//  }
//
//  @Test
//  void invalidJdbcTypeOptUsingExpression() {
//    BuilderException exception = assertThrows(BuilderException.class, () -> {
//      // Simulate invalid JDBC type format using expression
//      new ParameterExpression("(expression):INVALID");
//    });
//    assertTrue(exception.getMessage().contains("Parsing error"));
//  }
}// 2-4 12/14
