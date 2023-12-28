package spru.git.stravro;

public class Payload {
	
	private String content = "Content couldn't be deserialized";
	private boolean isAvro = false;
	private boolean isJson = false;
	
	public Payload setContent(String json) {
		this.content = json;
		return this;
	}

	public Payload setAvro() {
		this.isAvro = true;
		return this;
	}
	
	public Payload setJson() {
		this.isJson = true;
		return this;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public boolean isAvro() {
		return this.isAvro;
	}
	
	public boolean isJson() {
		return this.isJson;
	}
	
	public String toString() {
		return "Received content as avro: " + String.valueOf(this.isAvro) +
				"\nReceived content as json: " + String.valueOf(this.isJson) +
				"\nMessage contents were:\n" + this.content;
	}
	
}
