# Nand2Tetris Game and CPU Development

## Overview

This project implements a complete computer system from NAND gates to high-level programming, following the comprehensive "From NAND to Tetris" (nand2tetris) curriculum. The project demonstrates the construction of a 16-bit computer system including hardware components, virtual machine, compiler, and applications.

## Theory & Background

The project is based on the Hack computer architecture, a simple yet complete 16-bit computer system. The implementation follows a bottom-up approach:

1. **Hardware Layer (Part A)**: Built fundamental logic gates, ALU, memory units, CPU, and complete computer architecture
2. **Software Layer (Part B)**: Implemented virtual machine translator, compiler for Jack language, and operating system
3. **Applications (Part C)**: Developed games and applications running on the complete system

### Hack Computer Architecture

The Hack computer consists of:
- **CPU**: 16-bit processor with A and D registers, program counter
- **Memory**: 32K RAM (16K data memory + 8K screen + 1 keyboard register)
- **Instruction Set**: Two instruction types - A-instructions (addressing) and C-instructions (compute)

## Project Structure

```
nand2tetris-computer-system/
├── Part A - Computer Hardware/
│   ├── Computer files/
│   │   ├── CPU.hdl              # Central Processing Unit
│   │   ├── Memory.hdl           # Complete memory system
│   │   └── Computer.hdl         # Complete computer integration
│   └── Loaded program/
│       ├── factorial.asm        # Assembly factorial program
│       ├── factorial.vm         # VM factorial implementation
│       └── factorial.hack       # Machine code output
├── Part B - Software Stack/
│   ├── Jack Codes/              # High-level language programs
│   │   ├── Main.jack           # Main program entry
│   │   ├── Setup.jack          # Game setup utilities
│   │   ├── Single.jack         # Single player mode
│   │   └── Double.jack         # Two player mode
│   ├── Vm codes/               # Virtual machine code
│   └── Java Codes/             # Reference implementations
├── Part C - Applications/
│   ├── Flappy Bird Game/       # Complete game implementation
│   │   ├── Jack/              # Game source code
│   │   └── vm/                # Compiled VM code
│   └── Rock Paper Scissor/     # Classic game
└── Softwares built/
    ├── VM_Translator.py        # VM to Assembly translator
    └── assembler.java          # Assembly to Machine code
```

## Hardware Components (Part A)

### CPU Implementation
The CPU.hdl implements the Hack CPU with:
- A-register for addressing
- D-register for data storage
- ALU for arithmetic and logic operations
- Program counter for instruction sequencing
- Control logic for instruction decoding

### Memory System
The Memory.hdl provides:
- 16K words of RAM for data storage
- 8K words for screen memory mapping
- 1 word for keyboard input
- Memory-mapped I/O implementation

### Complete Computer
The Computer.hdl integrates:
- CPU for processing
- Memory for storage and I/O
- ROM32K for program storage
- Instruction fetch-decode-execute cycle

## Software Implementation (Part B)

### Jack Programming Language
High-level object-oriented language featuring:
- Classes and methods
- Arrays and strings
- Memory management
- Built-in graphics and I/O libraries

### Virtual Machine
Stack-based virtual machine with:
- Arithmetic and logical operations
- Memory segment access
- Program flow control
- Function calling conventions

### Compilation Process
1. **Jack Compiler**: Translates Jack to VM code
2. **VM Translator**: Converts VM code to Assembly
3. **Assembler**: Generates machine code from Assembly

## Applications (Part C)

### Flappy Bird Game
Complete implementation featuring:
- Sprite animation and graphics
- Physics simulation
- Collision detection
- Score tracking
- Random pipe generation

### Tic-Tac-Toe
Two-player game with:
- Interactive grid interface
- Win condition checking
- Player turn management
- Game state visualization

### Rock Paper Scissors
Classic game including:
- ASCII art graphics
- Multi-round gameplay
- Score tracking
- Input validation

## Usage Instructions

### Hardware Simulation
1. Open Nand2tetris Hardware Simulator
2. Load `Computer.hdl`
3. Load test file `Computer.tst`
4. Run simulation with `factorial.hack` program
5. Observe execution for 3021 clock cycles

### Running Applications
1. Compile Jack files using Jack Compiler
2. Load VM files in VM Emulator
3. Run programs and interact using keyboard/screen
4. Games support keyboard controls as displayed

### Development Tools
- **Hardware Simulator**: For testing HDL components
- **VM Emulator**: For running VM programs
- **Jack Compiler**: For compiling high-level code
- **Assembler**: For assembly to machine code conversion

## Technical Features

- **16-bit Architecture**: Complete instruction set and data path
- **Memory Mapping**: Efficient I/O through memory-mapped registers
- **Stack Machine**: VM implementation with function calling
- **Object-Oriented**: Jack language with classes and inheritance
- **Graphics Support**: Pixel-level screen manipulation
- **Real-time Input**: Keyboard integration for interactive programs

## Learning Outcomes

This project demonstrates:
- Computer architecture from logic gates to complete systems
- Compilation process from high-level language to machine code
- Operating system concepts and implementation
- Game development on custom hardware
- Full-stack computer system design

The implementation showcases the journey from fundamental logic to complex applications, providing deep understanding of how computers work at every abstraction level.