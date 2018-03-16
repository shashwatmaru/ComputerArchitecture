package stages;

import common.CPUContext;
import common.Instruction;
import common.RegisterBufferWrapper;

public class MultiplicationALU2 extends Stage{

	public MultiplicationALU2(CPUContext cpuContext) {
		super(cpuContext);
	
	}
	
	private String forwardingRegister="";
	private int forwardingRegisterValue=0;
	private Instruction forwardingRegisterInstruction= null;

	
	public void setForwardingValues() {
		Instruction currentInstruction = this.getInstruction();
		if(currentInstruction ==null) {
			return;
		}
		setForwardingRegister(currentInstruction.getDestination());
		setForwardingRegisterValue(cpuContext.getMultiplicationALU().getMultiplicationALUBufferMap().get(currentInstruction));
		setForwardingRegisterInstruction(currentInstruction);
	}


	@Override
	public void execute(int currentExecutionCycle) {
		
		this.currentExecutionCycle= currentExecutionCycle;
		if (this.getInstruction()!=null && this.getInstruction()!=cpuContext.getMemory().getInstruction() ) {
			System.out.println("Multiplication Stage2: " +instruction.getInstructionNumber()+" "+ instruction.getInstructionString()+" Stalled");
			return;

		}

		
		this.setInstruction(cpuContext.getMultiplicationALU().getInstruction());
		if(this.getInstruction()== null) 
		{
			System.out.println("MultiplicationALU Stage2: Empty");
		return;	
		}
	
		System.out.println("MultiplicationALU Stage2: " +instruction.getInstructionNumber()+" "+ instruction.getInstructionString());

	}


	public String getForwardingRegister() {
		return forwardingRegister;
	}


	public void setForwardingRegister(String forwardingRegister) {
		this.forwardingRegister = forwardingRegister;
	}


	public int getForwardingRegisterValue() {
		return forwardingRegisterValue;
	}


	public void setForwardingRegisterValue(int forwardingRegisterValue) {
		this.forwardingRegisterValue = forwardingRegisterValue;
	}


	public Instruction getForwardingRegisterInstruction() {
		return forwardingRegisterInstruction;
	}


	public void setForwardingRegisterInstruction(Instruction forwardingRegisterInstruction) {
		this.forwardingRegisterInstruction = forwardingRegisterInstruction;
	}
	
	


}
