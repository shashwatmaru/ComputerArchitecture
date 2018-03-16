package stages;

import java.util.HashMap;
import java.util.Map;

import common.CPUContext;
import common.CPUUtils;
import common.Instruction;

public class Memory extends Stage {

	public Memory(CPUContext cpuContext) {
		super(cpuContext);
	}

	private Map<Instruction,Integer> bufferMemoryMap = new HashMap<Instruction,Integer>(); 


	public Map<Instruction,Integer> getBufferMemoryMap() {
		return bufferMemoryMap;
	}


	public void setBufferMemoryMap(Map<Instruction,Integer> bufferMemoryMap) {
		this.bufferMemoryMap = bufferMemoryMap;
	}


	@Override
	public void execute(int currentExecutionCycle) {
		
		this.currentExecutionCycle= currentExecutionCycle;
		
		if(getCpuContext().getMultiplicationALU2().getInstruction() != null) 
		{
			this.setInstruction(cpuContext.getMultiplicationALU2().getInstruction());

		}
		else {
			this.setInstruction(cpuContext.getExecution().getInstruction());

		}
		if (this.getInstruction() == null)
		{
			System.out.println("Memory Stage:");			
			return;
		}
		else {
			if (this.getInstruction().getInstruction().contentEquals("LOAD")) {

				Integer memAddress= getCpuContext().getExecution().getBufferMap().get(this.getInstruction());
				Integer memValue= getCpuContext().getMemoryMap().get(memAddress);
				if (memValue==null) {
					memValue=0;
				}
			
				bufferMemoryMap.put(this.getInstruction(),memValue);
				
				// int registerSourceLoad=
				// getCpuContext().getMemoryArray()[getCpuContext().getExecution().getBufferMap().get(this.getInstruction())];
				// CPUUtils.getRegisterPosition(this.getInstruction().getDestination())=registerSourceLoad;
			}
			else if (this.getInstruction().getInstruction().equalsIgnoreCase("STORE"))
			{
				int registerDestinationPosition=CPUUtils.getRegisterPosition(this.getInstruction().getDestination());
				int registerDestinationValue= getCpuContext().getRegisters()[registerDestinationPosition].getValue();
				getCpuContext().getMemoryMap().put(getCpuContext().getExecution().getBufferMap().get(this.getInstruction()), registerDestinationValue);
				
			}
			else if (this.getInstruction().getInstruction().equalsIgnoreCase("BNZ")) {

				if(!getCpuContext().isZeroFlag()) {
					
					getCpuContext().setProgramCounter(getCpuContext().getExecution().getBufferMap().get(this.getInstruction()));
					getCpuContext().getFetch().setInstruction(null);
					getCpuContext().getDecoderRegisterFiles().setInstruction(null);
					getCpuContext().getFetch().setBranched(true);
				}
				
			}
			else if (this.getInstruction().getInstruction().equalsIgnoreCase("BZ")) {
				if(getCpuContext().isZeroFlag()) {

					getCpuContext().setProgramCounter(getCpuContext().getExecution().getBufferMap().get(this.getInstruction()));
					getCpuContext().getFetch().setInstruction(null);
					getCpuContext().getDecoderRegisterFiles().setInstruction(null);
					getCpuContext().getFetch().setBranched(true);
				}
			}
			else if (this.getInstruction().getInstruction().equalsIgnoreCase("JUMP")) {
				
				getCpuContext().setProgramCounter(getCpuContext().getExecution().getBufferMap().get(this.getInstruction()));
				getCpuContext().getFetch().setInstruction(null);
				getCpuContext().getDecoderRegisterFiles().setInstruction(null);
				getCpuContext().getFetch().setBranched(true);
			}
		}
		System.out.println("Memory Stage:" + instruction.getInstructionString());

	}

}
