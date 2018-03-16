package common;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import stages.*;

public class CPUContext {

	private Register[] registers = new Register[16];
	private Map <Integer, Integer> memoryMap = new HashMap<Integer,Integer>();
	private int programCounter=4000;
	private boolean zeroFlag= false ;
	private Instruction zeroFlagSetBy= null;
	private int zeroFlagSetByFetchSequence;
	private final Fetch fetch ;
	private final DecodeRegisterFiles decoderRegisterFiles;
	private final Execution execution;
	private final Memory memory;
	private final WriteBack writeBack;
	private final MultiplicationALU multiplicationALU;
	private final MultiplicationALU2 multiplicationALU2;
	private final DivALU1 divAlu1;
	private final DivALU2 divAlu2;
	private final DivALU3 divAlu3;
	private final DivALU4 divAlu4;
	
	public MultiplicationALU2 getMultiplicationALU2() {
		return multiplicationALU2;
	}
	
	public void init() {
		for (int i=0;i<16;i++) 
		{
			registers[i].setValue(0);
			registers[i].setValid(true);
			registers[i].setLockingInstruction(null);
			registers[i].setLockedInCycle(0);
			
		}
		
		this.programCounter=4000;
		zeroFlag=false;
		zeroFlagSetBy= null;
		memoryMap.keySet().removeAll(memoryMap.keySet());
		this.getFetch().setEndOfProgram(false);
		this.getFetch().setBranched(false);
		this.getDecoderRegisterFiles().setLastArithemeticInstruction(null);
		Map<Instruction, Integer> executionBufferMap = this.getExecution().getBufferMap();
		executionBufferMap.keySet().removeAll(executionBufferMap.keySet());
		Map<Instruction, Integer> multiplicationALU1BufferMap = this.getMultiplicationALU().getMultiplicationALUBufferMap();
		multiplicationALU1BufferMap.keySet().removeAll(multiplicationALU1BufferMap.keySet());
		Map<Instruction, Integer> memoryBufferMap = this.getMemory().getBufferMemoryMap();
		memoryBufferMap.keySet().removeAll(memoryBufferMap.keySet());
		
		
	}

	public CPUContext(String filePath) throws FileNotFoundException {
		
		for (int i=0;i<16;i++) 
		{
			registers[i]=new Register("R"+i);
		}
		fetch  = new Fetch(this);
		decoderRegisterFiles =new DecodeRegisterFiles(this);
		execution =new Execution(this);
		memory = new Memory(this);
		writeBack =new WriteBack(this);
		multiplicationALU = new MultiplicationALU(this);
		multiplicationALU2 = new MultiplicationALU2(this);
		divAlu1 = new DivALU1(this);
		divAlu2 = new DivALU2(this);
		divAlu3 = new DivALU3(this);
		divAlu4 = new DivALU4(this);
		loadInstructions(filePath);


	}



	// Map with key as the memory location and value as the Instruction.
	private Map<Integer,Instruction> instructionMap = new HashMap<Integer,Instruction>(); 

	public MultiplicationALU getMultiplicationALU() {
		return multiplicationALU;
	}


	
	private void loadInstructions(String filePath) throws FileNotFoundException {
		int pcValue=4000, instructionCount=0;
		File file = new File(filePath);
		Scanner scan = new Scanner(file);
		String str,instructionNumber;
		while (scan.hasNextLine())
		{
			instructionNumber="(I"+instructionCount+")";
			str=scan.nextLine();
			Instruction instruction = new Instruction(instructionNumber,str, pcValue);
			instructionMap.put(pcValue,instruction);
			pcValue+=4;
			instructionCount++;
		}
	
	}


	public Map<Integer, Instruction> getInstructionMap() {
		return instructionMap;
	}


	public void setInstructionMap(Map<Integer, Instruction> instructionMap) {
		this.instructionMap = instructionMap;
	}


	public Register[] getRegisters() {
		return registers;
	}

	public void setRegisters(Register[] registers) {
		this.registers = registers;
	}

	public Fetch getFetch() {
		return fetch;
	}
	public DecodeRegisterFiles getDecoderRegisterFiles() {
		return decoderRegisterFiles;
	}
	public Execution getExecution() {
		return execution;
	}
	public Memory getMemory() {
		return memory;
	}
	public WriteBack getWriteBack() {
		return writeBack;
	}

	public int getProgramCounter() {
		return programCounter;
	}
	public void setProgramCounter(int programCounter) {
		this.programCounter = programCounter;
	}


	public boolean isZeroFlag() {
		return zeroFlag;
	}


	public void setZeroFlag(boolean zeroFlag) {
		this.zeroFlag = zeroFlag;
	}


	public Instruction getZeroFlagSetBy() {
		return zeroFlagSetBy;
	}


	public void setZeroFlagSetBy(Instruction zeroFlagSetBy) {
		this.zeroFlagSetBy = zeroFlagSetBy;
	}


	public Map <Integer, Integer> getMemoryMap() {
		return memoryMap;
	}

	public DivALU1 getDivAlu1() {
		return divAlu1;
	}

	public DivALU2 getDivAlu2() {
		return divAlu2;
	}

	public DivALU3 getDivAlu3() {
		return divAlu3;
	}

	public DivALU4 getDivAlu4() {
		return divAlu4;
	}

	public int getZeroFlagSetByFetchSequence() {
		return zeroFlagSetByFetchSequence;
	}

	public void setZeroFlagSetByFetchSequence(int zeroFlagSetByFetchSequence) {
		this.zeroFlagSetByFetchSequence = zeroFlagSetByFetchSequence;
	}

}
