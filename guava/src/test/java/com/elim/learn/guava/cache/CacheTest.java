package com.elim.learn.guava.cache;

import com.google.common.cache.*;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Elim
 * 19-3-3
 */
public class CacheTest {

  @Test
  public void test1() throws Exception {
    Cache<String, Integer> cache = CacheBuilder.<String, Integer>newBuilder().maximumSize(10).build();
    for (int i = 0; i < 20; i++) {
      cache.put("KEY_" + i, i);
    }
    Assert.assertEquals(10, cache.size());

    ConcurrentMap<String, Integer> cacheMap = cache.asMap();
    Assert.assertTrue(cacheMap.values().containsAll(ImmutableSet.of(10, 11, 12, 13, 14, 15, 16, 17, 18, 19)));

    Assert.assertNull(cache.getIfPresent("ABC"));
    Integer abcResult = cache.get("ABC", () -> 123);
    Assert.assertEquals(abcResult.intValue(), 123);
    Assert.assertEquals(cache.getIfPresent("ABC").intValue(), 123);
  }

  @Test
  public void testLoadingCache() {
    LoadingCache<String, Integer> loadingCache = CacheBuilder.newBuilder().build(new CacheLoader<String, Integer>() {
      @Override
      public Integer load(String key) throws Exception {
        //不存在时就调用load方法
        return key.hashCode();
      }
    });

    try {
      Assert.assertEquals(65, loadingCache.get("A").intValue());
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    Assert.assertEquals(66, loadingCache.getUnchecked("B").intValue());
  }

  @Test
  public void testWeigher() {
    //maximumWeight指定所有元素的总权重
    Cache<String, Integer> cache = CacheBuilder.newBuilder()
            .maximumWeight(100).weigher((String key, Integer value) -> {
              int weight = key.length() + value;
              return weight;
            }).build();
    for (int i=0; i<10; i++) {
      cache.put("Key_" + i, (i+1) * 5);
    }
    System.out.println(cache.size());
    cache.asMap().forEach((key, value) -> {
      System.out.println(key + "=" + value);
    });
  }

  @Test
  public void testEvict() throws InterruptedException {
    CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
//    cacheBuilder.expireAfterAccess(Duration.ofSeconds(1));//读或写后一秒过期
    cacheBuilder.expireAfterWrite(1, TimeUnit.SECONDS);//写后一秒过期
    Cache<Object, Object> cache = cacheBuilder.build();
    cache.put("A", 1);
    Assert.assertEquals(1, cache.getIfPresent("A"));
    TimeUnit.MILLISECONDS.sleep(1500);
    Assert.assertNull(cache.getIfPresent("A"));


    cache.invalidate("A");//移除某个Key
    cache.invalidateAll(Lists.newArrayList("A", "B", "C"));//移除指定的Key
    cache.invalidateAll();//移除所有


    CacheBuilder.newBuilder().removalListener(notification -> {
      notification.getKey();
      notification.getValue();
      RemovalCause cause = notification.getCause();//Enum
    });

    //默认情况下RemovalListener是同步调用的，如果需要异步调用就使用RemovalListeners.asynchronous包装一下。
    CacheBuilder.newBuilder().removalListener(RemovalListeners.asynchronous(notification -> {

    }, Executors.newFixedThreadPool(5)));

    cacheBuilder
            .refreshAfterWrite(Duration.ofSeconds(10)) //刷新，通过CacheLoader的reload()，默认等同于load()。
            .recordStats();//记录统计信息
  }

  public void testReferecendBasedEvict() {
    CacheBuilder
            .newBuilder()
            .weakKeys()
            .weakValues()
            .softValues()
            .build();
  }

}
