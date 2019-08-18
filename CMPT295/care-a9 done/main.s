	.file	"main.c"
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"The trace is %ld.\n"
	.section	.text.unlikely,"ax",@progbits
.LCOLDB1:
	.section	.text.startup,"ax",@progbits
.LHOTB1:
	.p2align 4,,15
	.globl	main
	.type	main, @function
main:
.LFB38:
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
	xorl	%r15d, %r15d
	movl	$1717986919, %r13d
	subq	$8, %rsp
	.cfi_def_cfa_offset 64
	call	time
	movl	%eax, %edi
	call	srand
	movl	$1024, %esi
	movl	$1024, %edi
	call	newMatrix
	movl	$1024, %esi
	movl	$1024, %edi
	movq	%rax, %rbp
	call	newMatrix
	movq	%rax, %r12
	.p2align 4,,10
	.p2align 3
.L2:
	movq	0(%rbp,%r15,2), %rbx
	xorl	%r14d, %r14d
	.p2align 4,,10
	.p2align 3
.L3:
	call	rand
	movl	%eax, %ecx
	addq	%r14, %rbx
	imull	%r13d
	movl	%ecx, %eax
	sarl	$31, %eax
	sarl	$2, %edx
	subl	%eax, %edx
	leal	(%rdx,%rdx,4), %eax
	addl	%eax, %eax
	subl	%eax, %ecx
	movq	(%r12,%r14,2), %rax
	subl	$5, %ecx
	movl	%ecx, (%rbx)
	movq	0(%rbp,%r15,2), %rbx
	movl	(%rbx,%r14), %edx
	addq	$4, %r14
	cmpq	$4096, %r14
	movl	%edx, (%rax,%r15)
	jne	.L3
	addq	$4, %r15
	cmpq	$4096, %r15
	jne	.L2
	movl	$10, %r13d
	xorl	%ebx, %ebx
.L6:
	movl	$1024, %edx
	movl	$1024, %r9d
	movl	$1024, %r8d
	movq	%r12, %rcx
	movl	$1024, %esi
	movq	%rbp, %rdi
	call	mul
	xorl	%edx, %edx
	.p2align 4,,10
	.p2align 3
.L5:
	movq	(%rax,%rdx,2), %rcx
	movslq	(%rcx,%rdx), %rcx
	addq	$4, %rdx
	addq	%rcx, %rbx
	cmpq	$4096, %rdx
	jne	.L5
	movq	%rax, %rdi
	call	freeMatrix
	subl	$1, %r13d
	jne	.L6
	movq	%rbx, %rdx
	movl	$.LC0, %esi
	movl	$1, %edi
	xorl	%eax, %eax
	call	__printf_chk
	movq	%rbp, %rdi
	call	freeMatrix
	addq	$8, %rsp
	.cfi_def_cfa_offset 56
	movq	%r12, %rdi
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
	jmp	freeMatrix
	.cfi_endproc
.LFE38:
	.size	main, .-main
	.section	.text.unlikely
.LCOLDE1:
	.section	.text.startup
.LHOTE1:
	.comm	total,8,8
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits
