package exceptions;

public class InstanceNotFoundException extends Exception {

	private static final long serialVersionUID = 5980816034634183939L;
	private String className;

	public InstanceNotFoundException(String className) {
		super();
		this.className = className;
		// TODO Auto-generated constructor stub
	}

}
