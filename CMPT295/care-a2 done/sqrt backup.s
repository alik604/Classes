	.globl sqrt
sqrt:
		movl $0, %eax # our unsigned 32bit quant ## rax???????
		mov $15, %rcx # decrement variable 
		mov $0x8000, %rdx# mask = 65536, or 2^16 

		jmp loop

utilFunc: 
	xor %rdx,%rax  # change the kth bit back to 0
	dec %rcx  
	shr $1, %rdx #1 step to the right 
	jmp loop #?????????????????/ 


loop:
	
	or %rdx,%rax #geurntee 1 in kth position 
	movq %rax,%r11 #copy rax to r11 
	imulq %rax,%r11 # r11 is rax^2  #??????????????????????

	cmpq %r11,%rdi #edi will contain arg x 
	jl utilFunc # need to exe 2 instructions to (sadly) had to use a util func 

	dec %rcx # check if func is needed ??????????
	shr $1, %rdx #1 step to the right 





	cmpq $0,%rcx
	jge loop

	

	jmp endloop


endloop:

	ret
