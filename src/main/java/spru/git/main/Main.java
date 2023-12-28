package spru.git.main;

import spru.git.consumers.AbstractConsumer;
import spru.git.optional.OptionalConsumer;
import spru.git.stravro.StravroConsumer;

public class Main {

	private static final String topic_name = "kafka-topic-name-version-number";
	
	public static void main(String[] args) {
		
		AbstractConsumer<?>[] con_list = new AbstractConsumer[] {new OptionalConsumer(topic_name),new StravroConsumer(topic_name)};
		
		for(AbstractConsumer<?> c : con_list) {
			
			c.consume();
			
		}
		
	}
}
