# CMPT295


## Topics
* Machine language programs
* Representation of symbolic and numeric data
* Representation of instructions (instruction set architecture)
* Machine code optimization
* Basic digital systems
* CPU organization
* Memory organization
* Timing attacks (time permitting)



## CPU Registers

The CPU registers serve as small storage areas used to access data quickly.  In the x86 (32-bit) architecture there are 8 general-purpose registers: EAX, EBX, ECX, EDX, EDI, ESI, EBP, and ESP.  They can technically be used to store any data, though they were originally architected to perform specific tasks, and in many cases are still used that way today.  

[![registers](http://www.securitysift.com/wp-content/uploads/2013/12/registers.png)](http://www.securitysift.com/wp-content/uploads/2013/12/registers.png)

Here’s a bit more detail for each…

**EAX – The Accumulator Register.** 

It’s called the accumulator register because it’s the primary register used for common calculations (such as ADD and SUB).  While other registers can be used for calculations, EAX has been given preferential status by assigning it more efficient, one-byte opcodes.  Such efficiency can be important when it comes to writing exploit shellcode for a limited available buffer space (more on that in future tutorials!).  In addition to its use in calculations, EAX is also used to store the return value of a function. 

This general purpose register can be referenced in whole or in part as follows: EAX refers to the 32-bit register in its entirety. AX refers to the least significant 16 bits which can be further broken down into AH (the 8 most significant bits of AX) and AL (the 8 least significant bits).

Here’s a basic visual representation:

[![EAX](http://www.securitysift.com/wp-content/uploads/2013/12/EAX.png)](http://www.securitysift.com/wp-content/uploads/2013/12/EAX.png)

This same whole/partial 32-, 16-, and 8-bit referencing also applies to the next three registers (EBX, ECX, and EDX)

**EBX – The Base Register.** 

In 32-bit architecture, EBX doesn’t really have a special purpose so just think of it as a catch-all for available storage.  Like EAX, it can be referenced in whole (EBX) or in part (BX, BH, BL).

**ECX – The Counter Register.** 

As its name implies, the counter (or count) register is frequently used as a loop and function repetition counter, though it can also be used to store any data.  Like EAX, it can be referenced in whole (ECX) or in part (CX, CH, CL).

**EDX – The Data Register**

EDX is kind of like a partner register to EAX. It’s often used in mathematical operations like division and multiplication to deal with overflow where the most significant bits would be stored in EDX and the least significant in EAX.  It is also commonly used for storing function variables.  Like EAX, it can be referenced in whole (EDX) or in part (DX, DH, DL).

**ESI – The Source Index**

The counterpart to EDI, ESI is often used to store the pointer to a read location.  For example, if a function is designed to read a string, ESI would hold the pointer to the location of that string.

**EDI – The Destination Index**

Though it can be (and is) used for general data storage, EDI was primarily designed to store the storage pointers of functions, such as the write address of a string operation.

**EBP – The Base Pointer**

EBP is used to keep track of the base/bottom of the stack.  It is often used to reference variables located on the stack by using an offset to the current value of EBP, though if parameters are only referenced by register, you may choose to use EBP for general use purposes.  

**ESP – The Stack Pointer**

ESP is used to track the top of the stack. As items are moved to and from the stack ESP increments/decrements accordingly.  Of all of the general purpose registers, ESP is rarely/never used for anything other than it’s intended purpose. 

**The Instruction Pointer (EIP)**

Not a general purpose register, but fitting to cover here, EIP points to the memory address of the next instruction to be executed by the CPU.  As you’ll see in the coming tutorials, control the value of EIP and you can control the execution flow of the application (to execute code of your choosing).  

**Segment Registers and EFLAGS register**

[![EFLAGS](http://www.securitysift.com/wp-content/uploads/2013/12/EFLAGS.png)](http://www.securitysift.com/wp-content/uploads/2013/12/EFLAGS.png)

There are two additional registers you’ll see in the Register pane, the Segment Register and EFLAGS register.  I won’t cover either in detail but note that the EFLAGS register is comprised of a series of flags that represent Boolean values resulting from calculations and comparisons and can be used to determine when/if to take conditional jumps (more on these later). 

For more on the CPU registers, check out these resources:

- <http://wiki.skullsecurity.org/Registers> 



![Screen Shot 2013-11-30 at 1.35.49 PM](http://www.securitysift.com/wp-content/uploads/2013/12/Screen-Shot-2013-11-30-at-1.35.49-PM.png)

[Source](<http://www.securitysift.com/windows-exploit-development-part-1-basics/>)