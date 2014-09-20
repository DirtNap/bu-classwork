package disassembler

import (
	"errors"
//	"fmt"
//	"os"
)

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

func GetBitSplit(value uint32, sizes...int) ([]uint32, error) {
	var ranges []uint32 = make([]uint32, len(sizes), len(sizes))
	var shift uint8
	for  i := 0; i < len(sizes); i++ {
		if rangeValue, err := GetBitRangeValue(value, sizes[i], shift); err == nil {
			ranges[i] = rangeValue
		} else {
			return ranges[0:0], err
		}
		shift += uint8(sizes[i])
	}
	return ranges, nil
}
