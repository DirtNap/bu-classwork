package cache

import (
	"bytes"
	"fmt"
)

type Hit bool

type flag_bit bool
type tag uint32
type element byte
type cacheBlock [16]element

type CacheSlot struct {
	Valid    flag_bit
	Dirty    flag_bit
	Tag      tag
	Elements cacheBlock
}

type CacheInstruction struct {
	Cmd     string
	Address uint32
	Data    byte
}

func (h Hit) String() string {
	if h {
		return "hit"
	} else {
		return "miss"
	}
}

func (fb flag_bit) String() string {
	if fb {
		return "1"
	} else {
		return "0"
	}
}

func (e element) String() string {
	return fmt.Sprintf("%02X", byte(e))
}

func (t tag) String() string {
	return fmt.Sprintf("%08X", uint32(t))
}

func (cb cacheBlock) String() string {
	var result bytes.Buffer
	space := ""

	for i := 0; i < len(cb); i++ {
		result.WriteString(space)
		result.WriteString(cb[i].String())
		space = " "
	}
	return result.String()
}

func (cs CacheSlot) String() string {
	return fmt.Sprintf("%s %s %s %s", cs.Valid, cs.Dirty, cs.Tag, cs.Elements)
}
