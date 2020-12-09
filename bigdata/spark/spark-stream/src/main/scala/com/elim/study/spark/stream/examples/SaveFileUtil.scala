package com.elim.study.spark.stream.examples

import java.io.FileWriter
import java.nio.file.{Files, Paths}
import java.util.UUID

object SaveFileUtil {

  def main(args: Array[String]): Unit = {
    val dir = "./data/streaming-files"
    if (Files.notExists(Paths.get(dir))) {
      Files.createDirectories(Paths.get(dir))
    }
    while (true) {
      val uuid = UUID.randomUUID().toString
      val writer = new FileWriter(dir + "/" + uuid)
      writer.write("hello")
      writer.write("\n")
      writer.write("world")
      writer.close()
      println(s"FileName=${uuid}")
      Thread.sleep(3000)
    }
  }

}
