# The Anatomy of Redis & Coding Exercise

> Click :star:if you like the project. Pull Request are highly appreciated. Follow me [@HasanMahmud](https://www.linkedin.com/in/codemechanix/) for technical updates.

**Note:** This repository is specific to Redis.

### Table of Contents

| No. | Questions |
| --- | --------- |
|1  | [What is Redis?](#what-is-redis)|

## Core Message broker - Redis

1. ### What is Redis?

   Redis, which stands for Remote Dictionary Server, is a fast, open-source (BSD licensed), NoSQL, in-memory key-value
   data store use as a database, cache, message broker, and queue.

   Redis or the “Remote Dictionary Server" is a BSD licensed Open source Key-Value based NoSQL Database which can be
   used for many things like caching, pub/sub workflows, Fully-fledge database work etc. Its main value proposition, the
   speed, is achieved by residing in the memory. That is why Redis is known as an In-Memory database. However, being
   In-Memory suggest that it is volatile. But Redis provides options to become a persistent database by writing to disk
   in different ways.

   Another specialty in Redis is supporting many complex data structures. Those include Strings, Lists, Sets, Sorted
   Sets, Hashes, Bitmaps, HyperlogLogs, and Geospatial Indexes.

   If we have a glance at the history of Redis, it was a project initiated by Salvatore Sanfilippo back in the days and
   was first released on 10th May 2009. It is cross-platformed and written in ANCI C.

   ##### Redis supports the following features:-
    - Replication
    - Lua scripting
    - LRU eviction of keys
    - Transactions and different level of on-disk persistence
    - Provides high availability using Redis Sentinel
    - Automatic partitioning with Redis Cluster.
    - Automatic failover

   **[⬆ Back to Top](#table-of-contents)**