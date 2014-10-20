package cache

import ("fmt"
	"bytes")

type flag_bit bool
type tag uint32
type element byte
type cacheBlock [16]element

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

type CacheSlot struct {
	Valid     flag_bit
	Dirty     flag_bit
	Tag       tag
	Elements  cacheBlock
}

func (cs CacheSlot) String() string {
	return fmt.Sprintf("%s %s %s %s", cs.Valid, cs.Dirty, cs.Tag, cs.Elements)
}
