	.file	"main.c"
	.text
	.section	.rodata.str1.8,"aMS",@progbits,1
	.align 8
.LC0:
	.string	"It took %ld microseconds to initialize the array.\n"
	.section	.text.startup,"ax",@progbits
	.p2align 4,,15
	.globl	main
	.type	main, @function
main:
.LFB38:
	.cfi_startproc
	pushq	%r13
	.cfi_def_cfa_offset 16
	.cfi_offset 13, -16
	pushq	%r12
	.cfi_def_cfa_offset 24
	.cfi_offset 12, -24
	xorl	%edi, %edi
	pushq	%rbp
	.cfi_def_cfa_offset 32
	.cfi_offset 6, -32
	pushq	%rbx
	.cfi_def_cfa_offset 40
	.cfi_offset 3, -40
	movl	$A+4, %r13d
	movl	$1, %r12d
	movl	$1374389535, %ebp
	subq	$312, %rsp
	.cfi_def_cfa_offset 352
	movq	%fs:40, %rax
	movq	%rax, 296(%rsp)
	xorl	%eax, %eax
	call	time
	movl	%eax, %edi
	call	srand
	movl	$0, A(%rip)
	.p2align 4,,10
	.p2align 3
.L2:
	movl	%r12d, %eax
	addl	$1, %r12d
	addq	$4, %r13
	mull	%ebp
	shrl	$5, %edx
	movl	%edx, -4(%r13)
	call	rand
	movl	%eax, %ebx
	call	rand
	movslq	%ebx, %rcx
	movl	%ebx, %edx
	imulq	$-2147418109, %rcx, %rcx
	sarl	$31, %edx
	shrq	$32, %rcx
	addl	%ebx, %ecx
	sarl	$14, %ecx
	subl	%edx, %ecx
	movl	%ecx, %edx
	sall	$15, %edx
	subl	%ecx, %edx
	movslq	%eax, %rcx
	imulq	$-2147450879, %rcx, %rcx
	subl	%edx, %ebx
	cltd
	sall	$16, %ebx
	shrq	$32, %rcx
	addl	%eax, %ecx
	sarl	$15, %ecx
	subl	%edx, %ecx
	movl	%ecx, %edx
	sall	$16, %edx
	subl	%ecx, %edx
	subl	%edx, %eax
	orl	%ebx, %eax
	cltd
	idivl	%r12d
	movl	-4(%r13), %eax
	movslq	%edx, %rdx
	movl	A(,%rdx,4), %ecx
	movl	%ecx, -4(%r13)
	cmpl	$16000000, %r12d
	movl	%eax, A(,%rdx,4)
	jne	.L2
	xorl	%edi, %edi
	movq	%rsp, %rsi
	call	getrusage
	movl	$16000000, %esi
	movl	$A, %edi
	call	qsort295_2
	leaq	144(%rsp), %rsi
	xorl	%edi, %edi
	call	getrusage
	movq	152(%rsp), %rdx
	subq	8(%rsp), %rdx
	movl	$.LC0, %esi
	movl	$1, %edi
	xorl	%eax, %eax
	call	__printf_chk
	xorl	%edi, %edi
	movq	%rsp, %rsi
	call	getrusage
	movl	$16000000, %esi
	movl	$A, %edi
	call	qsort295_2
	leaq	144(%rsp), %rsi
	xorl	%edi, %edi
	call	getrusage
	movq	152(%rsp), %rdx
	subq	8(%rsp), %rdx
	xorl	%eax, %eax
	movl	$.LC0, %esi
	movl	$1, %edi
	call	__printf_chk
	movq	296(%rsp), %rax
	xorq	%fs:40, %rax
	jne	.L7
	addq	$312, %rsp
	.cfi_remember_state
	.cfi_def_cfa_offset 40
	popq	%rbx
	.cfi_def_cfa_offset 32
	popq	%rbp
	.cfi_def_cfa_offset 24
	popq	%r12
	.cfi_def_cfa_offset 16
	popq	%r13
	.cfi_def_cfa_offset 8
	ret
.L7:
	.cfi_restore_state
	call	__stack_chk_fail
	.cfi_endproc
.LFE38:
	.size	main, .-main
	.comm	A,64000000,32
	.ident	"GCC: (Ubuntu 7.4.0-1ubuntu1~16.04~ppa1) 7.4.0"
	.section	.note.GNU-stack,"",@progbits
