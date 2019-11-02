package components.entities;

public class Trigger {

	private String triggerName;

	public Trigger(String trigger) {
		this.triggerName = trigger;

	}

	public Trigger() {
	}

	public String toString() {
		return triggerName;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

}
