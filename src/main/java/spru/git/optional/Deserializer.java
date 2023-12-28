package spru.git.optional;

import java.util.Optional;

import org.apache.avro.Schema;

import org.apache.kafka.common.errors.SerializationException;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;

public class Deserializer extends KafkaAvroDeserializer {

	public Optional deserialize(String s, byte[] bytes) {
		try {
			return Optional.of(super.deserialize(bytes));
		} catch (SerializationException e) {
			return Optional.empty();
		}
	}

	public Optional deserialize(String s, byte[] bytes, Schema readerSchema) {
        try {
            return Optional.of(super.deserialize(bytes, readerSchema));
        } catch (SerializationException e) {
            return Optional.empty();
        }
	}
}
