package cache

import (
	"fmt"
	"github.com/DirtNap/bu-classwork/go/disassembler"
)

type CacheSimulation struct {
	memory []byte
	slots  []CacheSlot
}

func (cs *CacheSimulation) Init(memBytes int, cacheSlots int) {
	if memBytes == 0 {
		memBytes = 2048
	}
	if cacheSlots == 0 {
		cacheSlots = 16
	}
	cs.memory = make([]byte, memBytes)
	mask, _ := disassembler.GetBitMask(8, 0)
	for i := 0; i < memBytes; i++ {
		cs.memory[i] = byte(uint32(i) & mask)
	}
	cs.slots = make([]CacheSlot, cacheSlots)
}

func (cs *CacheSimulation) Display() {
	fmt.Println("S# V D T        Data")
	for i := 0; i < len(cs.slots); i++ {
		fmt.Printf("%02d %s\n", i, cs.slots[i])
	}

}
