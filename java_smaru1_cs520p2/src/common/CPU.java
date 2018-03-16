package common;

import java.io.FileNotFoundException;

public class CPU {
	

	CPUContext cpuContext ;
	private boolean endOfProgram;
	
	public void init(String filePath) throws FileNotFoundException {
		setEndOfProgram(false);
//		if(cpuContext== null) {
			this.cpuContext = new CPUContext(filePath);
//		}
//		else {
//			this.cpuContext.init();
//		}
	}
	
	public void executeCycle(int currentExecutionCycle) {
		
		cpuContext.getWriteBack().execute(currentExecutionCycle);
		cpuContext.getMemory().execute(currentExecutionCycle);
		cpuContext.getExecution().execute(currentExecutionCycle);
		cpuContext.getMultiplicationALU2().execute(currentExecutionCycle);
		cpuContext.getMultiplicationALU().execute(currentExecutionCycle);
		cpuContext.getDivAlu4().execute(currentExecutionCycle);
		cpuContext.getDivAlu3().execute(currentExecutionCycle);
		cpuContext.getDivAlu2().execute(currentExecutionCycle);
		cpuContext.getDivAlu1().execute(currentExecutionCycle);
		cpuContext.getDecoderRegisterFiles().execute(currentExecutionCycle);
		cpuContext.getFetch().execute(currentExecutionCycle);
		cpuContext.getExecution().setForwardingValues();
		cpuContext.getMultiplicationALU2().setForwardingValues();
		cpuContext.getDivAlu4().setForwardingValues();
		cpuContext.getMemory().clearForwardedValues();
		cpuContext.getWriteBack().releaseLocks();
		if(cpuContext.getWriteBack().getInstruction()!=null && cpuContext.getWriteBack().getInstruction().getInstruction().equalsIgnoreCase("HALT")) {
			endOfProgram = true;
		}
	}
	
	public CPUContext getCpuContext() {
		return cpuContext;
	}

	public void setCpuContext(CPUContext cpuContext) {
		this.cpuContext = cpuContext;
	}
	public boolean isEndOfProgram() {
		return endOfProgram;
	}

	public void setEndOfProgram(boolean endOfProgram) {
		this.endOfProgram = endOfProgram;
	}

}
