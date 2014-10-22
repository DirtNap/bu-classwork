package main

import (
	"bufio"
	"fmt"
	"github.com/DirtNap/bu-classwork/go/cache"
	"os"
	"strings"
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
	for response != "X" {
		fmt.Println("(R)ead, (W)rite, or (D)isplay the cache, or e(X)it?")
		input, _ := reader.ReadString('\n')
		response = strings.ToUpper(strings.TrimSpace(input))
		c <- cache.CacheInstruction{Cmd: response}
	}
	close(c)
}
