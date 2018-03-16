package common;

import java.util.ArrayList;
import java.util.List;

public class CPUUtils {
	
	public static int getRegisterPosition(String registerName) {
		
		List<String> registers = new ArrayList<String>();
		registers.add("R0");
		registers.add("R1");
		registers.add("R2");
		registers.add("R3");
		registers.add("R4");
		registers.add("R5");
		registers.add("R6");
		registers.add("R7");
		registers.add("R8");
		registers.add("R9");
		registers.add("R10");
		registers.add("R11");
		registers.add("R12");
		registers.add("R13");
		registers.add("R14");
		registers.add("R15");	
		
		registerName=registerName.trim();
		registerName= registerName.toUpperCase();
		
		
		return registers.indexOf(registerName); // if not found will return -1 ( Literal)
		
	}
	
	public static int getOperandValue(CPUContext cpuContext, String source) {
		int operand;
		int registerPosition = CPUUtils.getRegisterPosition(source);
		
		if(registerPosition== -1) {
			operand=Integer.parseInt(source);
					
		}
		else {
			Integer forwardedRegVal = cpuContext.getDecoderRegisterFiles().getForwardedMap().get(source);
			if(forwardedRegVal == null) {

				operand=cpuContext.getRegisters()[registerPosition].getValue();
				
			}
			else {
				
				operand=forwardedRegVal;
				
			}
		}
		return operand;
		
	}
	
	public static Integer getForwardedValue(CPUContext cpuContext, String register) {
		Integer value= null;
		if (register == null)
			return null;
		if(register.equalsIgnoreCase(cpuContext.getExecution().getForwardingRegister())) {
			value= cpuContext.getExecution().getForwardingRegisterValue();
		}
		else if(register.equalsIgnoreCase(cpuContext.getMultiplicationALU2().getForwardingRegister())) {
			value=cpuContext.getMultiplicationALU2().getForwardingRegisterValue();
		}
		else if(register.equalsIgnoreCase(cpuContext.getDivAlu4().getForwardingRegister())) {
			value= cpuContext.getDivAlu4().getForwardingRegisterValue();
		}
		return value;
	}
	
	public static boolean isSourceLocked(CPUContext cpuContext, String source, int currentExecutionCycle) {
		if(source==null || source.length()==0 ) {
			return false;
		}
		
		int sourceRegisterPosition1 = CPUUtils.getRegisterPosition(source);
		if(sourceRegisterPosition1 != -1 ) {
			Register register = cpuContext.getRegisters()[sourceRegisterPosition1];
			if(!register.isValid() && register.getLockedInCycle()!= currentExecutionCycle && cpuContext.getDecoderRegisterFiles().getForwardedMap().get(source)==null) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isDestinationLocked(CPUContext cpuContext, String destination, int currentExecutionCycle) {
		if(destination==null || destination.length()==0 ) {
			return false;
		}
		
		int sourceRegisterPosition1 = CPUUtils.getRegisterPosition(destination);
		if(sourceRegisterPosition1 != -1 ) {
			Register register = cpuContext.getRegisters()[sourceRegisterPosition1];
			if(!register.isValid() && register.getLockedInCycle()!= currentExecutionCycle ) {
					return true;	
				
			}
		}
		return false;
	}
	
	/*private Boolean getForwardedZeroFlag(CPUContext cpuContext) {
		
		Instruction lastArithemeticInstruction = cpuContext.getDecoderRegisterFiles().getLastArithemeticInstruction();
		if(lastArithemeticInstruction ==null)
			return null;
		if(lastArithemeticInstruction.equals(cpuContext.getExecution().getForwardingRegisterInstruction())) {
			return cpuContext.getExecution().getForwardingRegisterValue() ==0;
		} 
		if(lastArithemeticInstruction.equals(cpuContext.getMultiplicationALU2().getForwardingRegisterInstruction())) {
			return cpuContext.getMultiplicationALU2().getForwardingRegisterValue() ==0;
		}
		if(lastArithemeticInstruction.equals(cpuContext.getDivAlu4().getForwardingRegisterInstruction())) {
			return cpuContext.getDivAlu4().getForwardingRegisterValue() ==0;
		}
		return null;
	}*/

}
