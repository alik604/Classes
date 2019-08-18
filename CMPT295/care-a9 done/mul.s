	.file	"mul.c"
	.section	.rodata.str1.8,"aMS",@progbits,1
	.align 8
.LC0:
	.string	"Incompatible multiplication: %d x %d times a %d x %d\n"
	.section	.text.unlikely,"ax",@progbits
.LCOLDB1:
	.text
.LHOTB1:
	.p2align 4,,15
	.globl	mul
	.type	mul, @function
mul:
.LFB38:
	.cfi_startproc
	pushq	%r15
	.cfi_def_cfa_offset 16
	.cfi_offset 15, -16
	pushq	%r14
	.cfi_def_cfa_offset 24
	.cfi_offset 14, -24
	pushq	%r13
	.cfi_def_cfa_offset 32
	.cfi_offset 13, -32
	pushq	%r12
	.cfi_def_cfa_offset 40
	.cfi_offset 12, -40
	movl	%r9d, %r13d
	pushq	%rbp
	.cfi_def_cfa_offset 48
	.cfi_offset 6, -48
	pushq	%rbx
	.cfi_def_cfa_offset 56
	.cfi_offset 3, -56
	movl	%esi, %r12d
	movl	%edx, %ebx
	subq	$8, %rsp
	.cfi_def_cfa_offset 64
	cmpl	%r8d, %edx
	jne	.L23
	movl	%r9d, %esi
	movq	%rdi, %rbp
	movl	%r12d, %edi
	movq	%rcx, %r15
	call	newMatrix
	movl	%r13d, %esi
	movl	%ebx, %edi
	movq	%rax, %r14
	call	newMatrix
	testl	%ebx, %ebx
	jle	.L4
	leal	-1(%rbx), %edx
	xorl	%r8d, %r8d
	leaq	4(,%rdx,4), %r10
.L6:
	testl	%r13d, %r13d
	jle	.L7
	movq	(%r15,%r8,2), %r9
	xorl	%edx, %edx
	.p2align 4,,10
	.p2align 3
.L5:
	movl	(%r9,%rdx,4), %edi
	movq	(%rax,%rdx,8), %rsi
	addq	$1, %rdx
	cmpl	%edx, %r13d
	movl	%edi, (%rsi,%r8)
	jg	.L5
.L7:
	addq	$4, %r8
	cmpq	%r10, %r8
	jne	.L6
.L4:
	xorl	%r10d, %r10d
	testl	%r12d, %r12d
	jle	.L9
.L16:
	testl	%r13d, %r13d
	jle	.L11
	movq	(%r14,%r10,8), %r11
	xorl	%r9d, %r9d
	.p2align 4,,10
	.p2align 3
.L14:
	testl	%ebx, %ebx
	jle	.L15
	movq	0(%rbp,%r10,8), %r8
	movq	(%rax,%r9,8), %rdx
	xorl	%ecx, %ecx
	xorl	%edi, %edi
	.p2align 4,,10
	.p2align 3
.L10:
	movl	(%r8,%rcx,4), %esi
	imull	(%rdx,%rcx,4), %esi
	addq	$1, %rcx
	addl	%esi, %edi
	cmpl	%ecx, %ebx
	jg	.L10
	movl	%edi, (%r11,%r9,4)
	addq	$1, %r9
	cmpl	%r9d, %r13d
	jg	.L14
.L11:
	addq	$1, %r10
	cmpl	%r10d, %r12d
	jg	.L16
.L9:
	addq	$8, %rsp
	.cfi_remember_state
	.cfi_def_cfa_offset 56
	movq	%r14, %rax
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
	ret
.L15:
	.cfi_restore_state
	xorl	%edi, %edi
	movl	%edi, (%r11,%r9,4)
	addq	$1, %r9
	cmpl	%r9d, %r13d
	jg	.L14
	jmp	.L11
.L23:
	movl	%edx, %ecx
	movl	$1, %edi
	movl	%esi, %edx
	xorl	%eax, %eax
	movl	$.LC0, %esi
	call	__printf_chk
	movl	$-1, %edi
	call	exit
	.cfi_endproc
.LFE38:
	.size	mul, .-mul
	.section	.text.unlikely
.LCOLDE1:
	.text
.LHOTE1:
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits
