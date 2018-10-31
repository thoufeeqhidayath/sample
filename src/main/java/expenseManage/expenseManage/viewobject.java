package expenseManage.expenseManage;

public class viewobject {
	int id;
	float amount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	String mode;
	String reason;
	float bal;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getMode() {
		return mode;
	}
	public viewobject() {
		super();
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public float getBal() {
		return bal;
	}
	public void setBal(float bal) {
		this.bal = bal;
	}
	public viewobject(int id, float amount, String mode, String reason, float bal) {
		super();
		this.id = id;
		this.amount = amount;
		this.mode = mode;
		this.reason = reason;
		this.bal = bal;
	}
	String name;
	public viewobject(int id, String name) {
		this.id = id;
		this.name = name;
		
	}

}
