package spru.git.consumers;

import java.util.Properties;

public class ConsumerProperties extends Properties{
	
	private static final String bootstrap = "protocol://path.to.bootstrap.servers:port_num";
	private static final String groupid = "kafka.consumer";
	private static final String schema_url = "protocol://path.to.registry.servers:port_num";
	private static final String key_deserializer = "org.apache.kafka.common.serialization.StringDeserializer";
	
	public static final String opc_deserializer = "spru.git.optional.Deserializer";
	public static final String str_deserializer = "spru.git.stravro.Deserializer";
	
	public ConsumerProperties() {
		super();
		this.setProperty("bootstrap.servers", bootstrap);
		this.setProperty("group.id", groupid);
		this.setProperty("schema.registry.url", schema_url);
		this.setProperty("key.deserializer", key_deserializer);
	}
	
	public ConsumerProperties setValDeserializer(String deserializer_class) {
		this.setProperty("value.deserializer", deserializer_class);
		return this;
	}

}