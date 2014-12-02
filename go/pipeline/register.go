package pipeline

import "github.com/DirtNap/bu-classwork/go/disassembler"

type Register struct {
	Read registerSide
	Write registerSide
}
type registerSide struct {
	Instruction uint32
	DecodedInstruction disassembler.InstructionFields
	Control uint32
}
func (reg *Register) Flip() {
	reg.Read = reg.Write
	reg.Write = registerSide{}
}
