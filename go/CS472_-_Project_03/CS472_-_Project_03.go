package main

import (
	"fmt"
	"github.com/DirtNap/bu-classwork/go/disassembler"
	"github.com/DirtNap/bu-classwork/go/pipeline"
)

var MAIN_MEM [1024]byte
var Regs [32]uint32
var InstructionCache = [12]uint32 {
	0xA1020000,
	0x810AFFFC,
	0x00831820,
	0x01263820,
	0x01224820,
	0x81180000,
	0x81580010,
	0x00624022,
	0x00000000,
	0x00000000,
	0x00000000,
	0x00000000,
}

var ProgramCounter int

var IFIDRead, IFIDWrite *pipeline.IFIDRegister
var IDEXRead, IDEXWrite *pipeline.IDEXRegister
var EXMEMRead, EXMEMWrite *pipeline.EXMEMRegister
var MEMWBRead, MEMWBWrite *pipeline.MEMWBRegister

func IF_stage() {
	IFIDWrite.Instruction = InstructionCache[ProgramCounter]
}

func ID_stage() {
	// This function (from project 1) automatically sign-extends the Immediate
	result, err := disassembler.GetInstruction(IFIDRead.Instruction)
	if err == nil {
		IDEXWrite.DecodedInstruction = result
	} else {
		panic(err)
	}
}

func EX_stage() {
	EXMEMWrite.DecodedInstruction = IDEXRead.DecodedInstruction
}

func MEM_stage() {
	MEMWBWrite.DecodedInstruction = EXMEMRead.DecodedInstruction

}
func WB_stage() {
	fmt.Println(MEMWBRead.DecodedInstruction)
}

func Print_out_everything() {
	fmt.Println("stub")

}
func Copy_write_to_read() {
	IFIDRead = IFIDWrite
	IFIDWrite = new(pipeline.IFIDRegister)
	IDEXRead = IDEXWrite
	IDEXWrite = new(pipeline.IDEXRegister)
	EXMEMRead = EXMEMWrite
	EXMEMWrite = new(pipeline.EXMEMRegister)
	MEMWBRead = MEMWBWrite
	MEMWBWrite = new(pipeline.MEMWBRegister)
}

func main() {
	// Initialize main memory values
	mask, _ := disassembler.GetBitMask(8, 0)
	for i := 0; i < len(MAIN_MEM); i++ {
		MAIN_MEM[i] = byte(uint32(i) & mask)
	}
	// Initialize Registers
	for i := 1; i < len(Regs); i++ {
		Regs[i] = uint32(0x100 + i)
	}
	// Initialize Pipeline Registers
	IFIDRead, IFIDWrite = new(pipeline.IFIDRegister), new(pipeline.IFIDRegister)
	IDEXRead, IDEXWrite = new(pipeline.IDEXRegister), new(pipeline.IDEXRegister)
	EXMEMRead, EXMEMWrite = new(pipeline.EXMEMRegister), new(pipeline.EXMEMRegister)
	MEMWBRead, MEMWBWrite = new(pipeline.MEMWBRegister), new(pipeline.MEMWBRegister)


	for ProgramCounter = 0; ProgramCounter < len(InstructionCache); ProgramCounter++ {
		IF_stage()
		ID_stage()
		EX_stage()
		MEM_stage()
		WB_stage()
		Copy_write_to_read()
	}
}
