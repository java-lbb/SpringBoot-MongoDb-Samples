# MongoDB 删除操作

|                                                              |                                                              |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [`db.collection.deleteOne()`](https://www.mongodb.com/docs/v4.4/reference/method/db.collection.deleteOne/#mongodb-method-db.collection.deleteOne) | 删除最多一个文档                                             |
| [`db.collection.deleteMany()`](https://www.mongodb.com/docs/v4.4/reference/method/db.collection.deleteMany/#mongodb-method-db.collection.deleteMany) | Delete all documents that match a specified filter.*New in version 3.2*. |
| [`db.collection.remove()`](https://www.mongodb.com/docs/v4.4/reference/method/db.collection.remove/#mongodb-method-db.collection.remove) | 删除一个文档或者多一个文档                                   |

插入如下数据

```javascript
db.myColl.insertMany([
    { _id: 1, category: "café", status: "A" },
    { _id: 1, category: "café", status: "B" },
    { _id: 1, category: "juice", status: "A" },
	{ _id: 2, category: "juice",status: "B" },
	{ _id: 3, category: "milk", status: "B" }]
)
```



### 一、deleteOne()

```javascript
// 删除一条 category 为 "cafe" 且 status 为 A 的文档记录
db.myColl.deleteOne(
   { category: "cafe" },
)
```



### 二、deleteMany()

```javascript
// 删除所有 status 为 B 的文档记录
db.myColl.deleteOne(
   { status: "B" },
)
```



### 三、remove()

语法

```javascript
db.collection.remove(
    <query>, // 插叙过滤条件
    <justOne> // 是否只删除第一个文档，true为是，false表示不是
)
```

```javascript
db.myColl.remove( { category: juice }, true ) // 只删除一条category为jucie的文档，如果第二个参数为false则会删除满足条件的多条。
```

