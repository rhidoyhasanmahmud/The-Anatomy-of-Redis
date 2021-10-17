# The Anatomy of Redis & Coding Exercise

> Click :star:if you like the project. Pull Request are highly appreciated. Follow me [@HasanMahmud](https://www.linkedin.com/in/codemechanix/) for technical updates.

**Note:** This repository is specific to Redis.

### Table of Contents

| No. | Questions |
| --- | --------- |
|1  | [What is Redis?](#what-is-redis)|
|2  | [Why redis cache over Memcached and Redis Implementation?](#Why-redis-cache-over-Memcached-and-Redis-Implementation)|
|3  | [Use cases of Redis](#Use-cases-of-Redis)|
|4  | [Redis cache limits](#Redis-cache-limits)|
|5  | [Is Redis just a cache?](#Is-Redis-just-a-cache)|
|6  | [Does Redis persist data?](#Does-Redis-persist-data)|
|7  | [What's the advantage of Redis vs using memory?](#What-is-the-advantage-of-Redis-vs-using-memory)|
|8  | [When to use Redis Lists data type?](#When-to-use-Redis-Lists-data-type)|
|9  | [When to use Redis String data type?](#When-to-use-Redis-String-data-type)|
|10  | [When to use Redis Set data type?](#When-to-use-Redis-Set-data-type)|
|11  | [When to use Redis Sorted Set data type?](#When-to-use-Redis-Sorted-Set-data-type)|
|12  | [When to use Redis Hash data type?](#When-to-use-Redis-Hash-data-type)|
|13  | [When to use Redis over MongoDB?](#When-to-use-Redis-over-MongoDB)|
|14  | [How are Redis pipelining and transaction different?](How-are-Redis-pipelining-and-transaction-different)|
|15  | [Does Redis support transactions?](#Does-Redis-support-transactions)|
|16  | [Are redis operations on data structures thread safe?](#Are-redis-operations-on-data-structures-thread-safe)|
|17  | [Redis replication and redis sharding (cluster) difference](#Redis-replication-and-redis-sharding-difference)|
|18  | [What is Pipelining in Redis and when to use one?](#What-is-Pipelining-in-Redis-and-when-to-use-one)|
|19  | [Why use cache?](#Why-use-cache)|
|20  | [StringRedisTemplate vs RedisTemplate  vs Jedis](#StringRedisTemplate-vs-RedisTemplate-vs-Jedis)|
|21  | [List Operation In Redis](#List-Operation-In-Redis)|
|22  | [Hash Operation In Redis](#Hash-Operation-In-Redis)|
|23  | [Redis Clustering in a Nutshell](#Redis-Clustering-in-a-Nutshell)|
|24  | [Project - 01 : Redis Cache With SpringBoot](#Redis-Cache-With-SpringBoot)|

## Core Message broker - Redis

1. ### What is Redis?

Redis, which stands for Remote Dictionary Server, is a fast, open-source (BSD licensed), NoSQL, in-memory key-value data
store use as a database, cache, message broker, and queue.

Redis or the “Remote Dictionary Server" is a BSD licensed Open source Key-Value based NoSQL Database which can be used
for many things like caching, pub/sub workflows, Fully-fledge database work etc. Its main value proposition, the speed,
is achieved by residing in the memory. That is why Redis is known as an In-Memory database. However, being In-Memory
suggest that it is volatile. But Redis provides options to become a persistent database by writing to disk in different
ways.

Another specialty in Redis is supporting many complex data structures. Those include Strings, Lists, Sets, Sorted Sets,
Hashes, Bitmaps, HyperlogLogs, and Geospatial Indexes.

If we have a glance at the history of Redis, it was a project initiated by Salvatore Sanfilippo back in the days and was
first released on 10th May 2009. It is cross-platformed and written in ANCI C.

##### Redis supports the following features:-

- Replication
- Lua scripting
- LRU eviction of keys
- Transactions and different level of on-disk persistence
- Provides high availability using Redis Sentinel
- Automatic partitioning with Redis Cluster.
- Automatic failover

As we have mentioned earlier that Redis is a key-value store, but that doesn’t mean that it stores only string keys and
string values. Redis supports different types of data structures as values. The key in Redis is a binary-safe String,
with a max size of 512 MB, but you should always consider creating shorter keys.

A binary-safe string is a string that can contain any kind of data, e.g., a JPEG image or a serialized Java object

**[⬆ Back to Top](#table-of-contents)**

2. ### Why redis cache over Memcached and Redis Implementation?

Redis cache provides Snapshots, Replication, Transactions, Pub/Sub, Lua scripting, Geospatial support features which are
not provided by Memcached. Memcached could be preferable when caching relatively small and static data, such as HTML
code, images and small metadata sets.

Jedis is the client library in java for Redis. It is light-weight and easy to use. But, there are other popular client
libraries (i.e. lettuce and Redisson) are also available to connect with Redis which offers some unique features like
thread safety, transparent reconnection handling and async API calls.

we need to create a connection factory bean to connect to Redis client using JedisConnectionFactory. This connection
factory class comes under package “org.springframework.data.redis.connection.jedis”

```java
   @Bean
   JedisConnectionFactory jedisConnectionFactory(){
           RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration("localhost",6379);
           return new JedisConnectionFactory(redisStandaloneConfiguration);
           }
```

Here we need to provide the Redis server configurational detail. In my case, I’m using default local configuration and
default port (i.e. 6379).

Create an instance of RedisTemplate class and pass the jedisConnectionFactory bean. RedisTemplate can be used for
querying data with a custom repository. This class is under package: org.springframework.data.redis.core.

```java
@Bean
RedisTemplate<String, User> redisTemplate(){
        RedisTemplate<String, User> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
        }
```

Here I’m using User detail to keep in the Redis cache. This class expect two type parameter

K — the Redis key type against which the template works (i.e. String)

V — the Redis value type against which the template works (i.e. User)

```java
import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String name;
    private Long salary;

    public User(String id, String name, Long salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
```

Here I’m using @Autowiring annotation to inject dependency of UserRepository.So that I can be able to access all the
methods which I’ve declared inside the UserRepository interface.

**[⬆ Back to Top](#table-of-contents)**

3. ### Use cases of Redis

- Caching — It provides an in-memory cache facility to decrease latency, increase the throughput of the application.
- Chat system or massaging system — It supports a pub-sub model with pattern matching.
- Gaming leaderboards — It has implemented Sorted sets to handle time-series data by using timestamps as the score.
- Session stores — It has in-memory data stores with high availability and persistence.
- Media- streaming — Redis can be used to store metadata about users’ profiles and viewing histories, authentication
  information/tokens for millions of users, and manifest files to enable CDNs to stream videos to millions of mobile and
  desktop users at a time.
- Geospatial — It is used to log location-based features such as drive distance, drive time etc.
- ML — It provides fast in-memory data store to build, train and deploy machine learning model.

**[⬆ Back to Top](#table-of-contents)**

4. ### Redis cache limits

It has no limit on storing data on a 64-bit system but, on the 32-bit system, it can only store 3 GB. So, once cache
reaches its memory limit, we should remove the old data to make some space for the new one.

Conclusion, in this section we understood what is Redis cache, its capabilities and implementation using Spring-boot
data Redis.

**[⬆ Back to Top](#table-of-contents)**

5. ### Is Redis just a cache?

Like a cache Redis offers:

- in memory key-value storage

But unlike a cash Redis:

- Supports multiple datatypes (strings, hashes, lists, sets, sorted sets, bitmaps, and hyperloglogs)
- It provides an ability to store cache data into physical storage (if needed).
- Supports pub-sub model
- Redis cache provides replication for high availability (master/slave)
- Supports ultra-fast lua-scripts. Its execution time equals to C commands execution.
- Can be shared across multiple instances of the application (instead of in-memory cache for each app instance)

**[⬆ Back to Top](#table-of-contents)**

6. ### Does Redis persist data?

Redis supports so-called "snapshots". This means that it will do a complete copy of whats in memory at some points in
time (e.g. every full hour). When you lose power between two snapshots, you will lose the data from the time between the
last snapshot and the crash (doesn't have to be a power outage..). Redis trades data safety versus performance, like
most NoSQL-DBs do.

Redis saves data in one of the following cases:

- automatically from time to time
- when you manually call BGSAVE command
- when redis is shutting down

But data in redis is not really persistent, because:

- crash of redis process means losing all changes since last save
- BGSAVE operation can only be performed if you have enough free RAM (the amount of extra RAM is equal to the size of
  redis DB)

**[⬆ Back to Top](#table-of-contents)**

7. ### What is the advantage of Redis vs using memory?

Redis is a remote data structure server. It is certainly slower than just storing the data in local memory (since it
involves socket roundtrips to fetch/store the data). However, it also brings some interesting properties:

    - Redis can be accessed by all the processes of your applications, possibly running on several nodes (something local memory cannot achieve).

    - Redis memory storage is quite efficient, and done in a separate process. If the application runs on a platform whose memory is garbage collected (node.js, java, etc ...), it allows handling a much bigger memory cache/store. In practice, very large heaps do not perform well with garbage collected languages.

    - Redis can persist the data on disk if needed.

    - Redis is a bit more than a simple cache: it provides various data structures, various item eviction policies, blocking queues, pub/sub, atomicity, Lua scripting, etc ...

    - Redis can replicate its activity with a master/slave mechanism in order to implement high-availability.

Basically, if you need your application to scale on several nodes sharing the same data, then something like Redis (or
any other remote key/value store) will be required.

**[⬆ Back to Top](#table-of-contents)**

8. ### When to use Redis Lists data type?

If we need to store a collection of strings in Redis, then we can use the List type. If we use List in Redis, the
elements are stored in a linked list. The benefit of this is the quick insertion and removal of the element from the
head. If we need to insert an element in a List with 500 records, then it will take about the same amount of time as
adding the element in a list of 50,000 records.

The downside is that if we need to access an element, the entire list is scanned, and it becomes a time-consuming
operation. Since the List uses a linked list, the elements are sorted on the basis of the insertion order.

The list should be stored in those cases where the order of insertion matters and where the write speed matters as
compared to the read speed. One such case is storing logs.

**[⬆ Back to Top](#table-of-contents)**

9. ### When to use Redis String data type?

String is the simplest type of value that can be associated with a key in Redis. Memcached cache only supports string
values. In Redis, we have the advantage of storing both strings and collections of strings as values. A string value
cannot exceed 512 MB of text or binary data. However, it can store any type of data, like text, integers, floats,
videos, images, and audio files.

Memcached is also an open-source distributed memory caching system. Like Redis, it also stores data in key-value pairs,
but it only supports String type data.

Redis String can be used to store session IDs, static HTML pages, configuration XML, JSON, etc. It can also be used to
work as a counter if integers are stored.

**[⬆ Back to Top](#table-of-contents)**

10. ### When to use Redis Set data type?

The Set value type is similar to List. The only difference is that the set doesn’t allow duplicates. The elements are
not sorted in any order.

Set offers constant time performance for adding and removing operations. We can use set to store data where uniqueness
matters, e.g., storing the number of unique visitors on our website.

**[⬆ Back to Top](#table-of-contents)**

11. ### When to use Redis Sorted Set data type?

If we need our elements to be sorted, we can use Sorted Set as the value type. Each element in the sorted set is
associated with a number, called a score. The elements are stored in the Set based on their score. Let’s say we have a
key called fruits. We need to store apple and banana as the value. Let’s say the score of apple is 10, and the score of
banana is 15. As we can see, scoreapple

```text< scorebananascoreapple<scorebanana```,

so the order will be apple, followed by banana.

If the score of two elements is the same, then we check which String is lexicographically bigger. The two strings cannot
be the same, as this is a Set.

Lexicographic order is dictionary order, except that all the uppercase letters precede all the lowercase letters.

**[⬆ Back to Top](#table-of-contents)**

12. ### When to use Redis Hash data type?

The hash value type is a field-value pair. Let’s say we need to store the information about the marks scored by
students. In this case, the subject can be the key. The value can be a field-value pair, where the field is the student
name, and the value is the marks obtained.

**[⬆ Back to Top](#table-of-contents)**

13. ### When to use Redis over MongoDB?

I consider the following aspects to be worth adding:

> Use MongoDB if you don't know yet how you're going to query your data.

MongoDB is suited for Hackathons, startups or every time you don't know how you'll query the data you inserted. MongoDB
does not make any assumptions on your underlying schema. While MongoDB is schemaless and non-relational, this does not
mean that there is no schema at all. It simply means that your schema needs to be defined in your app (e.g. using
Mongoose). Besides that, MongoDB is great for prototyping or trying things out. Its performance is not that great and
can't be compared to Redis.

> Use Redis in order to speed up your existing application.

Redis can be easily integrated as a LRU cache. It is very uncommon to use Redis as a standalone database system (some
people prefer referring to it as a "key-value"-store). Websites like Craigslist use Redis next to their primary
database. Antirez (developer of Redis) demonstrated using Lamernews that it is indeed possible to use Redis as a stand
alone database system.

> Redis does not make any assumptions based on your data.

Redis provides a bunch of useful data structures (e.g. Sets, Hashes, Lists), but you have to explicitly define how you
want to store you data. To put it in a nutshell, Redis and MongoDB can be used in order to achieve similar things. Redis
is simply faster, but not suited for prototyping. That's one use case where you would typically prefer MongoDB. Besides
that, Redis is really flexible. The underlying data structures it provides are the building blocks of high-performance
DB systems.

##### When to use Redis?

> Caching

Caching using MongoDB simply doesn't make a lot of sense. It would be too slow.

> If you have enough time to think about your DB design.

You can't simply throw in your documents into Redis. You have to think of the way you in which you want to store and
organize your data. One example are hashes in Redis. They are quite different from "traditional", nested objects, which
means you'll have to rethink the way you store nested documents. One solution would be to store a reference inside the
hash to another hash (something like key: [id of second hash]). Another idea would be to store it as JSON, which seems
counter-intuitive to most people with a *SQL-background.

> If you need really high performance.

Beating the performance Redis provides is nearly impossible. Imagine you database being as fast as your cache. That's
what it feels like using Redis as a real database.

> If you don't care that much about scaling.

Scaling Redis is not as hard as it used to be. For instance, you could use a kind of proxy server in order to distribute
the data among multiple Redis instances. Master-slave replication is not that complicated, but distributing you keys
among multiple Redis-instances needs to be done on the application site (e.g. using a hash-function, Modulo etc.).
Scaling MongoDB by comparison is much simpler.

##### When to use MongoDB

> When to use MongoDB Prototyping, Startups, Hackathons

MongoDB is perfectly suited for rapid prototyping. Nevertheless, performance isn't that good. Also keep in mind that
you'll most likely have to define some sort of schema in your application.

> When you need to change your schema quickly.

Because there is no schema! Altering tables in traditional, relational DBMS is painfully expensive and slow. MongoDB
solves this problem by not making a lot of assumptions on your underlying data. Nevertheless, it tries to optimize as
far as possible without requiring you to define a schema.

TL;DR - Use Redis if performance is important , and you are willing to spend time optimizing and organizing your data. -
Use MongoDB if you need to build a prototype without worrying too much about your DB.

-------
Redis is an in memory data store, that can persist it's state to disk (to enable recovery after restart). However, being
an in-memory data store means the size of the data store (on a single node) cannot exceed the total memory space on the
system (physical RAM + swap space). In reality, it will be much less that this, as Redis is sharing that space with many
other processes on the system, and if it exhausts the system memory space it will likely be killed off by the operating
system.

Mongo is a disk based data store, that is most efficient when it's working set fits within physical RAM (like all
software). Being a disk based data means there are no intrinsic limits on the size of a Mongo database, however
configuration options, available disk space, and other concerns may mean that databases sizes over a certain limit may
become impractical or inefficient.

Both Redis and Mongo can be clustered for high availability, backup and to increase the overall size of the datastore.

-------

Redis and MongoDB are both non-relational databases but they're of different categories.

Redis is a Key/Value database, and it's using In-memory storage which makes it super fast. It's a good candidate for
caching stuff and temporary data storage(in memory) and as the most of cloud platforms (such as Azure,AWS) support it,
it's memory usage is scalable.But if you're gonna use it on your machines with limited resources, consider it's memory
usage.

MongoDB on the other hand, is a document database. It's a good option for keeping large texts, images, videos, etc and
almost anything you do with databases except transactions.For example if you wanna develop a blog or social network,
MongoDB is a proper choice. It's scalable with scale-out strategy. It uses disk as storage media, so data would be
persisted.

---------
**[⬆ Back to Top](#table-of-contents)**

14. ### How are Redis pipelining and transaction different?

Pipelining is primarily a network optimization. It essentially means the client buffers up a bunch of commands and ships
them to the server in one go. The commands are not guaranteed to be executed in a transaction. The benefit here is
saving network round trip time for every command.

Redis is single threaded so an individual command is always atomic, but two given commands from different clients can
execute in sequence, alternating between them for example.

Multi/exec, however, ensures no other clients are executing commands in between the commands in the multi/exec sequence.

**[⬆ Back to Top](#table-of-contents)**

15. ### Does Redis support transactions?

Redis transactions are different. It guarantees two things.

1. All or none of the commands are executed
2. sequential and uninterrupted commands

Having said that, if you have the control over your code and know when the system failure would happen (some sort of
catching the exception) you can achieve your requirement in this way.

1. MULTI -> Start transaction
2. LPUSH queue1 1 -> pushing in queue 1
3. LPUSH queue2 1 -> pushing in queue 2
4. EXEC/DISCARD In the

4th step do EXEC if there is no error, if you encounter an error or exception and you wanna rollback do DISCARD.

**[⬆ Back to Top](#table-of-contents)**

16. ### Are redis operations on data structures thread safe?

You might not know it, but Redis is actually single-threaded, which is how every command is guaranteed to be atomic.
While one command is executing, no other command will run.

**[⬆ Back to Top](#table-of-contents)**

17. ### Redis replication and redis sharding difference

Sharding, also known as partitioning, is splitting the data up by key; While replication, also known as mirroring, is to
copy all data.

Sharding is useful to increase performance, reducing the hit and memory load on any one resource. Replication is useful
for getting a high availability of reads. If you read from multiple replicas, you will also reduce the hit rate on all
resources, but the memory requirement for all resources remains the same. It should be noted that, while you can write
to a slave, replication is master->slave only. So you cannot scale writes this way.

Suppose you have the following tuples:

```text
[1:Apple], 
[2:Banana], 
[3:Cherry], 
[4:Durian]
```

and we have two machines A and B. With Sharding, we might store keys 2,4 on machine A; and keys 1,3 on machine B. With
Replication, we store keys 1,2,3,4 on machine A and 1,2,3,4 on machine B.

Sharding is typically implemented by performing a consistent hash upon the key. The above example was implemented with
the following hash function ->

```text
h(x){return x%2==0?A:B}.
```

To combine the concepts, We might replicate each shard. In the above cases, all of the data (2,4) of machine A could be
replicated on machine C and all of the data (1,3) of machine B could be replicated on machine D.

Any key-value store (of which Redis is only one example) supports sharding, though certain cross-key functions will no
longer work. Redis supports replication out of the box.

**[⬆ Back to Top](#table-of-contents)**

18. ### What is Pipelining in Redis and when to use one?

Redis is a Transmission Control Protocol (TCP) server which supports request or response protocol. A request is
completed in 2 steps :

- The client sends query to server in blocking manner to get server response.
- Then server operates command and sends response back result of query to client.

In pipelining, client can send multiple queries or requests to server without waiting for all replies of queries at all
and can finally reads replies in single go. In pipelining, client needs reply of read command and then it can call write
command.

Advantages of Redis Pipelining :

The main advantage of Redis pipelining is to boosting up protocol performance. It improves Redis performance because of
multiple commands simultaneous execution. The speedup gained by pipelining ranges from factor of 5 for connections to
localhost up to factor of at least 100 over low speed internet connections.

**[⬆ Back to Top](#table-of-contents)**

19. ### Why use cache?

In the front-end query, the amount of matching data for a single query may reach hundreds or even thousands, and it is
definitely required to display the page in the front end. Even if you query 10 data at a time, the entire query still
takes 6–8 seconds. Imagine a 10s waiting for every page.

So, use the redis cache at this time. Reduce the number of requests to the database. The matching data is stored in the
database together. This only takes a little longer in the first query. Once the query is completed, the user clicks on
the next page to be a millisecond-level operation.

**[⬆ Back to Top](#table-of-contents)**

20. ### StringRedisTemplate vs RedisTemplate vs Jedis

- StringRedisTemplate can only manage the data in StringRedisTemplate.
- RedisTemplate can only manage the data in RedisTemplate.
- Use Jedis to connect to Redis database.

> Use redisTemplate:: Spring encapsulates a more powerful template, redisTemplate, to facilitate the operation of the Redis cache during development. String, List, Set, Hash, and Zset can be stored in Redis. The following will be introduced separately for List and Hash.

**[⬆ Back to Top](#table-of-contents)**

21. ### List Operation In Redis

The List in Redis is a simple list of strings. The following are common operations.

1. hasKey

To judge if a key exists. Suppose the Key is `test`, the usage is as follows.

```java

if(redisTemplate.hasKey(“test”)){
        System.out.println(“exist”);
        }else{
        System.out.println(“does not ex.”);
        }

```

2. range

This function is used to get the data of the specified interval from the redis cache. The specific usage is as follows.

```java

if(redisTemplate.hasKey(“test”)){
// [4, 3, 2, 1]
        System.out.println(redisTemplate.opsForList().range(“test”,0,0));
// [4]
        System.out.println(redisTemplate.opsForList().range(“test”,0,1));
// [4, 3]
        System.out.println(redisTemplate.opsForList().range(“test”,0,2));
// [4, 3, 2]
        System.out.println(redisTemplate.opsForList().range(“test”,0,3));
// [4, 3, 2, 1]
        System.out.println(redisTemplate.opsForList().range(“test”,0,4));
// [4, 3, 2, 1]
        System.out.println(redisTemplate.opsForList().range(“test”,0,5));
// [4, 3, 2, 1]
        System.out.println(redisTemplate.opsForList().range(“test”,0,-1));
// [4, 3, 2, 1] if end withs -1, it means get all values.
        }

```

3. delete :: Delete key.

```java

List<String> test=new ArrayList<>();
        test.add(“1”);
        test.add(“2”);
        test.add(“3”);
        test.add(“4”);redisTemplate.opsForList().rightPushAll(“test”,test);
        System.out.println(redisTemplate.opsForList().range(“test”,0,-1)); // [1, 2, 3, 4]
        redisTemplate.delete(“test”);
        System.out.println(redisTemplate.opsForList().range(“test”,0,-1)); // []

```

4. size :: Get size of key.

```java

List<String> test=new ArrayList<>();
        test.add(“1”);
        test.add(“2”);
        test.add(“3”);
        test.add(“4”);redisTemplate.opsForList().rightPushAll(“test”,test);
        System.out.println(redisTemplate.opsForList().size(“test”)); // 4

```

5. leftPush

We think of the place where this value is stored as a container. And the data is always taken from the left, but the
data can be stored from the left or the right. Left is leftPush and right is rightPush. leftPush is shown below.

Here is the usage.

```java

for(int i=0;i< 4;i++){
        Integer value=i+1;
        redisTemplate.opsForList().leftPush(“test”,value.toString());
        System.out.println(redisTemplate.opsForList().range(“test”,0,-1));
        }
/*
The output of console is below.
[1]
[2, 1]
[3, 2, 1]
[4, 3, 2, 1]
*/

```

6. leftPushAll

```java

List<String> test=new ArrayList<>();
        test.add(“1”);
        test.add(“2”);
        test.add(“3”);
        test.add(“4”);
        redisTemplate.opsForList().leftPushAll(“test”,test);
        System.out.println(redisTemplate.opsForList().range(“test”,0,-1));
// [4, 3, 2, 1]

```

You can write like this as well.

```java

redisTemplate.opsForList().leftPushAll(“test”, “1”, “2”, “3”, “4”);
        System.out.println(redisTemplate.opsForList().range(“test”,0,-1));
// [4, 3, 2, 1]

```

7. leftPushIfPresent

The same operation as leftPush, the only difference is that the value of the key is updated if and only if the key
exists. If the key does not exist, no action will be taken on the data.redisTemplate.delete(“test”);

```java

redisTemplate.opsForList().leftPushIfPresent(“test”, “1”);
        redisTemplate.opsForList().leftPushIfPresent(“test”, “2”);
        System.out.println(redisTemplate.opsForList().range(“test”,0,-1));
// []

```

8. leftPop

This function is used to remove the leftmost element in our abstract container.

```java

List<String> test=new ArrayList<>();
        test.add(“1”);
        test.add(“2”);
        test.add(“3”);
        test.add(“4”);
        redisTemplate.opsForList().rightPushAll(“test”,test);redisTemplate.opsForList().leftPop(“test”); // [2, 3, 4]
        redisTemplate.opsForList().leftPop(“test”); // [3, 4]
        redisTemplate.opsForList().leftPop(“test”); // [4]
        redisTemplate.opsForList().leftPop(“test”); // []
        redisTemplate.opsForList().leftPop(“test”); // []

```

It is worth noting that when the return is empty, the key does not exist in redis. If you call leftPushIfPresent at this
time, you cannot add data anymore.

```java

List<String> test=new ArrayList<>();
        test.add(“1”);
        test.add(“2”);
        test.add(“3”);
        test.add(“4”);
        redisTemplate.opsForList().rightPushAll(“test”,test);redisTemplate.opsForList().leftPop(“test”); // [2, 3, 4]
        redisTemplate.opsForList().leftPop(“test”); // [3, 4]
        redisTemplate.opsForList().leftPop(“test”); // [4]
        redisTemplate.opsForList().leftPop(“test”); // []
        redisTemplate.opsForList().leftPop(“test”); // []redisTemplate.opsForList().leftPushIfPresent(“test”, “1”); // []
        redisTemplate.opsForList().leftPushIfPresent(“test”, “1”); // []

```

9. rightPush

```java

for(int i=0;i< 4;i++){
        Integer value=i+1;
        redisTemplate.opsForList().leftPush(“test”,value.toString());
        System.out.println(redisTemplate.opsForList().range(“test”,0,-1));
        }
/*
Here is console output.
[1]
[1, 2]
[1, 2, 3]
[1, 2, 3, 4]
*/

```

10. rightPushAll

Same as rightPush ,push all the values that contained in the list.

```java

List<String> test=new ArrayList<>();
        test.add(“1”);
        test.add(“2”);
        test.add(“3”);
        test.add(“4”);
        redisTemplate.opsForList().leftPushAll(“test”,test);
        System.out.println(redisTemplate.opsForList().range(“test”,0,-1));
// [1, 2, 3, 4]

```

You can write like this as well.

```java

redisTemplate.opsForList().rightPushAll(“test”, “1”, “2”, “3”, “4”);
        System.out.println(redisTemplate.opsForList().range(“test”,0,-1));
// [1, 2, 3, 4]

```

11. rightPushIfPresent

Same as rightPush .But only one thing different. It will update the key’s value only if the key is exist.If the key is
not exist, nothing happens.

```java

redisTemplate.delete(“test”);redisTemplate.opsForList().rightPushIfPresent(“test”, “1”);
        redisTemplate.opsForList().rightPushIfPresent(“test”, “2”);
        System.out.println(redisTemplate.opsForList().range(“test”,0,-1)); // []

```

12. rightPop

This function is used to remove the rightmost element from our abstract container.

```java

List<String> test=new ArrayList<>();
        test.add(“1”);
        test.add(“2”);
        test.add(“3”);
        test.add(“4”);
        redisTemplate.opsForList().rightPushAll(“test”,test);redisTemplate.opsForList().rightPop(“test”); // [1, 2, 3]
        redisTemplate.opsForList().rightPop(“test”); // [1, 2]
        redisTemplate.opsForList().rightPop(“test”); // [1]
        redisTemplate.opsForList().rightPop(“test”); // []
        redisTemplate.opsForList().rightPop(“test”); // []

```

Same as leftPop，If the return value is already equal [] , call rightPushIfPresent cannot write any data.

13. index

Used to get the value of the element at the specified position in the key.

```java

if(redisTemplate.hasKey(“test”)){
// [1, 2, 3, 4]
        System.out.println(redisTemplate.opsForList().index(“test”,-1)); // 4
        System.out.println(redisTemplate.opsForList().index(“test”,0)); // 1
        System.out.println(redisTemplate.opsForList().index(“test”,1)); // 2
        System.out.println(redisTemplate.opsForList().index(“test”,2)); // 3
        System.out.println(redisTemplate.opsForList().index(“test”,3)); // 4
        System.out.println(redisTemplate.opsForList().index(“test”,4)); // null
        System.out.println(redisTemplate.opsForList().index(“test”,5)); // null
        }

```

There are two points that worth noting. One is that if the index is -1, it will return the last element of the List, and
the other will return `null` if the subscript is out of bounds.

14. trim

```java

List<String> test=new ArrayList<>();
        test.add(“1”);
        test.add(“2”);
        test.add(“3”);
        test.add(“4”);
        redisTemplate.opsForList().rightPushAll(“test”,test);
// [1, 2, 3, 4]
        redisTemplate.opsForList().trim(“test”,0,2);
// [1, 2, 3]

```

15. remove

Used to remove the element specified in the key. Accepts 3 parameters, which are the hash key name, count, and the value
to be removed. There are three values that can be passed to the count, which are -1, 0, 1.

-1 means starting from the right side of the storage container, deleting a single value that matches the value to be
removed; 0means deleting all data matching value; 1means starting from the left side of the storage container , delete a
single data that matches the value you want to remove.

```java

List<String> test=new ArrayList<>();
        test.add(“1”);
        test.add(“2”);
        test.add(“3”);
        test.add(“4”);
        test.add(“4”);
        test.add(“3”);
        test.add(“2”);
        test.add(“1”);redisTemplate.opsForList().rightPushAll(“test”,test); // [1, 2, 3, 4, 4, 3, 2, 1]// When the count is -1, value is 1
        redisTemplate.opsForList().remove(“test”,-1, “1”); // [1, 2, 3, 4, 4, 3, 2]// When the count is 1, value is 1
        redisTemplate.opsForList().remove(“test”,1, “1”); // [2, 3, 4, 4, 3, 2]// When the count is 0, value is 4
        redisTemplate.opsForList().remove(“test”,0, “4”); // [2, 3, 3, 2]

```

16. rightPopAndLeftPush

This function is used to manipulate data between two hash keys. And it accepts three parameters, source key, target
key.And it will rightPop the source key, and leftPush the target key with the return value of rightPop.

```java

List<String> test=new ArrayList<>();
        test.add(“1”);
        test.add(“2”);
        test.add(“3”);
        test.add(“4”);List<String> test2=new ArrayList<>();
        test2.add(“1”);
        test2.add(“2”);
        test2.add(“3”);redisTemplate.opsForList().rightPushAll(“test”,test); // [1, 2, 3, 4]
        redisTemplate.opsForList().rightPushAll(“test2”,test2); // [1, 2, 3]redisTemplate.opsForList().rightPopAndLeftPush(“test”, “test2”);System.out.println(redisTemplate.opsForList().range(“test”, 0, -1)); // [1, 2, 3]
        System.out.println(redisTemplate.opsForList().range(“test2”,0,-1)); // [4, 1, 2, 3]

```

**[⬆ Back to Top](#table-of-contents)**

22. ### Hash Operation In Redis

1. put :: Used to set key and value into a hash key.

```java

List<String> list=new ArrayList<>();
        list.add(“1”);
        list.add(“2”);
        list.add(“3”);
        list.add(“4”);redisTemplate.opsForHash().put(“test”, “map”,list.toString()); // [1, 2, 3, 4]
        redisTemplate.opsForHash().put(“test”, “isAdmin”,true); // true

```

2. putAll :: Used to set multiple key and value at the same time.

```java
List<String> list=new ArrayList<>();
        list.add(“1”);
        list.add(“2”);
        list.add(“3”);
        list.add(“4”);
        List<String> list2=new ArrayList<>();
        list2.add(“5”);
        list2.add(“6”);
        list2.add(“7”);
        list2.add(“8”);
        Map<String, String> valueMap=new HashMap<>();
        valueMap.put(“map1”,list.toString());
        valueMap.put(“map2”,list2.toString());redisTemplate.opsForHash().putAll(“test”,valueMap); // {map2=[5, 6, 7, 8], map1=[1, 2, 3, 4]}
```

3. putIfAbsent

Used to write data into a key that contained in a hash key. When the key is exist, it will not write any data.It will
write data only if the hash key is not exist.

And what if the hash key is not exist? Redis will create the hask key, then create the key and set the value inside the
hash key.

```java
List<String> list=new ArrayList<>();
        list.add(“1”);
        list.add(“2”);
        list.add(“3”);
        list.add(“4”);
        redisTemplate.opsForHash().putIfAbsent(“test”, “map”,list.toString());
        System.out.println(redisTemplate.opsForHash().entries(“test”)); // {map=[1, 2, 3, 4]}
```

4. get :: Used to get a specific key’s value in a hash key.

```java
List<String> list=new ArrayList<>();
        list.add(“1”);
        list.add(“2”);
        list.add(“3”);
        list.add(“4”);redisTemplate.opsForHash().put(“test”, “map”,list.toString());
        redisTemplate.opsForHash().put(“test”, “isAdmin”,true);System.out.println(redisTemplate.opsForHash().get(“test”, “map”)); // [1, 2, 3, 4]
        System.out.println(redisTemplate.opsForHash().get(“test”, “isAdmin”)); // trueBoolean bool = (Boolean) redisTemplate.opsForHash().get(“test”, “isAdmin”);
        System.out.println(bool); // trueString str = redisTemplate.opsForHash().get(“test”, “map”).toString();
        List<String> array=JSONArray.parseArray(str,String.class);
        System.out.println(array.size()); // 4
```

It’s worth noting that the return data of using the `get` function is of type Object.

So if you need another type, you need to force cast the type. For example, If you want to cast the type to List, you can
use fastjson to do that.

5. delete

Used to delete a key that contained in a hash key. You can also think it like delete a key in a map.

```java
List<String> list=new ArrayList<>();
        list.add(“1”);
        list.add(“2”);
        list.add(“3”);
        list.add(“4”);
        List<String> list2=new ArrayList<>();
        list2.add(“5”);
        list2.add(“6”);
        list2.add(“7”);
        list2.add(“8”);
        Map<String, String> valueMap=new HashMap<>();
        valueMap.put(“map1”,list.toString());
        valueMap.put(“map2”,list2.toString());redisTemplate.opsForHash().putAll(“test”,valueMap); // {map2=[5, 6, 7, 8], map1=[1, 2, 3, 4]}
        redisTemplate.opsForHash().delete(“test”, “map1”); // {map2=[5, 6, 7, 8]}
```

6. values :: Used to get all values that contained in a hash key.

```java
List<String> list=new ArrayList<>();
        list.add(“1”);
        list.add(“2”);
        list.add(“3”);
        list.add(“4”);redisTemplate.opsForHash().put(“test”, “map”,list.toString());
        redisTemplate.opsForHash().put(“test”, “isAdmin”,true);System.out.println(redisTemplate.opsForHash().values(“test”)); // [[1, 2, 3, 4], true]
```

7. entries :: Used to get all keys and values that contained in a hash key, return with Map.

```java
List<String> list=new ArrayList<>();
        list.add(“1”);
        list.add(“2”);
        list.add(“3”);
        list.add(“4”);redisTemplate.opsForHash().put(“test”, “map”,list.toString());
        redisTemplate.opsForHash().put(“test”, “isAdmin”,true);Map<String, String> map=redisTemplate.opsForHash().entries(“test”);
        System.out.println(map.get(“map”)); // [1, 2, 3, 4]
        System.out.println(map.get(“map”)instanceof String); // true
        System.out.println(redisTemplate.opsForHash().entries(“test”)); // {a=[1, 2, 3, 4], isAdmin=true}
```

8. hasKey :: Used to Judge if a hash key contains a key.

```java
List<String> list=new ArrayList<>();
        list.add(“1”);
        list.add(“2”);
        list.add(“3”);
        list.add(“4”);redisTemplate.opsForHash().put(“test”, “map”,list.toString());
        redisTemplate.opsForHash().put(“test”, “isAdmin”,true);System.out.println(redisTemplate.opsForHash().hasKey(“test”, “map”)); // true
        System.out.println(redisTemplate.opsForHash().hasKey(“test”, “b”)); // false
        System.out.println(redisTemplate.opsForHash().hasKey(“test”, “isAdmin”)); // true
```

9. keys :: Used to get all keys that contained in a hash key.

```java
List<String> list=new ArrayList<>();
        list.add(“1”);
        list.add(“2”);
        list.add(“3”);
        list.add(“4”);redisTemplate.opsForHash().put(“test”, “map”,list.toString());
        redisTemplate.opsForHash().put(“test”, “isAdmin”,true);System.out.println(redisTemplate.opsForHash().keys(“test”)); // [a, isAdmin]
```

10. size :: Used to get the number of keys contained in a hash key.

```java
List<String> list=new ArrayList<>();
        list.add(“1”);
        list.add(“2”);
        list.add(“3”);
        list.add(“4”);redisTemplate.opsForHash().put(“test”, “map”,list.toString());
        redisTemplate.opsForHash().put(“test”, “isAdmin”,true);System.out.println(redisTemplate.opsForHash().size(“test”)); // 2
```

11. increment :: Used to make a key in a hash key accumulate based on the value passed in. The value passed in can only
    be double or long, not accepting float.

```java
redisTemplate.opsForHash().increment(“test”, “a”,3);
        redisTemplate.opsForHash().increment(“test”, “a”,-3);
        redisTemplate.opsForHash().increment(“test”, “a”,1);
        redisTemplate.opsForHash().increment(“test”, “a”,0);System.out.println(redisTemplate.opsForHash().entries(“test”)); // {a=1}
```

12. multiGet :: To get multiple keys’s value at once.

```java
List<String> list=new ArrayList<>();
        list.add(“1”);
        list.add(“2”);
        list.add(“3”);
        list.add(“4”);
        List<String> list2=new ArrayList<>();
        list2.add(“5”);
        list2.add(“6”);
        list2.add(“7”);
        list2.add(“8”);redisTemplate.opsForHash().put(“test”, “map1”,list.toString()); // [1, 2, 3, 4]
        redisTemplate.opsForHash().put(“test”, “map2”,list2.toString()); // [5, 6, 7, 8]List<String> keys = new ArrayList<>();
        keys.add(“map1”);
        keys.add(“map2”);System.out.println(redisTemplate.opsForHash().multiGet(“test”,keys)); // [[1, 2, 3, 4], [5, 6, 7, 8]]
        System.out.println(redisTemplate.opsForHash().multiGet(“test”,keys)instanceof List); // true
```

13. scan :: Get all key and value which matching the condition. I have checked some data, most of them said that scan is
    unable to match fuzzyly. But I tried it myself.It can.

```java
List<String> list=new ArrayList<>();
        list.add(“1”);
        list.add(“2”);
        list.add(“3”);
        list.add(“4”);
        List<String> list2=new ArrayList<>();
        list2.add(“5”);
        list2.add(“6”);
        list2.add(“7”);
        list2.add(“8”);
        List<String> list3=new ArrayList<>();
        list3.add(“9”);
        list3.add(“10”);
        list3.add(“11”);
        list3.add(“12”);
        Map<String, String> valueMap=new HashMap<>();
        valueMap.put(“map1”,list.toString());
        valueMap.put(“SCAN_map2”,list2.toString());
        valueMap.put(“map3”,list3.toString());redisTemplate.opsForHash().putAll(“test”,valueMap); // {SCAN_map2=[5, 6, 7, 8], map3=[9, 10, 11, 12], map1=[1, 2, 3, 4]}Cursor<Map.Entry<String, String>> cursor = redisTemplate.opsForHash().scan(“test”, ScanOptions.scanOptions().match(“*SCAN*”).build());
        if(cursor.hasNext()){
        while(cursor.hasNext()){
        Map.Entry<String, String> entry=cursor.next();
        System.out.println(entry.getValue()); // [5, 6, 7, 8]
        }
        }
```

**[⬆ Back to Top](#table-of-contents)**

23. ### Redis Clustering in a Nutshell

> What is Redis

Redis or the “Remote Dictionary Server" is a BSD licensed Open source Key-Value based NoSQL Database which can be used
for many things like caching, pub/sub workflows, Fully-fledge database work etc. Its main value proposition, the speed,
is achieved by residing in the memory. That is why Redis is known as an In-Memory database. However, being In-Memory
suggest that it is volatile. But Redis provides options to become a persistent database by writing to disk in different
ways. Another specialty in Redis is supporting many complex data structures. Those include Strings, Lists, Sets, Sorted
Sets, Hashes, Bitmaps, HyperlogLogs, and Geospatial Indexes. If we have a glance at the history of Redis, it was a
project initiated by Salvatore Sanfilippo back in the days and was first released on 10th May 2009. It is
cross-platformed and written in ANCI C.

> Who uses Redis

Redis is one of the widely used Database in the world currently, ranking at number 8 in all Database Engines category
and ranking at number 1 in the Key-Value store category (DB-Engines, 2019)

Some of the major companies or solutions that use Redis in practice includes Twitter, GitHub, StackOverFlow, Flickr,
Alibaba, Instagram, SnapChat and Pinterest.

> Clustering and Sharding

When there is a large amount of data needed to be stored, it is natural to exhaust one machine (or Node). In such a
situation, the most attractive solution is going into distributed architecture.

Similarly, in Redis also, we have the concept of clustering, where you can divide your data to be stored in multiple
nodes, who works together to achieve the common objective.

So how does Redis do this? That is were “Sharding” comes into the picture. In simple terms, sharding means
partitioning (like in SQL database terminology). In Redis, every key that we save is conceptually a part of “Hash slot”.
There are 16384 Hash Slots in a Redis implementation (whether it is a standalone version of Redis or clustered one,
doesn’t matter). In sharding, these Hash slots are getting divided between the nodes, hence, each node carries a subset
of Hash Slots.

For example, if the cluster need to have 3 nodes, the Hash slots will be divided between them like,

- A — 0 to 5500
- B — 5501 to 11000
- C — 11001 to 16384

Let say you are adding a node newly. What will happen is, A, B, and C will transfer some of its slots to the new Node.
Similarly, in removing, (let's say we are removing C from the cluster of A, B, and C) A and B will take C’s Hash Slots
and make C capable of abandoning the Cluster.

This process of moving the Hash slots from node to node (or partitioning of Hash slots — Sharding) is not requiring the
operations to stop or pause. Hence, adding and removing of nodes also does not requires operations to stop or pause.
Therefore, it ensures no downtime for the cluster i.e. maximum availability.

> Replication & Failover in Redis

Redis follows a Master-Slave replication process. Now, you might be wondering what that is. Let me explain:

Let’s take the above example I mentioned; the cluster of A, B, and C. The client is directly talking with the cluster, and one of the masters will be accepting the request. In here if the B fails, the whole cluster will fail since we don’t have the Hash Slots from 5501 to 11000.

But, what if we have a replica for each of A, B, and C nodes? Let’s say these replicas are A1, B1, and C1. They are called “Slaves” in the Redis world, and A, B, and C is known as “Masters”. Each Slave is an exact copy of its master (theoretically. I will discuss when it is not, in the Trade-off section) and one Master can have multiple Slaves as well.

So does how this ensures the high availability or what happens in a failover scenario?

Let's say B had some internal issues and stopped working. Others in the cluster will get to know it by the “Gossip Port” since they run node-to-node communication (including health checks) through this port. Then A and C does voting and, will promote one of the B’s replicas to be a Master. In this example, since we have only B1, it will be promoted to be the new master. If the B rejoins the cluster, it has to be a slave, not a master.

![Failover Scenario in Redis - Image - 1](https://github.com/CodeMechanix/Redis-Interview-Questions/blob/main/images/image_01.gif)

In a nutshell, even if the B fails as a master, since its slave takes his position and does his work, the cluster does not fail. Hence, it ensures maximum availability as well.

The replication mechanism provides basic two uses in the Redis clusters.

1. Multiple Reads or Sorts at a time — performance improvement
2. Recovery in the case of failure — high availability and fault-tolerance

Note that, I only took one replica scenario as an example. you can have N number of copies of your data using N-1 replicas for a master. However, if all these N nodes ( N nodes means including the master) fail, the cluster fails as well. Therefore, if you are going for a cloud solution, like AWS (Amazon Web Services), the best practice is to have a Master in one availability zone and each replica in separate availability zones.


> Trade-off of consistency

In delivering Redis’s value proposition, the Performance, it has to trade off between Performance and Consistency. Redis cannot guarantee strong Consistency, i.e. there is a possibility for a “Write” to be lost. This can happen due to two reasons: Asynchronous Replication and Network Partitioning.
Asynchronous Replication Scenario
Consider above example and a situation where a client is writing to the Master A. As soon as the client finish writing, A acknowledge it by sending an “OK” message to the client, without writing it to its replica (slave) A1. A writes to its slave A1, after the acknowledgment is sent to the client.

What if, A fails after the acknowledgment is sent, but before writing to the slave? It will make the other masters, B and C, to select A1 as the new master. But, A1 does not have the last write that client did to A. If the A rejoin cluster, it will be placed as a slave of A1. It will look at A1, and update itself by flushing the last write of the client, since, in Redis, Master is getting copied by the slaves. In this case, this last write of the client will be forever lost.

Note that, replication process can be configured to be synchronous via WAIT command. However, that also does not guarantee 100% consistency. Instead, it will make it degrade in performance aspects. But in case the consistency is very crucial to you, there are commercial solutions available like SOSS (ScaleOut State Server) or Amazon ElastiCache, which are In-Memory Data Grid (IMDG) implementations that guarantee no loss of data.

![Asynchronous Replication Data Loss - Image - 2](https://github.com/CodeMechanix/Redis-Interview-Questions/blob/main/images/image_02.gif)

> Network Partitioning Scenario

There can be a situation where there is a network partitioning, which result in the client on the minority side with at least one master.

Let take an example where A ends up alone with the client in the minority side while B, C, A1, B1, and C1 are in majority partitioning. When the client tries to write not knowing about the partitioning, Master A accepts the writes. If Partition lasts only a few seconds, it will be ok. But if not, the majority side understands that A is no longer in the communicating, hence elect A1 as new Master. At that moment, A also stops accepting the writes. However, the data written until the node time-out is forever lost even if the A rejoin the cluster. (because A is rejoining as a Slave since A1 is promoted). This “node time-out” is a parameter that we can tune in Redis clustering.

> Redis as a Persistence Storage

Like I mentioned before, Redis is an In-Memory database with Persistency option. So let's look at how does Redis achieves Persistency while residing in the Volatile memory.

Redis achieves this in 2 ways.
- RDB Mechanism
- AOF logs

> RDB Mechanism

RDB (Redis Database Backup) mechanism is very much similar to the SQL database’s concept of the snapshot. RDB file is a snapshot taken of all the user data at a given point-of-time, which is saved in secondary storage (permanent storage — in disk etc.) and used for the recovery processes. This snapshot taking can be invoked through 3 different triggers:

- Time transpire — periodic automatic invocation
- Reaching a change threshold — periodic automatic invocation
- SAVE Command — forceful invocation

However, you can still lose data which were written after the last snapshot, in a cluster failure situation, since the RDB mechanism is mostly periodic. But on the contrary, being periodic ensures the process is less resource intensive.

> AOF logs

The Append-Only Files (AOF) writes all operations received by the server to the disk. This process happens after each operation. Hence, it is very resource intensive. AOFs are also very large compared to the RDB Files, which are by default compressed. Therefore, larger disk space is needed for storing AOFs.

On the other hand, since it writes in each operation, it can guarantee minimum data loss in a cluster failure.

In practice, both of these methods are being used in industry; sometimes, both together.

> Pros and Cons

>> Pros

- Performance (Speed):
Since Redis is In-memory, it reduces the overhead in accessing the Disk to retrieve data, since it requests from disk only when the data is not available in Redis cache.

![How Redis improve performance by minimizing disk reads - Image 3](https://github.com/CodeMechanix/Redis-Interview-Questions/blob/main/images/image_03.png)

There are some other options which are available in Redis for performance optimization. For example, pipelining feature which clusters multiple commands and sends them at once to the cluster.

Another mechanism in achieving high performance is the architectural design of the cluster. For example, have a look at the bellow architecture which optimizes the performance in a caching use case.

![Architecture to maximize performance - image 4](https://github.com/CodeMechanix/Redis-Interview-Questions/blob/main/images/image_04.png)

- Durability :

Since there is an option to be written into Disk, Redis can be used as both Fully-fledge Database and Caching system.

- Multi-language support :

According to Redis.io, there are many languages supported by the Redis including, C, C#, C++, Java, Python, Scalar, Ruby, Go Lang etc.

- Flexibility and Simplicity
- Scalability

>> Cons

- The possibility of Data loss

24. ### Redis Cache With SpringBoot

[Redis-Cache-With-SpringBoot-Project-Source-Code](https://github.com/CodeMechanix/Redis-Interview-Questions/tree/main/Project/Redis-Cache-With-SpringBoot)




 

