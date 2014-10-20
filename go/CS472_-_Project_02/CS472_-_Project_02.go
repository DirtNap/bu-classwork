package main
import ("fmt"
	"github.com/DirtNap/bu-classwork/go/cache"
)

var memory [2048]byte
var slots [16]cache.CacheSlot

func Display(cs []cache.CacheSlot) {
	fmt.Println("S# V D T        Data")
	for i := 0; i < len(cs); i++ {
		fmt.Printf("%02d %s\n", i, cs[i])
	}
}

func main() {
	for i := 0; i < len(memory); i++ {
		memory[i] = byte(i % 0xFF);
	}
}

