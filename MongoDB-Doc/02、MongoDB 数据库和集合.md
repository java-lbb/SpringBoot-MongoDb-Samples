# MongoDB 数据库和集合

MongoDB 将数据记录存储为文档（特别是 BSON 文档），这些文档聚集在集合中。 数据库存储一个或多个文档集合。



### 一、数据库

在 MongoDB 中，数据库存储一个或多个文档集合。 

要选择要使用的数据库，请在 mongo shell 中发出 `use <db>` 语句，如下例所示：

```java
user myDB
```

#### 1. 创建一个数据库

如果数据库不存在，MongoDB 会在您首次存储该数据库的数据时创建该数据库。 因此，您可以切换到不存在的数据库并在 mongo shell 中执行以下操作：

```javascript
use myNewDB

db.myNewCollection1.insertOne( { x: 1 } )
```

`insertOne()` 操作会创建数据库 myNewDB 和集合 myNewCollection1（如果它们尚不存在）。 确保数据库和集合名称都遵循 MongoDB 命名限制。



### 二、集合

MongoDB 将文档存储在集合中。 集合类似于关系数据库中的表。

![collection](..\images\collection.png)

#### 1. 创建一个集合

如果集合不存在，MongoDB 会在您首次存储该集合的数据时创建该集合。

```javascript
db.myNewCollection2.insertOne( { x: 1 } )
db.myNewCollection3.createIndex( { y: 1 } )
```

insertOne() 和 createIndex() 操作都会创建各自的集合（如果它们尚不存在）。

#### 2. 显示创建

MongoDB 提供 `db.createCollection()` 方法来显式创建具有各种选项的集合，例如设置最大大小或文档校验规则。 如果您未指定这些选项，则无需显式创建集合，因为 MongoDB 会在您首次存储集合数据时创建新集合。

