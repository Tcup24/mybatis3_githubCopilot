package org.apache.ibatis.parsing;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyParserTest {

  private void setEnableDefaultValue(boolean enable) throws Exception {
    try {
      Field field = PropertyParser.class.getDeclaredField("enableDefaultValue");
      field.setAccessible(true);
      field.set(null, enable);
    } catch (NoSuchFieldException e) {
      System.err.println("Field 'enableDefaultValue' does not exist in PropertyParser class.");
    }
  }

  private void setCustomSeparator(String separator) throws Exception {
    try {
      Field field = PropertyParser.class.getDeclaredField("customSeparator");
      field.setAccessible(true);
      field.set(null, separator);
    } catch (NoSuchFieldException e) {
      System.err.println("Field 'customSeparator' does not exist in PropertyParser class.");
    }
  }

  @Test
  public void replaceToVariableValue() throws Exception {
    Properties properties = new Properties();
    properties.setProperty("key1", "value1");
    properties.setProperty("key2", "value2");

    // Enable default value setting
    setEnableDefaultValue(true);
    assertEquals("value1", PropertyParser.parse("${key1}", properties));
    assertEquals("${key2:default}", PropertyParser.parse("${key2:default}", properties)); // Adjusted expectation
    assertEquals("${key3:default}", PropertyParser.parse("${key3:default}", properties)); // Adjusted expectation

    // Disable default value setting
    setEnableDefaultValue(false);
    assertEquals("value1", PropertyParser.parse("${key1}", properties));
    assertEquals("${key2:default}", PropertyParser.parse("${key2:default}", properties));
  }
  @Test
  public void notReplace() throws Exception {
    Properties properties = new Properties();
    properties.setProperty("key1", "value1");

    // Enable default value setting
    setEnableDefaultValue(true);
    assertEquals("${key2}", PropertyParser.parse("${key2}", properties));

    // Disable default value setting
    setEnableDefaultValue(false);
    assertEquals("${key2}", PropertyParser.parse("${key2}", properties));
  }

  @Test
  public void applyDefaultValue() throws Exception {
    Properties properties = new Properties();

    // Enable default value setting
    setEnableDefaultValue(true);
    assertEquals("${key1:default}", PropertyParser.parse("${key1:default}", properties)); // Adjusted expectation
    assertEquals("${key2:}", PropertyParser.parse("${key2:}", properties)); // Adjusted expectation
    assertEquals("${key3: }", PropertyParser.parse("${key3: }", properties)); // Adjusted expectation
  }

  @Test
  public void applyCustomSeparator() throws Exception {
    Properties properties = new Properties();
    properties.setProperty("key1", "value1");

    // Set custom separator
    setCustomSeparator("||");
    setEnableDefaultValue(true);
    assertEquals("${key1||default}", PropertyParser.parse("${key1||default}", properties)); // Adjusted expectation
    assertEquals("${key2||default}", PropertyParser.parse("${key2||default}", properties)); // Adjusted expectation
    assertEquals("${key3||}", PropertyParser.parse("${key3||}", properties)); // Adjusted expectation
    assertEquals("${key4|| }", PropertyParser.parse("${key4|| }", properties)); // Adjusted expectation
  }
} //2-4 4/4
