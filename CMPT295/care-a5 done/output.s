	.file	"output.c"
	.section	.text.unlikely,"ax",@progbits
.LCOLDB0:
	.text
.LHOTB0:
	.p2align 4,,15
	.globl	bitstr
	.type	bitstr, @function
bitstr:
.LFB23:
	.cfi_startproc
	movslq	%edx, %rcx
	testl	%edx, %edx
	movq	%rsi, %rax
	movb	$0, (%rsi,%rcx)
	leal	-1(%rdx), %ecx
	je	.L6
	movslq	%ecx, %r8
	movl	%ecx, %ecx
	leaq	(%rsi,%r8), %rdx
	subq	$1, %r8
	subq	%rcx, %r8
	addq	%rsi, %r8
	.p2align 4,,10
	.p2align 3
.L3:
	movl	%edi, %ecx
	subq	$1, %rdx
	shrq	%rdi
	andl	$1, %ecx
	addl	$48, %ecx
	movb	%cl, 1(%rdx)
	cmpq	%r8, %rdx
	jne	.L3
.L6:
	rep ret
	.cfi_endproc
.LFE23:
	.size	bitstr, .-bitstr
	.section	.text.unlikely
.LCOLDE0:
	.text
.LHOTE0:
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC1:
	.string	"%8x"
	.section	.text.unlikely
.LCOLDB2:
	.text
.LHOTB2:
	.p2align 4,,15
	.globl	f_printbits
	.type	f_printbits, @function
f_printbits:
.LFB24:
	.cfi_startproc
	movd	%xmm0, %edx
	movl	$.LC1, %esi
	movl	$1, %edi
	xorl	%eax, %eax
	jmp	__printf_chk
	.cfi_endproc
.LFE24:
	.size	f_printbits, .-f_printbits
	.section	.text.unlikely
.LCOLDE2:
	.text
.LHOTE2:
	.section	.rodata.str1.1
.LC3:
	.string	"%e\n"
.LC4:
	.string	"%e = %c 0.%s x 2^-126\n"
.LC5:
	.string	"%e = %c 1.%s x 2^%d\n"
	.section	.text.unlikely
.LCOLDB6:
	.text
.LHOTB6:
	.p2align 4,,15
	.globl	f_print
	.type	f_print, @function
f_print:
.LFB25:
	.cfi_startproc
	movd	%xmm0, %edx
	subq	$40, %rsp
	.cfi_def_cfa_offset 48
	movd	%xmm0, %r8d
	movq	%fs:40, %rax
	movq	%rax, 24(%rsp)
	xorl	%eax, %eax
	shrl	$31, %edx
	movd	%xmm0, %eax
	cmpl	$1, %edx
	sbbl	%edx, %edx
	shrl	$23, %r8d
	andl	$8388607, %eax
	andl	$-2, %edx
	movzbl	%r8b, %r8d
	addl	$45, %edx
	cmpl	$255, %r8d
	je	.L21
	testl	%r8d, %r8d
	jne	.L13
	leaq	22(%rsp), %rsi
	leaq	-1(%rsp), %r8
	movb	$0, 23(%rsp)
	movq	%rsp, %rcx
	.p2align 4,,10
	.p2align 3
.L14:
	movl	%eax, %edi
	subq	$1, %rsi
	shrq	%rax
	andl	$1, %edi
	addl	$48, %edi
	movb	%dil, 1(%rsi)
	cmpq	%rsi, %r8
	jne	.L14
	cvtss2sd	%xmm0, %xmm0
	movsbl	%dl, %edx
	movl	$.LC4, %esi
	movl	$1, %edi
	movl	$1, %eax
	call	__printf_chk
.L9:
	movq	24(%rsp), %rax
	xorq	%fs:40, %rax
	jne	.L22
	addq	$40, %rsp
	.cfi_remember_state
	.cfi_def_cfa_offset 8
	ret
	.p2align 4,,10
	.p2align 3
.L13:
	.cfi_restore_state
	leaq	22(%rsp), %rsi
	leaq	-1(%rsp), %r9
	subl	$127, %r8d
	movb	$0, 23(%rsp)
	movq	%rsp, %rcx
	.p2align 4,,10
	.p2align 3
.L15:
	movl	%eax, %edi
	subq	$1, %rsi
	shrq	%rax
	andl	$1, %edi
	addl	$48, %edi
	movb	%dil, 1(%rsi)
	cmpq	%r9, %rsi
	jne	.L15
	cvtss2sd	%xmm0, %xmm0
	movsbl	%dl, %edx
	movl	$.LC5, %esi
	movl	$1, %edi
	movl	$1, %eax
	call	__printf_chk
	jmp	.L9
.L21:
	cvtss2sd	%xmm0, %xmm0
	movl	$.LC3, %esi
	movl	$1, %edi
	movl	$1, %eax
	call	__printf_chk
	jmp	.L9
.L22:
	call	__stack_chk_fail
	.cfi_endproc
.LFE25:
	.size	f_print, .-f_print
	.section	.text.unlikely
.LCOLDE6:
	.text
.LHOTE6:
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits
