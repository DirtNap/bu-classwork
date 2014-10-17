package cache

import "fmt"

type flag_bit bool
type tag uint32
type element byte

func (fb flag_bit) String() string {
     if fb {
     	return "1"
     } else {
        return "0"
     }
}

func (e element) String() string {
     return fmt.Sprintf("%02X", e)
}

func (t tag) String() string {
     return fmt.Sprintf("%08X", t)
}

type CacheSlot struct {
     Valid     flag_bit
     Dirty     flag_bit
     Tag       tag
     Elements  [16]element
}