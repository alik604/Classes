	.file	"main.c"
	.text
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"Average of %ld cycles.\n"
	.section	.text.startup,"ax",@progbits
	.p2align 4,,15
	.globl	main
	.type	main, @function
main:
.LFB41:
	.cfi_startproc
	pushq	%r15
	.cfi_def_cfa_offset 16
	.cfi_offset 15, -16
	pushq	%r14
	.cfi_def_cfa_offset 24
	.cfi_offset 14, -24
	xorl	%edi, %edi
	pushq	%r13
	.cfi_def_cfa_offset 32
	.cfi_offset 13, -32
	pushq	%r12
	.cfi_def_cfa_offset 40
	.cfi_offset 12, -40
	xorl	%eax, %eax
	pushq	%rbp
	.cfi_def_cfa_offset 48
	.cfi_offset 6, -48
	pushq	%rbx
	.cfi_def_cfa_offset 56
	.cfi_offset 3, -56
	leaq	A(%rip), %rbx
	xorl	%r12d, %r12d
	subq	$8, %rsp
	.cfi_def_cfa_offset 64
	movq	%rbx, %r14
	movq	%rbx, %r13
	call	time@PLT
	movl	%eax, %edi
	call	srand@PLT
	call	newLL@PLT
	movq	%rax, %rbp
	.p2align 4,,10
	.p2align 3
.L2:
	leal	(%r12,%r12), %eax
	addl	$1, %r12d
	addq	$4, %r13
	movl	%eax, -4(%r13)
	call	rand@PLT
	movl	%eax, %r15d
	call	rand@PLT
	movslq	%r15d, %rcx
	movl	%r15d, %edx
	imulq	$-2147418109, %rcx, %rcx
	sarl	$31, %edx
	shrq	$32, %rcx
	addl	%r15d, %ecx
	sarl	$14, %ecx
	subl	%edx, %ecx
	movl	%ecx, %edx
	sall	$15, %edx
	subl	%ecx, %edx
	movslq	%eax, %rcx
	imulq	$-2147450879, %rcx, %rcx
	subl	%edx, %r15d
	cltd
	sall	$16, %r15d
	shrq	$32, %rcx
	addl	%eax, %ecx
	sarl	$15, %ecx
	subl	%edx, %ecx
	movl	%ecx, %edx
	sall	$16, %edx
	subl	%ecx, %edx
	subl	%edx, %eax
	orl	%r15d, %eax
	cltd
	idivl	%r12d
	movl	-4(%r13), %eax
	movslq	%edx, %rdx
	movl	(%r14,%rdx,4), %ecx
	movl	%ecx, -4(%r13)
	cmpl	$200, %r12d
	movl	%eax, (%r14,%rdx,4)
	jne	.L2
	leaq	800+A(%rip), %r12
	.p2align 4,,10
	.p2align 3
.L3:
	movl	(%rbx), %esi
	movq	%rbp, %rdi
	addq	$4, %rbx
	call	appendLL@PLT
	cmpq	%r12, %rbx
	jne	.L3
	leaq	Q(%rip), %r14
	leaq	cycles(%rip), %r13
	xorl	%r12d, %r12d
	.p2align 4,,10
	.p2align 3
.L4:
#APP
# 44 "main.c" 1
	cpuid
	rdtscp
	movl %eax, %esi
	
# 0 "" 2
#NO_APP
	movl	%esi, start_time(%rip)
	movq	%rbp, %rdi
	movl	%r12d, %esi
	call	LLsearch@PLT
	movl	%eax, (%r14,%r12,4)
#APP
# 56 "main.c" 1
	cpuid
	rdtscp
	movl %eax, %esi
	
# 0 "" 2
#NO_APP
	movl	%esi, end_time(%rip)
	subl	start_time(%rip), %esi
	movl	%esi, 0(%r13,%r12,4)
	addq	$1, %r12
	cmpq	$400, %r12
	jne	.L4
	leaq	cycles(%rip), %rax
	xorl	%ecx, %ecx
	leaq	1600(%rax), %rsi
	.p2align 4,,10
	.p2align 3
.L5:
	movslq	(%rax), %rdx
	addq	$4, %rax
	addq	%rdx, %rcx
	cmpq	%rax, %rsi
	jne	.L5
	movq	%rcx, %rax
	movabsq	$-6640827866535438581, %rdx
	movq	%rcx, total(%rip)
	imulq	%rdx
	leaq	.LC0(%rip), %rsi
	movl	$1, %edi
	xorl	%eax, %eax
	addq	%rcx, %rdx
	sarq	$63, %rcx
	sarq	$8, %rdx
	subq	%rcx, %rdx
	call	__printf_chk@PLT
	addq	$8, %rsp
	.cfi_def_cfa_offset 56
	movq	%rbp, %rdi
	popq	%rbx
	.cfi_def_cfa_offset 48
	popq	%rbp
	.cfi_def_cfa_offset 40
	popq	%r12
	.cfi_def_cfa_offset 32
	popq	%r13
	.cfi_def_cfa_offset 24
	popq	%r14
	.cfi_def_cfa_offset 16
	popq	%r15
	.cfi_def_cfa_offset 8
	jmp	freeLL@PLT
	.cfi_endproc
.LFE41:
	.size	main, .-main
	.globl	end_time
	.data
	.align 4
	.type	end_time, @object
	.size	end_time, 4
end_time:
	.long	125
	.globl	start_time
	.align 4
	.type	start_time, @object
	.size	start_time, 4
start_time:
	.long	150
	.comm	total,8,8
	.comm	cycles,1600,32
	.comm	Q,1600,32
	.comm	P,1600,32
	.comm	A,800,32
	.ident	"GCC: (Ubuntu 7.3.0-27ubuntu1~18.04) 7.3.0"
	.section	.note.GNU-stack,"",@progbits
