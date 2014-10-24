package main

import (
	"bufio"
	"fmt"
	"github.com/DirtNap/bu-classwork/go/cache"
	"os"
	"strconv"
	"strings"
)

var memory [2048]byte
var slots [16]cache.CacheSlot

func main() {
	simulator := new(cache.CacheSimulation)
	simulator.Init(0, 0)
	inputs := make(chan cache.CacheInstruction)
	readControl := make(chan bool)
	go getInstructionsFromStdIn(inputs, readControl)
	readControl <- true
	for i, ok := <-inputs, true; ok; i, ok = <-inputs {
		switch i.Cmd {
		case "D":
			simulator.Display()
		case "R":
			data, hit := simulator.Read(i.Address)
			hitDesc := "miss"
			if hit {
				hitDesc = "hit"
			}
			fmt.Printf("%0X (Cache %s)\n", data, hitDesc)
		case "W":
			hit := simulator.Write(i.Address, i.Data)
			hitDesc := "miss"
			if hit {
				hitDesc = "hit"
			}
			fmt.Printf("(Cache %s)\n", hitDesc)
		}

		readControl <- true
	}
	close(readControl)
}

func getInstructionsFromStdIn(c chan cache.CacheInstruction, r chan bool) {
	response := ""
	shouldRead := <-r
	reader := bufio.NewReader(os.Stdin)
Repl:
	for shouldRead {
		var address uint32
		var data byte
		fmt.Print("(R)ead, (W)rite, or (D)isplay the cache, or e(X)it? ")
		input, _ := reader.ReadString('\n')
		response = strings.ToUpper(strings.TrimSpace(input))
		switch response {
		case "R", "W":
			fmt.Print("Address:  ")
			input, _ := reader.ReadString('\n')
			num, err := strconv.ParseUint(fmt.Sprintf("0x%s", strings.TrimSpace(input)), 0, 32)
			if err == nil {
				address = uint32(num)
			} else {
				fmt.Printf("Invalid Hexadecimal Number:  %s\n", err)
				continue Repl
			}
			if response == "W" {
				fmt.Print("Data to write:  ")
				input, _ := reader.ReadString('\n')
				num, err := strconv.ParseUint(fmt.Sprintf("0x%s", strings.TrimSpace(input)), 0, 8)
				if err == nil {
					data = byte(num)
				} else {
					fmt.Printf("Invalid Hexadecimal Number:  %s\n", err)
					continue Repl
				}
			}
		case "D":
			address = 0
		case "X":
			break Repl
		default:
			continue Repl
		}
		c <- cache.CacheInstruction{Cmd: response, Address: address, Data: data}
		shouldRead = <-r
	}
	close(c)
}
