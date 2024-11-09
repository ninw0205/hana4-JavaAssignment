package company;

import java.util.ArrayList;
import java.util.List;

public class ManagerService {
	private final List<Worker> workers;

	public ManagerService() {
		this.workers = new ArrayList<>();
	}

	public void addWorker(Worker worker) {
		workers.add(worker);
	}

	public void showAllSalaryInfo() {
		for (Worker w : workers) {
			w.showSalaryInfo(w.name);
		}
	}

	public void showSalaryInfo(String name) {
		for (Worker w : workers) {
			if (w.name.equals(name)) {
				w.showSalaryInfo(w.name);
			}
		}
	}

	public void showTotalSalary() {
		int sum = 0;
		for (Worker w : workers) {
			sum += w.getPay();
		}

		System.out.printf("모든 사원들의 급여 총액은: %,d원\n", sum);
	}
}
