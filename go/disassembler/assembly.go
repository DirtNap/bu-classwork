package disassembler

import "fmt"

type InstructionFields struct {
	Opcode uint8
	Rs uint8
	Rt uint8
	Rd uint8
	Shamt uint8 // Unused for now.
	Funct uint8
	Immediate int16
	Address uint32 // Unused for now.
	Format string
	detail instructionDetail
}

type instructionDetail struct {
	mnemonic string
	outputFormat string
}

var iFormatDetails = map[uint8]instructionDetail {
	0x04: instructionDetail{"beq", "%05X %s $%[4]d, $%[5]d, Address %05[7]X%[8]s"},
	0x05: instructionDetail{"bne", "%05X %s $%[4]d,$%[5]d, Address %05[7]X%[8]s"},
	0x20: instructionDetail{"lb", "%05X %s $%[5]d, %[6]d($%[4]d)%[8]s"},
	0x23: instructionDetail{"lw", "%05X %s $%[5]d, %[6]d($%[4]d)%[8]s"},
	0x28: instructionDetail{"sb", "%05X %s $%[5]d, %[6]d($%[4]d)%[8]s"},
	0x2B: instructionDetail{"sw", "%05X %s $%[5]d, %[6]d($%[4]d)%[8]s"},
}
var rFormatDetails = map[uint8]instructionDetail {
	0x00: instructionDetail{"nop", "%05X %s %[8]s"},
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
		result = InstructionFields{Opcode: uint8(fields[3]), Rs: uint8(fields[2]), Rt: uint8(fields[1]), Immediate: int16(fields[0])}
		if fields, err = GetBitSplit(value, 6, 5, 5); err == nil {
			result.Funct = uint8(fields[0])
			result.Shamt = uint8(fields[1])
			result.Rd = uint8(fields[2])
		}
	}
	if result.Opcode == 0 {
		result.Format = "R"
		result.detail = rFormatDetails[result.Funct]
	} else {
		result.Format = "I"
		result.detail = iFormatDetails[result.Opcode]
	}
	return result, err
}
func (i InstructionFields) ToString(pwc uint32) string {
	return fmt.Sprintf(i.detail.outputFormat, pwc, i.detail.mnemonic, i.Rd, i.Rs, i.Rt, i.Immediate, i.GetTargetAddress(pwc), "")
}

func (i InstructionFields) GetTargetAddress(pwc uint32) uint32 {
	base := pwc + 4 // Work from the next instruction
	moveAmount := i.Immediate << 2
	return  uint32(int(base) + int(moveAmount))
}
