package com.elim.study.hbase.sample;

/**
 * 需求：
 * <ul>
 *     <li>添加、查看关注</li>
 *     <li>粉丝列表</li>
 *     <li>写微博</li>
 *     <li>查看首页，所有关注过的好友发布的最新微博</li>
 *     <li>查看某个用户发布的所有微博，时间倒序排列</li>
 * </ul>
 *
 * 主要是考验对rowkey的设计，通过列族的设计保存关联关系，列族中的列保存关联的信息的主键
 */
public class WeiboTest extends AbstractTest {
}
