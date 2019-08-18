	.file	"times.c"
	.text
	.globl	times
	.type	times, @function
times:
.LFB0:
	.cfi_startproc
	movl	%edi, %eax
	imull	%esi, %eax
	ret
	.cfi_endproc
.LFE0:
	.size	times, .-times
	.ident	"GCC: (Ubuntu 7.4.0-1ubuntu1~16.04~ppa1) 7.4.0"
	.section	.note.GNU-stack,"",@progbits
