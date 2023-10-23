# MongoDB 更新操作

下列介绍三种更新方法：

- `db.collection.updateOne`
- `db.collection.updateMany`
- `db.collection.replaceOne`

插入如下数据

```javascript
db.inventory.insertMany( [
   { item: "canvas", qty: 100, size: { h: 28, w: 35.5, uom: "cm" }, status: "A" },
   { item: "journal", qty: 25, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
   { item: "mat", qty: 85, size: { h: 27.9, w: 35.5, uom: "cm" }, status: "A" },
   { item: "mousepad", qty: 25, size: { h: 19, w: 22.85, uom: "cm" }, status: "P" },
   { item: "notebook", qty: 50, size: { h: 8.5, w: 11, uom: "in" }, status: "P" },
   { item: "paper", qty: 100, size: { h: 8.5, w: 11, uom: "in" }, status: "D" },
   { item: "planner", qty: 75, size: { h: 22.85, w: 30, uom: "cm" }, status: "D" },
   { item: "postcard", qty: 45, size: { h: 10, w: 15.25, uom: "cm" }, status: "A" },
   { item: "sketchbook", qty: 80, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
   { item: "sketch pad", qty: 95, size: { h: 22.85, w: 30.5, uom: "cm" }, status: "A" }
] );
```

### 一、 更新集合中的文档

MongoDB 提供了更新操作符，例如 `$set`，用于修改字段值。

语法格式：

```javascript
{
  <update operator>: { <field1>: <value1>, ... },
  <update operator>: { <field2>: <value2>, ... },
  ...
}
```

如果字段不存在，某些更新运算符（例如 `$set`）将创建该字段。 有关详细信息，请参阅各个[update operator](https://www.mongodb.com/docs/manual/reference/operator/update/) 参考。

#### 1. 更新单条文档

> - 使用 `$set` 运算符将 `size.uom` 字段的值更新为 “cm”，将 `status` 字段的值更新为 “P”，
>
>
> - 使用 `$currentDate` 运算符将 `lastModified` 字段的值更新为当前日期。 如果lastModified字段不存在，$currentDate将创建该字段。 有关详细信息，请参阅 [$currentDate](https://www.mongodb.com/docs/v4.4/reference/operator/update/currentDate/#mongodb-update-up.-currentDate)。

```javascript
//使用 db.collection.updateOne() 方法来更新第一个文档，条件为是 item 等于“paper”
db.inventory.updateOne(
   { item: "paper" },
   {
     $set: { "size.uom": "cm", status: "P" },
     $currentDate: { lastModified: true }
   }
)
```

#### 2. 更新多条文档

```javascript
// db.collection.updateMany()方法来更新满足 qty 小于50条件 的文档
db.inventory.updateMany(
   { "qty": { $lt: 50 } },
   {
     $set: { "size.uom": "in", status: "P" },
     $currentDate: { lastModified: true }
   }
)
```

#### 3. 替换一条文档

要替换除 `_id` 字段之外的文档的全部内容，将全新文档作为第二个参数传递给 db.collection.replaceOne()。

> - [x] 替代的文档必须是键值对的形式
> - [x] 可以忽略 `_id` 字段，如果含有 `_id` 字段，则必须与元素文档的 `_id` 字段相同

```java
// 替换满足 item 等于 paper 条件的第一条文档
db.inventory.replaceOne(
   { item: "paper" },
   { item: "paper", instock: [ { warehouse: "A", qty: 60 }, { warehouse: "B", qty: 40 } ] }
)
```

#### 

### 二、行为性

#### 1. 原子性

在 MongoDB 中单条文档层级上的所有操作都是原子性的。更多信息请查看 [Atomicity and Transactions.](https://www.mongodb.com/docs/v4.4/core/write-operations-atomicity/)

#### 2. `_id` 字段

一旦设置了 `_id` 字段，就不能修改 `_id` 字段的值，也不能用新文档的 `_id` 值进行替换

#### 3. 字段顺序

对于写操作，MongoDB 保留了文档字段的顺序除了以下情况

- `_id` 字段总是作为文档中的第一个字段
- 有关重命名字段名的更新操作可能会导致该字段在文档中重排序

#### 4. 更新插入操作

如果 `updateOne()`, `updateMany()` 或者 `replaceOne()` 方法包括 `upsert:true` **并且**没有文档匹配指定的查询条件，那么该次更新操作将会创建一个新的文档并且插入。

如果存在匹配的文档，那么该才做将会修改或者替代这些匹配的文档

#### 5. 写确认机制

对于写入问题，您可以指定 MongoDB 请求的写入操作确认级别。 

有关详细信息，请参阅 [Write Concern.](https://www.mongodb.com/docs/v4.4/reference/write-concern/)