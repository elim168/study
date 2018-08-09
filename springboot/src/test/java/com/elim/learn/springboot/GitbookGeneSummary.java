package com.elim.learn.springboot;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import lombok.Data;

public class GitbookGeneSummary {

    private static final String ORDER = "spring,bean,aop,mvc,boot";
    
    private static final List<String> ORDERS = Arrays.asList(ORDER.split(","));
    
    private static final Path ROOT = Paths.get("D:\\work\\dev\\git\\projects\\note");

    @Test
    public void gene() throws Exception {
        int level = 0;
        Node node = this.gene(ROOT);
        this.printNode(node, level);
    }

    private Node gene(Path dir) throws IOException {
        Node node = new Node(dir);
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
        List<Node> children = new ArrayList<>();
        for (Path path : stream) {
            if (Files.isDirectory(path)) {
                String name = path.getName(path.getNameCount() - 1).toString();
                if (name.startsWith(".") || name.startsWith("_")) {
                    continue;
                }
                Node childNode = this.gene(path);
                if (childNode.getChildren() != null && !childNode.getChildren().isEmpty()) {
                    children.add(childNode);
                }
            } else {
                if (!path.toString().endsWith(".md")) {
                    continue;
                }
                children.add(new Node(path));
            }
        }
        node.setChildren(children);
        return node;
    }

    private void printNode(Node node, int level) {
        List<Node> children = node.getChildren();
        if (children != null && !children.isEmpty()) {
            Collections.sort(children);
            int i = 0;
            for (Node child : children) {
                Path path = child.getPath();
                String name = getPathName(path);
                if (Files.isDirectory(path)) {
                    System.out.println(this.build(level, name, null));
                    this.printNode(child, level + 1);
                } else {
                    if (level == 0) {
                        continue;
                    }
                    name = ++i + "." + name.replaceFirst("(\\d+\\.)+", "");
                    String linkPath = ROOT.relativize(path).toString().replaceAll("\\\\", "/");
                    System.out.println(this.build(level, name, linkPath));
                }
            }
        }
    }
    
    private static String getPathName(Path path ) {
        String name = path.getName(path.getNameCount() - 1).toString();
        return name;
    }

    private String build(int level, String name, String linkPath) {
        if (name.lastIndexOf(".") != -1) {
            name = name.substring(0, name.lastIndexOf("."));
        }
        StringBuilder builder = new StringBuilder();
        builder.append(this.getSpace(level)).append("* ");
        if (linkPath == null) {
            builder.append(name);
        } else {
            builder.append("[").append(name).append("](").append(linkPath).append(")");
        }
        return builder.toString();
    }

    /**
     * 获取空格
     * 
     * @param count
     * @return
     */
    private String getSpace(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- > 0) {
            builder.append("  ");
        }
        return builder.toString();
    }

    @Data
    private static class Node implements Comparable<Node> {
        private final Path path;

        private List<Node> children;

        @Override
        public int compareTo(Node o) {
            boolean thisDir = Files.isDirectory(this.path);
            boolean otherDir = Files.isDirectory(o.path);
            if (thisDir == otherDir) {
                if (thisDir) {
                    int order1 = ORDERS.indexOf(getPathName(this.path));
                    int order2 = ORDERS.indexOf(getPathName(o.path));
                    if (order1 != order2) {
                        if (order1 > order2) {
                            return order2;
                        } else {
                            return -order1;
                        }
                    }
                }
                return this.path.compareTo(o.path);
            } else if (thisDir) {
                return 1;
            } else {
                return -1;
            }
        }
    }

}
