	.file	"main.c"
	.text
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	" n= %d:%d\n"
	.section	.text.startup,"ax",@progbits
	.p2align 4,,15
	.globl	main
	.type	main, @function
main:
.LFB23:
	.cfi_startproc
	pushq	%rbx
	.cfi_def_cfa_offset 16
	.cfi_offset 3, -16
	xorl	%ebx, %ebx
	.p2align 4,,10
	.p2align 3
.L2:
	movq	str(%rip), %rdi
	movl	%ebx, %esi
	call	mystery
	movl	%ebx, %edx
	movl	%eax, %ecx
	movl	$.LC0, %esi
	xorl	%eax, %eax
	movl	$1, %edi
	addl	$1, %ebx
	call	__printf_chk
	cmpl	$16, %ebx
	jne	.L2
	popq	%rbx
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE23:
	.size	main, .-main
	.globl	str
	.section	.rodata.str1.1
.LC1:
	.string	"Good mood food."
	.data
	.align 8
	.type	str, @object
	.size	str, 8
str:
	.quad	.LC1
	.ident	"GCC: (Ubuntu 7.4.0-1ubuntu1~16.04~ppa1) 7.4.0"
	.section	.note.GNU-stack,"",@progbits
