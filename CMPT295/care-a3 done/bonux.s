	.file	"bonux.c"
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"Original signal:  "
.LC1:
	.string	"%4d"
.LC2:
	.string	"Convolved signal: "
.LC3:
	.string	"   OF"
.LC4:
	.string	" %4d"
	.section	.text.unlikely,"ax",@progbits
.LCOLDB5:
	.section	.text.startup,"ax",@progbits
.LHOTB5:
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
	movl	$1, %ebp
	xorl	%ebx, %ebx
	movl	$5, %r12d
	call	_IO_putc
	movl	$.LC2, %edi
	call	puts
	jmp	.L6
	.p2align 4,,10
	.p2align 3
.L13:
	xorl	%eax, %eax
	movl	$.LC3, %esi
	movl	$1, %edi
	addl	$1, %ebx
	addl	$1, %ebp
	call	__printf_chk
	cmpl	$20, %ebx
	je	.L12
.L6:
	cmpl	$3, %ebx
	movl	%r12d, %edx
	movl	%ebx, %edi
	cmovbe	%ebp, %edx
	movl	$h, %esi
	subl	%edx, %edi
	movslq	%edi, %rdi
	addq	$signal+1, %rdi
	call	conv
#APP
# 29 "bonux.c" 1
	movl %edx, %ecx
	
# 0 "" 2
#NO_APP
	testl	%ecx, %ecx
	jne	.L13
	movsbl	%al, %edx
	movl	$.LC4, %esi
	xorl	%eax, %eax
	movl	$1, %edi
	addl	$1, %ebx
	addl	$1, %ebp
	call	__printf_chk
	cmpl	$20, %ebx
	jne	.L6
.L12:
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
.LCOLDE5:
	.section	.text.startup
.LHOTE5:
	.globl	h
	.data
	.type	h, @object
	.size	h, 5
h:
	.byte	-5
	.byte	2
	.byte	6
	.byte	2
	.byte	-5
	.globl	signal
	.align 16
	.type	signal, @object
	.size	signal, 20
signal:
	.byte	0
	.byte	0
	.byte	0
	.byte	40
	.byte	80
	.byte	60
	.byte	50
	.byte	60
	.byte	70
	.byte	40
	.byte	10
	.byte	-20
	.byte	-50
	.byte	-70
	.byte	-40
	.byte	-20
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits
