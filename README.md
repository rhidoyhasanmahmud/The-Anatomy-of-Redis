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
|5  | [Is Redis just a cache?](#Is-Redis-just-a-cache)
|6  | [Does Redis persist data?](#Does-Redis-persist-data)
|7  | [What's the advantage of Redis vs using memory?](#What-is-the-advantage-of-Redis-vs-using-memory)
|8  | [When to use Redis Lists data type?](#When-to-use-Redis-Lists-data-type)
|9  | [When to use Redis String data type?](#When-to-use-Redis-String-data-type)
|10  | [When to use Redis Set data type?](#When-to-use-Redis-Set-data-type)
|11  | [When to use Redis Sorted Set data type?](#When-to-use-Redis-Sorted-Set-data-type)
|12  | [When to use Redis Hash data type?](#When-to-use-Redis-Hash-data-type)
|13  | [When to use Redis over MongoDB?](#When-to-use-Redis-over-MongoDB)
|14  | [How are Redis pipelining and transaction different?](How-are-Redis-pipelining-and-transaction-different)
|15  | [Does Redis support transactions?](#Does-Redis-support-transactions)

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





 

