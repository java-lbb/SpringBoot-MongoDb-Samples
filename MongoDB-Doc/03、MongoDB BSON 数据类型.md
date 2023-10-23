# MongoDB BSON 数据类型

BSON 是一种二进制序列化格式，用于在 MongoDB 中存储文档和进行远程过程调用。

<img src="..\images\bson-serialization-format.png" alt="bson-serialization-format" style="zoom:67%;" />

每个 BSON 类型都具有整数和字符串标识符，如下表所示：

| Type                       | Number | Alias                 | Notes                      |
| :------------------------- | :----- | :-------------------- | :------------------------- |
| Double                     | 1      | "double"              |                            |
| String                     | 2      | "string"              |                            |
| Object                     | 3      | "object"              |                            |
| Array                      | 4      | "array"               |                            |
| Binary data                | 5      | "binData"             |                            |
| Undefined                  | 6      | "undefined"           | Deprecated.                |
| ObjectId                   | 7      | "objectId"            |                            |
| Boolean                    | 8      | "bool"                |                            |
| Date                       | 9      | "date"                |                            |
| Null                       | 10     | "null"                |                            |
| Regular Expression         | 11     | "regex"               |                            |
| DBPointer                  | 12     | "dbPointer"           | Deprecated.                |
| JavaScript                 | 13     | "javascript"          |                            |
| Symbol                     | 14     | "symbol"              | Deprecated.                |
| JavaScript code with scope | 15     | "javascriptWithScope" | Deprecated in MongoDB 4.4. |
| 32-bit integer             | 16     | "int"                 |                            |
| Timestamp                  | 17     | "timestamp"           |                            |
| 64-bit integer             | 18     | "long"                |                            |
| Decimal128                 | 19     | "decimal"             |                            |
| Min key                    | -1     | "minKey"              |                            |
| Max key                    | 127    | "maxKey"              |                            |



### 一、ObjectId

ObjectIds是小型、可能唯一、生成速度快且有序的。ObjectId值长度为12字节，包括：
- 4字节的时间戳值，表示ObjectId的创建时间，以自Unix纪元以来的秒数为单位。

- 每个进程一次生成的5字节随机值。此随机值对于该机器和进程是唯一的。

- 3字节的递增计数器，初始化为随机值。

ObjectId的详情操作可查阅官方文档 [Object Constructors and Methods](https://www.mongodb.com/docs/v4.4/reference/method/js-constructor/)



### 二、String

BSON字符串是UTF-8编码的。

通常情况下，每种编程语言的驱动程序在序列化和反序列化BSON时会将其转换为UTF-8格式的字符串。这样就可以轻松地存储大多数国际字符在BSON字符串中。此外，MongoDB的`$regex`查询也支持UTF-8编码的正则表达式字符串。

对于使用UTF-8字符集的字符串，使用`sort()`函数进行排序可能会得到合理的结果。然而，由于`sort()`函数在内部使用C++的strcmp API，因此排序顺序可能会对某些字符处理不正确。



### 三、Timestamps

BSON在MongoDB内部使用中具有特殊的时间戳类型，与常规的日期类型（Date）无关。这个内部时间戳类型是一个64位字节值，其中：

- 最高32位是一个time_t值（自Unix纪元以来的秒数）

- 最低32位是在给定秒内操作的递增序号。
- 在单个mongod实例中，时间戳值始终是唯一的。

> BSON 时间戳类型供 MongoDB 内部使用。 对于大多数情况，在应用程序开发中，大多数使用 Date 类型。



### 四、Date

BSON Date 是一个 64位字节整数，表示自 Unix 纪元（1970 年 1 月 1 日）以来的毫秒数。 这导致可表示的日期范围为过去和未来约 2.9 亿年。

官方 BSON 规范将 BSON Date 类型称为 UTC 日期时间。

BSON 日期类型有符号。 负值代表 1970 年之前的日期。



。。。。。。



