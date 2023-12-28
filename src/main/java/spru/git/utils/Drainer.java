package spru.git.utils;

import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import spru.git.consumers.AbstractConsumer;
import spru.git.logger.LOG;

public class Drainer extends AbstractConsumer<String> {

	public Drainer(String topic) {
		super(topic, "org.apache.kafka.common.serialization.StringDeserializer");
	}

	@Override
	public void consume() {
		LOG.debug("Removing all records from topic: " + this.topic );
		ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(this.milisseconds));
		for(ConsumerRecord<String, String> record : records){
			consumer.commitSync();
		}
	}
}
