package code;

import java.lang.management.ManagementFactory;

public class Main {

	public static double getProcessCpuLoad() {
		com.sun.management.OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();
		return osBean.getProcessCpuLoad() * 100; // CPU load as a percentage
	}

	public static int logMemoryUsage() {
		Runtime runtime = Runtime.getRuntime();
		long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
		return (int) (memoryUsed / (1024)); // Memory used in KB
	}

	public static void logPerformance(String strategy) {
		System.gc();
		double cpuBefore = getProcessCpuLoad();
		int memoryBefore = logMemoryUsage();
	

		// Solve the Water Sort problem
	    String grid = "6;" +
	            "3;" +
	            "r,r,y;" +
	            "b,y,r;" +
	            "y,b,g;" +
	            "g,g,b;" +
	            "e,e,e;" +
	            "e,e,e;";

		String result = WaterSortSearch.solve(grid, strategy, false);

		// Log CPU and memory usage after the algorithm has completed
		double cpuAfter = getProcessCpuLoad();
		
		int memoryAfter = logMemoryUsage();

		// Output the result and CPU/Memory usage
		System.out.println("Strategy Used: " + strategy);
		System.out.println("Result: " + result);
		System.out.println("CPU Load Before: " + cpuBefore + "%");
		System.out.println("CPU Load After: " + cpuAfter + "%");
		System.out.println("CPU Load Difference: " + (cpuAfter - cpuBefore) + "%");
		System.out.println("Memory Before: " + memoryBefore + " KB");
		System.out.println("Memory After: " + memoryAfter + " KB");
		System.out.println("Memory Difference: " + (memoryAfter - memoryBefore) + " KB");

	}
	public static void main(String[] args) {
		String [] strategies = {"BF", "DF", "ID", "UC", "GR1", "GR2", "AS1", "AS2"};
		
		for (String strategy : strategies) {
			logPerformance(strategy);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 2-second delay
		}
		

	}

}