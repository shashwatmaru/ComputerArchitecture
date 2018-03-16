package stages;

import java.util.HashMap;
import java.util.Map;

import common.CPUContext;
import common.CPUUtils;
import common.Instruction;
import common.Register;
import common.RegisterBufferWrapper;

public class Execution extends Stage {

	public Execution(CPUContext cpuContext) {
		super(cpuContext);
	}
	
	private String forwardingRegister="";
	private int forwardingRegisterValue=0;
	private Instruction forwardingRegisterInstruction= null;
	// to store temporary result of execution stage.
	
	private Map<Instruction,Integer> bufferMap = new HashMap<Instruction,Integer>(); 


	public Map<Instruction, Integer> getBufferMap() {
		return bufferMap;
	}


	public void setBufferMap(Map<Instruction, Integer> bufferMap) {
		this.bufferMap = bufferMap;
	}
	
	public void setForwardingValues() {
		Instruction currentInstruction = this.getInstruction();
		
		if(currentInstruction==null || currentInstruction.getInstruction().equalsIgnoreCase("HALT") 
		|| currentInstruction.getInstruction().equalsIgnoreCase("STORE") || currentInstruction.getInstruction().equalsIgnoreCase("JUMP") || currentInstruction.getInstruction().equalsIgnoreCase("LOAD"))
		{
			return;
			
		}
		
		forwardingRegister= currentInstruction.getDestination();
		forwardingRegisterValue= this.bufferMap.get(currentInstruction);
		forwardingRegisterInstruction = currentInstruction;
	}

	@Override
	public void execute(int currentExecutionCycle) {

		this.currentExecutionCycle= currentExecutionCycle;
		if (this.getInstruction()!=null && this.getInstruction()!=cpuContext.getMemory().getInstruction() ) {
			System.out.println("Execution Stage: " +instruction.getInstructionNumber()+" "+ instruction.getInstructionString()+" Stalled");
			return;

		}

		
		else if(cpuContext.getDecoderRegisterFiles().getInstruction()!=null && ( cpuContext.getDecoderRegisterFiles().getInstruction().getInstruction().equalsIgnoreCase("MUL")
				|| cpuContext.getDecoderRegisterFiles().getInstruction().getInstruction().equalsIgnoreCase("DIV") ||cpuContext.getDecoderRegisterFiles().getInstruction().getInstruction().equalsIgnoreCase("HALT"))) {
			this.setInstruction(null);
		}


		else if(getCpuContext().getDecoderRegisterFiles().isInstructionStalled(this.currentExecutionCycle)) {
			this.setInstruction(null);
		}

		else
		{	
			this.setInstruction(cpuContext.getDecoderRegisterFiles().getInstruction());

		}


		if (this.getInstruction() == null)
		{
			System.out.println("Execution Stage: Empty");
			return;
		}
		System.out.println("Execution Stage: "+instruction.getInstructionNumber()+" "+instruction.getInstructionString());


		switch(this.getInstruction().getInstruction()) {

		case "MOVC": movc();
		break;
		// Dont need to implement, proffesor withdraws this requirement.
		//		case "ADDC": movc();
		//		break;
		//		case "MOV": mov();
		//		break;
		case "ADD": add();
		break;
		case "SUB": substract();
		break;
		case "LOAD": load();
		break;
		case "STORE": store();
		break;
		case "AND": and();
		break;
		case "OR": or();
		break;
		case "EXOR": exOR();
		break;
		case "BZ": 	bz();
		break;
		case "BNZ":		bnz();
		break;
		case "JUMP": jump();
			break;
		case "JAL":	jal();
		break;
		default:
			System.out.println("Invalid instruction");
			break;
		}

	}

