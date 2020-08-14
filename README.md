# Assembler_interpreter
simulation of assembler interpreter in operating system - created for Operating Systems Laboratory


Project consists of three classes: VirtualMemory, PCB (Process Control Block), Interpreter.

VirtualMemory class has static byte array where orders are held.
PCV class stores information about: process name, process state, process size, opened files, values in registers, process counter.
Interpreter reads order from virtual memory and executes it. Address of virtual memory cell is based on process counter of running process.
