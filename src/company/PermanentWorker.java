package company;

public class PermanentWorker extends Worker{
	private final int salary;

	public PermanentWorker(String name, int salary) {
		super(name);
		this.salary = salary;
	}

	@Override
	public int getPay() {
		return salary;
	}

	@Override
	public void showSalaryInfo(String name) {
		System.out.printf("사원 %s의 급여는 %,d원\n", name, this.getPay());
	}
}