	private void jal() {

		int operandValue1= CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource1()); // This is not destination, it is source 1 only. 
		int operandValue2= CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource2()); // This is not source1, it is source 2.
		int newPCValue=operandValue1+operandValue2;
		this.getBufferMap().put(instruction, newPCValue);
		int destionationRegisterPosition = CPUUtils.getRegisterPosition(this.getInstruction().getDestination());
		getCpuContext().getRegisters()[destionationRegisterPosition].setValid(false);
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
		
	}


	public void add() {

		int operand1=CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource1());
		int operand2=CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource2());

		int result= operand1 + operand2 ;
		
		bufferMap.put(this.getInstruction(), result);
		int destionationRegisterPosition = CPUUtils.getRegisterPosition(this.getInstruction().getDestination());
		getCpuContext().getRegisters()[destionationRegisterPosition].setValid(false);
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());


	}

	public void substract() {

		int operand1=CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource1());
		int operand2=CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource2());

		int result= operand1 - operand2 ;
		
		bufferMap.put(this.getInstruction(), result);
		int destionationRegisterPosition = CPUUtils.getRegisterPosition(this.getInstruction().getDestination());
		getCpuContext().getRegisters()[destionationRegisterPosition].setValid(false);
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
	}

	public void movc() {
		int registerPosition = CPUUtils.getRegisterPosition(this.getInstruction().getSource1());
		if(registerPosition == -1) {
			bufferMap.put(this.getInstruction(),Integer.parseInt(this.getInstruction().getSource1()));
		}
		else
		{

			bufferMap.put(this.getInstruction(),getCpuContext().getRegisters()[registerPosition].getValue());

		}
		int destionationRegisterPosition = CPUUtils.getRegisterPosition(this.getInstruction().getDestination());
		getCpuContext().getRegisters()[destionationRegisterPosition].setValid(false);
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
	}

	public void and() {

		int operand1=CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource1());
		int operand2=CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource2());

		int result= operand1 & operand2;
		
		bufferMap.put(this.getInstruction(), result);
		int destionationRegisterPosition = CPUUtils.getRegisterPosition(this.getInstruction().getDestination());
		getCpuContext().getRegisters()[destionationRegisterPosition].setValid(false);
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());

	}

	public void or() {
		int operand1=CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource1());
		int operand2=CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource2());

		int result= operand1 | operand2;
		
		bufferMap.put(this.getInstruction(), result);
		int destionationRegisterPosition = CPUUtils.getRegisterPosition(this.getInstruction().getDestination());
		getCpuContext().getRegisters()[destionationRegisterPosition].setValid(false);
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
	}
	public void exOR() {
		int operand1=CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource1());
		int operand2=CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource2());

		int result= operand1 ^ operand2;
		
		bufferMap.put(this.getInstruction(), result);
		int destionationRegisterPosition = CPUUtils.getRegisterPosition(this.getInstruction().getDestination());
		getCpuContext().getRegisters()[destionationRegisterPosition].setValid(false);
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
	}


	public void load() {

		int registerPosition_1 = CPUUtils.getRegisterPosition(this.getInstruction().getSource1());
		int registerPosition_2 = CPUUtils.getRegisterPosition(this.getInstruction().getSource2());

		if (registerPosition_1 != -1 && registerPosition_2 == -1) {
			bufferMap.put(this.getInstruction(), ((getCpuContext().getRegisters()[registerPosition_1].getValue())
					+ (Integer.parseInt(this.getInstruction().getSource2()))));

		} else if (registerPosition_1 == -1 && registerPosition_2 != -1) {
			bufferMap.put(this.getInstruction(), ((Integer.parseInt(this.getInstruction().getSource1()))
					+ (getCpuContext().getRegisters()[registerPosition_2].getValue())));

		}
		int destionationRegisterPosition = CPUUtils.getRegisterPosition(this.getInstruction().getDestination());
		getCpuContext().getRegisters()[destionationRegisterPosition].setValid(false);
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockedInCycle(this.getCurrentExecutionCycle());
		getCpuContext().getRegisters()[destionationRegisterPosition].setLockingInstruction(this.getInstruction());
	}

	public void store() {
		int registerPosition_1 = CPUUtils.getRegisterPosition(this.getInstruction().getSource1());
		int registerPosition_2 = CPUUtils.getRegisterPosition(this.getInstruction().getSource2());

		if (registerPosition_1 != -1 && registerPosition_2 == -1) {
			bufferMap.put(this.getInstruction(), ((getCpuContext().getRegisters()[registerPosition_1].getValue())
					+ (Integer.parseInt(this.getInstruction().getSource2()))));

		} else if (registerPosition_1 == -1 && registerPosition_2 != -1) {
			bufferMap.put(this.getInstruction(), ((Integer.parseInt(this.getInstruction().getSource1()))
					+ (getCpuContext().getRegisters()[registerPosition_2].getValue())));

		}

	}
	
	public void jump() {

		int operandValue1= CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getDestination()); // This is not destination, it is source 1 only. 
		int operandValue2= CPUUtils.getOperandValue(this.cpuContext, this.getInstruction().getSource1()); // This is not source1, it is source 2.
		int newPCValue=operandValue1+operandValue2;
		this.getBufferMap().put(instruction, newPCValue);


	}
	public void bnz() {
		
			int bzLoop= CPUUtils.getOperandValue(this.cpuContext,this.getInstruction().getDestination());
			int newPCValue= getCpuContext().getProgramCounter() -8 + bzLoop;
			this.getBufferMap().put(instruction, newPCValue);



	}
	public void bz() {

		
			int bzLoop= CPUUtils.getOperandValue(this.cpuContext,this.getInstruction().getDestination());
			int newPCValue= getCpuContext().getProgramCounter() -8 + bzLoop;
			this.getBufferMap().put(instruction, newPCValue);

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
