package main

import (
	"bufio"
	"flag"
	"fmt"
	"github.com/DirtNap/bu-classwork/go/cache"
	"os"
	"strconv"
	"strings"
)

var memory [2048]byte
var slots [16]cache.CacheSlot
var infile, outfile string

func main() {
	flag.StringVar(&infile, "input-file", "", "File from which to read commands.")
	flag.StringVar(&outfile, "output-file", "", "File in which to save commands.")
	flag.Parse()
	simulator := new(cache.CacheSimulation)
	simulator.Init(0, 0)
	inputs := make(chan cache.CacheInstruction)
	readControl := make(chan bool)
	if infile == "" {
		go getInstructionsFromStdIn(outfile, inputs, readControl)
	} else {
		go getInstructionsFromFile(infile, inputs, readControl)
	}
	readControl <- true
	for i, ok := <-inputs; ok; i, ok = <-inputs {
		switch i.Cmd {
		case "D":
			simulator.Display()
		case "R":
			data, hit := simulator.Read(i.Address)
			fmt.Printf("At that byte there is the value %0X (Cache %s)\n", data, hit)
		case "W":
			hit := simulator.Write(i.Address, i.Data)
			fmt.Printf("Value %0X has been written to address %0X. (Cache %s)\n", i.Data, i.Address, hit)
		}

		readControl <- true
	}
	close(readControl)
}

func getInstructionsFromFile(file string, c chan cache.CacheInstruction, r chan bool) {
	fh, err := os.Open(file)
	if err != nil {
		panic(err)
	}
	scanner := bufio.NewScanner(fh)
	shouldRead := <- r
	var address uint32
	var data byte
	for shouldRead && scanner.Scan() {
		parts := strings.Split(scanner.Text(), "-")
		num, _ :=  strconv.ParseUint(parts[1], 0, 32)
		address = uint32(num)
		num, _ =  strconv.ParseUint(parts[2], 0, 8)
		data = byte(num)
		ci := cache.CacheInstruction{Cmd: parts[0], Address: address, Data: data}
		switch ci.Cmd {
		case "R":
			fmt.Printf("Read from address %X\n", ci.Address)
		case "W":
			fmt.Printf("Write %X to address %X\n", ci.Data, ci.Address)
		case "D":
			fmt.Println("Display Cache")
		}
		c <- ci
		shouldRead = <- r
	}
	close(c)
}
func getInstructionsFromStdIn(file string, c chan cache.CacheInstruction, r chan bool) {
	response := ""
	shouldRead := <-r
	reader := bufio.NewReader(os.Stdin)
	history := make([]cache.CacheInstruction, 0)
	fmt.Printf("file: %s\n", file)
Repl:
	for shouldRead {
		var address uint32
		var data byte
		fmt.Print("(R)ead, (W)rite, or (D)isplay Cache, or e(X)it? ")
		input, _ := reader.ReadString('\n')
		response = strings.ToUpper(strings.TrimSpace(input))
		switch response {
		case "R", "W":
			fmt.Print("What address would you like to use?  ")
			input, _ := reader.ReadString('\n')
			num, err := strconv.ParseUint(fmt.Sprintf("0x%s", strings.TrimSpace(input)), 0, 32)
			if err == nil {
				address = uint32(num)
			} else {
				fmt.Printf("Invalid Hexadecimal Number:  %s\n", err)
				continue Repl
			}
			if response == "W" {
				fmt.Print("What data would you like to write at that address?  ")
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
		ci := cache.CacheInstruction{Cmd: response, Address: address, Data: data}
		c <- ci
		history = append(history, ci)
		shouldRead = <-r
	}
	close(c)
	if file != "" {
		saveToFile(file, history)
	}
}

func saveToFile(file string, history []cache.CacheInstruction) {
	f, err := os.Create(file)
	defer f.Close()
	if (err == nil) {
		fmt.Println("Saving file.")
		w := bufio.NewWriter(f)
		for i := 0; i < len(history); i++ {
			w.WriteString(fmt.Sprintf("%s\n", history[i]))
		}
		w.Flush()
	} else {
		fmt.Println("Error")
	}
}
