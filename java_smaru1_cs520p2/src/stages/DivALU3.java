package stages;

import common.CPUContext;

public class DivALU3 extends Stage{

	public DivALU3(CPUContext cpuContext) {
		super(cpuContext);
		// TODO Auto-generated constructor stub
	}

	@Override

public void execute(int currentExecutionCycle) {
		
		this.currentExecutionCycle= currentExecutionCycle;
		
		this.setInstruction(cpuContext.getDivAlu2().getInstruction());
		if(this.getInstruction()== null) 
		{
			System.out.println("DivALU Stage3: Empty");
		return;	
		}
	
		System.out.println("DivALU Stage3: " +instruction.getInstructionNumber()+" "+ instruction.getInstructionString());

	}

}
