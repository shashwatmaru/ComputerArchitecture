package stages;

import common.CPUContext;
import common.Instruction;

public class DivALU4 extends Stage{

	public DivALU4(CPUContext cpuContext) {
		super(cpuContext);
	}

	private String forwardingRegister="";
	private int forwardingRegisterValue=0;
	private Instruction forwardingRegisterInstruction= null;

	
	public void setForwardingValues() {
		Instruction currentInstruction = this.getInstruction();
		if(currentInstruction ==null || currentInstruction.getInstruction().equalsIgnoreCase("HALT")) {
			return;
		}
		setForwardingRegister(currentInstruction.getDestination());
		setForwardingRegisterValue(cpuContext.getDivAlu1().getDivisionALUBufferMap().get(currentInstruction));
		setForwardingRegisterInstruction(currentInstruction);
	}
	
	
	@Override
	

public void execute(int currentExecutionCycle) {
		
		this.currentExecutionCycle= currentExecutionCycle;
		
		this.setInstruction(cpuContext.getDivAlu3().getInstruction());
		if(this.getInstruction()== null) 
		{
			System.out.println("DivALU Stage4: Empty");
		return;	
		}
	
		System.out.println("DivALU Stage4: " +instruction.getInstructionNumber()+" "+ instruction.getInstructionString());

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
