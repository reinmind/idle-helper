# IdleHelper
帮助你实现服务器真正意义上的`"负载均衡"`,智能调节使得服务器CPU负载运行在规定的百分比左右

## 1.package
```shell
mvn clean install spring-boot:repackage
```

## 2.run
```shell
java -jar idle-helper-1.0.jar [loadFactor] # loadFactor 为区间0-1的浮点数，默认值为0.6 
```
