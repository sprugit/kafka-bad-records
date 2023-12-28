package spru.git.stravro;

import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import spru.git.logger.LOG;
import spru.git.consumers.AbstractConsumer;
import spru.git.consumers.ConsumerProperties;

public class StravroConsumer extends AbstractConsumer<Payload>{

	public StravroConsumer(String topic) {
		super(topic, ConsumerProperties.str_deserializer);
	}

	@Override
	public void consume() {
		LOG.debug("Polling for records (" + String.valueOf(milisseconds/1000)+ " seconds)");
		ConsumerRecords<String, Payload> records = consumer.poll(Duration.ofMillis(milisseconds));
		LOG.debug("Received " + records.count() + " records.");
		int count = 0;
		if(records.count() != 0) {
			for(ConsumerRecord<String, Payload> record : records) {
				LOG.debug("Record no#"+String.valueOf(++count));
				Payload temp = record.value();
				LOG.debug(temp.toString());
				consumer.commitSync();
			}
		}
	}
}
