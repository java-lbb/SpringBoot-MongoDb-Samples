# MongoDB  查询操作

MongoDB 中的所有查询语句的**语法规则**都类比于`json`格式！

### 一、查询文档

首先，我们往inventory商品清单集合中插入如下数据

```javascript
db.inventory.insertMany( [
   { item: "journal", qty: 25, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
   { item: "notebook", qty: 50, size: { h: 8.5, w: 11, uom: "in" }, status: "A" },
   { item: "paper", qty: 100, size: { h: 8.5, w: 11, uom: "in" }, status: "D" },
   { item: "planner", qty: 75, size: { h: 22.85, w: 30, uom: "cm" }, status: "D" },
   { item: "postcard", qty: 45, size: { h: 10, w: 15.25, uom: "cm" }, status: "A" }
]);
```

#### 1. 查询集合中所有文档数据

```javascript
db.inventory.find( {} )
```

等价于mysql中的

```mysql
SELECT * FROM inventory
```

#### 2.指定"相等"条件

##### 1) 语法

```javascript
{ <field1>: <value1>, ... }
```

##### 2) 示例

```javascript
db.inventory.find( { status: "D" } )
```

等价于sql中的

```mysql
SELECT * FROM inventory WHERE status = "D"
```

#### 3.使用查询运算符指定条件

##### 1) 语法

```javascript
{ <field1>: { <operator1>: <value1> }, ... }
//{ 字段1: { 运算符1: 值1 }, ...}
```

##### 2) 示例

```javascript
db.inventory.find( { status: { $in: [ "A", "D" ] } } )
```

等价于sql中

```mysql
SELECT * FROM inventory WHERE status in ("A", "D")
```

#### 4. 指定 `AND` 条件

##### 1) 示例

```javascript
// 实际上是隐式指定 `and` 条件
db.inventory.find( { status: "A", qty: { $lt: 30 } } )
```

等价于sql中的

```sql
SELECT * FROM inventory WHERE status = "A" AND qty < 30
```

#### 5. 指定 `OR` 条件

##### 1) 示例

```javascript
db.inventory.find( { $or: [ { status: "A" }, { qty: { $lt: 30 } } ] } )
```

等价于sql中的

```sql
SELECT * FROM inventory WHERE status = "A" OR qty < 30
```

#### 6. 指定`AND`条件和`OR`条件

##### 1) 示例

```javascript
db.inventory.find( {
   status: 'A',
   $or: [
     { qty: { $lt: 30 } }, { item: { $regex: '^p' } }
   ]
})

```

> [$regex](https://www.mongodb.com/docs/v4.4/reference/operator/query/regex/#mongodb-query-op.-regex)为正则表达式运算符

等价于sql中的

```sql
SELECT * FROM inventory WHERE status = "A" AND ( qty < 30 OR item LIKE "p%")
```



### 二、查询嵌套文档

插入如下文档数据

```javascript
// 其中 size 为该条文档数据中的嵌套文档
// uom 表示 Unit Of Measure 即计量单位；
db.inventory.insertMany( [
   { item: "journal", qty: 25, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
   { item: "notebook", qty: 50, size: { h: 8.5, w: 11, uom: "in" }, status: "A" },
   { item: "paper", qty: 100, size: { h: 8.5, w: 11, uom: "in" }, status: "D" },
   { item: "planner", qty: 75, size: { h: 22.85, w: 30, uom: "cm" }, status: "D" },
   { item: "postcard", qty: 45, size: { h: 10, w: 15.25, uom: "cm" }, status: "A" }
]);
```

#### 1. 通过 `.` 符号来查询嵌套字段

> - [x] 注意：使用点 `.` 符号查询时，字段和嵌套字段必须在`" "`引号内。

##### 1) 指定"相等"条件在嵌套字段中

```javascript
// 查询 size 中 uom 等于 in (inch英寸) 的文档
db.inventory.find( { "size.uom": "in" } )
```

##### 2) 指定`and`运算符

```javascript
// 查询 size 中 h 小于15，并且 size 的 计量单位为in的，且 status 为 D 的文档
db.inventory.find( { "size.h": { $lt: 15 }, "size.uom": "in", status: "D" } )
```

#### 2.  匹配嵌套文档

##### 1) 语法

```javascript
{ <field>: <value> } 其中value为一个文档
```

##### 2) 示例

```javascript
// 查询 size 字段等于 { h: 14, w: 21, uom: "cm" } 该嵌套文档的所有文档
db.inventory.find( { size: { h: 14, w: 21, uom: "cm" } } )
```

> - [x] 注意：MongoDB 不建议对嵌入文档进行“相等”匹配，因为操作需要指定的 `<value>` 文档的**精确匹配**，包括**字段顺序**。
>
>   例如，以下查询与库存集合中的任何文档均不匹配 ：
>
>   db.inventory.find( { size: { w: 21, h: 14, uom: "cm" } } )



### 三、查询一个数组

插入如下清单数据

```javascript
db.inventory.insertMany([
   { item: "journal", qty: 25, tags: ["blank", "red"], dim_cm: [ 14, 21 ] },
   { item: "notebook", qty: 50, tags: ["red", "blank"], dim_cm: [ 14, 21 ] },
   { item: "paper", qty: 100, tags: ["red", "blank", "plain"], dim_cm: [ 14, 21 ] },
   { item: "planner", qty: 75, tags: ["blank", "red"], dim_cm: [ 22.85, 30 ] },
   { item: "postcard", qty: 45, tags: ["blue"], dim_cm: [ 10, 15.25 ] }
]);
// dim_cm : dimension in centimeters 以厘米为单位的尺寸维度
```

#### 1. 匹配一个数组

##### 1) 示例1

> - ags 标签中**有且仅有** red 和 blank 两个元素
>
> - 必须满足 red 、blank 的顺序

```javascript
db.inventory.find( { tags: ["red", "blank"] } )
```

##### 2) 示例2

> - tags 标签中只需**同时含有** red 和 blank 两个元素即可
>
> - 无论 red 和 blank 顺序如何

```javascript
// $all 运算符
db.inventory.find( { tags: { $all: ["red", "blank"] } } )
```

#### 2. 查询一个数组中的一个元素

##### 1) 示例1

语法

```javascript
{ <field>: <value> }  <value> 是一个元素值
```

查询语句

> - tags 数组中**至少**有一个元素为 red

```javascript
db.inventory.find( { tags: "red" } )
```

##### 2) 示例2

语法

```javascript
{ <array field>: { <operator1>: <value1>, ... } }
```

查询语句

> - dim_cm 中**至少**有一个元素要大于25

```javascript
db.inventory.find( { dim_cm: { $gt: 25 } } )
```

#### 3. 指定数组元素中的多个条件

##### 1) 示例1 :在数组元素上使用复合过滤条件查询数组

> - dim_cm 中有一个元素满足大于15，另外一个元素满足小于20或者有一个元素能同时满足两则即可

```javascript
db.inventory.find( { dim_cm: { $gt: 15, $lt: 20 } } )
```

##### 2) 示例2：查询同时满足多个条件的数组元素

> - dim_cm 数组中至少有一个元素必须同时满足大于22且小于30

```javascript
// $elemMatch 运算符针对数组中单独每一个元素要满足的条件
db.inventory.find( { dim_cm: { $elemMatch: { $gt: 22, $lt: 30 } } } )
```

##### 3) 示例3：通过数组索引位置查询元素

> - 查询 dim_cm 数组中索引为1（即第二个位置）的元素必须大于25

```java
// 注意：使用 . 符号时，必须把字段或者嵌套字段用引号括起来
db.inventory.find( { "dim_cm.1": { $gt: 25 } } )
```

##### 4) 示例4：按数组长度查询指定数组

> - 使用 `$size` 运算符可以指定数组元素的个数作为查询条件，如下是查询 tags 数组中有3个元素的文档

```javascript
db.inventory.find( { "tags": { $size: 3 } } )
```



### 四、查询嵌套文档数组

插入该集合中如下数据
```javascript
db.inventory.insertMany( [
   { item: "journal", instock: [ { warehouse: "A", qty: 5 }, { warehouse: "C", qty: 15 } ] },
   { item: "notebook", instock: [ { warehouse: "C", qty: 5 } ] },
   { item: "paper", instock: [ { warehouse: "A", qty: 60 }, { warehouse: "B", qty: 15 } ] },
   { item: "planner", instock: [ { warehouse: "A", qty: 40 }, { warehouse: "B", qty: 5 } ] },
   { item: "postcard", instock: [ { warehouse: "B", qty: 15 }, { warehouse: "C", qty: 35 } ] }
]);
```

#### 1. 查询嵌套在数组中的文档

> - 查询 instock 数组元素中含有符合下列指定文档的所有文档数据

```javascript
db.inventory.find( { "instock": { warehouse: "A", qty: 5 } } )
```

- [x] 注意：嵌入/嵌套文档中的的"相等"匹配条件需要与指定文档**精确匹配**，包括**字段顺序**。 例如，以下查询与库存集合中的任何文档均不匹配：

```javascript
db.inventory.find( { "instock": { qty: 5, warehouse: "A" } } )
```

#### 2. 指定文档数组中字段的查询条件

##### 1) 示例1：指定嵌入在文档数组中的字段的查询条件

> - 如果您不知道数组中嵌套文档的索引位置，请使用点 `.` 连接数组字段的名称和嵌套文档中的字段名称。
> - 以下示例是查询出满足 instock 数组中**至少**有一个嵌入文档中字段 qty 值小于或等于 20 条件的所有文档：

```javascript
db.inventory.find( { 'instock.qty': { $lte: 20 } } )
```

##### 2) 示例2:  使用数组索引查询嵌入文档中的字段

- [x] 再次强调：使用点`.`表示法查询时，字段和索引必须位于引号 `" "` 或者`' '`内 。

> - 使用点表示法，您可以指定文档中位于数组的特定索引或位置的字段的查询条件。 该数组是以零开始的索引。
>- 如下是查询在 instock 数组第一个元素的嵌套文档中 qty 字段值小于或等于 20  的所有文档

```javascript
db.inventory.find( { 'instock.0.qty': { $lte: 20 } } )
```

#### 3. 为文档数组指定多个条件

当对嵌套在文档数组中的多个字段指定条件时，可以指定查询条件，使得单个文档满足这些条件，或者数组中的任意文档组合（包括单个文档）满足这些条件。

##### 1) 示例1：单个嵌套文档满足嵌套字段中的多个查询条件

> - 使用 `$elemMatch` 运算符在一组嵌入文档上指定多个条件，以便至少一个嵌入文档满足所有指定的条件。
> - 以下查询示例需满足如下条件： instock 数组中至少有一个嵌入文档，且其中字段 qty 等于 5 且字段warehouse等于 A 

```javascript
db.inventory.find( { "instock": { $elemMatch: { qty: 5, warehouse: "A" } } } )
```

> - 以下查询示例需满足如下条件： instock 数组中至少有一个嵌入文档，且其中字段 qty 大于10并且小于等于20

```javascript
db.inventory.find( { "instock": { $elemMatch: { qty: { $gt: 10, $lte: 20 } } } } )
```

##### 2) 示例2:  满足条件的元素组合

- [x] 注意：如果数组字段上的复合查询条件不使用 `$elemMatch` 运算符，则语句选择查询出那些其数组包含满足条件的任意元素组合的文档。

> - 例如，以下查询只需满足：
>
>   instock 数组中的一个嵌套文档的 qty 字段大于 10 ，一个嵌套文档（不一定是与前面相同的嵌入文档）的 qty 字段小于或等于 20 。即不要求同时满足这些条件的是同一个文档

```javascript
db.inventory.find( { "instock.qty": { $gt: 10,  $lte: 20 } } )
```

> - 如下例子同理, 该数组中一个嵌入文档的 qty 等于10，另一个嵌入文档的 warehouse 等于 A 即可。不要求两者是同一个嵌入文档

```javascript
db.inventory.find( { "instock.qty": 5, "instock.warehouse": "A" } )
```



### 五、Project (投影/筛选)从查询结果集中的字段

默认情况下，MongoDB 中的查询返回匹配文档中的所有字段。 要限制 MongoDB 发送到应用程序的数据量，可以在查询中包含投影文档来**指定或限制**要返回的字段。

插入如下文档数据

```javascript
db.inventory.insertMany( [
  { item: "journal", status: "A", size: { h: 14, w: 21, uom: "cm" }, instock: [ { warehouse: "A", qty: 5 } ] },
  { item: "notebook", status: "A",  size: { h: 8.5, w: 11, uom: "in" }, instock: [ { warehouse: "C", qty: 5 } ] },
  { item: "paper", status: "D", size: { h: 8.5, w: 11, uom: "in" }, instock: [ { warehouse: "A", qty: 60 } ] },
  { item: "planner", status: "D", size: { h: 22.85, w: 30, uom: "cm" }, instock: [ { warehouse: "A", qty: 40 } ] },
  { item: "postcard", status: "A", size: { h: 10, w: 15.25, uom: "cm" }, instock: [ { warehouse: "B", qty: 15 }, { warehouse: "C", qty: 35 } ] }
]);
```

#### 1. 默认返回所有字段

```javascript
db.inventory.find( { status: "A" } )
```

等价于sql中

```sql
SELECT * from inventory WHERE status = "A"
```

#### 2. 返回指定字段

通过在投影文档中将` <field> `设置为 **1**，投影可以**显式包含**多个字段。

> - 下列查询结果集中，匹配文档中仅返回 item、status 以及默认情况下会返回的 _id 字段。

```javascript
// { item: 1, status: 1 } 这就是一个投影文档-> projection document
db.inventory.find( { status: "A" }, { item: 1, status: 1 } )
```

#### 3. 去除`_id`字段

> - 可以通过在投影中将 `_id` 字段设置为 0 从结果中删除该字段，如下例所示：

> 通过显示指定 `_id` 字段为**0**

```javascript
db.inventory.find( { status: "A" }, { item: 1, status: 1, _id: 0 } )
```

- [x] 注意：除 `_id` 字段外，在投影文档中不能同时具有"包含"和"排除语句。

  即如下写法是错误的:

```javascript
//不能一部分字段指定1，一部分字段指定0，要么指定字段显示"包含"，要么指定字段显示"排除"。
db.inventory.find( { status: "A" }, { item: 1, status: 1, size: 0, instock:0 } )
```

#### 4. 返回除了"排除"字段以外的所有字段

> - 除了 `status` 和 `instock` 字段，其他字段都返回

```javascript
db.inventory.find( { status: "A" }, { status: 0, instock: 0 } )
```

#### 5. 返回在嵌套文档中的指定字段

> - 使用点 `.` 符号来引用嵌入字段并在投影文档中设置为 1。

指定下列字段返回:

- `_id` 字段 (默认会返回)
- `item` 字段
- `status` 字段
- `size` 文档 中的 `uom` 字段 ，该 `uom` 字段嵌入在 `size` 文档中

```javascript
db.inventory.find(
   { status: "A" },
   { item: 1, status: 1, "size.uom": 1 }
)
```

> - [x] 从 MongoDB 4.4 开始 ，可以使用文档形式来指定嵌入字段的投影值(1或0)，
>
>   比如：{ item: 1, status: 1, size: { uom: 1 } }

#### 6. 排除在嵌套文档中的指定字段

```javascript
// size 文档中除了 uom 字段，其余字段都返回
db.inventory.find(
   { status: "A" },
   { "size.uom": 0 }
)
```

> - 同理，从 MongoDB 4.4 开始 ，可以使用该形式：{ size: { uom: 0 } }

#### 7. 投影嵌入在数组中的文档内的特定字段

> - 其中 在 `instock` 数组中的嵌入文档内只返回 `qty` 字段

```javascript
db.inventory.find( { status: "A" }, { item: 1, status: 1, "instock.qty": 1 } )
```

#### 8. 返回数组中的指定数量元素

> - `$slice` 操作符返回 instock 数组中元素的数量，如果 instock 数组有三个元素，那么那么 `$slice:1` 只会返回第一个。为2则按顺序返回2个，如果是负数则返回倒数第n个。-1为倒数第一个，-2为倒数两个

```javascript
db.inventory.find( { status: "A" }, { item: 1, status: 1, instock: { $slice: 1 } } )
```

- [x] 注意：$[elemMatch](https://www.mongodb.com/docs/v4.4/reference/operator/projection/elemMatch/#mongodb-projection-proj.-elemMatch)、[$slice](https://www.mongodb.com/docs/v4.4/reference/operator/projection/slice/#mongodb-projection-proj.-slice) 和 [$ ](https://www.mongodb.com/docs/v4.4/reference/operator/projection/positional/#mongodb-projection-proj.-)是投影特定元素以包含在返回的数组中的唯一方法。 例如，不能使用数组索引来投影特定的数组元素； 例如` { "instock.0": 1 }` 并不会只投影数组中的第一个元素



### 六、查询null或者不存在的字段

插入如下数据

```javascript
db.inventory.insertMany([
   { _id: 1, item: null },
   { _id: 2 }
])
```

#### 1. 查询某字段null的文档

```javascript
db.inventory.find( { item: null } )
```

#### 2. 字段类型检查

> - 只匹配 `item` 字段类型为 **10** 的唯一数字标识
> - 如下图，10 对应的 Bson 类型为 Null

```javascript
db.inventory.find( { item : { $type: 10 } } )
```

<img src="..\images\Bson Type.jpeg" alt="Bson Type" style="zoom: 80%;" />

#### 3. 字段是否存在检查

> - 下列示例中只匹配不存在 item 的字段的文档

```javascript
db.inventory.find( { item : { $exists: false } } )
```

