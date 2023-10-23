# MongoDB 聚合管道阶段概述

除了 `$out`、`$merge`、`$geoNear` 和 `$changeStream` 阶段之外的所有阶段都可以在管道中出现多次。

```javascript
db.collection.aggregate( [ { <stage> }, ... ] )
```

| 阶段                                                         | 描述                                                         |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [`$addFields`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/addFields/#mongodb-pipeline-pipe.-addFields) | 向文档添加新字段。与 类似 [`$project`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project)，[`$addFields`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/addFields/#mongodb-pipeline-pipe.-addFields)重塑流中的每个文档；具体来说，通过向输出文档添加新字段，该输出文档包含输入文档中的现有字段和新添加的字段。[`$set`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/set/#mongodb-pipeline-pipe.-set)是 的别名[`$addFields`。](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/addFields/#mongodb-pipeline-pipe.-addFields) |
| [`$bucket`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/bucket/#mongodb-pipeline-pipe.-bucket) | 根据指定的表达式和存储桶边界，将传入文档分类为称为存储桶的组。 |
| [`$bucketAuto`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/bucketAuto/#mongodb-pipeline-pipe.-bucketAuto) | 根据指定的表达式将传入文档分类为特定数量的组（称为存储桶）。自动确定桶边界，以尝试将文档均匀分布到指定数量的桶中。 |
| [`$changeStream`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/changeStream/#mongodb-pipeline-pipe.-changeStream) | 返回集合的[更改流游标。](https://www.mongodb.com/docs/v4.4/changeStreams/#std-label-changeStreams)此阶段只能在聚合管道中发生一次，并且必须作为第一阶段发生。 |
| [`$collStats`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/collStats/#mongodb-pipeline-pipe.-collStats) | 返回有关集合或视图的统计信息。                               |
| [`$count`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/count/#mongodb-pipeline-pipe.-count) | 返回聚合管道此阶段的文档数量计数。                           |
| [`$facet`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/facet/#mongodb-pipeline-pipe.-facet) | 在同一组输入文档的单个阶段内处理多个[聚合管道。](https://www.mongodb.com/docs/v4.4/core/aggregation-pipeline/#std-label-aggregation-pipeline)允许创建多方面聚合，能够在单个阶段中跨多个维度或方面表征数据。 |
| [`$geoNear`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/geoNear/#mongodb-pipeline-pipe.-geoNear) | 根据与地理空间点的接近程度返回有序的文档流。结合了地理空间数据的[`$match`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/match/#mongodb-pipeline-pipe.-match)、[`$sort`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/sort/#mongodb-pipeline-pipe.-sort)和功能 。[`$limit`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/limit/#mongodb-pipeline-pipe.-limit)输出文档包括附加距离字段，并且可以包括位置标识符字段。 |
| [`$graphLookup`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/graphLookup/#mongodb-pipeline-pipe.-graphLookup) | 对集合执行递归搜索。向每个输出文档添加一个新的数组字段，其中包含该文档的递归搜索的遍历结果。 |
| [`$group`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) | 按指定的标识符表达式对输入文档进行分组，并将累加器表达式（如果指定）应用于每个组。消耗所有输入文档并为每个不同组输出一个文档。输出文档仅包含标识符字段和累积字段（如果指定）。 |
| [`$indexStats`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/indexStats/#mongodb-pipeline-pipe.-indexStats) | 返回有关集合的每个索引的使用情况的统计信息。                 |
| [`$limit`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/limit/#mongodb-pipeline-pipe.-limit) | 将未修改的前*n 个*文档传递到管道，其中*n*是指定的限制。对于每个输入文档，输出一个文档（前*n 个*文档）或零个文档（前*n 个*文档之后）。 |
| [`$listSessions`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/listSessions/#mongodb-pipeline-pipe.-listSessions) | 列出所有活动时间足够长以传播到`system.sessions`集合的会话。  |
| [`$lookup`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/lookup/#mongodb-pipeline-pipe.-lookup) | *对同一*数据库中的另一个集合执行左外联接 ，以从“联接”集合中筛选文档进行处理。 |
| [`$match`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/match/#mongodb-pipeline-pipe.-match) | 过滤文档流以仅允许匹配的文档未经修改地传递到下一个管道阶段。 [`$match`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/match/#mongodb-pipeline-pipe.-match)使用标准 MongoDB 查询。对于每个输入文档，输出一个文档（匹配）或零个文档（不匹配）。 |
| [`$merge`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/merge/#mongodb-pipeline-pipe.-merge) | 将聚合管道的结果文档写入集合。该阶段可以将结果合并（插入新文档、合并文档、替换文档、保留现有文档、操作失败、使用自定义更新管道处理文档）结果到输出集合中。要使用该[`$merge`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/merge/#mongodb-pipeline-pipe.-merge)阶段，它必须是管道中的最后一个阶段。*4.2版本中的新增功能*。 |
| [`$out`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/out/#mongodb-pipeline-pipe.-out) | 将聚合管道的结果文档写入集合。要使用该[`$out`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/out/#mongodb-pipeline-pipe.-out)阶段，它必须是管道中的最后一个阶段。 |
| [`$planCacheStats`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/planCacheStats/#mongodb-pipeline-pipe.-planCacheStats) | 返回集合的[计划缓存信息。](https://www.mongodb.com/docs/v4.4/core/query-plans/) |
| [`$project`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project) | 重塑流中的每个文档，例如通过添加新字段或删除现有字段。对于每个输入文档，输出一个文档。另请参阅[`$unset`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/unset/#mongodb-pipeline-pipe.-unset)删除现有字段。 |
| [`$redact`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/redact/#mongodb-pipeline-pipe.-redact) | 根据文档本身存储的信息限制每个文档的内容，从而重塑流中的每个文档。[`$project`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project)合并了和的功能 [`$match`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/match/#mongodb-pipeline-pipe.-match)。可用于实现字段级编辑。对于每个输入文档，输出一个或零个文档。 |
| [`$replaceRoot`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/replaceRoot/#mongodb-pipeline-pipe.-replaceRoot) | 将文档替换为指定的嵌入文档。该操作将替换输入文档中的所有现有字段，包括该`_id`字段。指定嵌入在输入文档中的文档，以将嵌入文档提升到顶层。[`$replaceWith`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/replaceWith/#mongodb-pipeline-pipe.-replaceWith)是 stage 的别名 [`$replaceRoot`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/replaceRoot/#mongodb-pipeline-pipe.-replaceRoot)。 |
| [`$replaceWith`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/replaceWith/#mongodb-pipeline-pipe.-replaceWith) | 将文档替换为指定的嵌入文档。该操作将替换输入文档中的所有现有字段，包括该`_id`字段。指定嵌入在输入文档中的文档，以将嵌入文档提升到顶层。[`$replaceWith`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/replaceWith/#mongodb-pipeline-pipe.-replaceWith)是 stage 的别名 [`$replaceRoot`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/replaceRoot/#mongodb-pipeline-pipe.-replaceRoot)。 |
| [`$sample`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/sample/#mongodb-pipeline-pipe.-sample) | 从输入中随机选择指定数量的文档。                             |
| [`$search`](https://docs.atlas.mongodb.com/atlas-search/query-syntax/#mongodb-pipeline-pipe.-search) | 对文档中的一个或多个字段执行全文搜索 [阿特拉斯](https://www.mongodb.com/docs/atlas/reference/atlas-search/query-syntax/) 收藏。笔记`$search`仅适用于 MongoDB Atlas 集群，不适用于自我管理部署。 |
| [`$set`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/set/#mongodb-pipeline-pipe.-set) | 向文档添加新字段。与 类似 [`$project`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project)，[`$set`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/set/#mongodb-pipeline-pipe.-set)重塑流中的每个文档；具体来说，通过向输出文档添加新字段，该输出文档包含输入文档中的现有字段和新添加的字段。[`$set`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/set/#mongodb-pipeline-pipe.-set)是 stage 的别名[`$addFields`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/addFields/#mongodb-pipeline-pipe.-addFields)。 |
| [`$skip`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/skip/#mongodb-pipeline-pipe.-skip) | 跳过前*n*个文档，其中*n*是指定的跳过编号，并将未修改的剩余文档传递到管道。对于每个输入文档，输出零个文档（对于前*n 个*文档）或一个文档（如果在前*n 个*文档之后）。 |
| [`$sort`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/sort/#mongodb-pipeline-pipe.-sort) | 按指定的排序键对文档流重新排序。仅顺序发生变化；文件保持不变。对于每个输入文档，输出一个文档。 |
| [`$sortByCount`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/sortByCount/#mongodb-pipeline-pipe.-sortByCount) | 根据指定表达式的值对传入文档进行分组，然后计算每个不同组中的文档计数。 |
| [`$unionWith`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/unionWith/#mongodb-pipeline-pipe.-unionWith) | 执行两个集合的并集； 将两个集合的管道结果合并到一个结果集中。*4.4版本中的新功能*。 |
| [`$unset`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/unset/#mongodb-pipeline-pipe.-unset) | 从文档中删除/排除字段。[`$unset`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/unset/#mongodb-pipeline-pipe.-unset)[`$project`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project)是删除字段的阶段的别名。 |
| [`$unwind`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/unwind/#mongodb-pipeline-pipe.-unwind) | *从输入文档解构数组字段以输出每个*元素的文档。每个输出文档都用一个元素值替换数组。对于每个输入文档，输出*n 个*文档，其中*n*是数组元素的数量，对于空数组可以为零。 |

### 一、`$match`

#### 1. 定义

过滤文档仅把与指定条件匹配的文档传递到下一个管道阶段。

#### 2. 语法

```javascript
{ $match: { <query> } }
```

`$match` 接受满足指定查询条件的文档，该查询语法与读操作查询语法相同

#### 3. 示例

插入如下 `articles` 集合中文档数据

```javascript
{ "_id" : ObjectId("512bc95fe835e68f199c8686"), "author" : "dave", "score" : 80, "views" : 100 }
{ "_id" : ObjectId("512bc962e835e68f199c8687"), "author" : "dave", "score" : 85, "views" : 521 }
{ "_id" : ObjectId("55f5a192d4bede9ac365b257"), "author" : "ahn", "score" : 60, "views" : 1000 }
{ "_id" : ObjectId("55f5a192d4bede9ac365b258"), "author" : "li", "score" : 55, "views" : 5000 }
{ "_id" : ObjectId("55f5a1d3d4bede9ac365b259"), "author" : "annT", "score" : 60, "views" : 50 }
{ "_id" : ObjectId("55f5a1d3d4bede9ac365b25a"), "author" : "li", "score" : 94, "views" : 999 }
{ "_id" : ObjectId("55f5a1d3d4bede9ac365b25b"), "author" : "ty", "score" : 95, "views" : 1000 }
```

##### 1) 等值匹配

`$match `选择 `author`字段等于 `dave` 的文档

```javascript
db.articles.aggregate(
    [ { $match : { author : "dave" } } ]
);
```

返回结果聚合结果如下

```javascript
{ "_id" : ObjectId("512bc95fe835e68f199c8686"), "author" : "dave", "score" : 80, "views" : 100 }
{ "_id" : ObjectId("512bc962e835e68f199c8687"), "author" : "dave", "score" : 85, "views" : 521 }
```



### 二、`$addFields` 

#### 1. 定义

3.4 版本中的新功能。

向文档添加新字段。 `$addFields` 输出包含输入文档中所有现有字段和新添加字段的文档。
`$addFields`  stage 相当于 `$project` 阶段，它显式指定输入文档中的所有现有字段并添加新字段。

#### 2. 语法

```javascript
// 其中 $addFields 代表的是聚合管道阶段
// <expression> 代表的是聚合表达式
{ $addFields: { <newField>: <expression>, ... } }
```

- [x] 注意：如果新字段的名称与现有字段名称（包括 _id）相同，则 `$addFields` 会使用指定表达式的值覆盖该字段的现有值。

#### 2. 示例

##### 1) 使用两次 `$addFields` 阶段

假设一个集合中包含如下两条文档

```javascript
db.scores.insertMany([{
    _id: 1,
    student: "Maya",
    homework: [10, 5, 10],
    quiz: [10, 8],
    extraCredit: 0
}
{
    _id: 2,
    student: "Ryan",
    homework: [5, 6, 5],
    quiz: [8, 8],
    extraCredit: 8
}])
```

下面的操作使用了两个 `$addFields` 在输出文档中包含**三个新字段**的阶段:

```javascript
db.scores.aggregate( [
   {
     $addFields: {
       totalHomework: { $sum: "$homework" } ,
       totalQuiz: { $sum: "$quiz" }
     }
   },
   {
     $addFields: { totalScore:
       { $add: [ "$totalHomework", "$totalQuiz", "$extraCredit" ] } }
   }
] )
```

返回如下文档数据：

```javascript
{
  "_id" : 1,
  "student" : "Maya",
  "homework" : [ 10, 5, 10 ],
  "quiz" : [ 10, 8 ],
  "extraCredit" : 0,
  "totalHomework" : 25,
  "totalQuiz" : 18,
  "totalScore" : 43
}
{
  "_id" : 2,
  "student" : "Ryan",
  "homework" : [ 5, 6, 5 ],
  "quiz" : [ 8, 8 ],
  "extraCredit" : 8,
  "totalHomework" : 16,
  "totalQuiz" : 16,
  "totalScore" : 40
}
```

##### 2) 在嵌入式文档中添加字段

使用点 `.` 符号向嵌入文档添加新字段。

插入如下集合数据

```javascript
db.vehicles.insertMany(
   [
      { _id: 1, type: "car", specs: { doors: 4, wheels: 4 } },
      { _id: 2, type: "motorcycle", specs: { doors: 0, wheels: 2 } },
      { _id: 3, type: "jet ski" }
   ]
)
```

以下聚合操作将新字段 `fuel_type`添加到嵌入文档中。

```javascript
db.vehicles.aggregate( [
        {
           $addFields: {
              "specs.fuel_type": "unleaded"
           }
        }
   ] )
```

输出结果如下：

```javascript
{ _id: 1, type: "car",
   specs: { doors: 4, wheels: 4, fuel_type: "unleaded" } }
{ _id: 2, type: "motorcycle",
   specs: { doors: 0, wheels: 2, fuel_type: "unleaded" } }
{ _id: 3, type: "jet ski",
   specs: { fuel_type: "unleaded" } }
```

##### 3) 覆盖已经存在的字段值

一个名为`animals`的集合包含以下文档：

```javascript
{ _id: 1, dogs: 10, cats: 15 }
```

`$addFields` 指定 `cats` 字段

```javascript
db.animals.aggregate( [
  {
    $addFields: { "cats": 20 }
  }
] )
```

输出结果：

```javascript
{ _id: 1, dogs: 10, cats: 20 }
```

也可以用一个字段替换另一个字段。 在以下示例中，`item` 字段替换 ` _id` 字段。

有一个叫 `fruit` 的集合含有如下文档：

```javascript
{ "_id" : 1, "item" : "tangerine", "type" : "citrus" }
{ "_id" : 2, "item" : "lemon", "type" : "citrus" }
{ "_id" : 3, "item" : "grapefruit", "type" : "citrus" }
```

以下聚合操作使用 `$addFields` 将每个文档的 `_id` 字段值替换为 `item` 字段的值，并将 `item` 字段替换为静态值。

```javascript
db.fruit.aggregate( [
  {
    $addFields: {
      _id : "$item",
      item: "fruit"
    }
  }
] )
```

返回结果如下：

```javascript
{ "_id" : "tangerine", "item" : "fruit", "type" : "citrus" }
{ "_id" : "lemon", "item" : "fruit", "type" : "citrus" }
{ "_id" : "grapefruit", "item" : "fruit", "type" : "citrus" }
```

##### 4) 往数组中添加元素

往集合 `scores` 插入如下数据

```javascript
db.scores.insertMany([
   { _id: 1, student: "Maya", homework: [ 10, 5, 10 ], quiz: [ 10, 8 ], extraCredit: 0 },
   { _id: 2, student: "Ryan", homework: [ 5, 6, 5 ], quiz: [ 8, 8 ], extraCredit: 8 }
])
```

可以将 `$addFields`  **聚合阶段**与 `$concatArrays` **聚合表达式**结合使用，将元素添加到现有数组字段。 

例如，以下操作使用 `$addFields` 将`homework`字段替换为一个新数组，该数组的元素是当前`homework`数组与另一个包含新分数的数组的**合并结果**

```javascript
db.scores.aggregate([
   { $match: { _id: 1 } }, // 第一个阶段的过滤条件 -> 只匹配 _id 为 1 的文档
   { $addFields: { homework: { $concatArrays: [ "$homework", [ 7 ] ] } } }
])
```

输出结果如下:

```javascript
{ "_id" : 1, "student" : "Maya", "homework" : [ 10, 5, 10, 7 ], "quiz" : [ 10, 8 ], "extraCredit" : 0 }
```



### 三、`$COUNT`

#### 1. 定义

3.4 版本中的新功能。

将文档传递到下一个阶段，其中包含输入到该阶段的文档数量的计数。

#### 2. 语法

```javascript
{ $count: <string> }
```

`<string>` 是输出字段的名称，其值是计数。` <string>` 必须是非空字符串，不能以 `$` 开头，也不能包含 `.  `  符号。

#### 3. 示例

一个名为 `scores` 集合中有如下文档

```javascript
{ "_id" : 1, "subject" : "History", "score" : 88 }
{ "_id" : 2, "subject" : "History", "score" : 92 }
{ "_id" : 3, "subject" : "History", "score" : 97 }
{ "_id" : 4, "subject" : "History", "score" : 71 }
{ "_id" : 5, "subject" : "History", "score" : 79 }
{ "_id" : 6, "subject" : "History", "score" : 83 }
```

以下聚合操作有两个阶段：

1. `$match`阶段排除掉得分值小于或等于80的文档，将得分大于80的文档传递到下一阶段。
2. `$count` 阶段返回聚合管道中剩余文档的计数，并将该值分配给名为 `passing_scores` 的字段。

```javascript
db.scores.aggregate(
  [
    {
      $match: {
        score: {
          $gt: 80
        }
      }
    },
    {
      $count: "passing_scores"
    }
  ]
)
```

输出结果为：

```javascript
{ "passing_scores" : 4 }
```



### 四、`$limit`

#### 1. 定义

限制传递到管道中下一阶段的文档数量。

#### 2. 语法

```javascript
{ $limit: <positive 64-bit integer> }
```

`$limit` 采用正整数，指定要传递的最大文档数。

#### 3. 示例

```javascript
db.article.aggregate([
   { $limit : 5 }
]);
```

此操作仅返回管道传递给它的前 5 个文档。 `$limit`对其传递的文档内容没有影响。



### 五、`$sort`

#### 1. 定义

对所有输入文档进行排序并返回排序结果到管道。

#### 2. 语法

```javascript
{ $sort: { <field1>: <sort order>, <field2>: <sort order> ... } }
```

`$sort` 接受一个文档，指定要排序的字段以及相应的排序顺序。 

`<sort order>` 可以是下列值中的一种:

| Value                    | Description                                                  |
| :----------------------- | :----------------------------------------------------------- |
| `1`                      | 升序                                                         |
| `-1`                     | 降序                                                         |
| `{ $meta: "textScore" }` | 按计算出的 “textScore” 元数据降序排序。<br />[Text Score Metadata Sort](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/sort/#std-label-sort-pipeline-metadata) |

#### 3. 排序一致性

MongoDB 不按特定顺序将文档存储在集合中。 对包含重复值的字段进行排序时，包含这些值的文档可能会以任何顺序返回。

如果需要一致的排序顺序，请在排序字段中**至少有一个包含唯一值**的字段。 保证这一点的最简单方法是在排序中包含` _id` 字段。

参考如下例子，插入如下 `restaurant` 集合数据：

```javascript
db.restaurants.insertMany( [
   { "_id" : 1, "name" : "Central Park Cafe", "borough" : "Manhattan"},
   { "_id" : 2, "name" : "Rock A Feller Bar and Grill", "borough" : "Queens"},
   { "_id" : 3, "name" : "Empire State Pub", "borough" : "Brooklyn"},
   { "_id" : 4, "name" : "Stan's Pizzaria", "borough" : "Manhattan"},
   { "_id" : 5, "name" : "Jane's Deli", "borough" : "Brooklyn"},
] )
```

使用 `sort` 阶段对 ``borough` 字段进行排序
```javascript
db.restaurants.aggregate(
   [
     { $sort : { borough : 1 } }
   ]
)
```

在上述示例中，排序顺序可能不一致，因为 `borough` 字段包含`Manhattan` 和 `Brooklyn` 的重复值。 文档按 `borough` 的字母顺序返回，但具有重复 `borough` 值的文档的顺序在同一排序的多次执行中可能不相同。

 例如，以下是上述命令的两次不同执行的结果：

```javascript
// 第一次执行结果：
{ "_id" : 3, "name" : "Empire State Pub", "borough" : "Brooklyn" }
{ "_id" : 5, "name" : "Jane's Deli", "borough" : "Brooklyn" }
{ "_id" : 1, "name" : "Central Park Cafe", "borough" : "Manhattan" }
{ "_id" : 4, "name" : "Stan's Pizzaria", "borough" : "Manhattan" }
{ "_id" : 2, "name" : "Rock A Feller Bar and Grill", "borough" : "Queens" }

// 第二次执行结果：
{ "_id" : 5, "name" : "Jane's Deli", "borough" : "Brooklyn" }
{ "_id" : 3, "name" : "Empire State Pub", "borough" : "Brooklyn" }
{ "_id" : 4, "name" : "Stan's Pizzaria", "borough" : "Manhattan" }
{ "_id" : 1, "name" : "Central Park Cafe", "borough" : "Manhattan" }
{ "_id" : 2, "name" : "Rock A Feller Bar and Grill", "borough" : "Queens" }
```

虽然 `borough` 的值仍然按字母顺序排序，但包含 `borough`重复值的文档的顺序并不相同。

要实现一致的排序，请向排序添加一个**仅包含唯一值**的字段。 以下命令使用 `$sort` 阶段对 `borough` 字段和  `_id` 字段进行排序：

```java
db.restaurants.aggregate(
   [
     { $sort : { borough : 1, _id: 1 } }
   ]
)
```

由于 `_id` 字段始终保证包含唯一的值，因此在同一排序的多次执行中返回的排序顺序将始终相同。

#### 4. 示例

##### 1)升序/降序 排序

对于要排序的一个或多个字段，将排序顺序设置为 `1` 或 `-1` 以分别指定升序或降序排序，如下例所示：

```javascript
db.users.aggregate(
   [
     { $sort : { age : -1, posts: 1 } }
   ]
)
```

不同 BSON 类型值的比较规则会不同，请参考：[Comparison/Sort Order.](https://www.mongodb.com/docs/v4.4/reference/bson-type-comparison-order/#std-label-bson-types-comparison-order)

##### 2) 按文本匹配得分元数据排序

To be done ...

#### 5. `sort` 内存

##### 1) `$sort` + `$limit` 内存优化

当 `$sort` 在 `$limit` 之前并且没有修改文档数量的中间阶段时，优化器可以将 `$limit` 合并到 `$sort` 中。 这允许 `$sort` 操作在进行时仅维护前 `n` 个结果，其中 `n` 是指定的限制，并确保 MongoDB 仅需要在内存中存储 `n` 个条例。

##### 2) 内存限制

`$sort` 阶段内存排序的 RAM 限制为 100 MB。 默认情况下，如果阶段超过此限制，
`$sort` 产生错误。 为了允许管道处理占用更多空间，请使用 `allowDiskUse` 选项启用聚合管道阶段将数据写入临时文件。

#### 6. `$sort` 性能

如果 `$sort` 运算符在管道的第一阶段使用或者仅在 `$match` 阶段之前使用，则它可以利用索引。



### 六、`$group`

#### 1. 定义

`$group` 阶段根据 "分组键" 将文档进行分组，每组的输出结果是一个文档。

分组键通常是一个字段或一组字段。 分组键也可以是聚合表达式的结果。 使用 `$group` 管道阶段中的 ` _id` 字段来作为分组键。

在 `$group` 阶段输出中，`_id` 字段作为该文档的分组键传递到下一个阶段。

输出文档还可以包含使用 [accumulator expressions](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/group/#std-label-accumulators-group) "累加器表达式"(如 `$sum`、`$avg` 、`$max` 等) 设置的其他字段。

> - [x] 注意：`$group` 不会对输出的文档进行排序

#### 2. 语法

```javascript
{
 $group:
   {
     _id: <expression>, // Group key
     <field1>: { <accumulator1> : <expression1> },
     ...
   }
 }
```

| Field   | Description                                                  |
| :------ | :----------------------------------------------------------- |
| `_id`   | *必要参数* 。`_id` 表达式指定组密钥。 如果您将 `_id` 值指定为 null 或任何其他常量值，则 `$group` 阶段将返回一个聚合了所有输入文档中的值的单个文档。 |
| `field` | *可选参数* 。使用 [accumulator operators](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/group/#std-label-accumulators-group) 累加器运算符进行计算 |

#### 3. 注意事项

##### 1) Accumulator Operator 累加器运算符

`<accumulator>` 运算符必须是以下累加器运算符之一：

| name                                                         | description                                                  |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [`$accumulator`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/accumulator/#mongodb-group-grp.-accumulator) | 返回用户定义的累加器函数的结果。                             |
| [`$addToSet`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/addToSet/#mongodb-group-grp.-addToSet) | 返回每个组的唯一表达式值的数组。数组元素的顺序未定义。       |
| [`$avg`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/avg/#mongodb-group-grp.-avg) | 返回数值的平均值。忽略非数字值。                             |
| [`$first`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/first/#mongodb-group-grp.-first) | 返回每个组的第一个文档的值。仅当文档按定义的顺序排列时才定义顺序。 |
| [`$last`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/last/#mongodb-group-grp.-last) | 返回每个组的最后一个文档的值。仅当文档按定义的顺序排列时才定义顺序。 |
| [`$max`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/max/#mongodb-group-grp.-max) | 返回每个组的最高表达值。                                     |
| [`$mergeObjects`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/mergeObjects/#mongodb-expression-exp.-mergeObjects) | 返回通过组合每个组的输入文档创建的文档。                     |
| [`$min`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/min/#mongodb-group-grp.-min) | 返回每个组的最低表达值。                                     |
| [`$push`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/push/#mongodb-group-grp.-push) | 返回每个组的表达式值数组。                                   |
| [`$stdDevPop`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/stdDevPop/#mongodb-group-grp.-stdDevPop) | 返回输入值的总体标准差。                                     |
| [`$stdDevSamp`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/stdDevSamp/#mongodb-group-grp.-stdDevSamp) | 返回输入值的样本标准差。                                     |
| [`$sum`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/sum/#mongodb-group-grp.-sum) | 返回数值的总和。忽略非数字值。                               |

##### 2) `$group` 以及缓存限制

`$group` stage 的 RAM 限制为 100 MB。 默认情况下，如果阶段超出此限制，`$group` 将返回错误。 要为阶段处理提供更多空间，请使用 allowDiskUse 选项启用聚合管道阶段将数据写入临时文件。

> - [x] 注意: 可以查看  [Aggregation Pipeline Limits](https://www.mongodb.com/docs/v4.4/core/aggregation-pipeline-limits/)

##### 3) 优化返回每组的第一个文档

如果管道按相同字段进行排序和分组并且 `$group` stage 仅使用 `$first` 累加器运算符，请考虑在与排序顺序匹配的分组字段上添加索引。 在某些情况下，`$group` 阶段可以使用索引来快速找到每个组的第一个文档。

如果名为 `foo` 的集合包含索引 { x: 1, y: 1 }，则以下管道可以使用该索引来查找每个组的第一个文档：

```javascript
db.foo.aggregate([
  {
    $sort:{ x : 1, y : 1 }
  },
  {
    $group: {
      _id: { x : "$x" },
      y: { $first : "$y" }
    }
  }
])
```

#### 4. 示例

##### 1) 计算集合中的文档数

创建 `sales` 集合并插入如下数据:

```javascript
db.sales.insertMany([
  { "_id" : 1, "item" : "abc", "price" : NumberDecimal("10"), "quantity" : NumberInt("2"), "date" : ISODate("2014-03-01T08:00:00Z") },
  { "_id" : 2, "item" : "jkl", "price" : NumberDecimal("20"), "quantity" : NumberInt("1"), "date" : ISODate("2014-03-01T09:00:00Z") },
  { "_id" : 3, "item" : "xyz", "price" : NumberDecimal("5"), "quantity" : NumberInt( "10"), "date" : ISODate("2014-03-15T09:00:00Z") },
  { "_id" : 4, "item" : "xyz", "price" : NumberDecimal("5"), "quantity" :  NumberInt("20") , "date" : ISODate("2014-04-04T11:21:39.736Z") },
  { "_id" : 5, "item" : "abc", "price" : NumberDecimal("10"), "quantity" : NumberInt("10") , "date" : ISODate("2014-04-04T21:23:13.331Z") },
  { "_id" : 6, "item" : "def", "price" : NumberDecimal("7.5"), "quantity": NumberInt("5" ) , "date" : ISODate("2015-06-04T05:08:13Z") },
  { "_id" : 7, "item" : "def", "price" : NumberDecimal("7.5"), "quantity": NumberInt("10") , "date" : ISODate("2015-09-10T08:43:00Z") },
  { "_id" : 8, "item" : "abc", "price" : NumberDecimal("10"), "quantity" : NumberInt("5" ) , "date" : ISODate("2016-02-06T20:20:13Z") },
])
```

以下聚合操作使用 `$group` 阶段计算销售集合中的所有文档数量：

```javascript
db.sales.aggregate( [
  {
    $group: {
       _id: null,
       count: { $sum: 1 }
    }
  }
] )
```

返回结果如下：

```javascript
{ "_id" : null, "count" : 8 }
```

等价于sql中的

```sql
SELECT COUNT(*) AS count FROM sales
```

> 如果是需要计算整个集合的文档数则直接使用 `$count` 会更加方便 

##### 2) 获取指定字段不同的值

以下聚合操作使用 `$group` 阶段从`sales`集合中获取不同的 `item`值：

```javascript
db.sales.aggregate( [ { $group : { _id : "$item" } } ] )
```

返回结果如下：

```javascript
{ "_id" : "abc" }
{ "_id" : "jkl" }
{ "_id" : "def" }
{ "_id" : "xyz" }
```

##### 3) 根据 `item` 进行分组

以下聚合操作按 `item`字段对文档进行分组，计算每个商品的总销售额，并仅返回总销售额大于或等于 100 的商品：

```javascript
db.sales.aggregate(
  [
    // 第一阶段：按照 item 商品进行分组，并计算出每组的总销售额添加到totalSaleAmount 字段然后输入到第二个阶段
    {
      $group :
        {
          _id : "$item",
          totalSaleAmount: { $sum: { $multiply: [ "$price", "$quantity" ] } }
        }
     },
     // 第二阶段：过滤返回的文档结果集，只返回总销售额大于或等于100的
     {
       $match: { "totalSaleAmount": { $gte: 100 } }
     }
   ]
 )
```

返回的结果集如下：

```javascript
{ "_id" : "abc", "totalSaleAmount" : NumberDecimal("170") }
{ "_id" : "xyz", "totalSaleAmount" : NumberDecimal("150") }
{ "_id" : "def", "totalSaleAmount" : NumberDecimal("112.5") }
```

等价于如下sql

```sql
SELECT item,
   Sum(( price * quantity )) AS totalSaleAmount
FROM   sales
GROUP  BY item
HAVING totalSaleAmount >= 100
```

##### 4) 计算数目、总和和平均值

插入如下数据

```javascript
db.sales.insertMany([
  { "_id" : 1, "item" : "abc", "price" : NumberDecimal("10"), "quantity" : NumberInt("2"), "date" : ISODate("2014-03-01T08:00:00Z") },
  { "_id" : 2, "item" : "jkl", "price" : NumberDecimal("20"), "quantity" : NumberInt("1"), "date" : ISODate("2014-03-01T09:00:00Z") },
  { "_id" : 3, "item" : "xyz", "price" : NumberDecimal("5"), "quantity" : NumberInt( "10"), "date" : ISODate("2014-03-15T09:00:00Z") },
  { "_id" : 4, "item" : "xyz", "price" : NumberDecimal("5"), "quantity" :  NumberInt("20") , "date" : ISODate("2014-04-04T11:21:39.736Z") },
  { "_id" : 5, "item" : "abc", "price" : NumberDecimal("10"), "quantity" : NumberInt("10") , "date" : ISODate("2014-04-04T21:23:13.331Z") },
  { "_id" : 6, "item" : "def", "price" : NumberDecimal("7.5"), "quantity": NumberInt("5" ) , "date" : ISODate("2015-06-04T05:08:13Z") },
  { "_id" : 7, "item" : "def", "price" : NumberDecimal("7.5"), "quantity": NumberInt("10") , "date" : ISODate("2015-09-10T08:43:00Z") },
  { "_id" : 8, "item" : "abc", "price" : NumberDecimal("10"), "quantity" : NumberInt("5" ) , "date" : ISODate("2016-02-06T20:20:13Z") },
])
```

以下聚合管道计算 2014 年每一天的总销售额、平均销售数量和销售数量：

```javascript
db.sales.aggregate([
  // First Stage 
  {
    $match : { "date": { $gte: new ISODate("2014-01-01"), $lt: new ISODate("2015-01-01") } }
  },
  // Second Stage
  {
    $group : {
       _id : { $dateToString: { format: "%Y-%m-%d", date: "$date" } },
       totalSaleAmount: { $sum: { $multiply: [ "$price", "$quantity" ] } },
       averageQuantity: { $avg: "$quantity" },
       count: { $sum: 1 }
    }
  },
  // Third Stage
  {
    $sort : { totalSaleAmount: -1 }
  }
 ])
```

- ##### 第一阶段：

  `$match` 阶段过滤文档，仅将 2014 年的文档传递到下一阶段。

- **第二阶段**：

  `$group` 阶段按日期对文档进行分组，并计算每组中文档的总销售额、平均数量和总数。

- **第三阶段**：

  `$sort` 阶段按每组的总销售额降序对结果进行排序。

返回结果集如下：

```javascript
{ "_id" : "2014-04-04", "totalSaleAmount" : NumberDecimal("200"), "averageQuantity" : 15, "count" : 2 }
{ "_id" : "2014-03-15", "totalSaleAmount" : NumberDecimal("50"), "averageQuantity" : 10, "count" : 1 }
{ "_id" : "2014-03-01", "totalSaleAmount" : NumberDecimal("40"), "averageQuantity" : 1.5, "count" : 2 }
```

等价于sql中的

```sql
SELECT date,
       Sum(( price * quantity )) AS totalSaleAmount,
       Avg(quantity)             AS averageQuantity,
       Count(*)                  AS Count
FROM   sales
WHERE date >= '2014-01-01' AND date < '2015-01-01'
GROUP  BY date
ORDER  BY totalSaleAmount DESC
```

##### 5) 根据 `null`进行分组

```javascript
db.sales.aggregate([
  {
    $group : {
       _id : null,
       totalSaleAmount: { $sum: { $multiply: [ "$price", "$quantity" ] } },
       averageQuantity: { $avg: "$quantity" },
       count: { $sum: 1 }
    }
  }
 ])
```

返回结果集如下：

```javascript
{
  "_id" : null,
  "totalSaleAmount" : NumberDecimal("452.5"),
  "averageQuantity" : 7.875,
  "count" : 8
}
```

等价于sql中的
```sql
SELECT Sum(price * quantity) AS totalSaleAmount,
       Avg(quantity)         AS averageQuantity,
       Count(*)              AS Count
FROM   sales
```

##### 6) 数据透视

创建一个名为 `books` 的示例集合，其中包含以下文档：

```javascript
db.books.insertMany([
  { "_id" : 8751, "title" : "The Banquet", "author" : "Dante", "copies" : 2 },
  { "_id" : 8752, "title" : "Divine Comedy", "author" : "Dante", "copies" : 1 },
  { "_id" : 8645, "title" : "Eclogues", "author" : "Dante", "copies" : 2 },
  { "_id" : 7000, "title" : "The Odyssey", "author" : "Homer", "copies" : 10 },
  { "_id" : 7020, "title" : "Iliad", "author" : "Homer", "copies" : 10 }
])
```

###### ① 根据`author`对`title`进行分组

```javascript
db.books.aggregate([
   { $group : { _id : "$author", books: { $push: "$title" } } }
 ])
```

返回结果集如下：

```javascript
{ "_id" : "Homer", "books" : [ "The Odyssey", "Iliad" ] }
{ "_id" : "Dante", "books" : [ "The Banquet", "Divine Comedy", "Eclogues" ] }
```

###### ②  根据`author` 对文档进行分组

```shell
db.books.aggregate([
   ## 第一阶段
   {
     $group : { _id : "$author", books: { $push: "$$ROOT" } }
   },
   ## 第二阶段
   {
     $addFields:
       {
         totalCopies : { $sum: "$books.copies" }
       }
   }
 ])
```

- 第一阶段

  `$group` 使用 `$$ROOT` 系统变量按作者对整个文档进行分组。 此阶段将以下文档传递到下一阶段：

  ```javascript
  { "_id" : "Homer",
    "books" :
      [
         { "_id" : 7000, "title" : "The Odyssey", "author" : "Homer", "copies" : 10 },
         { "_id" : 7020, "title" : "Iliad", "author" : "Homer", "copies" : 10 }
      ]
   },
   { "_id" : "Dante",
     "books" :
       [
         { "_id" : 8751, "title" : "The Banquet", "author" : "Dante", "copies" : 2 },
         { "_id" : 8752, "title" : "Divine Comedy", "author" : "Dante", "copies" : 1 },
         { "_id" : 8645, "title" : "Eclogues", "author" : "Dante", "copies" : 2 }
       ]
   }
  ```

- 第二阶段

  `$addField` 在输出文档中添加一个新字段，其中包含每个作者的图书总份数。

  返回结果如下：

  ```javascript
  {
    "_id" : "Homer",
    "books" :
       [
         { "_id" : 7000, "title" : "The Odyssey", "author" : "Homer", "copies" : 10 },
         { "_id" : 7020, "title" : "Iliad", "author" : "Homer", "copies" : 10 }
       ],
     "totalCopies" : 20
  }，
  
  {
    "_id" : "Dante",
    "books" :
       [
         { "_id" : 8751, "title" : "The Banquet", "author" : "Dante", "copies" : 2 },
         { "_id" : 8752, "title" : "Divine Comedy", "author" : "Dante", "copies" : 1 },
         { "_id" : 8645, "title" : "Eclogues", "author" : "Dante", "copies" : 2 }
       ],
     "totalCopies" : 5
  }
  ```



### 七、`unwind`

#### 1. 定义

从输入文档解构数组字段以输出每个元素的文档。 每个输出文档都是输入文档，其中数组字段的值被元素替换。

#### 2. 语法

可以传递字段路径操作数或文档操作数来展开数组字段。

##### 1) Field Path Operand  字段路径操作数

可以将数组字段路径传递给 `$unwind`。 使用此语法时，如果字段值**为 null**、**缺失**或**空数组**，`$unwind` 则不会输出文档。

```shell
{ $unwind: <field path> } 
```

> - 指定字段路径时，请在字段名称前添加美元符号 `$` 并用引号`" "`括起来。

##### 2) 带选项的文档操作数

```shell
{
  $unwind:
    {
      path: <field path>,
      includeArrayIndex: <string>,
      preserveNullAndEmptyArrays: <boolean>
    }
}
```

| Field                                                        | Type    | Description                                                  |
| :----------------------------------------------------------- | :------ | :----------------------------------------------------------- |
| [path](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/unwind/#std-label-unwind-path) | string  | 数组字段的字段路径。 要指定字段路径，请在字段名称前添加美元符号`$`并用引号`" "`括起来。 |
| [includeArrayIndex](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/unwind/#std-label-unwind-includeArrayIndex) | string  | *可选参数*.  用于保存元素的数组索引的新字段的名称。 名称**不能**以美元符号 `$` 开头 |
| [preserveNullAndEmptyArrays](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/unwind/#std-label-unwind-preserveNullAndEmptyArrays) | boolean | *可选参数*. <br />1. 如果是 `true`，如果字段路劲为 `null`、缺失或为空数组，`$unwind`输出文档。<br />2. 如果是 `false`，则不会输出文档<br />3. 默认是为 `false` |

#### 3. 行为

##### 1) 非数组字段路径

- 当操作数不能解析为数组，但不缺失、不为 `null` 或不为空数组时，`$unwind` 会将操作数视为单个元素数组。
- 当操作数为 `null`、缺失或空数组时，`$unwind` 遵循为 `preserveNullAndEmptyArrays` 设置的
   选项。

##### 2) 缺失(不存在)该字段

- 如果输入文档中不存在该字段指定路径或者该字段是空数组，默认情况下，`$unwind` 会忽略输入文档，并且不会输出该输入文档。


- 版本 3.2 中的新增功能：要输出数组字段缺失、为 `null` 或空数组的文档，请使用`preserveNullAndEmptyArrays` 选项。



#### 4. 示例

##### 1) `unwind` 数组

```shell
# 插入如下数据
db.inventory.insertOne({ "_id" : 1, "item" : "ABC1", sizes: [ "S", "M", "L"] })
```

```shell
# 以下聚合阶段使用 $unwind 为 sizes 数组中的每一个元素输出文档：
db.inventory.aggregate( [ { $unwind : "$sizes" } ] )
```

返回结果集如下：

```shell
{ "_id" : 1, "item" : "ABC1", "sizes" : "S" }
{ "_id" : 1, "item" : "ABC1", "sizes" : "M" }
{ "_id" : 1, "item" : "ABC1", "sizes" : "L" }
```

除了 `size` 字段的值，每个文档都与原始输入文档相同，该字段持有原始 `sizes` 数组中的值。

##### 2) `includeArrayIndex` 和 `preserveNullAndEmptyArrays`

插入如下集合数据

```shell
db.inventory2.insertMany([
  { "_id" : 1, "item" : "ABC", price: NumberDecimal("80"), "sizes": [ "S", "M", "L"] },
  { "_id" : 2, "item" : "EFG", price: NumberDecimal("120"), "sizes" : [ ] },
  { "_id" : 3, "item" : "IJK", price: NumberDecimal("160"), "sizes": "M" },
  { "_id" : 4, "item" : "LMN" , price: NumberDecimal("10") },
  { "_id" : 5, "item" : "XYZ", price: NumberDecimal("5.75"), "sizes" : null }
])
```

以下两个 `$unwind` 操作是等效的，并为 `sizes` 字段中的每个元素返回一个文档。 

> - [x] note：如果 `sizes` 字段未解析为数组，但不丢失、不为 `null` 、也不为空数组，则 `$unwind` 会将非数组操作数视为单元素数组。

```shell
# 下面两个 $unwind 操作是等效的
db.inventory2.aggregate( [ { $unwind: "$sizes" } ] )
db.inventory2.aggregate( [ { $unwind: { path: "$sizes" } } ] )
```

输出结果如下：
```shell
{ "_id" : 1, "item" : "ABC", "price" : NumberDecimal("80"), "sizes" : "S" }
{ "_id" : 1, "item" : "ABC", "price" : NumberDecimal("80"), "sizes" : "M" }
{ "_id" : 1, "item" : "ABC", "price" : NumberDecimal("80"), "sizes" : "L" }
{ "_id" : 3, "item" : "IJK", "price" : NumberDecimal("160"), "sizes" : "M" }
```

######  ①`includeArrayIndex`

以下 `$unwind ` 使用 `includeArrayIndex` 选项可在输出文档中包含该元素所在数组的索引

```shell
db.inventory2.aggregate( [
  {
    $unwind:
      {
        path: "$sizes",
        includeArrayIndex: "arrayIndex"
      }
   }])
```

输出结果如下：

该操作展开 `sizes` 数组，并将每一个元素的数组索引包含在新的 `arrayIndex` 字段中。 如果 `sizes` 字段未解析为数组，但不丢失、不为 null 或不为空数组，则 `arrayIndex` 字段为 `null`。

```shell
{ "_id" : 1, "item" : "ABC", "price" : NumberDecimal("80"), "sizes" : "S", "arrayIndex" : NumberLong(0) }
{ "_id" : 1, "item" : "ABC", "price" : NumberDecimal("80"), "sizes" : "M", "arrayIndex" : NumberLong(1) }
{ "_id" : 1, "item" : "ABC", "price" : NumberDecimal("80"), "sizes" : "L", "arrayIndex" : NumberLong(2) }
{ "_id" : 3, "item" : "IJK", "price" : NumberDecimal("160"), "sizes" : "M", "arrayIndex" : null }
```

###### ② `preserveNullAndEmptyArrays`

```shell
db.inventory2.aggregate( [
   { $unwind: { path: "$sizes", preserveNullAndEmptyArrays: true } }
] )
```

输出结果也包括 `size` 字段为 `null`、缺失或空数组的文档：

```shell
{ "_id" : 1, "item" : "ABC", "price" : NumberDecimal("80"), "sizes" : "S" }
{ "_id" : 1, "item" : "ABC", "price" : NumberDecimal("80"), "sizes" : "M" }
{ "_id" : 1, "item" : "ABC", "price" : NumberDecimal("80"), "sizes" : "L" }
{ "_id" : 2, "item" : "EFG", "price" : NumberDecimal("120") }
{ "_id" : 3, "item" : "IJK", "price" : NumberDecimal("160"), "sizes" : "M" }
{ "_id" : 4, "item" : "LMN", "price" : NumberDecimal("10") }
{ "_id" : 5, "item" : "XYZ", "price" : NumberDecimal("5.75"), "sizes" : null }
```

##### 3) 根据 `unwind` 值进行分组

同样的插入如下数据

```shell
db.inventory2.insertMany([
  { "_id" : 1, "item" : "ABC", price: NumberDecimal("80"), "sizes": [ "S", "M", "L"] },
  { "_id" : 2, "item" : "EFG", price: NumberDecimal("120"), "sizes" : [ ] },
  { "_id" : 3, "item" : "IJK", price: NumberDecimal("160"), "sizes": "M" },
  { "_id" : 4, "item" : "LMN" , price: NumberDecimal("10") },
  { "_id" : 5, "item" : "XYZ", price: NumberDecimal("5.75"), "sizes" : null }
])
```

以下管道展开 `sizes` 数组并按展开的 `sizes` 值对结果文档进行分组：

```shell
db.inventory2.aggregate( [
   # 第一阶段
   {
     $unwind: { path: "$sizes", preserveNullAndEmptyArrays: true }
   },
   # 第二阶段
   {
     $group:
       {
         _id: "$sizes",
         averagePrice: { $avg: "$price" }
       }
   },
   # 第三阶段
   {
     $sort: { "averagePrice": -1 }
   }
] )
```

- 第一阶段

  `$unwind` 阶段为 `sizes` 数组中的每个元素输出一个新文档。并且 `preserveNullAndEmptyArrays` 为 `true`,  此阶段将以下文档传递到下一阶段：

  ```shell
  { "_id" : 1, "item" : "ABC", "price" : NumberDecimal("80"), "sizes" : "S" }
  { "_id" : 1, "item" : "ABC", "price" : NumberDecimal("80"), "sizes" : "M" }
  { "_id" : 1, "item" : "ABC", "price" : NumberDecimal("80"), "sizes" : "L" }
  { "_id" : 2, "item" : "EFG", "price" : NumberDecimal("120") }
  { "_id" : 3, "item" : "IJK", "price" : NumberDecimal("160"), "sizes" : "M" }
  { "_id" : 4, "item" : "LMN", "price" : NumberDecimal("10") }
  { "_id" : 5, "item" : "XYZ", "price" : NumberDecimal("5.75"), "sizes" : null }
  ```

- 第二阶段

  `$group` 阶段按 `sizes` 对文档进行分组，并计算每个 `sizes` 的平均价格。 此阶段将以下文档传递到下一阶段：

  ```shell
  { "_id" : "S", "averagePrice" : NumberDecimal("80") }
  { "_id" : "L", "averagePrice" : NumberDecimal("80") }
  { "_id" : "M", "averagePrice" : NumberDecimal("120") }
  { "_id" : null, "averagePrice" : NumberDecimal("45.25") }
  ```

- 第三阶段

  `$sort` 阶段按 `averagePrice` 对文档进行降序排序。 该操作返回以下结果：

  ```shell
  { "_id" : "M", "averagePrice" : NumberDecimal("120") }
  { "_id" : "L", "averagePrice" : NumberDecimal("80") }
  { "_id" : "S", "averagePrice" : NumberDecimal("80") }
  { "_id" : null, "averagePrice" : NumberDecimal("45.25") }
  ```

##### 4) `unwind` 嵌套数组

插入如下集合数据
```shell
db.sales.insertMany([
  {
    _id: "1",
    "items" : [
     {
      "name" : "pens",
      "tags" : [ "writing", "office", "school", "stationary" ],
      "price" : NumberDescimal("12.00"),
      "quantity" : NumberInt("5")
     },
     {
      "name" : "envelopes",
      "tags" : [ "stationary", "office" ],
      "price" : NumberDecimal("19.95"),
      "quantity" : NumberInt("8")
     }
    ]
  },
  {
    _id: "2",
    "items" : [
     {
      "name" : "laptop",
      "tags" : [ "office", "electronics" ],
      "price" : NumberDecimal("800.00"),
      "quantity" : NumberInt("1")
     },
     {
      "name" : "notepad",
      "tags" : [ "stationary", "school" ],
      "price" : NumberDecimal("14.95"),
      "quantity" : NumberInt("3")
     }
    ]
  }
])
```

以下操作按 `tag` 标签对销售的商品进行分组，并计算每个标签的总销售额。

```shell
db.sales.aggregate([
  # First Stage
  { $unwind: "$items" },

  # Second Stage
  { $unwind: "$items.tags" },

  # Third Stage
  {
    $group:
      {
        _id: "$items.tags",
        totalSalesAmount:
          {
            $sum: { $multiply: [ "$items.price", "$items.quantity" ] }
          }
      }
  }
])
```

- 第一阶段

  第一个 `unwind` 阶段为 `items` 数组中的每个元素输出一个新文档：

  ```shell
  { "_id" : "1", "items" : { "name" : "pens", "tags" : [ "writing", "office", "school", "stationary" ], "price" : NumberDecimal("12.00"), "quantity" : 5 } }
  { "_id" : "1", "items" : { "name" : "envelopes", "tags" : [ "stationary", "office" ], "price" : NumberDecimal("19.95"), "quantity" : 8 } }
  { "_id" : "2", "items" : { "name" : "laptop", "tags" : [ "office", "electronics" ], "price" : NumberDecimal("800.00"), "quantity" : 1 } }
  { "_id" : "2", "items" : { "name" : "notepad", "tags" : [ "stationary", "school" ], "price" : NumberDecimal("14.95"), "quantity" : 3 } }
  ```

- 第二阶段

  第二个 `$unwind` 阶段为 `items.tags` 数组中的每个元素输出一个新文档：

  ```shell
  { "_id" : "1", "items" : { "name" : "pens", "tags" : "writing", "price" : NumberDecimal("12.00"), "quantity" : 5 } }
  { "_id" : "1", "items" : { "name" : "pens", "tags" : "office", "price" : NumberDecimal("12.00"), "quantity" : 5 } }
  { "_id" : "1", "items" : { "name" : "pens", "tags" : "school", "price" : NumberDecimal("12.00"), "quantity" : 5 } }
  { "_id" : "1", "items" : { "name" : "pens", "tags" : "stationary", "price" : NumberDecimal("12.00"), "quantity" : 5 } }
  { "_id" : "1", "items" : { "name" : "envelopes", "tags" : "stationary", "price" : NumberDecimal("19.95"), "quantity" : 8 } }
  { "_id" : "1", "items" : { "name" : "envelopes", "tags" : "office", "price" : NumberDecimal("19.95"), "quantity" : 8 } }
  { "_id" : "2", "items" : { "name" : "laptop", "tags" : "office", "price" : NumberDecimal("800.00"), "quantity" : 1 } }
  { "_id" : "2", "items" : { "name" : "laptop", "tags" : "electronics", "price" : NumberDecimal("800.00"), "quantity" : 1 } }
  { "_id" : "2", "items" : { "name" : "notepad", "tags" : "stationary", "price" : NumberDecimal("14.95"), "quantity" : 3 } }
  { "_id" : "2", "items" : { "name" : "notepad", "tags" : "school", "price" : NumberDecimal("14.95"), "quantity" : 3 } }
  ```

- 第三阶段

  `$group` 阶段按 `tag `标签对文档进行分组，并计算带有每个标签的商品的总销售额：

  ```shell
  { "_id" : "writing", "totalSalesAmount" : NumberDecimal("60.00") }
  { "_id" : "stationary", "totalSalesAmount" : NumberDecimal("264.45") }
  { "_id" : "electronics", "totalSalesAmount" : NumberDecimal("800.00") }
  { "_id" : "school", "totalSalesAmount" : NumberDecimal("104.85") }
  { "_id" : "office", "totalSalesAmount" : NumberDecimal("1019.60") }
  ```



### 八、`projcet`

#### 1. 定义

将带有要求字段的文档传递到管道的下一阶段。 指定的字段可以是输入文档中的现有字段或新计算的字段。

#### 2. 语法

```shell
{ $project: { <specification(s)> } }
```

`$project` 接受一个文档，可以指定包含字段、去除 `_id` 字段、添加新字段以及重置现有字段的值。 或者，也可以指定排除字段。

 [`$project`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project) specifications 遵循如下形式

| 形式                    | 描述                                     |
| :---------------------- | :--------------------------------------- |
| `<field>: <1 or true>`  | 指定包含字段。 非零整数也被视为 `true`。 |
| `_id: <0 or false>`     | 指定排除 `_id` 字段。                    |
| `<field>: <expression>` | 添加新字段或重置现有字段的值。           |
| `<field>: <0 or false>` | 指定排除的字段                           |

> - [x] 注意：如果 `$project` 规范是空文档，则会返回错误。
>
>   即如下形式会报错: `db.collection.aggregate([{ $project: {}}])`

#### 3. 注意事项

##### 1) 包括已存在的字段

- 默认情况下，该 `_id` 字段包含在输出文档中。要在输出文档中包含输入文档中的任何其他字段，您必须明确指定包含 `$project`
- 如果您指定一个在文档中不存在的字段，[`$project`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project)忽略该字段包含，并且不将该字段添加到文档中。

##### 2) 抑制 `_id` 字段

默认情况下，`_id` 字段包含在输出文档中。 要从输出文档中排除 `_id` 字段，您必须在 `$project` 中显式指定抑制 `_id` 字段。

##### 3) 排除字段

如果指定排除一个或多个字段，则输出文档中将返回所有其他字段。

```shell
# 返回除了指定排除字段以外的其他所有字段
{ $project: { "<field1>": 0, "<field2>": 0, ... } } 
```

> - [x] 注意：如果指定了除 `_id` 意外的其他排除字段，则不能再指定包含字段、重置现有字段的值或添加新字段。但此限制不适用于使用 `REMOVE` 变量有条件排除字段。

也可以通过 [`$unset`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/unset/#mongodb-pipeline-pipe.-unset) 阶段去排除指定字段。

###### ① 有条件地排除字段

您可以在聚合表达式中使用变量 [`REMOVE`](https://www.mongodb.com/docs/v4.4/reference/aggregation-variables/#mongodb-variable-variable.REMOVE) 有条件地抑制字段。 有关示例，请参阅[
Conditionally Exclude Fields.](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/project/#std-label-remove-example)。

##### 4) 添加新字段或者重置已有字段

要添加新字段或重置现有字段的值，请指定字段名称并将其值设置为某个**表达式**。 有关表达式的更多信息，请参阅 [Expressions](https://www.mongodb.com/docs/v4.4/reference/aggregation-quick-reference/#std-label-aggregation-expressions)。

###### ① Literal Values

要将字段值直接设置为数字或布尔值，而不是将字段设置为解析为文字的表达式，请使用 [`$literal`](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/literal/#mongodb-expression-exp.-literal) 运算符。 否则，`$project` 将数字或布尔值视为包含或排除该字段的标志。

###### ② 字段重命名

通过指定新字段并将其值设置为现有字段的字段路径，您可以有效地重命名字段。

###### ③ 添加新的数组字段

`$project` 阶段支持使用方括号 `[]` 直接创建新的数组字段。 如果指定文档中不存在的数组字段，该操作将替换 `null` 作为该字段的值。 有关示例，请参阅 [Project New Array Fields.](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/project/#std-label-example-project-new-array-fields)

不能使用数组索引在 `$project` 阶段，请查阅 [Array Indexes are Unsupported](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/project/#std-label-example-project-array-indexes)

##### 5) 嵌入式文档字段

在嵌入文档中投影或添加/重置字段时，您可以使用点表示法，如下所示

```shell
"contact.address.country": <1 or 0 or expression>
```

或者也可以使用嵌套字段形式

```shell
contact: { address: { country: <1 or 0 or expression> } }
```

当使用嵌套字段形式时，不能在嵌入文档中使用点`.`符号来指定字段，例如

`contact: { "address.country": <1 or 0 or expression> } ` 该形式是无效的

###### ① 嵌入字段中的路径冲突错误

您不能在同一投影中同时指定嵌入文档和该嵌入文档中的字段。

以下 `$project` 阶段将失败并出现路径冲突错误，因为它尝试同时投影嵌入式文档字段 `contact` 和 `contact.address.country` 字段：

#### 4. 示例

##### 1) 包括指定字段

创建一个 `books` 的集合并有如下文档数据

```shell
{
  "_id" : 1,
  title: "abc123",
  isbn: "0001122223334",
  author: { last: "zzz", first: "aaa" },
  copies: 5
}
```

以下 `$project` 阶段在其输出文档中仅包含 `_id`、`title` 和 `author` 字段：

```shell
db.books.aggregate( [ { $project : { title : 1 , author : 1 } } ] )
```

返回结果如下：

```shell
{ "_id" : 1, "title" : "abc123", "author" : { "last" : "zzz", "first" : "aaa" } }
```

##### 2) 抑制 `id` 字段

默认情况下始终包含 `_id` 字段。 要从 `$project` 阶段的输出文档中排除 `_id` 字段，通过在投影文档中将 `_id` 字段设置为 0 来指定排除 `_id` 字段。

```shell
db.books.aggregate( [ { $project : { _id: 0, title : 1 , author : 1 } } ] )
```

输出结果如下：

```shell
{ "title" : "abc123", "author" : { "last" : "zzz", "first" : "aaa" } }
```

##### 3) 排除指定字段

创建如下集合数据

```shell
{
  "_id" : 1,
  title: "abc123",
  isbn: "0001122223334",
  author: { last: "zzz", first: "aaa" },
  copies: 5,
  lastModified: "2016-07-28"
}
```

在输出文档中排除 `lastModified` 字段

```shell
db.books.aggregate( [ { $project : { "lastModified": 0 } } ] )
```

##### 4) 在嵌入式文档中排除指定字段

创建以下集合数据

```shell
{
  "_id" : 1,
  title: "abc123",
  isbn: "0001122223334",
  author: { last: "zzz", first: "aaa" },
  copies: 5,
  lastModified: "2016-07-28"
}
```

排除 `author.first` 字段和 `lastModified` 字段

```shell
db.books.aggregate( [ { $project : { "author.first" : 0, "lastModified" : 0 } } ] )
```

或者，也可以将排除规范的形式嵌套在文档中

```shell
db.bookmarks.aggregate( [ { $project: { "author": { "first": 0}, "lastModified" : 0 } } ] )
```

输出结果如下：

```shell
{
   "_id" : 1,
   "title" : "abc123",
   "isbn" : "0001122223334",
   "author" : {
      "last" : "zzz"
   },
   "copies" : 5,
}
```

##### 5) Conditionally 有条件地排除字段

您可以在聚合表达式中使用变量 `REMOVE` 有条件地排除字段。

创建一个 `books` 集合：

```shell
{
  "_id" : 1,
  title: "abc123",
  isbn: "0001122223334",
  author: { last: "zzz", first: "aaa" },
  copies: 5,
  lastModified: "2016-07-28"
}
{
  "_id" : 2,
  title: "Baked Goods",
  isbn: "9999999999999",
  author: { last: "xyz", first: "abc", middle: "" },
  copies: 2,
  lastModified: "2017-07-21"
}
{
  "_id" : 3,
  title: "Ice Cream Cakes",
  isbn: "8888888888888",
  author: { last: "xyz", first: "abc", middle: "mmm" },
  copies: 5,
  lastModified: "2017-07-22"
}
```

以下 `$project` 阶段使用 `REMOVE` 变量来排除 `author.middle` 字段仅在该字段等于 `""` 时：

```shell
db.books.aggregate( [
   {
      $project: {
         title: 1,
         "author.first": 1,
         "author.last" : 1,
         "author.middle": {
            $cond: {
               if: { $eq: [ "", "$author.middle" ] },
               then: "$$REMOVE",
               else: "$author.middle"
            }
         }
      }
   }
] )
```

输出结果如下：

```shell
{ "_id" : 1, "title" : "abc123", "author" : { "last" : "zzz", "first" : "aaa" } }
{ "_id" : 2, "title" : "Baked Goods", "author" : { "last" : "xyz", "first" : "abc" } }
{ "_id" : 3, "title" : "Ice Cream Cakes", "author" : { "last" : "xyz", "first" : "abc", "middle" : "mmm" } }
```

##### 6) 包括嵌入文档中的特定字段

以下 `bookmarks `集合有如下文档数据

```shell
{ _id: 1, user: "1234", stop: { title: "book1", author: "xyz", page: 32 } }
{ _id: 2, user: "7890", stop: [ { title: "book2", author: "abc", page: 5 }, { title: "book3", author: "ijk", page: 100 } ] }
```

要在 `stop` 字段中仅包含嵌入文档中的`title`字段，您可以使用点 `.` 符号：

```shell
db.bookmarks.aggregate( [ { $project: { "stop.title": 1 } } ] )
```

或者也可以使用规范嵌套文档的形式

```shell
db.bookmarks.aggregate( [ { $project: { stop: { title: 1 } } } ] )
```

返回结果如下：

```shell
{ "_id" : 1, "stop" : { "title" : "book1" } }
{ "_id" : 2, "stop" : [ { "title" : "book2" }, { "title" : "book3" } ] }
```

##### 7) 包括新计算的字段

创建一个 `books` 集合

```shell
{
  "_id" : 1,
  title: "abc123",
  isbn: "0001122223334",
  author: { last: "zzz", first: "aaa" },
  copies: 5
}
```

以下 `$project` 阶段添加新字段 `isbn`、`lastName` 和 `copySold`：

```shell
db.books.aggregate(
   [
      {
         $project: {
            title: 1,
            isbn: {
               prefix: { $substr: [ "$isbn", 0, 3 ] },
               group: { $substr: [ "$isbn", 3, 2 ] },
               publisher: { $substr: [ "$isbn", 5, 4 ] },
               title: { $substr: [ "$isbn", 9, 3 ] },
               checkDigit: { $substr: [ "$isbn", 12, 1] }
            },
            lastName: "$author.last",
            copiesSold: "$copies"
         }
      }
   ]
)
```

输出结果如下：

```shell
{
   "_id" : 1,
   "title" : "abc123",
   "isbn" : {
      "prefix" : "000",
      "group" : "11",
      "publisher" : "2222",
      "title" : "333",
      "checkDigit" : "4"
   },
   "lastName" : "zzz",
   "copiesSold" : 5
}
```

##### 8) 投影新的数组字段

例如有以下集合文档

```shell
{ "_id" : ObjectId("55ad167f320c6be244eb3b95"), "x" : 1, "y" : 1 }
```

以下操作将字段 `x` 和 `y` 投影为新字段 `myArray` 中的元素：

```shell
db.collection.aggregate( [ { $project: { myArray: [ "$x", "$y" ] } } ] )
```

返回结果如下：

```shell
{ "_id" : ObjectId("55ad167f320c6be244eb3b95"), "myArray" : [ 1, 1 ] }
```

如果数组规范包含文档中不存在的字段，则将 `null` 作为该字段的值。

例如，给定与上面相同的文档，以下操作将字段 `x`、`y` 和不存在的字段 `$unExistedField` 投影为新字段 `myArray` 中的元素：

```shell
db.collection.aggregate( [ { $project: { myArray: [ "$x", "$y", "$unExistedField" ] } } ] )
```

返回结果如下：

```shell	
{ "_id" : ObjectId("55ad167f320c6be244eb3b95"), "myArray" : [ 1, 1, null ] }
```

##### 9) 不支持数组索引

例如创建如下 `pizzas` 集合

```shell
db.pizzas.insert( [
   { _id: 0, name: [ 'Pepperoni', 'Margherita' ] },
] )
```

###### ① 正确示例

```shell
db.pizzas.aggregate( [
   { $project: { x: '$name', _id: 0 } },
] )
```

返回结果如下：

```shell
[ { x: [ 'Pepperoni','Margherita' ] } ]
```

###### ② 错误示例

以下示例使用数组索引 `$name.0` 尝试返回数组中的第一个元素：

```shell
db.pizzas.aggregate( [
   { $project: { x: '$name.0', _id: 0 } },
] )
```

返回结果如下：

```shell
[ { x: [] } ]
```



### 九、`$facet`

#### 1. 定义

在同一组输入文档的单个阶段内处理多个聚合管道。每个子管道在输出文档中都有自己的字段，其结果以文档数组的形式存储。 

$facet 阶段允许您在单个聚合阶段中创建多维聚合，以描述数据在多个维度或方面上的特征。多维聚合提供多个过滤器和分类，以指导数据浏览和分析。零售商通常使用分面来通过创建产品价格、制造商、尺寸等过滤器来缩小搜索结果。 

输入文档仅通过 `$facet` 阶段一次。`$facet` 可以在相同的输入文档集上进行各种聚合，而无需多次检索输入文档。

#### 2. 语法

```shell
{ $facet:
    {
      <outputField1>: [ <stage1>, <stage2>, ... ],
      <outputField2>: [ <stage1>, <stage2>, ... ],
      ...

    }
}
```

#### 3. 行为

`$facet` 中的每个子管道都会传递完全相同的一组输入文档。 这些子管道彼此完全独立，每个子管道输出的文档数组存储在输出文档中的单独字段中。 一个子管道的输出不能用作同一 `$facet` 阶段内不同子管道的输入。 如果需要进一步聚合，请在 `$facet` 之后添加其他阶段，并指定所需子管道输出的字段名称 `<outputField>`。

##### 索引使用

管道顺序决定 `$facet` 阶段如何使用索引。

- 如果 `$facet` 阶段是管道中的第一个阶段，则该阶段将执行 `COLLSCAN`(全集合扫描)。 如果 `$facet` 阶段是管道中的第一阶段，则它不会使用索引。


- 如果 `$facet` 阶段出现在管道中的较晚阶段且较早的阶段已使用索引，则 `$facet` 在执行期间将不会触发 `COLLSCAN`。


例如，`$facet` 阶段之前的 `$match` 或 `$sort` 阶段可以使用索引，并且 `$facet` 不会触发 `COLLSCAN`。



#### 4. 示例

考虑一家在线商店，其库存 `inventory` 存储在以下 `artwork` 艺术品收藏中：

```shell
{ "_id" : 1, "title" : "The Pillars of Society", "artist" : "Grosz", "year" : 1926,
  "price" : NumberDecimal("199.99"),"tags" : [ "painting", "satire", "Expressionism", "caricature" ] }
  
{ "_id" : 2, "title" : "Melancholy III", "artist" : "Munch", "year" : 1902,
  "price" : NumberDecimal("280.00"), "tags" : [ "woodcut", "Expressionism" ] }
  
{ "_id" : 3, "title" : "Dancer", "artist" : "Miro", "year" : 1925,
  "price" : NumberDecimal("76.04"), "tags" : [ "oil", "Surrealism", "painting" ] 
  
{ "_id" : 4, "title" : "The Great Wave off Kanagawa", "artist" : "Hokusai",
  "price" : NumberDecimal("167.30"),"tags" : [ "woodblock", "ukiyo-e" ] }
  
{ "_id" : 5, "title" : "The Persistence of Memory", "artist" : "Dali", "year" : 1931,"price" : NumberDecimal("483.00"), "tags" : [ "Surrealism", "painting", "oil" ] }

{ "_id" : 6, "title" : "Composition VII", "artist" : "Kandinsky", "year" : 1913,
  "price" : NumberDecimal("385.00"), "tags" : [ "oil", "painting", "abstract" ] }
  
{ "_id" : 7, "title" : "The Scream", "artist" : "Munch", "year" : 1893 
/* no price */  "tags" : [ "Expressionism", "painting", "oil" ] }
  
{ "_id" : 8, "title" : "Blue Flower", "artist" : "O'Keefe", "year" : 1918,
  "price" : NumberDecimal("118.42"),"tags" : [ "abstract", "painting" ] }
```

以下操作使用 MongoDB 的 **facet** 聚合功能为客户提供跨多个维度（例如标签、价格和创建年份）分类的商店库存。 此 `$facet` 阶段具有三个子管道，它们使用 `$sortByCount`、`$bucket` 或 `$bucketAuto` 来执行此多方面聚合。 

```shell
db.artwork.aggregate( [
  {
    $facet: {
      "categorizedByTags": [
        { $unwind: "$tags" },
        { $sortByCount: "$tags" }
      ],
      "categorizedByPrice": [
        // Filter out documents without a price e.g., _id: 7
        { $match: { price: { $exists: 1 } } },
        {
          $bucket: {
            groupBy: "$price",
            boundaries: [  0, 150, 200, 300, 400 ],
            default: "Other",
            output: {
              "count": { $sum: 1 },
              "titles": { $push: "$title" }
            }
          }
        }
      ],
      "categorizedByYears(Auto)": [
        {
          $bucketAuto: {
            groupBy: "$year",
            buckets: 4
          }
        }
      ]
    }
  }
])
```

输出结果如下：

```shell
{
  "categorizedByYears(Auto)" : [
    // First bucket includes the document without a year, e.g., _id: 4
    { "_id" : { "min" : null, "max" : 1902 }, "count" : 2 },
    { "_id" : { "min" : 1902, "max" : 1918 }, "count" : 2 },
    { "_id" : { "min" : 1918, "max" : 1926 }, "count" : 2 },
    { "_id" : { "min" : 1926, "max" : 1931 }, "count" : 2 }
  ],
  "categorizedByPrice" : [
    {
      "_id" : 0,
      "count" : 2,
      "titles" : [
        "Dancer",
        "Blue Flower"
      ]
    },
    {
      "_id" : 150,
      "count" : 2,
      "titles" : [
        "The Pillars of Society",
        "The Great Wave off Kanagawa"
      ]
    },
    {
      "_id" : 200,
      "count" : 1,
      "titles" : [
        "Melancholy III"
      ]
    },
    {
      "_id" : 300,
      "count" : 1,
      "titles" : [
        "Composition VII"
      ]
    },
    {
      // Includes document price outside of bucket boundaries, e.g., _id: 5
      "_id" : "Other",
      "count" : 1,
      "titles" : [
        "The Persistence of Memory"
      ]
    }
  ],
  "categorizedByTags" : [
    { "_id" : "painting", "count" : 6 },
    { "_id" : "oil", "count" : 4 },
    { "_id" : "Expressionism", "count" : 3 },
    { "_id" : "Surrealism", "count" : 2 },
    { "_id" : "abstract", "count" : 2 },
    { "_id" : "woodblock", "count" : 1 },
    { "_id" : "woodcut", "count" : 1 },
    { "_id" : "ukiyo-e", "count" : 1 },
    { "_id" : "satire", "count" : 1 },
    { "_id" : "caricature", "count" : 1 }
  ]
}
```



### 十、`$bucket`

#### 1. 定义

根据指定的表达式和存储桶边界，将传入文档分类为称为存储桶的组，并为每个存储桶输出一个文档。 每个输出文档都包含一个` _id` 字段，其值指定存储桶的包含下限。 `output` 选项指定每个输出文档中包含的字段。

`$bucket` 仅为包含至少一个输入文档的存储桶生成输出文档。

#### 2. 语法

```shell
{
  $bucket: {
      groupBy: <expression>,
      boundaries: [ <lowerbound1>, <lowerbound2>, ... ],
      default: <literal>,
      output: {
         <output1>: { <$accumulator expression> },
         ...
         <outputN>: { <$accumulator expression> }
      }
   }
}
```

| Field                                                        | Type       | Description                                                  |
| :----------------------------------------------------------- | :--------- | :----------------------------------------------------------- |
| [groupBy](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/bucket/#std-label-bucket-group-by) | expression | 用于对文档进行分组的表达式。 要指定字段路径，请在字段名称前添加美元符号 `$` 并将其括在引号`""`中。 |
| [boundaries](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/bucket/#std-label-bucket-boundaries) | array      | 1. 基于指定每个存储桶边界的 `groupBy` 表达式的值数组。 **每对相邻**的值都充当存储桶的包含下边界和排他上边界,  即左闭右开 `[ )`。 必须指定至少两个边界。<br />2. 指定的数组值必须是升序排序并且是相同类型<br />3. 例如： `[ 0, 5, 10 ]` 将创建两个 buckets：[0, 5) 和 [5,10) |
| [default](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/bucket/#std-label-bucket-default) | literal    | *可选参数*。<br />1. 它是一个字面量，用于指定一个额外的存储桶的 `_id`，其中包含所有 `groupBy` 表达式的结果不在由 `boundaries` 指定的存储桶范围内的文档。 <br />2. 如果未指定，默认情况下，每个输入文档必须将 `groupBy` 表达式解析为 `boundaries` 指定的存储桶范围内的值，否则操作将抛出错误。 <br />3. 默认值必须小于最低 `boundaries` 值，或大于等于最高 `boundaries` 值。 <br />4. 默认值可以与 `boundaries` 中的条目类型不同。 |
| [output](https://www.mongodb.com/docs/v4.4/reference/operator/aggregation/bucket/#std-label-bucket-output) | document   | *可选参数*<br />1. 指定除 `_id `字段之外还要包含在输出文档中的字段的文档。 要指定要包含的字段，**必须使用累加器表达式**。<br />2. 如果不指定输出文档，该操作将返回一个 `count` 字段，其中包含每个存储桶中的文档数。<br />3. 如果指定输出文档，则仅返回文档中指定的字段； 即，除非明确地将`count` 字段包含在输出文档中，否则不会返回 `count` 字段。 |

#### 3. 行为

`$bucket` 操作需要满足以下条件之一，否则将抛出错误： 

- 每个输入文档的 `groupBy` 表达式解析为在 `boundaries` 指定的桶范围内的值
- 或者指定了一个默认值，用于将 `groupBy` 值在边界之外或与边界中的值不同的文档分桶。 

如果`groupBy` 表达式解析为数组或文档，则 `$bucket` 将使用 `$sort` 的比较逻辑将输入文档分组成桶。

#### 4. 示例

##### 1) 按年份将文档进行分桶并过滤

创建一个艺术家 `artists` 的集合

```shell
db.artists.insertMany([
  { "_id" : 1, "last_name" : "Bernard", "first_name" : "Emil", "year_born" : 1868, "year_died" : 1941, "nationality" : "France" },
  { "_id" : 2, "last_name" : "Rippl-Ronai", "first_name" : "Joszef", "year_born" : 1861, "year_died" : 1927, "nationality" : "Hungary" },
  { "_id" : 3, "last_name" : "Ostroumova", "first_name" : "Anna", "year_born" : 1871, "year_died" : 1955, "nationality" : "Russia" },
  { "_id" : 4, "last_name" : "Van Gogh", "first_name" : "Vincent", "year_born" : 1853, "year_died" : 1890, "nationality" : "Holland" },
  { "_id" : 5, "last_name" : "Maurer", "first_name" : "Alfred", "year_born" : 1868, "year_died" : 1932, "nationality" : "USA" },
  { "_id" : 6, "last_name" : "Munch", "first_name" : "Edvard", "year_born" : 1863, "year_died" : 1944, "nationality" : "Norway" },
  { "_id" : 7, "last_name" : "Redon", "first_name" : "Odilon", "year_born" : 1840, "year_died" : 1916, "nationality" : "France" },
  { "_id" : 8, "last_name" : "Diriks", "first_name" : "Edvard", "year_born" : 1855, "year_died" : 1930, "nationality" : "Norway" }
])
```

以下操作根据 `year_born` 字段将文档分组到桶中，并根据桶中文档的数量进行过滤：

```shell
db.artists.aggregate( [
  #  第一阶段
  {
    $bucket: {
      groupBy: "$year_born",  # 按照出生年份进行分组的字段
      boundaries: [ 1840, 1850, 1860, 1870, 1880 ], # 分桶的边界值
      default: "Other", # 不属于任何桶的文档的桶标识_id                
      output: {  #  每个桶的输出字段
        "count": { $sum: 1 },
        "artists" :
          {
            $push: {
              "name": { $concat: [ "$first_name", " ", "$last_name"] },
              "year_born": "$year_born"
            }
          }
      }
    }
  },
  # 第二阶段
  {
    $match: { count: {$gt: 3} }
  }
] )
```

**第一阶段**

`$bucket` 阶段根据 `year_born` 字段将文档分组成桶。这些桶具有以下边界：

- [1840, 1850) 包含下限 `1840`，不包含上限 `1850`。
- [1850, 1860) 包含下限 `1850`，不包含上限 `1860`。
- [1860, 1870) 同理。。。
- [1870, 1880) 同理。。。

如果一个文档没有包含 `year_born` 字段，或者它的 `year_born` 字段超出了上述范围，它将被放置在默认的桶中，该桶的 `_id` 值为 `Other`。

该阶段包括 `output` 文档来确定要返回的字段：

- ` _id`：桶的**下限**值。 

- `count`：各桶中的文档数目。
- `artists`：包含每个桶中艺术家信息的文档数组。每个文档包含艺术家的 `name`字段，该字段是艺术家的 `first_name` 和 `last_name` 的连接（即 `$concat`表达式 ）。

第一阶段的输出结果如下：

```shell
[
    {
        "_id": 1840,
        "count": 1,
        "artists": [
            {
                "name": "Odilon Redon",
                "year_born": 1840
            }
        ]
    },
    {
        "_id": 1850,
        "count": 2,
        "artists": [
            {
                "name": "Vincent Van Gogh",
                "year_born": 1853
            },
            {
                "name": "Edvard Diriks",
                "year_born": 1855
            }
        ]
    },
    {
        "_id": 1860,
        "count": 4,
        "artists": [
            {
                "name": "Emil Bernard",
                "year_born": 1868
            },
            {
                "name": "Joszef Rippl-Ronai",
                "year_born": 1861
            },
            {
                "name": "Alfred Maurer",
                "year_born": 1868
            },
            {
                "name": "Edvard Munch",
                "year_born": 1863
            }
        ]
    },
    {
        "_id": 1870,
        "count": 1,
        "artists": [
            {
                "name": "Anna Ostroumova",
                "year_born": 1871
            }
        ]
    }
]
```

**第二阶段**

`$match` 阶段过滤前一阶段的输出文档，仅返回包含 3 个以上文档的存储桶。

最终结果集如下：

```shell
{ "_id" : 1860, "count" : 4, "artists" :
  [
    { "name" : "Emil Bernard", "year_born" : 1868 },
    { "name" : "Joszef Rippl-Ronai", "year_born" : 1861 },
    { "name" : "Alfred Maurer", "year_born" : 1868 },
    { "name" : "Edvard Munch", "year_born" : 1863 }
  ]
}
```

##### 2) 使用 `$bucket` 和 `$facet` 来按多个字段进行文档分桶

往 `artwork` 集合中插入如下数据：

```shell
db.artwork.insertMany([
  { "_id" : 1, "title" : "The Pillars of Society", "artist" : "Grosz", "year" : "price" : NumberDecimal("199.99") },
  
  { "_id" : 2, "title" : "Melancholy III", "artist" : "Munch", "year" : 1902,
      "price" : NumberDecimal("280.00") },
      
  { "_id" : 3, "title" : "Dancer", "artist" : "Miro", "year" : 1925,
      "price" : NumberDecimal("76.04") },
      
  { "_id" : 4, "title" : "The Great Wave off Kanagawa", "artist" : "Hokusai",
      "price" : NumberDecimal("167.30") },
      
  { "_id" : 5, "title" : "The Persistence of Memory", "artist" : "Dali", "year" : 1931, "price" : NumberDecimal("483.00") },
  
  { "_id" : 6, "title" : "Composition VII", "artist" : "Kandinsky", "year" : 1913, "price" : NumberDecimal("385.00") },
  
  { "_id" : 7, "title" : "The Scream", "artist" : "Munch", "year" : 1893
      # No price # },
      
  { "_id" : 8, "title" : "Blue Flower", "artist" : "O'Keefe", "year" : 1918,
      "price" : NumberDecimal("118.42") }
])
```

以下操作使 `$facet`阶段中的两个`$bucket`阶段来创建两个分组，一个按`price`，另一个按`year`.

```shell
db.artwork.aggregate( [
  {
    $facet: {                               // Top-level $facet stage
      "price": [                            // Output field 1
        {
          $bucket: {
              groupBy: "$price",            // Field to group by
              boundaries: [ 0, 200, 400 ],  // Boundaries for the buckets
              default: "Other",             // Bucket ID for documents which do not fall into a bucket
              output: {                     // Output for each bucket
                "count": { $sum: 1 },
                "artwork" : { $push: { "title": "$title", "price": "$price" } },
                "averagePrice": { $avg: "$price" }
              }
          }
        }
      ],
      "year": [                                      // Output field 2
        {
          $bucket: {
            groupBy: "$year",                        // Field to group by
            boundaries: [ 1890, 1910, 1920, 1940 ],  // Boundaries for the buckets
            default: "Unknown",                      // Bucket ID for documents which do not fall into a bucket
            output: {                                // Output for each bucket
              "count": { $sum: 1 },
              "artwork": { $push: { "title": "$title", "year": "$year" } }
            }
          }
        }
      ]
    }
  }
] )
```

输出结果如下：

```shell
{
  "price" : [ // Output of first facet
    {
      "_id" : 0,
      "count" : 4,
      "artwork" : [
        { "title" : "The Pillars of Society", "price" : NumberDecimal("199.99") },
        { "title" : "Dancer", "price" : NumberDecimal("76.04") },
        { "title" : "The Great Wave off Kanagawa", "price" : NumberDecimal("167.30") },
        { "title" : "Blue Flower", "price" : NumberDecimal("118.42") }
      ],
      "averagePrice" : NumberDecimal("140.4375")
    },
    {
      "_id" : 200,
      "count" : 2,
      "artwork" : [
        { "title" : "Melancholy III", "price" : NumberDecimal("280.00") },
        { "title" : "Composition VII", "price" : NumberDecimal("385.00") }
      ],
      "averagePrice" : NumberDecimal("332.50")
    },
    {
      // Includes documents without prices and prices greater than 400
      "_id" : "Other",
      "count" : 2,
      "artwork" : [
        { "title" : "The Persistence of Memory", "price" : NumberDecimal("483.00") },
        { "title" : "The Scream" }
      ],
      "averagePrice" : NumberDecimal("483.00")
    }
  ],
  "year" : [ // Output of second facet
    {
      "_id" : 1890,
      "count" : 2,
      "artwork" : [
        { "title" : "Melancholy III", "year" : 1902 },
        { "title" : "The Scream", "year" : 1893 }
      ]
    },
    {
      "_id" : 1910,
      "count" : 2,
      "artwork" : [
        { "title" : "Composition VII", "year" : 1913 },
        { "title" : "Blue Flower", "year" : 1918 }
      ]
    },
    {
      "_id" : 1920,
      "count" : 3,
      "artwork" : [
        { "title" : "The Pillars of Society", "year" : 1926 },
        { "title" : "Dancer", "year" : 1925 },
        { "title" : "The Persistence of Memory", "year" : 1931 }
      ]
    },
    {
      // Includes documents without a year
      "_id" : "Unknown",
      "count" : 1,
      "artwork" : [
        { "title" : "The Great Wave off Kanagawa" }
      ]
    }
  ]
}
```

