C++ threading: https://youtu.be/wXBcwHwIt_I?t=9m24s

Size of array:   int size = my_sizeof(arr)/my_sizeof(arr[0]);

Initializer list

struct S {
    int n = 42;
    S() : n(7) {} // will set n to 7, not 42
};

Virtual function: override like in java. Call function of inheriting class.
	Virtual void attack() = 0; //pure virtual function. Inheriting class must implement.

* Function template: https://www.youtube.com/watch?v=KeKCQt2RqxI

* Class template: https://www.youtube.com/watch?v=QvOd4W2oi3M

* Multi class templates: https://www.youtube.com/watch?v=RxkvqOJUN6U

* POLYMORPHIC TEMPLATES: http://www.newthinktank.com/2018/05/c-tutorial-15-2/

* Threads: http://www.newthinktank.com/2018/05/c-tutorial-16-2/

Function pointer : http://www.stackoverflow.com/questions/840501/how-do-function-pointers-in-c-work


April 4th.
For cpu to operate on a byte of memory, it must be in a CPU register
When request is made to load the byte from memory
Latency for main memory (DRAM) is ~500 “CPU instruction”  (how long it takes for request to deliver )
Bandwidth: how fast stuff moves
-
Cpu register (10s of bytes , ~1 instruction)
L1 cache (10s of KB, ~10 instruction latency)
L2 cache (MBs, ~50 instr)
Main memory (GBs, ~500 instr)


Chunk of cache moving from L2 to L1 is a “cache line”// size ~64bytes


Data must go upward ↑. Cant skip step. Dram to L2,L2 to L1, L1 to reg. (~500 instructions)
1% of cpu is computation; 99% is strange & movement, 30% is cache
