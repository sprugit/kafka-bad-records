package spru.git.consumers;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.KafkaConsumer; 
import spru.git.stravro.Payload;

public abstract class AbstractConsumer<T> {
	
	protected final KafkaConsumer<String, T> consumer;
	protected final int milisseconds = 10000;
	protected final String topic;
	
	public AbstractConsumer(String topic, String deserializer_class) {
		this.consumer = new KafkaConsumer<String, T>(new ConsumerProperties().setValDeserializer(deserializer_class));
		this.topic = topic;
		consumer.subscribe(Arrays.asList(this.topic));
	}
	
	public abstract void consume();

}
