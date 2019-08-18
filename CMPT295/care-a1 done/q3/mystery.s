	.globl mystery
mystery:
	movl	$0, %eax
	leaq	(%rdi, %rsi), %rcx # Load effective address
#The lea instruction places the address 
#specified by its first operand into the register 
#specified by its second operand. Note, the contents of the
# memory location are not loaded, only the effective address is 
# computed and placed into the register. This is useful for obtaining a
#  pointer into a memory region or to perform simple arithmetic operations.

#lea (%ebx,%esi,8), %edi — the quantity EBX+8*ESI is placed in EDI.

loop:
	cmpq	%rdi, %rcx # if rcx <= rdi, jump endl 
	jle	endl
	decq	%rcx # decrement 
	cmpb	$0x6f, (%rcx) #if (rcx) <= 111,  "jump when not equal" loop 
	jne	loop # jmp if not equal 
	incl	%eax # inrement 
	jmp	loop # jmp

endl:
	ret


