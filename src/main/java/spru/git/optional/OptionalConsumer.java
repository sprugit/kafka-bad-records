package spru.git.optional;

import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import spru.git.consumers.AbstractConsumer;
import spru.git.consumers.ConsumerProperties;
import spru.git.logger.LOG;

public class OptionalConsumer extends AbstractConsumer<Optional<GenericRecord>> {

	public OptionalConsumer(String topic) {
		super(topic, ConsumerProperties.opc_deserializer);
	}

	@Override
	public void consume() {
		LOG.debug("Waiting (" + String.valueOf((milisseconds / 1000)) + " seconds) for new messages.");
		ConsumerRecords<String, Optional<GenericRecord>> records = consumer.poll(Duration.ofMillis(this.milisseconds));
		LOG.debug("Consumer Received " + String.valueOf(records.count())+ " records." );
		int i = 0;
		for (ConsumerRecord<String, Optional<GenericRecord>> record : records) {
			try {
				//This code deserializes from GenericRecord to Json
				LOG.debug("\n-------------------------------------------------------------");
				LOG.debug("Attempting to deserialize record no #" + String.valueOf(i++) );
				GenericRecord r = record.value().get();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				GenericDatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(r.getSchema());
				Encoder jsonEncoder = EncoderFactory.get().jsonEncoder(r.getSchema(), baos);
				writer.write(r, jsonEncoder);
				jsonEncoder.flush();
				String payload = new String(baos.toByteArray());
				LOG.debug( "Decoded the following message: \n" + payload);
			} catch (NoSuchElementException e) {
				LOG.debug( "Discarding received malformed record no#"+String.valueOf(i));
			} catch (Exception e1) {
				LOG.debug(e1.getMessage());
				e1.printStackTrace();
			} finally {
				consumer.commitSync();
			}
		}
	}

}
