package cache

import (
	"fmt"
	"github.com/DirtNap/bu-classwork/go/disassembler"
	"math"
)

type CacheSimulation struct {
	memory []byte
	slots  []CacheSlot
}

type decodedAddress struct {
	address           uint32
	tag               uint32
	slot              int
	offset            int
	blockStartAddress int
	blockEndAddress   int
}

func getBitSizes(slots int) (tag int, slot int, offset int) {
	offset = 4
	slot = int(math.Log2(float64(slots)))
	tag = 32 - slot - offset
	return
}

func (cs *CacheSimulation) decodeAddress(address uint32) (addressInfo decodedAddress) {
	addressInfo.address = address
	tagBitSize, slotBitSize, offsetBitSize := getBitSizes(len(cs.slots))
	offsetMask, _ := disassembler.GetBitMask(offsetBitSize, 0)
	addressInfo.blockStartAddress = int(addressInfo.address &^ offsetMask)
	addressInfo.blockEndAddress = int(addressInfo.address | offsetMask)
	parts, _ := disassembler.GetBitSplit(addressInfo.address, offsetBitSize, slotBitSize, tagBitSize)
	addressInfo.tag = parts[2]
	addressInfo.slot = int(parts[1])
	addressInfo.offset = int(parts[0])
	fmt.Println(addressInfo)
	return
}

func (cs *CacheSimulation) encodeAddress(tag uint32, slot int, offset int) (addressInfo decodedAddress) {
	var address uint32
	_, slotSize, offsetSize := getBitSizes(len(cs.slots))
	address = uint32(offset) | uint32(slot << uint(offsetSize)) | uint32(tag << uint(slotSize + offsetSize))
	addressInfo =  cs.decodeAddress(address)
	return
}

func (cs *CacheSimulation) writeBack(da decodedAddress) {
	for i := 0; i < len(cs.slots[da.slot].Elements); i++ {
		cs.memory[da.blockStartAddress+i] = byte(cs.slots[da.slot].Elements[i])
	}
}

func (cs *CacheSimulation) loadInCache(da decodedAddress) bool {
	if cs.slots[da.slot].Valid {
		if cs.slots[da.slot].Tag == tag(da.tag) {
			return true
		} else {
			if cs.slots[da.slot].Dirty {
				wda := cs.encodeAddress(uint32(cs.slots[da.slot].Tag), da.slot, da.offset)
				cs.writeBack(wda)
				cs.slots[da.slot].Dirty = false
			}
		}
	}
	for i := 0; i < len(cs.slots[da.slot].Elements); i++ {
		cs.slots[da.slot].Elements[i] = element(cs.memory[da.blockStartAddress+i])
	}
	cs.slots[da.slot].Valid = true
	cs.slots[da.slot].Tag = tag(da.tag)
	return false
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

func (cs *CacheSimulation) Read(address uint32) (data byte, hit bool) {
	da := cs.decodeAddress(address)
	hit = cs.loadInCache(da)
	data = byte(cs.slots[da.slot].Elements[da.offset])
	return
}

func (cs *CacheSimulation) Write(address uint32, data byte) (hit bool) {
	da := cs.decodeAddress(address)
	hit = cs.loadInCache(da)
	cs.slots[da.slot].Elements[da.offset] = element(data)
	cs.slots[da.slot].Dirty = true
	return
}
