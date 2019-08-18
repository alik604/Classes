	.file	"LL.c"
	.section	.text.unlikely,"ax",@progbits
.LCOLDB0:
	.text
.LHOTB0:
	.p2align 4,,15
	.globl	newLL
	.type	newLL, @function
newLL:
.LFB38:
	.cfi_startproc
	subq	$8, %rsp
	.cfi_def_cfa_offset 16
	movl	$24, %edi
	call	malloc
	testq	%rax, %rax
	je	.L2
	movl	$0, (%rax)
	movq	$0, 8(%rax)
	movq	$0, 16(%rax)
.L2:
	addq	$8, %rsp
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE38:
	.size	newLL, .-newLL
	.section	.text.unlikely
.LCOLDE0:
	.text
.LHOTE0:
	.section	.text.unlikely
.LCOLDB1:
	.text
.LHOTB1:
	.p2align 4,,15
	.globl	freeLL
	.type	freeLL, @function
freeLL:
.LFB39:
	.cfi_startproc
	testq	%rdi, %rdi
	je	.L16
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	pushq	%rbx
	.cfi_def_cfa_offset 24
	.cfi_offset 3, -24
	movq	%rdi, %rbp
	subq	$8, %rsp
	.cfi_def_cfa_offset 32
	movq	8(%rdi), %rdi
	testq	%rdi, %rdi
	je	.L12
	.p2align 4,,10
	.p2align 3
.L13:
	movq	8(%rdi), %rbx
	call	free
	testq	%rbx, %rbx
	movq	%rbx, %rdi
	jne	.L13
.L12:
	addq	$8, %rsp
	.cfi_def_cfa_offset 24
	movq	%rbp, %rdi
	popq	%rbx
	.cfi_restore 3
	.cfi_def_cfa_offset 16
	popq	%rbp
	.cfi_restore 6
	.cfi_def_cfa_offset 8
	jmp	free
	.p2align 4,,10
	.p2align 3
.L16:
	rep ret
	.cfi_endproc
.LFE39:
	.size	freeLL, .-freeLL
	.section	.text.unlikely
.LCOLDE1:
	.text
.LHOTE1:
	.section	.text.unlikely
.LCOLDB2:
	.text
.LHOTB2:
	.p2align 4,,15
	.globl	appendLL
	.type	appendLL, @function
appendLL:
.LFB40:
	.cfi_startproc
	testq	%rdi, %rdi
	je	.L29
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	pushq	%rbx
	.cfi_def_cfa_offset 24
	.cfi_offset 3, -24
	movq	%rdi, %rbx
	movl	$16, %edi
	movl	%esi, %ebp
	subq	$8, %rsp
	.cfi_def_cfa_offset 32
	call	malloc
	testq	%rax, %rax
	je	.L17
	addl	$1, (%rbx)
	cmpq	$0, 8(%rbx)
	movl	%ebp, (%rax)
	movq	$0, 8(%rax)
	movq	16(%rbx), %rdx
	je	.L30
.L22:
	movq	%rax, 8(%rdx)
.L23:
	movq	%rax, 16(%rbx)
.L17:
	addq	$8, %rsp
	.cfi_def_cfa_offset 24
	popq	%rbx
	.cfi_restore 3
	.cfi_def_cfa_offset 16
	popq	%rbp
	.cfi_restore 6
	.cfi_def_cfa_offset 8
.L29:
	rep ret
	.p2align 4,,10
	.p2align 3
.L30:
	.cfi_def_cfa_offset 32
	.cfi_offset 3, -24
	.cfi_offset 6, -16
	testq	%rdx, %rdx
	jne	.L22
	movq	%rax, 8(%rbx)
	jmp	.L23
	.cfi_endproc
.LFE40:
	.size	appendLL, .-appendLL
	.section	.text.unlikely
.LCOLDE2:
	.text
.LHOTE2:
	.section	.text.unlikely
.LCOLDB3:
	.text
.LHOTB3:
	.p2align 4,,15
	.globl	LLsearch
	.type	LLsearch, @function
LLsearch:
.LFB41:
	.cfi_startproc
	movq	8(%rdi), %rdx
	movl	$-1, %eax
	testq	%rdx, %rdx
	je	.L32
	xorl	%eax, %eax
	cmpl	(%rdx), %esi
	jne	.L33
	jmp	.L35
	.p2align 4,,10
	.p2align 3
.L40:
	cmpl	%esi, (%rdx)
	je	.L39
.L33:
	movq	8(%rdx), %rdx
	addl	$1, %eax
	testq	%rdx, %rdx
	jne	.L40
	movl	$-1, %eax
.L32:
	rep ret
	.p2align 4,,10
	.p2align 3
.L39:
	rep ret
.L35:
	rep ret
	.cfi_endproc
.LFE41:
	.size	LLsearch, .-LLsearch
	.section	.text.unlikely
.LCOLDE3:
	.text
.LHOTE3:
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC4:
	.string	" %d"
	.section	.text.unlikely
.LCOLDB5:
	.text
.LHOTB5:
	.p2align 4,,15
	.globl	LLdump
	.type	LLdump, @function
LLdump:
.LFB42:
	.cfi_startproc
	testq	%rdi, %rdi
	je	.L49
	pushq	%rbx
	.cfi_def_cfa_offset 16
	.cfi_offset 3, -16
	movq	8(%rdi), %rbx
	testq	%rbx, %rbx
	je	.L45
	.p2align 4,,10
	.p2align 3
.L46:
	movl	(%rbx), %edx
	xorl	%eax, %eax
	movl	$.LC4, %esi
	movl	$1, %edi
	call	__printf_chk
	movq	8(%rbx), %rbx
	testq	%rbx, %rbx
	jne	.L46
.L45:
	popq	%rbx
	.cfi_restore 3
	.cfi_def_cfa_offset 8
	movq	stdout(%rip), %rsi
	movl	$10, %edi
	jmp	_IO_putc
.L49:
	movq	stdout(%rip), %rsi
	movl	$10, %edi
	jmp	_IO_putc
	.cfi_endproc
.LFE42:
	.size	LLdump, .-LLdump
	.section	.text.unlikely
.LCOLDE5:
	.text
.LHOTE5:
	.section	.text.unlikely
.LCOLDB6:
	.text
.LHOTB6:
	.p2align 4,,15
	.globl	lsearch
	.type	lsearch, @function
lsearch:
.LFB43:
	.cfi_startproc
	testl	%esi, %esi
	movl	$-1, %eax
	jle	.L51
	cmpl	(%rdi), %edx
	je	.L55
	addq	$4, %rdi
	xorl	%eax, %eax
	jmp	.L52
	.p2align 4,,10
	.p2align 3
.L53:
	addq	$4, %rdi
	cmpl	%edx, -4(%rdi)
	je	.L51
.L52:
	addl	$1, %eax
	cmpl	%eax, %esi
	jne	.L53
	movl	$-1, %eax
	ret
.L55:
	xorl	%eax, %eax
.L51:
	rep ret
	.cfi_endproc
.LFE43:
	.size	lsearch, .-lsearch
	.section	.text.unlikely
.LCOLDE6:
	.text
.LHOTE6:
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits
