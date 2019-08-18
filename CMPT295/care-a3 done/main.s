	.file	"main.c"
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"Original signal:  "
.LC1:
	.string	"%4d"
.LC2:
	.string	"Convolved signal: "
	.section	.text.unlikely,"ax",@progbits
.LCOLDB3:
	.section	.text.startup,"ax",@progbits
.LHOTB3:
	.p2align 4,,15
	.globl	main
	.type	main, @function
main:
.LFB23:
	.cfi_startproc
	pushq	%r12
	.cfi_def_cfa_offset 16
	.cfi_offset 12, -16
	pushq	%rbp
	.cfi_def_cfa_offset 24
	.cfi_offset 6, -24
	movl	$.LC0, %edi
	pushq	%rbx
	.cfi_def_cfa_offset 32
	.cfi_offset 3, -32
	movl	$signal, %ebx
	call	puts
	.p2align 4,,10
	.p2align 3
.L2:
	movsbl	(%rbx), %edx
	xorl	%eax, %eax
	movl	$.LC1, %esi
	movl	$1, %edi
	addq	$1, %rbx
	call	__printf_chk
	cmpq	$signal+20, %rbx
	jne	.L2
	movq	stdout(%rip), %rsi
	movl	$10, %edi
	movl	$1, %r12d
	xorl	%ebx, %ebx
	movl	$3, %ebp
	call	_IO_putc
	movl	$.LC2, %edi
	call	puts
	.p2align 4,,10
	.p2align 3
.L4:
	cmpl	$1, %ebx
	movl	%ebp, %edx
	movl	%ebx, %edi
	cmovbe	%r12d, %edx
	movl	$h, %esi
	addl	$1, %ebx
	subl	%edx, %edi
	addl	$1, %r12d
	movslq	%edi, %rdi
	addq	$signal+1, %rdi
	call	conv
	movl	$.LC1, %esi
	movsbl	%al, %edx
	movl	$1, %edi
	xorl	%eax, %eax
	call	__printf_chk
	cmpl	$20, %ebx
	jne	.L4
	popq	%rbx
	.cfi_def_cfa_offset 24
	popq	%rbp
	.cfi_def_cfa_offset 16
	popq	%r12
	.cfi_def_cfa_offset 8
	movq	stdout(%rip), %rsi
	movl	$10, %edi
	jmp	_IO_putc
	.cfi_endproc
.LFE23:
	.size	main, .-main
	.section	.text.unlikely
.LCOLDE3:
	.section	.text.startup
.LHOTE3:
	.globl	h
	.data
	.type	h, @object
	.size	h, 3
h:
	.byte	1
	.byte	-2
	.byte	1
	.globl	signal
	.align 16
	.type	signal, @object
	.size	signal, 20
signal:
	.byte	0
	.byte	0
	.byte	0
	.byte	4
	.byte	8
	.byte	6
	.byte	5
	.byte	6
	.byte	7
	.byte	4
	.byte	1
	.byte	-2
	.byte	-5
	.byte	-7
	.byte	-4
	.byte	-2
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits
