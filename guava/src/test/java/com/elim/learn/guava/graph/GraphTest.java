package com.elim.learn.guava.graph;

import com.google.common.collect.Sets;
import com.google.common.graph.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * 一共有三种类型的图，Graph/ValueGraph/Network
 * @author Elim
 * 19-2-24
 */
public class GraphTest {

  @Test
  public void testBuildGraph() {
    MutableGraph<Integer> graph = GraphBuilder.undirected().build();
    graph.addNode(1);
    graph.addNode(2);
    graph.addNode(3);
    graph.addNode(4);
    graph.addNode(5);
    graph.addNode(6);
    graph.putEdge(1, 2);
    graph.putEdge(1, 3);
    graph.putEdge(1, 4);
    graph.putEdge(5, 1);
    graph.putEdge(3, 4);
    graph.putEdge(3, 5);
    graph.putEdge(3, 6);
    graph.putEdge(4, 7);
    graph.putEdge(7, 8);
    Assert.assertTrue(graph.removeNode(8));
    Assert.assertFalse(graph.removeNode(9));
    // 获取与节点1有边连起来的节点，2/3/4/5
    Set<Integer> adjacentNodes1 = graph.adjacentNodes(1);
    Assert.assertEquals(4, adjacentNodes1.size());
    Assert.assertTrue(adjacentNodes1.equals(Sets.newHashSet(2, 3, 4, 5)));

    Assert.assertTrue(graph.removeEdge(1, 4));
    Assert.assertEquals(2, graph.adjacentNodes(1).size());

    Assert.assertEquals(2, graph.degree(1));
    Assert.assertFalse(graph.isDirected());
    Assert.assertTrue(graph.inDegree(1) == graph.outDegree(1));
    Assert.assertTrue(graph.inDegree(1) == graph.degree(1));//因为它是无向的
  }

  @Test
  public void testValueGraphBuilder() {
    MutableValueGraph<String, Integer> roads = ValueGraphBuilder.undirected().build();
    roads.addNode("北京");
    roads.addNode("上海");
    roads.putEdgeValue("北京", "上海", 1000);
    roads.putEdgeValue("北京", "广州", 1100);
    roads.putEdgeValue("北京", "深圳", 1200);
    roads.putEdgeValue("上海", "深圳", 600);
    roads.putEdgeValue("上海", "广州", 700);
    roads.putEdgeValue("广州", "深圳", 100);

    Assert.assertEquals(1000, roads.edgeValue("北京", "上海").get().intValue());

  }

  @Test
  public void testNetworkBuilder() {
    //Network是从一个点到另一个点可能有多条边
    MutableNetwork<String, Integer> network = NetworkBuilder.directed().build();
    network.addEdge("A", "B", 10);
    Assert.assertEquals(10, network.edgeConnecting("A", "B").get().intValue());
  }

  @Test
  public void testImmutableGraph() {
    MutableGraph<Object> mutableGraph = GraphBuilder.undirected().build();
    mutableGraph.putEdge(1, 2);
    mutableGraph.putEdge(2, 3);

    ImmutableGraph<Object> immutableGraph = ImmutableGraph.copyOf(mutableGraph);
    Assert.assertEquals(2, immutableGraph.degree(2));

    immutableGraph.nodes().forEach(System.out::println);//1,2,3

  }

}
