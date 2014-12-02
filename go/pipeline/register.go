package pipeline

import "github.com/DirtNap/bu-classwork/go/disassembler"

type aluFunc func(int, int) int

// var ALUOps = map[byte]aluFunc(){
// 	0x000: func(x int, y int) {return x & y}
// 	0x001: func(x int, y int) {return x | y}
// 	0x002: func(x int, y int)
// }

type IFIDRegister struct {
	Instruction uint32
}

type IDEXRegister struct {
	DecodedInstruction disassembler.InstructionFields
}

type EXMEMRegister struct {
	DecodedInstruction disassembler.InstructionFields
}

type MEMWBRegister struct {
	DecodedInstruction disassembler.InstructionFields
}
