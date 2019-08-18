#khizr PARDHAN 301314376 
#output 0 0 0 4 0 -6 1 2 0 -4 0 0 0 1 5 -10 -2 0 0
	.globl conv
conv:
	movl	%edx, %eax #copy edx into eax
	leaq	-1(%rsi,%rax), %rsi # load -1(%rsi,%rax) into %rsi 

	subl	%ecx, %ecx # clear this 64 bit reg
	subl	%r8d, %r8d # clear this 32 bit reg

top: #"of our loop"
	movl	(%rsi), %eax # use movl, not movq 
	imulb	(%rdi,%rcx) # normal signed multiply 
	decq	%rsi 
	incq	%rcx
	addl	%eax, %r8d
	
	cmpl	%edx, %ecx # if <= , jump to top of our loop .... else return %r8d 
	jle top

	jmp end #unneeded?

end: 
	movl	%r8d, %eax # lower 32bits of r8 into eax
	ret