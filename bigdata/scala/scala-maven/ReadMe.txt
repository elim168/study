直接编译或打包时可能会报错，提示Class找不到之类的，这是由于Scala未编译的结果。
需要在指令中加上scala:compile以编译Scala代码。

mvn clean scala:compile compile package