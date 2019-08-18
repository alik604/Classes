	.file	"matrix.c"
	.section	.text.unlikely,"ax",@progbits
.LCOLDB0:
	.text
.LHOTB0:
	.p2align 4,,15
	.globl	newMatrix
	.type	newMatrix, @function
newMatrix:
.LFB38:
	.cfi_startproc
	pushq	%r12
	.cfi_def_cfa_offset 16
	.cfi_offset 12, -16
	movslq	%edi, %rdi
	pushq	%rbp
	.cfi_def_cfa_offset 24
	.cfi_offset 6, -24
	pushq	%rbx
	.cfi_def_cfa_offset 32
	.cfi_offset 3, -32
	movq	%rdi, %r12
	salq	$3, %rdi
	movl	%esi, %ebx
	call	malloc
	testq	%rax, %rax
	je	.L6
	movl	%r12d, %edi
	movq	%rax, %rbp
	imull	%ebx, %edi
	movslq	%edi, %rdi
	salq	$2, %rdi
	call	malloc
	testq	%rax, %rax
	je	.L3
	movslq	%ebx, %rsi
	xorl	%edx, %edx
	salq	$2, %rsi
	testl	%r12d, %r12d
	jle	.L9
	.p2align 4,,10
	.p2align 3
.L5:
	movq	%rax, 0(%rbp,%rdx,8)
	addq	$1, %rdx
	addq	%rsi, %rax
	cmpl	%edx, %r12d
	jg	.L5
.L9:
	movq	%rbp, %rax
.L2:
	popq	%rbx
	.cfi_remember_state
	.cfi_def_cfa_offset 24
	popq	%rbp
	.cfi_def_cfa_offset 16
	popq	%r12
	.cfi_def_cfa_offset 8
	ret
.L6:
	.cfi_restore_state
	xorl	%eax, %eax
	jmp	.L2
.L3:
	movq	%rbp, %rdi
	call	free
	xorl	%eax, %eax
	jmp	.L2
	.cfi_endproc
.LFE38:
	.size	newMatrix, .-newMatrix
	.section	.text.unlikely
.LCOLDE0:
	.text
.LHOTE0:
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC1:
	.string	" %3d"
	.section	.text.unlikely
.LCOLDB2:
	.text
.LHOTB2:
	.p2align 4,,15
	.globl	printMatrix
	.type	printMatrix, @function
printMatrix:
.LFB39:
	.cfi_startproc
	testl	%esi, %esi
	pushq	%r14
	.cfi_def_cfa_offset 16
	.cfi_offset 14, -16
	pushq	%r13
	.cfi_def_cfa_offset 24
	.cfi_offset 13, -24
	pushq	%r12
	.cfi_def_cfa_offset 32
	.cfi_offset 12, -32
	pushq	%rbp
	.cfi_def_cfa_offset 40
	.cfi_offset 6, -40
	pushq	%rbx
	.cfi_def_cfa_offset 48
	.cfi_offset 3, -48
	jle	.L13
	leal	-1(%rsi), %eax
	movl	%edx, %r12d
	movq	%rdi, %r14
	leaq	8(%rdi,%rax,8), %r13
	leal	-1(%rdx), %eax
	leaq	4(,%rax,4), %rbp
	.p2align 4,,10
	.p2align 3
.L15:
	xorl	%ebx, %ebx
	testl	%r12d, %r12d
	jle	.L17
	.p2align 4,,10
	.p2align 3
.L18:
	movq	(%r14), %rax
	movl	$.LC1, %esi
	movl	$1, %edi
	movl	(%rax,%rbx), %edx
	xorl	%eax, %eax
	addq	$4, %rbx
	call	__printf_chk
	cmpq	%rbp, %rbx
	jne	.L18
.L17:
	movq	stdout(%rip), %rsi
	movl	$10, %edi
	addq	$8, %r14
	call	_IO_putc
	cmpq	%r13, %r14
	jne	.L15
.L13:
	popq	%rbx
	.cfi_def_cfa_offset 40
	popq	%rbp
	.cfi_def_cfa_offset 32
	popq	%r12
	.cfi_def_cfa_offset 24
	popq	%r13
	.cfi_def_cfa_offset 16
	popq	%r14
	.cfi_def_cfa_offset 8
	movq	stdout(%rip), %rsi
	movl	$10, %edi
	jmp	_IO_putc
	.cfi_endproc
.LFE39:
	.size	printMatrix, .-printMatrix
	.section	.text.unlikely
.LCOLDE2:
	.text
.LHOTE2:
	.section	.text.unlikely
.LCOLDB3:
	.text
.LHOTB3:
	.p2align 4,,15
	.globl	freeMatrix
	.type	freeMatrix, @function
freeMatrix:
.LFB40:
	.cfi_startproc
	pushq	%rbx
	.cfi_def_cfa_offset 16
	.cfi_offset 3, -16
	movq	%rdi, %rbx
	movq	(%rdi), %rdi
	call	free
	movq	%rbx, %rdi
	popq	%rbx
	.cfi_def_cfa_offset 8
	jmp	free
	.cfi_endproc
.LFE40:
	.size	freeMatrix, .-freeMatrix
	.section	.text.unlikely
.LCOLDE3:
	.text
.LHOTE3:
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits
