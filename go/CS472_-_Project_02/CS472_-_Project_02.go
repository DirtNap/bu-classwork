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

func main() {
	simulator := new(cache.CacheSimulation);
	simulator.Init(0, 0)
	inputs := make(chan cache.CacheInstruction)
	readControl := make(chan bool)
	go getInstructionsFromStdIn(inputs, readControl)
	readControl <- true
	for i, ok := <- inputs, true; ok; i, ok = <- inputs {
		fmt.Printf("You entered '%s'\n", i.Cmd)
		switch i.Cmd {
		case "D":
			simulator.Display()
		}

		readControl <- true
	}
	close(readControl)
}

func getInstructionsFromStdIn(c chan cache.CacheInstruction, r chan bool) {
	response := ""
	shouldRead := <- r
	reader := bufio.NewReader(os.Stdin)
Repl:
	for shouldRead {
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
		shouldRead = <- r
	}
	close(c)
}
