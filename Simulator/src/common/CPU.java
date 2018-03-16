package common;

import java.io.FileNotFoundException;

public class CPU {
	

	CPUContext cpuContext ;
	
	public void init() throws FileNotFoundException {
		if(cpuContext== null) {
			this.cpuContext = new CPUContext();
		}
		else {
			this.cpuContext.init();
		}
	}
	
	public void executeCycle(int currentExecutionCycle) {
		
		cpuContext.getWriteBack().execute(currentExecutionCycle);
		cpuContext.getMemory().execute(currentExecutionCycle);
		cpuContext.getExecution().execute(currentExecutionCycle);
		cpuContext.getMultiplicationALU2().execute(currentExecutionCycle);
		cpuContext.getMultiplicationALU().execute(currentExecutionCycle);
		cpuContext.getDecoderRegisterFiles().execute(currentExecutionCycle);
		cpuContext.getFetch().execute(currentExecutionCycle);
		cpuContext.getWriteBack().releaseLocks();
		
	}
	
	public CPUContext getCpuContext() {
		return cpuContext;
	}

	public void setCpuContext(CPUContext cpuContext) {
		this.cpuContext = cpuContext;
	}

}
