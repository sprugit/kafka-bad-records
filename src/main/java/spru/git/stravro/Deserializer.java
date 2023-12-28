package spru.git.stravro;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringDeserializer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;


public class Deserializer extends KafkaAvroDeserializer {
	
	public Payload deserialize(String s, byte[] bytes) {
		Payload p = new Payload();
		try {
			GenericRecord r = (GenericRecord) super.deserialize(bytes);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GenericDatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(r.getSchema());
			Encoder jsonEncoder = EncoderFactory.get().jsonEncoder(r.getSchema(), baos);
			writer.write(r, jsonEncoder);
			jsonEncoder.flush();
			p.setContent(new String(baos.toByteArray())).setAvro().setJson();
		} catch(SerializationException e) {
			try (StringDeserializer ds = new StringDeserializer()) {
				p.setContent(ds.deserialize(s, bytes)).setJson();
			} catch (Exception e1) {
				p.setContent(Base64.getEncoder().encodeToString(bytes));
			}
		} finally {
			return p;
		}
	}

	public Payload deserialize(String s, byte[] bytes, Schema readerSchema) {
		Payload p = new Payload();
		try {
			GenericRecord r = (GenericRecord) super.deserialize(bytes, readerSchema);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GenericDatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(r.getSchema());
			Encoder jsonEncoder = EncoderFactory.get().jsonEncoder(r.getSchema(), baos);
			writer.write(r, jsonEncoder);
			jsonEncoder.flush();
			p.setContent(new String(baos.toByteArray())).setAvro().setJson();
	        } catch (SerializationException e) {
	        	p.setContent(Base64.getEncoder().encodeToString(bytes));
	        } finally {
	        	return p;
	        }
	}
	
}
