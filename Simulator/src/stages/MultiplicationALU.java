package stages;

import java.util.HashMap;
import java.util.Map;

import common.CPUContext;
import common.CPUUtils;
import common.Instruction;

public class MultiplicationALU extends Stage {

	public MultiplicationALU(CPUContext cpuContext) {
		super(cpuContext);

	}


	
	private Map<Instruction,Integer> multiplicationALUBufferMap = new HashMap<Instruction,Integer>(); 

	public Map<Instruction, Integer> getMultiplicationALUBufferMap() {
		return multiplicationALUBufferMap;
	}




	@Override
	public void execute(int currentExecutionCycle) {
		
		this.currentExecutionCycle= currentExecutionCycle;

			if(getCpuContext().getDecoderRegisterFiles().isInstructionStalled(this.currentExecutionCycle)) {
				this.setInstruction(null);
			}
			else if (cpuContext.getDecoderRegisterFiles().getInstruction()!=null && cpuContext.getDecoderRegisterFiles().getInstruction().getInstruction().equalsIgnoreCase("MUL"))
			{	
				this.setInstruction(cpuContext.getDecoderRegisterFiles().getInstruction());
				multiply();
			} 
			else
			{
			 this.setInstruction(null);	
			}
			
			if (this.getInstruction()== null)
			{
				System.out.println("MultiplicationALU Stage:");
			}
			else {
				System.out.println("MultiplicationALU Stage:" + instruction.getInstructionString());

			}


	}

	public void multiply() {

		int operand1=CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource1());
		int operand2=CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource2());

		int result= operand1 * operand2 ;
		
		multiplicationALUBufferMap.put(this.getInstruction(), result);
		int destionationRegisterPosition = CPUUtils.getRegisterPosition(this.getInstruction().getDestination());
		getCpuContext().getRegisters()[destionationRegisterPosition].setValid(false);
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
	}


}
