package com.elim.learn.springboot.spring.core;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class GitbookGeneSummary {

	private static final Path ROOT = Paths.get("/home/elim/dev/projects/elim168.github.io");
	
	@Test
	public void gene() throws Exception {
		int level = 0;
		this.printInfo(ROOT, level);
	}
	
	private void printInfo(Path dir, int level) throws IOException {
		DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
		List<Path> streams = new ArrayList<>();
		for (Path path : stream) {
			streams.add(path);
		}
		Collections.sort(streams);
		for (Path path : streams) {
			String name = path.getName(path.getNameCount()-1).toString();
			if (name.startsWith(".")) {
				continue;
			}
			if (Files.isDirectory(path)) {
				System.out.println(this.build(level, name, null));
				this.printInfo(path, level+1);
			} else {
				if (level == 0 || !name.endsWith(".md")) {//第一层的非目录或非md文件不输出
					continue;
				}
				String linkPath = ROOT.relativize(path).toString();
				System.out.println(this.build(level, name, linkPath));
			}
		}
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
	 * @param count
	 * @return
	 */
	private String getSpace(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-->0) {
			builder.append("  ");
		}
		return builder.toString();
	}
	
}
