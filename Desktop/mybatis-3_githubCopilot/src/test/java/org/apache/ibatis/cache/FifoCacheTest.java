package org.apache.ibatis.cache;

import org.apache.ibatis.cache.decorators.FifoCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FifoCacheTest {

  private FifoCache cache;

  @BeforeEach
  void setUp() {
    cache = new FifoCache(new PerpetualCache("testCache"));
    cache.setSize(5);
  }

  @Test
  void shouldRemoveFirstItemInBeyondFiveEntries() {
    for (int i = 0; i < 6; i++) {
      cache.putObject(i, "value" + i);
    }
    assertNull(cache.getObject(0), "First item should be removed");
    assertEquals("value1", cache.getObject(1), "Second item should remain");
  }

  @Test
  void shouldRemoveItemOnDemand() {
    cache.putObject(1, "value1");
    assertEquals("value1", cache.getObject(1), "Item should be retrievable");
    cache.removeObject(1);
    assertNull(cache.getObject(1), "Item should be removed on demand");
  }

  @Test
  void shouldFlushAllItemsOnDemand() {
    for (int i = 0; i < 5; i++) {
      cache.putObject(i, "value" + i);
    }
    cache.clear();
    for (int i = 0; i < 5; i++) {
      assertNull(cache.getObject(i), "All items should be removed");
    }
  }

  @Test
  void shouldRiseConflictInBeyondFiveEntries() {
    for (int i = 0; i < 5; i++) {
      cache.putObject(i, "value" + i);
    }
    cache.removeObject(2);
    cache.putObject(5, "value5");
    assertNull(cache.getObject(2), "Removed item should not be in cache");
    assertEquals("value5", cache.getObject(5), "New item should be added");
    assertEquals("value0", cache.getObject(0), "First item should remain");
  }
}
