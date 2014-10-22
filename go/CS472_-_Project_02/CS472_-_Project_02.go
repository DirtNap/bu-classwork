package main

import (
	"bufio"
	"fmt"
	"github.com/DirtNap/bu-classwork/go/cache"
	"os"
	"strings"
	"strconv"
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
		memory[i] = byte(i % 0xFF)
	}
	inputs := make(chan cache.CacheInstruction)
	go getInstructionsFromStdIn(inputs)
	for i := range inputs {
		fmt.Printf("You entered '%s'", i.Cmd)
	}
}

func getInstructionsFromStdIn(c chan cache.CacheInstruction) {
	response := ""
	reader := bufio.NewReader(os.Stdin)
Repl:
	for {
		var address uint16
		fmt.Print("(R)ead, (W)rite, or (D)isplay the cache, or e(X)it? ")
		input, _ := reader.ReadString('\n')
		response = strings.ToUpper(strings.TrimSpace(input))
		switch response {
		case "R", "W":
			fmt.Print("Address:  ")
			input, _ := reader.ReadString('\n')
			num, err := strconv.ParseUint(fmt.Sprintf("0x%s", strings.TrimSpace(input)), 0, 64)
			if err == nil {
				address = uint16(num)
			} else {
				fmt.Printf("Invalid Hexadecimal Number:  %s\n", err)
				continue Repl
			}
		case "D":
			address = 0
		case "X":
			break Repl
		default:
			continue Repl
		}
		c <- cache.CacheInstruction{Cmd: response, Address: address}
	}
	close(c)
}
