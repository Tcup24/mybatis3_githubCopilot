package org.apache.ibatis.reflection;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilTest {

  @Test
  void testHashCode() {
    assertEquals(0, ArrayUtil.hashCode(null));
    assertEquals("test".hashCode(), ArrayUtil.hashCode("test"));
    assertEquals(Integer.valueOf(123).hashCode(), ArrayUtil.hashCode(123));

    assertEquals(Arrays.hashCode(new long[]{1L, 2L, 3L}), ArrayUtil.hashCode(new long[]{1L, 2L, 3L}));
    assertEquals(Arrays.hashCode(new int[]{1, 2, 3}), ArrayUtil.hashCode(new int[]{1, 2, 3}));
    assertEquals(Arrays.hashCode(new short[]{1, 2, 3}), ArrayUtil.hashCode(new short[]{1, 2, 3}));
    assertEquals(Arrays.hashCode(new char[]{'a', 'b', 'c'}), ArrayUtil.hashCode(new char[]{'a', 'b', 'c'}));
    assertEquals(Arrays.hashCode(new byte[]{1, 2, 3}), ArrayUtil.hashCode(new byte[]{1, 2, 3}));
    assertEquals(Arrays.hashCode(new boolean[]{true, false, true}), ArrayUtil.hashCode(new boolean[]{true, false, true}));
    assertEquals(Arrays.hashCode(new float[]{1.0f, 2.0f, 3.0f}), ArrayUtil.hashCode(new float[]{1.0f, 2.0f, 3.0f}));
    assertEquals(Arrays.hashCode(new double[]{1.0, 2.0, 3.0}), ArrayUtil.hashCode(new double[]{1.0, 2.0, 3.0}));
    assertEquals(Arrays.hashCode(new Object[]{"a", "b", "c"}), ArrayUtil.hashCode(new Object[]{"a", "b", "c"}));
  }

  @Test
  void testEquals() {
    assertTrue(ArrayUtil.equals(null, null));
    assertFalse(ArrayUtil.equals(null, new int[]{1, 2, 3}));
    assertFalse(ArrayUtil.equals(new int[]{1, 2, 3}, null));
    assertTrue(ArrayUtil.equals(new int[]{1, 2, 3}, new int[]{1, 2, 3}));
    assertFalse(ArrayUtil.equals(new int[]{1, 2, 3}, new int[]{4, 5, 6}));

    assertTrue(ArrayUtil.equals(new long[]{1L, 2L, 3L}, new long[]{1L, 2L, 3L}));
    assertTrue(ArrayUtil.equals(new short[]{1, 2, 3}, new short[]{1, 2, 3}));
    assertTrue(ArrayUtil.equals(new char[]{'a', 'b', 'c'}, new char[]{'a', 'b', 'c'}));
    assertTrue(ArrayUtil.equals(new byte[]{1, 2, 3}, new byte[]{1, 2, 3}));
    assertTrue(ArrayUtil.equals(new boolean[]{true, false, true}, new boolean[]{true, false, true}));
    assertTrue(ArrayUtil.equals(new float[]{1.0f, 2.0f, 3.0f}, new float[]{1.0f, 2.0f, 3.0f}));
    assertTrue(ArrayUtil.equals(new double[]{1.0, 2.0, 3.0}, new double[]{1.0, 2.0, 3.0}));
    assertTrue(ArrayUtil.equals(new Object[]{"a", "b", "c"}, new Object[]{"a", "b", "c"}));

    assertFalse(ArrayUtil.equals("test", new int[]{1, 2, 3}));
    assertTrue(ArrayUtil.equals("test", "test"));
    assertFalse(ArrayUtil.equals("test", "different"));
  }

  @Test
  void testToString() {
    assertEquals("null", ArrayUtil.toString(null));
    assertEquals("test", ArrayUtil.toString("test"));
    assertEquals("123", ArrayUtil.toString(123));

    assertEquals("[1, 2, 3]", ArrayUtil.toString(new long[]{1L, 2L, 3L}));
    assertEquals("[1, 2, 3]", ArrayUtil.toString(new int[]{1, 2, 3}));
    assertEquals("[1, 2, 3]", ArrayUtil.toString(new short[]{1, 2, 3}));
    assertEquals("[a, b, c]", ArrayUtil.toString(new char[]{'a', 'b', 'c'}));
    assertEquals("[1, 2, 3]", ArrayUtil.toString(new byte[]{1, 2, 3}));
    assertEquals("[true, false, true]", ArrayUtil.toString(new boolean[]{true, false, true}));
    assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new float[]{1.0f, 2.0f, 3.0f}));
    assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new double[]{1.0, 2.0, 3.0}));
    assertEquals("[a, b, c]", ArrayUtil.toString(new Object[]{"a", "b", "c"}));
  }
}
// 1-2 3/3
