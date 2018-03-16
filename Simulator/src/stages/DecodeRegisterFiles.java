package stages;

import java.util.ArrayList;
import java.util.List;

import common.CPUContext;
import common.CPUUtils;
import common.Instruction;
import common.Register;

public class DecodeRegisterFiles extends Stage {

		private Instruction lastArithemeticInstruction= null;
	
	public Instruction getLastArithemeticInstruction() {
			return lastArithemeticInstruction;
		}

		public void setLastArithemeticInstruction(Instruction lastArithemeticInstruction) {
			this.lastArithemeticInstruction = lastArithemeticInstruction;
		}

	public DecodeRegisterFiles(CPUContext cpuContext) {
		super(cpuContext);
	}

	@Override
	public void execute(int currentExecutionCycle) {
	
		List<String> nonArithemeticInstruction = new ArrayList<String>();
		nonArithemeticInstruction.add("HALT");
		nonArithemeticInstruction.add("BNZ");
		nonArithemeticInstruction.add("BZ");
		nonArithemeticInstruction.add("JUMP");
		nonArithemeticInstruction.add("LOAD");
		nonArithemeticInstruction.add("STORE");
		nonArithemeticInstruction.add("MOVC");
		this.currentExecutionCycle= currentExecutionCycle;		

		if(this.isInstructionStalled(currentExecutionCycle) || this.isInstructionStalledAtNextStage()) {
			System.out.println("Decode RF Stage:"+instruction.getInstructionString());
			return;

		}

		this.setInstruction(cpuContext.getFetch().getInstruction());
		
		if (this.getInstruction()==null) {
			System.out.println("Decode RF Stage:");
			return;

		}

		String [] strArray = null;
		strArray = instruction.getInstructionString().split("[,\\s\\#]+");
		instruction.setInstruction(strArray[0]);


		if(strArray.length>1)
		{
			instruction.setDestination(strArray[1]);

		}
		else
		{
			instruction.setDestination(null); // if Halt instr comes, then in that case no destination will be present.
		}


		if(strArray.length>2)
		{
			instruction.setSource1(strArray[2]);

		}
		else
		{
			instruction.setSource1(null); // if BNZ instr comes, than in that case only one register will be needed.
		}

		if(strArray.length>3) {
			instruction.setSource2(strArray[3]);
		}
		else
		{
			instruction.setSource2(null);	// if MOVC R1 #10 type instruction comes than it will not take source2.
		}

		System.out.println("Decode RF Stage:"+instruction.getInstructionString());

		if(this.getInstruction().getInstruction().equalsIgnoreCase("HALT")) {
		halt();
		}
		
		if(!nonArithemeticInstruction.contains(this.instruction.getInstruction())) {
			this.lastArithemeticInstruction= this.instruction;
		}
		
	}

	public void halt() {

		getCpuContext().setProgramCounter(-1);

	}

	
	public boolean isInstructionStalled(int currentExecutionCycle) { // due to registers
		
		
		if (this.getInstruction()==null)
		{

			return false;
		}
		
		if(this.getInstruction().getInstruction().equalsIgnoreCase("STORE") || this.getInstruction().getInstruction().equalsIgnoreCase("JUMP")) {
			
			if(CPUUtils.isSourceLocked(this.cpuContext, this.getInstruction().getDestination(), currentExecutionCycle )) {
				
				return true;
				
			}
		}
		
		if(CPUUtils.isSourceLocked(this.cpuContext, this.getInstruction().getSource1(), currentExecutionCycle ) || CPUUtils.isSourceLocked(this.cpuContext, this.getInstruction().getSource2(),currentExecutionCycle ) ) 
		{
			
			return true;
		}
		
		if(this.getInstruction().getInstruction().equalsIgnoreCase("BZ") || this.getInstruction().getInstruction().equalsIgnoreCase("BNZ")) {
			if(!(getCpuContext().getZeroFlagSetBy() == this.lastArithemeticInstruction)) {
			return true;
		}
		}
		return false;

	}
	public boolean isInstructionStalledAtNextStage() { // due to stalling at MUL/EXE stage.
		if (this.getInstruction()==null)
		{

			return false;
		}


//		if(this.getInstruction().getInstruction().equalsIgnoreCase("MUL") && getCpuContext().getMultiplicationALU().getInstruction()==this.getInstruction()) 
//		{
//			return false;
//		}
//

		if(!this.getInstruction().getInstruction().equalsIgnoreCase("MUL") && this.getInstruction() != cpuContext.getExecution().getInstruction()) {
			
			
			return true;
		}
		return false;

	}

}
