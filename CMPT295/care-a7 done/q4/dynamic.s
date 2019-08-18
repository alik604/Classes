	.file	"dynamic.c"
	.text
	.globl	new_42
	.type	new_42, @function
new_42:
.LFB0:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movq	$42, x.1833(%rip)
	movq	$x.1833, -8(%rbp)
	movq	-8(%rbp), %rax
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	new_42, .-new_42
	.globl	N
	.data
	.align 8
	.type	N, @object
	.size	N, 8
N:
	.quad	10
	.text
	.globl	nnn
	.type	nnn, @function
nnn:
.LFB1:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	nop
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1:
	.size	nnn, .-nnn
	.globl	donotmuchofanything
	.type	donotmuchofanything, @function
donotmuchofanything:
.LFB2:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$8, %rsp
	movq	%rdi, -8(%rbp)
	movl	$0, %eax
	call	nnn
	nop
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE2:
	.size	donotmuchofanything, .-donotmuchofanything
	.local	x.1833
	.comm	x.1833,8,8
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits
