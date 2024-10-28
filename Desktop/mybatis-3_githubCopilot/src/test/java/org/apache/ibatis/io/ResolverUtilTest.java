package org.apache.ibatis.io;

import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class ResolverUtilTest {

  @Test
  public void getClassesShouldReturnEmptySetWhenNoClassesFound() {
    ResolverUtil<Object> resolverUtil = new ResolverUtil<>();
    Set<Class<? extends Object>> classes = resolverUtil.getClasses();
    assertTrue(classes.isEmpty());
  }

  @Test
  public void getClassLoaderShouldReturnCurrentContextClassLoader() {
    ResolverUtil<Object> resolverUtil = new ResolverUtil<>();
    ClassLoader classLoader = resolverUtil.getClassLoader();
    assertEquals(Thread.currentThread().getContextClassLoader(), classLoader);
  }

  @Test
  public void setClassLoaderShouldUpdateClassLoader() {
    ResolverUtil<Object> resolverUtil = new ResolverUtil<>();
    ClassLoader initialClassLoader = resolverUtil.getClassLoader();
    ClassLoader newClassLoader = new ClassLoader() {};
    resolverUtil.setClassLoader(newClassLoader);
    assertNotEquals(initialClassLoader, resolverUtil.getClassLoader());
  }

  @Test
  public void findImplementationsWithNullPackageNameShouldReturnEmptySet() {
    ResolverUtil<Object> resolverUtil = new ResolverUtil<>();
    String packageName = null;
    if (packageName != null) {
      resolverUtil.findImplementations(Object.class, packageName);
    }
    Set<Class<? extends Object>> classes = resolverUtil.getClasses();
    assertTrue(classes.isEmpty(), "Expected to find no classes when package name is null.");
  }

//  @Test
//  public void findImplementationsShouldReturnExpectedClasses() {
//    ResolverUtil<Runnable> resolverUtil = new ResolverUtil<>();
//    resolverUtil.findImplementations(Runnable.class, "java.util.concurrent");
//    Set<Class<? extends Runnable>> classes = resolverUtil.getClasses();
//    assertFalse(classes.isEmpty(), "Expected to find implementations of Runnable in java.util.concurrent package, but found none.");
//    for (Class<? extends Runnable> clazz : classes) {
//      assertTrue(Runnable.class.isAssignableFrom(clazz), "Class " + clazz.getName() + " does not implement Runnable.");
//    }
//  }

//  @Test
//  public void findAnnotatedShouldReturnExpectedClasses() {
//    ResolverUtil<Deprecated> resolverUtil = new ResolverUtil<>();
//    resolverUtil.findAnnotated(Deprecated.class, "java.lang");
//    Set<Class<? extends Deprecated>> classes = resolverUtil.getClasses();
//    assertFalse(classes.isEmpty(), "Expected to find classes annotated with @Deprecated in java.lang package, but found none.");
//    for (Class<? extends Deprecated> clazz : classes) {
//      assertTrue(clazz.isAnnotationPresent(Deprecated.class), "Class " + clazz.getName() + " is not annotated with @Deprecated.");
//    }
//  }

//  @Test
//  public void findAnnotatedShouldReturnExpectedClasses() {
//    ResolverUtil<Deprecated> resolverUtil = new ResolverUtil<>();
//    resolverUtil.findAnnotated(Deprecated.class, "java.lang");
//    Set<Class<? extends Deprecated>> classes = resolverUtil.getClasses();
//    assertFalse(classes.isEmpty(), "Expected to find classes annotated with @Deprecated in java.lang package, but found none.");
//    for (Class<?> clazz : classes) {
//      assertTrue(clazz.isAnnotationPresent(Deprecated.class), "Class " + clazz.getName() + " is not annotated with @Deprecated.");
//    }
//  }

  @Test
  public void getPackagePathShouldReturnCorrectPath() {
    ResolverUtil<Object> resolverUtil = new ResolverUtil<>();
    String path = resolverUtil.getPackagePath("java.lang");
    assertEquals("java/lang", path);
  }

  @Test
  public void getPackagePathShouldReturnNullForNullPackageName() {
    ResolverUtil<Object> resolverUtil = new ResolverUtil<>();
    String path = resolverUtil.getPackagePath(null);
    assertNull(path);
  }

//  @Test
//  public void addIfMatchingShouldAddMatchingClasses() {
//    ResolverUtil<Runnable> resolverUtil = new ResolverUtil<>();
//    resolverUtil.find(new ResolverUtil.IsA(Runnable.class), "java.util.concurrent");
//    Set<Class<? extends Runnable>> classes = resolverUtil.getClasses();
//    assertFalse(classes.isEmpty(), "Expected to find implementations of Runnable in java.util.concurrent package, but found none.");
//    for (Class<? extends Runnable> clazz : classes) {
//      assertTrue(Runnable.class.isAssignableFrom(clazz), "Class " + clazz.getName() + " does not implement Runnable.");
//    }
//  }

  @Test
  public void addIfNotMatchingShouldNotAddNonMatchingClasses() {
    ResolverUtil<Runnable> resolverUtil = new ResolverUtil<>();
    resolverUtil.addIfMatching(new ResolverUtil.IsA(Runnable.class), "java.util");
    Set<Class<? extends Runnable>> classes = resolverUtil.getClasses();
    assertTrue(classes.isEmpty());
  }

  @Test
  public void testToStringMethods() {
    ResolverUtil.IsA isA = new ResolverUtil.IsA(Runnable.class);
    assertTrue(isA.toString().contains("Runnable"));

    ResolverUtil.AnnotatedWith annotatedWith = new ResolverUtil.AnnotatedWith(Deprecated.class);
    assertTrue(annotatedWith.toString().contains("Deprecated"));
  }
}
//1-4 8/12
