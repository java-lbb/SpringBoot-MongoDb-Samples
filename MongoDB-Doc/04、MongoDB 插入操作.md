# MongoDB 插入操作

| Method                       | Description                          |
| :--------------------------- | :----------------------------------- |
| `db.collection.insertOne()`  | 向指定集合中插入单条文档数据         |
| `db.collection.insertMany()` | 向指定集合中插入多条文档数据         |
| `db.collection.insert()`     | 向指定集合中插入单条或者多条文档数据 |

### 一、db.collection.insertOne()

#### 1. 语法

```javascript
db.collection.insertOne(
    <document>
)
```

| Parameter      | Type     | Description                    |
| :------------- | :------- | :----------------------------- |
| `document`     | document | 插入的文档数据                 |
| `writeConcern` | document | 可选，有关分片集的写入操作参数 |

#### 2. 返回值

- `acknowledged` ：boolean值，true 或 false。有关`write concern`即事务相关的确认机制
- `insertedId` ：成功插入文档的主键`_id`

> 如果插入到集合不存在，则`insertOne()`方法将会创建该集合

#### 3.`_id` Field

如果文档没有指定`_id`字段，那么mongod会在插入之前添加`_id`字段并为文档分配一个唯一的`ObjectId()`。 

如果文档包含 `_id` 字段，则` _id` 值在集合中必须是唯一的，以避免重复键错误。

#### 4. 异常处理

出错时，`db.collection.insertOne()`抛出 `writeError`或 `writeConcernError` 异常。

#### 5. 事务

