package company;

public class SalesWorker extends PermanentWorker {
	private final int salesAmount;
	private final double bonusRatio;

	public SalesWorker(String name, int salary, int salesAmount, double bonusRatio) {
		super(name, salary);
		this.salesAmount = salesAmount;
		this.bonusRatio = bonusRatio;
	}

	@Override
	public int getPay() {
		return super.getPay() + (int)(this.salesAmount * this.bonusRatio);
	}

	@Override
	public void showSalaryInfo(String name) {
		System.out.printf("사원 %s의 급여는 월급 %,d원, 수당 %,d원을 합한 총액 %,d원\n", name, super.getPay(),
			(int)(this.salesAmount * this.bonusRatio), this.getPay());
	}
}
