package common;

public class Instruction {
	
	String instructionNumber;
	
	String instruction;
	String source1;
	String source2;
	String destination;
	String instructionString;  
	int instructionAddress=0;
	Instruction lastArithmeticInstruction;
	int fetchSequence;
	
	public Instruction(String instructionNumber, String instr, int instructionAddress)
	{	
		this.instructionNumber=instructionNumber;
		instructionString=instr;
		this.instructionAddress=instructionAddress;
	}

	public Instruction getLastArithmeticInstruction() {
		return lastArithmeticInstruction;
	}

	public void setLastArithmeticInstruction(Instruction lastArithmeticInstruction) {
		this.lastArithmeticInstruction = lastArithmeticInstruction;
	}
	
	public String getInstructionNumber() {
		return instructionNumber;
	}

	public void setInstructionNumber(String instructionNumber) {
		this.instructionNumber = instructionNumber;
	}

	
	public int getInstructionAddress() {
		return instructionAddress;
	}

	public void setInstructionAddress(int instructionAddress) {
		this.instructionAddress = instructionAddress;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getSource1() {
		return source1;
	}

	public void setSource1(String source1) {
		this.source1 = source1;
	}

	public String getSource2() {
		return source2;
	}

	public void setSource2(String source2) {
		this.source2 = source2;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getInstructionString() {
		return instructionString;
	}

	public void setInstructionString(String instructionString) {
		this.instructionString = instructionString;
	}
	
	public int getFetchSequence() {
		return fetchSequence;
	}

	public void setFetchSequence(int fetchSequence) {
		this.fetchSequence = fetchSequence;
	}
}
