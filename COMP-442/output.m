	entry	% 
	addi	R3, R0, 1	% 
	sw	96(R1), R3	% 
	addi	R3, R0, 0	% 
	sw	100(R1), R3	% 
	lw	R3, 96(R1)	% *(R1(STACK_POINTER) + 96)
	lw	R12, 100(R1)	% *(R1(STACK_POINTER) + 100)
	or	R3, R3, R12	% (*(R1(STACK_POINTER) + (0 + 96)) or *(R1(STACK_POINTER) + (0 + 100)))
	bz	R3, else_1	% 
	addi	R12, R0, 1	% 
	sw	104(R1), R12	% 
	j	endif_1	% 
else_1	addi	R12, R0, 0	% 
	sw	104(R1), R12	% 
endif_1	nop	% 
	hlt	% 
