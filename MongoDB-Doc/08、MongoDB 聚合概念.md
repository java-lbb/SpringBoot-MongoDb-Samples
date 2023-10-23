# MongoDB Aggregation 聚合概念

聚合操作可以处理多个文档并返回计算结果。 您可以使用聚合操作来：

- 将多个文档的值分组在一起
- 对分组数据执行操作以返回单个结果
- 分析数据随时间的变化

### 一、 Aggregation Pipelines 聚合管道

聚合管道由一个或多个处理文档的阶段组成：

- 每个阶段都对输入文档执行操作。 例如，一个阶段可以过滤文档、对文档进行分组以及计算值。
- 从上一个阶段输出的文档将作为下一阶段的输入。
- 聚合管道可以返回文档组的结果。 例如，返回总计、平均值、最大值和最小值。

> - 从 MongoDB 4.2 开始，可以在更新操作中使用聚合管道操作 
>
> - [Updates with Aggregation Pipeline](https://www.mongodb.com/docs/v4.4/tutorial/update-documents-with-aggregation-pipeline/)

操作方法：

> - 使用 `db.collection.aggregate()` 方法运行的聚合管道**不会修改**集合中的文档，除非管道包含 $merge 或 $out 阶段。



#### 1. 聚合管道示例

插入如下数据

```javascript
db.orders.insertMany( [
   { _id: 0, productName: "Steel beam", status: "new", quantity: 10 },
   { _id: 1, productName: "Steel beam", status: "urgent", quantity: 20 },
   { _id: 2, productName: "Steel beam", status: "urgent", quantity: 30 },
   { _id: 3, productName: "Iron rod", status: "new", quantity: 15 },
   { _id: 4, productName: "Iron rod", status: "urgent", quantity: 50 },
   { _id: 5, productName: "Iron rod", status: "urgent", quantity: 10 }
] )
```

以下聚合管道示例包含两个阶段，并返回每个产品的紧急订单总数：

```javascript
db.orders.aggregate( [
   { $match: { status: "urgent" } },
   { $group: { _id: "$productName", sumQuantity: { $sum: "$quantity" } } }
] )
```

`$match` 阶段：

- 匹配为`urgent`状态的文档。
- 将匹配后的文档输出到 `$group` 阶段。

`$group` 阶段：

- 按产品名称对输入文档进行分组。


- 使用 `$sum` 计算每个 ProductName 的总数量，该总数量存储在聚合管道返回的 sumQuantity 字段中。

输出结果:

```javascript
[
   { _id: 'Steel beam', sumQuantity: 50 },
   { _id: 'Iron rod', sumQuantity: 60 }
]
```



#### 2. 聚合管道阶段和操作

- 最基本的管道阶段提供过滤器，其操作类似于查询和修改输出文档形式的文档转换。


- 其他管道操作提供用于按特定字段对文档进行分组和排序的工具，以及用于聚合数组内容（包括文档数组）的工具。 此外，管道阶段可以使用运算符来执行计算平均值或连接字符串等任务。


- 该管道使用 MongoDB 中的本机操作提供高效的数据聚合，是 MongoDB 中数据聚合的首选方法。


- 聚合管道可以对分片集合进行操作。


- 聚合管道可以使用索引来提高其某些阶段的性能。 此外，聚合管道还有一个内部优化阶段。

#### 3. 单一目的的聚合操作

-  [`db.collection.estimatedDocumentCount()`](https://www.mongodb.com/docs/v4.4/reference/method/db.collection.estimatedDocumentCount/#mongodb-method-db.collection.estimatedDocumentCount)  评估集合中文档数(近似计算)
- [`db.collection.count()`](https://www.mongodb.com/docs/v4.4/reference/method/db.collection.count/#mongodb-method-db.collection.count)  精确计算集合文档数目
-  [db.collection.distinct()](https://www.mongodb.com/docs/v4.4/reference/method/db.collection.distinct/#mongodb-method-db.collection.distinct)   针对字段进行去重

所有这些操作都会聚合来自单个集合的文档。 虽然这些操作提供了对常见聚合过程的简单访问，但它们缺乏聚合管道的灵活性和功能。

> 例如下图中的 `distinct()`操作

![aggregate-distinct](..\images\aggregate-distinct.png)

### 二、Expressions 表达式

表达式能包括 [field paths](https://www.mongodb.com/docs/v4.4/reference/aggregation-quick-reference/#std-label-agg-quick-ref-field-paths),  [literals](https://www.mongodb.com/docs/v4.4/reference/aggregation-quick-reference/#std-label-agg-quick-ref-literals),  [system variables](https://www.mongodb.com/docs/v4.4/reference/aggregation-quick-reference/#std-label-agg-quick-ref-variables),  [expression objects](https://www.mongodb.com/docs/v4.4/reference/aggregation-quick-reference/#std-label-agg-quick-ref-expression-objects), 和 [expression operators](https://www.mongodb.com/docs/v4.4/reference/aggregation-quick-reference/#std-label-agg-quick-ref-operator-expressions). 表达式可以嵌套。

#### 1. Field Paths 字段路径

聚合表达式使用字段路径来访问输入文档中的字段。 要指定字段路径，请在字段名称或点`.`字段名称（如果该字段位于嵌入文档中）前面添加美元符号 `$`。 例如，`$user`指定用户字段的字段路径，或`$user.name`指定`user.name`字段的字段路径。

`$<field>`相当于`$$CURRENT.<field>`，其中 `CURRENT` 是一个系统变量，默认为当前对象的根，除非在特定阶段另有说明。

#### 2. 聚合变量

MongoDB 提供了各种在表达式中使用的聚合系统变量。 要访问变量，请在变量名称前加上 `$$` 前缀。 例如：

| Variable                                                     | 通过 `$$ ` 访问  | 简要描述                                                     |
| :----------------------------------------------------------- | :--------------- | :----------------------------------------------------------- |
| [`NOW`](https://www.mongodb.com/docs/v4.4/reference/aggregation-variables/#mongodb-variable-variable.NOW) | `$$NOW`          | 返回当前日期时间值，该值在部署的所有成员中都相同，并且在整个聚合管道中保持不变。（4.2+ 版本可用） |
| [`CLUSTER_TIME`](https://www.mongodb.com/docs/v4.4/reference/aggregation-variables/#mongodb-variable-variable.CLUSTER_TIME) | `$$CLUSTER_TIME` | 返回当前时间戳值，该值在部署的所有成员中都是相同的，并且在整个聚合管道中保持不变。仅适用于副本集和分片集群。（4.2+ 版本可用） |
| [`ROOT`](https://www.mongodb.com/docs/v4.4/reference/aggregation-variables/#mongodb-variable-variable.ROOT) | `$$ROOT`         | 引用根文档，即顶级文档。                                     |
| [`CURRENT`](https://www.mongodb.com/docs/v4.4/reference/aggregation-variables/#mongodb-variable-variable.CURRENT) | `$$CURRENT`      | 引用字段路径的开头，默认情况下是 [`ROOT`](https://www.mongodb.com/docs/v4.4/reference/aggregation-variables/#mongodb-variable-variable.ROOT)但可以更改。 |
| [`REMOVE`](https://www.mongodb.com/docs/v4.4/reference/aggregation-variables/#mongodb-variable-variable.REMOVE) | `$$REMOVE`       | 允许有条件排除字段。（3.6+ 版本可用）                        |
| [`DESCEND`](https://www.mongodb.com/docs/v4.4/reference/aggregation-variables/#mongodb-variable-variable.DESCEND) | `$$DESCEND`      | 表达式允许的结果之一[`$redact`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/redact/#mongodb-pipeline-pipe.-redact)。 |
| [`PRUNE`](https://www.mongodb.com/docs/v4.4/reference/aggregation-variables/#mongodb-variable-variable.PRUNE) | `$$PRUNE`        | 表达式允许的结果之一[`$redact`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/redact/#mongodb-pipeline-pipe.-redact)。 |
| [`KEEP`](https://www.mongodb.com/docs/v4.4/reference/aggregation-variables/#mongodb-variable-variable.KEEP) | `$$KEEP`         | 表达式允许的结果之一[`$redact`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/redact/#mongodb-pipeline-pipe.-redact)。 |

#### 3. Expression Objects 表达式对象

```javascript
{ <field1>: <expression1>, ... }
```

