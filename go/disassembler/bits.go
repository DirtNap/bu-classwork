package disassembler

import "errors"

func GetBitMask(size int, shift uint8) (mask uint32, err error)  {
	if 0 > size || int(shift) + size > 32 {
		err = errors.New("Invalid mask parameters for 32-bit operation")
	} else {
		for i := 0; i < size; i++ {
			if i > 0 {
				mask <<= 1
			}
			mask |= 1
		}
		mask <<= shift
	}
	return
}

func GetBitRangeValue(value uint32, size int, shift uint8) (rangeValue uint32, err error) {
	if mask, err := GetBitMask(size, shift); err == nil {
		rangeValue = value & mask
		rangeValue >>= shift
	}
	return
}