`db.collection.insertOne()` 可以被使用在 [multi-document transactions.](https://www.mongodb.com/docs/v4.4/core/transactions/)

#### 6.例子

##### 1) 插入一条不包含`_id`属性的文档

```javascript
db.products.insertOne( { item: "card", qty: 15 } );
```

返回值：

```javascript
{
   "acknowledged" : true,
   "insertedId" : ObjectId("56fc40f9d735c28df206d078") //自动生成
}
```

##### 2) 插入一条包含`_id`属性的文档

```javascript
 db.products.insertOne( { _id: 10, item: "box", qty: 20 } );
```

返回值：

```javascript
{ "acknowledged" : true, "insertedId" : 10 }
```

如果再次重复插入一条`_id`为10的文档数据，则会报错

```javascript
try {
   db.products.insertOne( { _id: 10, "item" : "packing peanuts", "qty" : 200 } );
} catch (e) {
   print (e);
}
```

报错信息：

```javascript
WriteError({
   "index" : 0,
   "code" : 11000,
   "errmsg" : "E11000 duplicate key error collection: inventory.products index: _id_ dup key: { : 10.0 }",
   "op" : {
      "_id" : 10,
      "item" : "packing peanuts",
      "qty" : 200
   }
})
```



### 二、db.collection.insertMany()

#### 1. 语法

```javascript
db.collection.insertMany(
   [ <document 1> , <document 2>, ... ],
   {
      writeConcern: <document>,
      ordered: <boolean>
   }
)
```

| Parameter      | Type     | Description                                                  |
| :------------- | :------- | :----------------------------------------------------------- |
| `document`     | document | 文档数组                                                     |
| `writeConcern` | document | 可选，有关分片集的写入操作参数                               |
| `ordered`      | boolean  | 可选. 指定文档的插入顺序，true表示顺序插入，false表示无序；默认true |

#### 2. 返回值

-  `acknowledged` ：boolean类型，true 或 false。有关`write concern`即事务相关的确认机制

-  `insertedIds`：数组类型， 包含成功插入文档数据的`_id`数组值

  > 如果插入到集合不存在，则insertOne()方法将会创建该集合

#### 3. 批量插入的执行

每组的操作数量不能超过数据库的maxWriteBatchSize的值。 从 MongoDB 3.6 开始，该值为 100,000。

 如果某个组超过此限制，客户端驱动程序会将该组划分为计数小于或等于该限制值的较小组。 例如，当 maxWriteBatchSize 值为 100,000 时，如果队列包含 200,000 个操作，则驱动程序将创建 2 个组，每个组包含 100,000 个操作。

#### 4. 例子

##### 1) (顺序)插入多条数据没有指定`_id`字段 

```javascript
try {
   db.products.insertMany( [
      { item: "card", qty: 15 },
      { item: "envelope", qty: 20 },
      { item: "stamps" , qty: 30 }
   ] );
} catch (e) {
   print (e);
}
```

######  返回结果

```javascript
{
   "acknowledged" : true,
   "insertedIds" : [
      ObjectId("562a94d381cb9f1cd6eb0e1a"),
      ObjectId("562a94d381cb9f1cd6eb0e1b"),
      ObjectId("562a94d381cb9f1cd6eb0e1c")
   ]
}
```

##### 2) 顺序插入多条包含重复的`_id`文档数据

```javascript
try {
   db.products.insertMany( [
      { _id: 13, item: "envelopes", qty: 60 },
      { _id: 13, item: "stamps", qty: 110 },
      { _id: 14, item: "packing tape", qty: 38 }
   ] );
} catch (e) {
   print (e);
}
```

###### 异常信息

```javascript
BulkWriteError({
   "writeErrors" : [
      {
         "index" : 0,
         "code" : 11000,
         "errmsg" : "E11000 duplicate key error collection: inventory.products index: _id_ dup key: { : 13.0 }",
         "op" : {
            "_id" : 13,
            "item" : "stamps",
            "qty" : 110
         }
      }
   ],
   "writeConcernErrors" : [ ],
   "nInserted" : 1, // 成功插入的条数
   "nUpserted" : 0,
   "nMatched" : 0,
   "nModified" : 0,
   "nRemoved" : 0,
   "upserted" : [ ]
})
```

> 注意：一个文档被插入：_id:13 的第一个文档会插入成功，但第二个插入会失败。 这也将阻止插入队列中剩余的其他文档。
>
> 但是当`ordered`设置为`false`时，即无序插入，那么剩余的文档将持续插入。

##### 3)无需插入多条包含重复`_id`的文档

```javascript
try {
   db.products.insertMany( [
      { _id: 10, item: "large box", qty: 20 },
      { _id: 11, item: "small box", qty: 55 },
      { _id: 11, item: "medium box", qty: 30 },
      { _id: 12, item: "envelope", qty: 100},
      { _id: 13, item: "stamps", qty: 125 },
      { _id: 13, item: "tape", qty: 20},
      { _id: 14, item: "bubble wrap", qty: 30}
   ], { ordered: false } ); //设置为无序插入
} catch (e) {
   print (e);
}
```

###### 异常抛出

```javascript
BulkWriteError({
   "writeErrors" : [
      {
         "index" : 2,
         "code" : 11000,
         "errmsg" : "E11000 duplicate key error collection: inventory.products index: _id_ dup key: { : 11.0 }",
         "op" : {
            "_id" : 11,
            "item" : "medium box",
            "qty" : 30
         }
      },
      {
         "index" : 5,
         "code" : 11000,
         "errmsg" : "E11000 duplicate key error collection: inventory.products index: _id_ dup key: { : 13.0 }",
         "op" : {
            "_id" : 13,
            "item" : "tape",
            "qty" : 20
         }
      }
   ],
   "writeConcernErrors" : [ ],
   "nInserted" : 5, // 成功插入的条数
   "nUpserted" : 0,
   "nMatched" : 0,
   "nModified" : 0,
   "nRemoved" : 0,
   "upserted" : [ ]
})
```

> 可见，即使 _id:11 和 _id:13 的数据都重复了，但是都会成功插入其中的一条，并且不会阻碍剩余文档的插入。



### 三、db.collection.insert()

#### 1. 语法

```javascript
db.collection.insert(
   <document or array of documents>,
   {
      writeConcern: <document>,
      ordered: <boolean>
   }
)
```

| Parameter      | Type              | Description                                      |
| :------------- | :---------------- | :----------------------------------------------- |
| `document`     | document or array | 一个文档或者文档数组                             |
| `writeConcern` | document          | 可选，有关分片集的参数                           |
| `ordered`      | boolean           | 可选，是否有序/无需插入，默认有序true, false无序 |

- 单条插入

  ```javascript
   db.myDB.insert(
      {
          _id: 15,
          item: "large box",
          qty: 20
      }
  )
  ```

- 批量插入

  ```javascript
   db.myDB.insert(
      [{
          _id: 25,
          item: "large box",
          qty: 20
      },
      {
          _id: 30,
          item: "able",
          qty: 26
      }]
  )
  ```

#### 2.返回值

-  `WriteResult`   -- 单条插入结果

  - 成功插入结果

  ```javascript
  WriteResult({ "nInserted" : 1, "writeConcernError" : [ ] })
  ```

  - wirteConcernError信息

  ```javascript
  WriteResult({
     "nInserted" : 0,
     "writeError" : {
        "code" : 11000,
        "errmsg" : "insertDocument :: caused by :: 11000 E11000 duplicate key error index: test.foo.$_id_  dup key: { : 15.0 }" // 重复键错误
     }
  })
  ```

  

-  `BulkWriteResult`  -- 批量插入结果

  - 成功插入

  ```javascript
  BulkWriteResult({
  	"nRemoved" : 0,
  	"nInserted" : 1,
  	"nUpserted" : 0,
  	"nMatched" : 0,
  	"nModified" : 0,
  	"writeErrors" : [ ]
  })
  ```

  



