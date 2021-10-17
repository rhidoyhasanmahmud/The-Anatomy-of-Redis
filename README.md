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
|10  | [When to use Redis Set data type?](#When-to-use-Redis-String-data-type)
|11  | [When to use Redis Sorted Set data type?](#When-to-use-Redis-String-data-type)
|12  | [When to use Redis Hash data type?](#When-to-use-Redis-String-data-type)

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



 

