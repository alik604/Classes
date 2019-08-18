	pushq %rax
	pushq %rbx
	pushq %rcx
	pushq %rdx
	cpuid 
	rdtscp
	movl %eax, start_time(%rip)
	pop %rdx
	pop %rcx
	pop %rbx
	pop %rax


	call	sum_plus



	pushq %rax
	pushq %rbx
	pushq %rcx
	pushq %rdx
	cpuid 
	rdtscp
	movl %eax, end_time(%rip)
	pop %rdx
	pop %rcx
	pop %rbx
	pop %rax