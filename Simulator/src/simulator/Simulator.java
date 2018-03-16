package simulator;

import java.io.FileNotFoundException;
import java.util.Scanner;

import common.CPU;
import common.CPUContext;
import common.Register;

public class Simulator {
	
	public static void main (String[] args) throws FileNotFoundException {
		
		CPU cpu= new CPU();	
		Scanner sc = new Scanner(System.in);
		int numberOfCycles;
		while(true) 
		{	
			System.out.println("=================================APEX SIMULATOR===============================");
			System.out.println("Enter Simulator Commands");
			System.out.println("\t\t Enter 1 for Initiate\n");
			System.out.println("\t\t Enter 2 for Simulate\n");
			System.out.println("\t\t Enter 3 for Display\n");
			System.out.println("\t\t Enter 4 for Exit Console\n");
			System.out.println("Enter option 1-3 for further operation & 4 to exit");
			int input= sc.nextInt();
			
			switch(input) {
			case 1: 
				cpu.init();
				System.out.println("====================Initializing====================");
				System.out.println("\nProgram Counter is set to 4000\n");
				System.out.println("Registers & Memory location are reset\n");
				break;
			case 2: 
				System.out.println("Enter the number of cycles for Simulation");
				numberOfCycles = sc.nextInt();
				int i;
				for(i=1;i<=numberOfCycles;i++)
				{
					System.out.println("Cycle:"+i);
					cpu.executeCycle(i);
					System.out.println("\n");
				}
				break;
			case 3: 
				display(cpu.getCpuContext());
				break;
			case 4: 
				System.out.println("You are out now, please run the program again to use it");
				System.exit(0);
				
				break;
			default:  
				System.out.println("Please reenter and choose from given options only");
				break;
				
			
			}
		}
		
		
		
	}
		public static void display(CPUContext cpuContext)
		{
			Register[] registers= cpuContext.getRegisters();
			int i;
			for (i=0;i<registers.length;i++)
			{
				System.out.println(registers[i].getName()+":"+registers[i].getValue());
			}
			
				
			for(Integer memLocation: cpuContext.getMemoryMap().keySet()) {
					
				System.out.println("Memory Address:"+memLocation+"\tValue:"+cpuContext.getMemoryMap().get(memLocation));
				
			}
			
//			for(i=0;i<100;i++) {
//				System.out.println("Memory location"+i+":"+memoryArray[i]);
//				
//			}
		}

}
