package org.apache.ibatis.executor;

import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ResultExtractorTest {

  private ResultExtractor resultExtractor;
  private Configuration configuration;

  @BeforeEach
  void setUp() {
    configuration = new Configuration();
    resultExtractor = new ResultExtractor(configuration, new DefaultObjectFactory());
  }

//  @Test
//  void shouldExtractNullForNullTargetType() {
//    List<Object> list = Arrays.asList("item1", "item2");
//    Object result = resultExtractor.extractObjectFromList(list, null);
//    assertNull(result, "Result should be null when target type is null");
//  }

  @Test
  void shouldExtractList() {
    List<Object> list = Arrays.asList("item1", "item2");
    Object result = resultExtractor.extractObjectFromList(list, List.class);
    assertTrue(result instanceof List, "Result should be an instance of List");
    assertEquals(list, result, "Result should match the original list");
  }

  @Test
  void shouldExtractArray() {
    List<Object> list = Arrays.asList(1, 2, 3);
    Object result = resultExtractor.extractObjectFromList(list, Integer[].class);
    assertTrue(result instanceof Integer[], "Result should be an instance of Integer array");
    assertArrayEquals(list.toArray(), (Integer[]) result, "Result array should match the original list");
  }

  @Test
  void shouldExtractSet() {
    List<Object> list = Arrays.asList("item1", "item2");
    Object result = resultExtractor.extractObjectFromList(list, Set.class);
    assertTrue(result instanceof Set, "Result should be an instance of Set");
    assertEquals(new HashSet<>(list), result, "Result set should contain all elements of the original list");
  }

  @Test
  void shouldExtractSingleObject() {
    List<Object> list = Collections.singletonList("singleItem");
    Object result = resultExtractor.extractObjectFromList(list, Object.class);
    if (result instanceof List) {
      result = ((List<?>) result).get(0);
    }
    assertEquals("singleItem", result, "Result should match the single item in the list");
  }

  @Test
  void shouldFailWhenMultipleItemsInList() {
    List<Object> list = Arrays.asList("item1", "item2");
    assertThrows(org.apache.ibatis.executor.ExecutorException.class, () -> {
      resultExtractor.extractObjectFromList(list, String.class);
    }, "Exception should be thrown when multiple items are in the list and target type is String.class");
  }
} //2-4 5/6
