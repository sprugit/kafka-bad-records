# Bad Records in Kafka Avro Deserializer

If you're reading this it's because you're either me (hi!) in the future coming back to take a look at this or because you're dealing with bad AVRO records in Kafka, which can lead a consumer to a blocking state.

As seen [here](https://jonboulineau.me/blog/kafka/dealing-with-bad-records-in-kafka), one of the easier solutions is to extend the behaviour **KafkaAvroSerializer** class and catch the **SerializationException** that goes uncaught by the regular Deserializer.

However, as mentioned by the author of the article, the solution (handling of the consumption through returning null) feels somewhat unpleasant. That's where this repo comes in: We can instead make use of the Optional class of java, wrapping a *try catch* block around the deserialize method and always returning an instance of Optional. The handling of bad records can then be done properly by the Consumer, by catching **NoSuchElementException**s raised by fetching the value of an empty Optional instance.

# Repo Structure

This repo contains 3 main packages in the same project:
## * The optional package
This package contains the example implemented [here](https://jonboulineau.me/blog/kafka/dealing-with-bad-records-in-kafka) using the aforementioned Optional class.
## * The stravro package
This package contains another handy example which requires a bit of a background to explain it's existance. At a point I had a consumer consuming both JSON strings and JSON with AvroSchema. This isn't good. From both testing and debugging, I noticed that when the Consumer is expecting to receive a GenericRecord class, if it ends up receiving a JSON string, it will throw a deserialization error mentioning something about a **magic byte**, resulting in a blocked Consumer. This deserializer was made to fix that behaviour.
While attempting to do the above, we conveniently move the deserialization from GenericRecord to JSON string directly into the deserializer itself making the code for the Consumer look much cleaner.
## * The utils package
This package contains a simple consumer: one that isn't concerned about anything regarding the messages, it just flushes them out of the queue.
