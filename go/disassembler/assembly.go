package disassembler

import "fmt"

type InstructionFields struct {
	opcode uint8
	rs uint8
	rt uint8
	rd uint8
	shamt uint8 // Unused for now.
	funct uint8
	immediate int16
	address uint32 // Unused for now.
	format string
	detail instructionDetail
}

type instructionDetail struct {
	mnemonic string
	outputFormat string
}

var iFormatDetails = map[uint8]instructionDetail {
	0x04: instructionDetail{"beq", "%05X %s $%[4]d, $%[5]d, address %05[7]X%[8]s"},
	0x05: instructionDetail{"bne", "%05X %s $%[4]d,$%[5]d, address %05[7]X%[8]s"},
	0x23: instructionDetail{"lw", "%05X %s $%[5]d, %[6]d($%[4]d)%[8]s"},
	0x2B: instructionDetail{"sw", "%05X %s $%[5]d, %[6]d($%[4]d)%[8]s"},
}
var rFormatDetails = map[uint8]instructionDetail {
	0x20: instructionDetail{"add", "%05X %s $%d, $%d, $%d%[8]s"},
	0x22: instructionDetail{"sub", "%05X %s $%d, $%d, $%d%[8]s"},
	0x24: instructionDetail{"and", "%05X %s $%d, $%d, $%d%[8]s"},
	0x25: instructionDetail{"or", "%05X %s $%d, $%d, $%d%[8]s"},
}
func GetInstruction(value uint32) (InstructionFields, error) {
	var fields []uint32
	var err error
	var result InstructionFields
	if fields, err = GetBitSplit(value, 16, 5, 5, 6); err == nil {
		result = InstructionFields{opcode: uint8(fields[3]), rs: uint8(fields[2]), rt: uint8(fields[1]), immediate: int16(fields[0])}
		if fields, err = GetBitSplit(value, 6, 5, 5); err == nil {
			result.funct = uint8(fields[0])
			result.shamt = uint8(fields[1])
			result.rd = uint8(fields[2])
		}
	}
	if result.opcode == 0 {
		result.format = "R"
		result.detail = rFormatDetails[result.funct]
	} else {
		result.format = "I"
		result.detail = iFormatDetails[result.opcode]
	}
	return result, err
}
func (i InstructionFields) ToString(pwc uint32) string {
	return fmt.Sprintf(i.detail.outputFormat, pwc, i.detail.mnemonic, i.rd, i.rs, i.rt, i.immediate, uint32(int(pwc) + int(i.immediate)), "")
}

