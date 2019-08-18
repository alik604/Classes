	.file	"LL.c"
	.text
	.p2align 4,,15
	.globl	newLL
	.type	newLL, @function
newLL:
.LFB41:
	.cfi_startproc
	subq	$8, %rsp
	.cfi_def_cfa_offset 16
	movl	$24, %edi
	call	malloc@PLT
	testq	%rax, %rax
	je	.L1
	movl	$0, (%rax)
	movq	$0, 8(%rax)
	movq	$0, 16(%rax)
.L1:
	addq	$8, %rsp
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE41:
	.size	newLL, .-newLL
	.p2align 4,,15
	.globl	freeLL
	.type	freeLL, @function
freeLL:
.LFB42:
	.cfi_startproc
	testq	%rdi, %rdi
	je	.L8
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
	je	.L10
	.p2align 4,,10
	.p2align 3
.L11:
	movq	8(%rdi), %rbx
	call	free@PLT
	testq	%rbx, %rbx
	movq	%rbx, %rdi
	jne	.L11
.L10:
	addq	$8, %rsp
	.cfi_def_cfa_offset 24
	movq	%rbp, %rdi
	popq	%rbx
	.cfi_restore 3
	.cfi_def_cfa_offset 16
	popq	%rbp
	.cfi_restore 6
	.cfi_def_cfa_offset 8
	jmp	free@PLT
	.p2align 4,,10
	.p2align 3
.L8:
	rep ret
	.cfi_endproc
.LFE42:
	.size	freeLL, .-freeLL
	.p2align 4,,15
	.globl	appendLL
	.type	appendLL, @function
appendLL:
.LFB43:
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
	call	malloc@PLT
	testq	%rax, %rax
	je	.L19
	addl	$1, (%rbx)
	cmpq	$0, 8(%rbx)
	movl	%ebp, (%rax)
	movq	$0, 8(%rax)
	movq	16(%rbx), %rdx
	je	.L32
.L23:
	movq	%rax, 8(%rdx)
.L24:
	movq	%rax, 16(%rbx)
.L19:
	addq	$8, %rsp
	.cfi_def_cfa_offset 24
	popq	%rbx
	.cfi_def_cfa_offset 16
	popq	%rbp
	.cfi_def_cfa_offset 8
	ret
	.p2align 4,,10
	.p2align 3
.L29:
	.cfi_restore 3
	.cfi_restore 6
	rep ret
	.p2align 4,,10
	.p2align 3
.L32:
	.cfi_def_cfa_offset 32
	.cfi_offset 3, -24
	.cfi_offset 6, -16
	testq	%rdx, %rdx
	jne	.L23
	movq	%rax, 8(%rbx)
	jmp	.L24
	.cfi_endproc
.LFE43:
	.size	appendLL, .-appendLL
	.p2align 4,,15
	.globl	LLsearch
	.type	LLsearch, @function
LLsearch:
.LFB44:
	.cfi_startproc
	movq	8(%rdi), %rdx
	movl	$-1, %eax
	testq	%rdx, %rdx
	je	.L33
	xorl	%eax, %eax
	cmpl	(%rdx), %esi
	jne	.L35
	jmp	.L37
	.p2align 4,,10
	.p2align 3
.L42:
	cmpl	%esi, (%rdx)
	je	.L41
.L35:
	movq	8(%rdx), %rdx
	addl	$1, %eax
	testq	%rdx, %rdx
	jne	.L42
	movl	$-1, %eax
.L33:
	rep ret
	.p2align 4,,10
	.p2align 3
.L41:
	rep ret
	.p2align 4,,10
	.p2align 3
.L37:
	rep ret
	.cfi_endproc
.LFE44:
	.size	LLsearch, .-LLsearch
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	" %d"
	.text
	.p2align 4,,15
	.globl	LLdump
	.type	LLdump, @function
LLdump:
.LFB45:
	.cfi_startproc
	testq	%rdi, %rdi
	je	.L53
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	pushq	%rbx
	.cfi_def_cfa_offset 24
	.cfi_offset 3, -24
	subq	$8, %rsp
	.cfi_def_cfa_offset 32
	movq	8(%rdi), %rbx
	testq	%rbx, %rbx
	je	.L45
	leaq	.LC0(%rip), %rbp
	.p2align 4,,10
	.p2align 3
.L46:
	movl	(%rbx), %edx
	xorl	%eax, %eax
	movq	%rbp, %rsi
	movl	$1, %edi
	call	__printf_chk@PLT
	movq	8(%rbx), %rbx
	testq	%rbx, %rbx
	jne	.L46
.L45:
	movq	stdout(%rip), %rsi
	addq	$8, %rsp
	.cfi_def_cfa_offset 24
	movl	$10, %edi
	popq	%rbx
	.cfi_restore 3
	.cfi_def_cfa_offset 16
	popq	%rbp
	.cfi_restore 6
	.cfi_def_cfa_offset 8
	jmp	_IO_putc@PLT
	.p2align 4,,10
	.p2align 3
.L53:
	movq	stdout(%rip), %rsi
	movl	$10, %edi
	jmp	_IO_putc@PLT
	.cfi_endproc
.LFE45:
	.size	LLdump, .-LLdump
	.p2align 4,,15
	.globl	lsearch
	.type	lsearch, @function
lsearch:
.LFB46:
	.cfi_startproc
	testl	%esi, %esi
	movl	$-1, %eax
	jle	.L54
	cmpl	(%rdi), %edx
	je	.L59
	subl	$1, %esi
	movl	$1, %ecx
	addq	$1, %rsi
	jmp	.L56
	.p2align 4,,10
	.p2align 3
.L57:
	addq	$1, %rcx
	movl -8(%rdi, %rcx, 4), %r8d #<--- add this line and recompile <---
	cmpl	%edx, -4(%rdi,%rcx,4)
	je	.L54
.L56:
	cmpq	%rsi, %rcx
	movl	%ecx, %eax
	jne	.L57
	movl	$-1, %eax
	ret
	.p2align 4,,10
	.p2align 3
.L59:
	xorl	%eax, %eax
.L54:
	rep ret
	.cfi_endproc
.LFE46:
	.size	lsearch, .-lsearch
	.ident	"GCC: (Ubuntu 7.3.0-27ubuntu1~18.04) 7.3.0"
	.section	.note.GNU-stack,"",@progbits
