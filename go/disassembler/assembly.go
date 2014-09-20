package disassembler

type Instruction struct {
	opcode uint8
	rs uint8
	rt uint8
	rd uint8
	shamt uint8 // Unused for now.
	funct uint8
	immediate int16
	address uint32 // Unused for now.
	format string
	mnemonic string
}

var IFormatMnemonics = map[uint8]string {
	0x04: "beq",
	0x05: "bne",
	0x23: "lw",
	0x2B: "sw",
}
var RFormatMnemonics = map[uint8]string {
	0x20: "add",
	0x22: "sub",
	0x24: "and",
	0x25: "or",
}
func GetInstruction(value uint32) (Instruction, error) {
	var fields []uint32
	var err error
	fields, err = GetBitSplit(value, 16, 5, 5, 6)
	result := Instruction{format: "I", opcode: uint8(fields[3]), rs: uint8(fields[2]), rt: uint8(fields[1])}
	if err == nil && result.opcode == 0 {
			fields, err = GetBitSplit(value, 6, 5, 5, 5, 5, 6)
		result.format = "R"
	}
	if err == nil {
		switch result.format {
		case "I":
			result.immediate = int16(fields[0])
			result.mnemonic = IFormatMnemonics[result.opcode]
		case "R":
			result.rd = uint8(fields[2])
			result.funct = uint8(fields[0])
			result.mnemonic = RFormatMnemonics[result.funct]
		}
		return result, nil
	} else {
		return result, err
	}
	return result, nil
}
func (i Instruction) ToString() string {
	return i.mnemonic
}
