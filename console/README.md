## 使用指南

HydraQL-Console 编译

先编译需要的插件

```shell
## 默认hbase版本1.2.0
mvn -T 2C clean -U install -pl shaded/shaded-hbase/ -Dmaven.test.skip=true
mvn -T 2C clean -U install -pl plugins/ -Dmaven.test.skip=true

# 激活指定版本
mvn -T 2C clean -U install -pl shaded/shaded-hbase/ -Dmaven.test.skip=true -Dhydraql.hbase.profile=1.4 -Dhydraql.hbase.version=1.4.3
mvn -T 2C clean -U install -pl plugins/ -Dmaven.test.skip=true -Dhydraql.hbase.profile=1.4 -Dhydraql.hbase.version=1.4.3

mvn -T 2C clean -U install -pl shaded/shaded-hbase/ -Dmaven.test.skip=true -Dhydraql.hbase.profile=2.2 -Dhydraql.hbase.version=2.2.6
mvn -T 2C clean -U install -pl plugins/ -Dmaven.test.skip=true -Dhydraql.hbase.profile=2.2 -Dhydraql.hbase.version=2.2.6


```
# 编译console

```shell
## 默认hbase版本1.2.0
mvn -T 2C clean -U package -pl console/ -Dmaven.test.skip=true
```