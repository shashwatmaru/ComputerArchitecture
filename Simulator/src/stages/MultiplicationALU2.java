package stages;

import common.CPUContext;

public class MultiplicationALU2 extends Stage{

	public MultiplicationALU2(CPUContext cpuContext) {
		super(cpuContext);
	
	}

	@Override
	public void execute(int currentExecutionCycle) {
		
		this.currentExecutionCycle= currentExecutionCycle;
		
		this.setInstruction(cpuContext.getMultiplicationALU().getInstruction());
		if(this.getInstruction()== null) 
		{
			System.out.println("MultiplicationALU Stage2:");
		return;	
		}
	
		System.out.println("MultiplicationALU Stage2:" + instruction.getInstructionString());

	}
	
	


}
