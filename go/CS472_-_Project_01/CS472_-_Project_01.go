package main

import (
	"fmt"
	"os"
	"strconv"
	"github.com/DirtNap/bu-classwork/go/disassembler"
)

func getInstructions() (instructions []uint32) {
	if len(os.Args) > 1 {
		instructions = make([]uint32, 0, len(os.Args[1:]))
		for i := 1; i < len(os.Args); i++ {
			intVal, err := strconv.ParseUint(os.Args[i], 0, 32)
			if err == nil {
				instructions = append(instructions, uint32(intVal))
			} else {
				fmt.Fprintf(os.Stderr, "%s\n", err.Error())
			}
		}
	} else {
		instructions = []uint32{
			0x022DA822, 0x8EF30018, 0x12A70004, 0x02689820, 0xAD930018, 0x02697824,
			0xAD8FFFF4, 0x018C6020, 0x02A4A825, 0x158FFFF6, 0x8E59FFF0 }
	}
	return
}

func main() {
	instructions := getInstructions()
	for i := 0; i < len(instructions); i++ {
		fmt.Printf("0x%08X %032b", instructions[i], instructions[i])
		if ranges, err := disassembler.GetBitSplit(instructions[i], 6, 5, 5, 5, 5, 6); err == nil {
			var num uint32
			var t string
			if ranges[5] == 0 {
				num = ranges[0]
				t = "R"
			} else {
				num = ranges[5]
				t = "I"
			}
			fmt.Printf(" %s %02X %02d\n", t, num, num)
		}
	}
}
