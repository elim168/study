package com.elim.learn.guava.reflect;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Elim
 * 19-4-21
 */
public class ReflectTest {

  @Test
  public void test() {
    TypeToken<List<String>> typeToken = new TypeToken<List<String>>() {};

    TypeToken<String> stringTypeToken = TypeToken.of(String.class);
    System.out.println(stringTypeToken.getType().getTypeName());
    System.out.println(stringTypeToken.getRawType().getTypeName());

    System.out.println(typeToken);//java.util.List<java.lang.String>
    System.out.println(typeToken.getType().getTypeName());//java.util.List<java.lang.String>
    System.out.println(typeToken.getRawType().getTypeName());//java.util.List

    Assert.assertTrue(typeToken.isSubtypeOf(List.class));


    TypeToken<Function<Integer, String>> funToken = new TypeToken<Function<Integer, String>>() {};

    TypeToken<?> funResultToken = funToken.resolveType(Function.class.getTypeParameters()[1]);
    // returns a TypeToken<String>
    Assert.assertEquals("java.lang.String", funResultToken.getType().getTypeName());
  }

  @Test
  public void testInvokable() throws NoSuchMethodException {
    Invokable<?, Object> invokable = Invokable.from(String.class.getDeclaredMethod("endsWith", String.class));
    invokable.getParameters().forEach(p -> System.out.println(p.getType()));

    Assert.assertFalse(invokable.isOverridable());
    Assert.assertFalse(invokable.isAbstract());
    Assert.assertFalse(invokable.isPrivate());
    Assert.assertFalse(invokable.isProtected());
    Assert.assertFalse(invokable.isStatic());

  }

  @Test
  public void testProxy() {
    List list = Reflection.newProxy(List.class, new InvocationHandler() {

      private List<Object> list = new ArrayList<>();

      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(list, args);
      }
    });

    for (int i=0; i<10; i++) {
      list.add(i);
    }

    Assert.assertEquals(10, list.size());
  }

  @Test
  public void testClassPath() throws IOException {
    ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
    ImmutableSet<ClassPath.ClassInfo> classes = classPath.getTopLevelClasses("com.elim.learn.guava.basic");
    classes.forEach(clazz -> System.out.println(clazz.getName()));
  }

}