要修改这些集合选项，请参阅 [collMod](https://www.mongodb.com/docs/v4.4/reference/command/collMod/#mongodb-dbcommand-dbcmd.collMod)。

#### 3. 文档校验

默认情况下，集合不要求其文档具有相同的结构； 即单个集合中的文档不需要具有相同的字段集，并且字段的数据类型可以在集合内的不同文档中不同。

但是，从 MongoDB 3.2 开始，您可以在更新和插入操作期间对集合强制执行[文档校验规则](https://www.mongodb.com/docs/v4.4/core/schema-validation/)。 有关详细信息，请参阅[模式验证](https://www.mongodb.com/docs/v4.4/core/schema-validation/)。

#### 4. 修改文档结构

要更改集合中文档的结构（例如添加新字段、删除现有字段或将字段值更改为新类型）。

#### 5. 唯一标识符

集合被分配一个不可变的 UUID。 集合 UUID 在副本集的所有成员和分片集群中的分片中保持不变。

要检索集合的 UUID，请运行 `db.getCollectionInfos()` 方法。



### 三、文档

MongoDB 将数据记录存储为 BSON 文档。 BSON 是 JSON 文档的二进制表示形式，它包含比 JSON 更多的数据类型。

![document](..\images\document.png)

#### 1. 文档结构

MongoDB 文档由字段-值对组成，具有以下结构：

```java
{
   field1: value1,
   field2: value2,
   field3: value3,
   ...
   fieldN: valueN
}
```

字段的值可以是任何 BSON 数据类型，包括其他文档、数组和文档数组。 例如，以下文档包含不同类型的值：

```javascript
var mydoc = {
               _id: ObjectId("5099803df3f4948bd2f98391"),
               name: { first: "Alan", last: "Turing" },
               birth: new Date('Jun 23, 1912'),
               death: new Date('Jun 07, 1954'),
               contribs: [ "Turing machine", "Turing test", "Turingery" ],
               views : NumberLong(1250000)
            }
```

以上字段具有以下数据类型：

- _id 是一个 ObjectId。
- name 是一个嵌入文档，包含字段 first 和 last。
- birth 和 death 是 Date 类型的值。
- contribs 是一个字符串数组。
- views 是 NumberLong 类型的值。

##### 1) 字段名

字段名为字符串类型，文档对字段名有以下限制：

- 字段名 _id 保留用作主键；其值必须在集合中唯一，是不可变的，并且可以是除了数组之外的任何类型。

- 字段名不能包含空字符。

- BSON文档可能具有多个相同名称的字段。然而，大多数MongoDB接口使用不支持重复字段名称的结构（例如哈希表）来表示MongoDB。

- 顶级字段名不能以美元符号（$）字符开头。

  在 MongoDB 3.6 开始，服务器允许存储包含点（即 .）和美元符号（即 $）的字段名。

##### 2) 字段值限制

​	对于索引集合，索引字段的值具有最大索引键长度。 



#### 2. 点符号

MongoDB 使用点`.`符号来访问数组的元素和嵌入文档的字段。

##### 1) 数组

基本语法：

```javascript
"<array>.<index>"
```

例如如下文档中的属性以及数组值

```javascript
{
   ...
   contribs: [ "Turing machine", "Turing test", "Turingery" ],
   ...
}
```

要指定 contribs 数组中的第三个元素，请使用点符号`“contribs.2”`。

##### 2) 嵌入式文档

基本语法：

```javascript
"<embedded document>.<field>"
```

例子：

```javascript
{
   ...
   name: { first: "Alan", last: "Turing" },
   contact: { phone: { type: "cell", number: "111-222-3333" } },
   ...
}
```

要指定名称字段中最后一个名称的字段，使用点符号`“name.last”`。

要在联系人字段中指定电话文档中的号码，使用点符号`“contact.phone.number”`。



#### 3. 文档限制

##### 1) 文档大小限制

一条BSON 文档的最大大小为 16 MB。

最大文档大小有助于确保单个文档不会使用过多的 RAM，或者在传输过程中不会使用过多的带宽。 为了存储大于最大大小的文档，MongoDB 提供了 GridFS API

##### 2) 文档字段顺序

与 JavaScript 对象不同，BSON 文档中的字段是有序的。

###### i. 在查询中的字段顺序

- 比较文档时，字段排序很重要。 例如，在查询中比较文档与字段 a 和 b 时：

  - `{a: 1, b: 1}` is equal to `{a: 1, b: 1}`

  - `{a: 1, b: 1}` is not equal to `{b: 1, a: 1}`

- 为了高效执行查询，查询引擎可以在查询处理期间对字段重新排序。

###### ii. 在写操作中的字段顺序

对于写操作，MongoDB保留文档字段的顺序，但有两种情况除外：
- `_id`字段始终是文档中的第一个字段。

- 包含字段重命名的更新可能会导致文档中字段的重新排序。

##### 3) `_id`属性

在 MongoDB 中，存储在集合中的每个文档都需要一个唯一的 `_id` 字段作为主键。 如果插入的文档省略 `_id` 字段，MongoDB 驱动程序会自动为`_id`字段生成一个 ObjectId。

这也适用于通过 upsert: true 的更新操作插入的文档。

`_id` 字段具有以下行为和约束：

- 默认情况下，MongoDB 在创建集合期间在 `_id` 字段上创建唯一索引。


- `_id` 字段始终是文档中的第一个字段。 如果服务器首先收到一个没有 _id 字段的文档，那么服务器会将该字段移动到开头。

- `_id` 字段可以包含除数组、正则表达式或未定义之外的任何 BSON 数据类型的值。

以下是存储`_id`字段的常见选项

- 使用ObjectId。

- 使用自然唯一标识符, 这样可以节省空间并避免额外的索引。

- 生成自动递增的数字。

- 在应用程序代码中生成UUID。为了更有效地存储集合和`_id`索引中的UUID值，将UUID存储为BSON `BinData`类型的值。

- 如果索引键是`BinData`类型，并且二进制子类型值在0-7或128-135范围内，并且字节数组的长度为0、1、2、3、4、5、6、7、8、10、12、14、16、20、24或32，则`BinData`类型的索引键在索引中的存储更有效。

- 使用驱动程序的BSON UUID工具生成UUID。请注意，驱动程序实现可能以不同的方式实现UUID序列化和反序列化逻辑，这可能与其他驱动程序不完全兼容。有关UUID互操作性的信息，请参阅[驱动程序文档](https://api.mongodb.com/?_ga=2.95567768.567121885.1697525627-722909773.1686126060)。

