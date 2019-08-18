	.file	"conv.c"
	.section	.text.unlikely,"ax",@progbits
.LCOLDB0:
	.text
.LHOTB0:
	.p2align 4,,15
	.globl	conv
	.type	conv, @function
conv:
.LFB0:
	.cfi_startproc
	testl	%edx, %edx
	jle	.L4
	movslq	%edx, %rax
	leaq	-1(%rsi,%rax), %rcx
	leal	-1(%rdx), %eax
	xorl	%edx, %edx
	leaq	1(%rdi,%rax), %rsi
	.p2align 4,,10
	.p2align 3
.L3:
	movzbl	(%rdi), %eax
	addq	$1, %rdi
	subq	$1, %rcx
	mulb	1(%rcx)
	addl	%eax, %edx
	cmpq	%rdi, %rsi
	jne	.L3
.L2:
	movl	%edx, %eax
	ret
.L4:
	xorl	%edx, %edx
	jmp	.L2
	.cfi_endproc
.LFE0:
	.size	conv, .-conv
	.section	.text.unlikely
.LCOLDE0:
	.text
.LHOTE0:
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits
