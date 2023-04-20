DATA SEGMENT
	a DD
	b DD
	aux DD
DATA ENDS
CODE SEGMENT
	in eax
	mov a, eax
	in eax
	mov b, eax
debut_while_1:
	mov eax, 0
	push eax
	mov eax, b
	pop ebx
	sub eax, ebx
	jle faux_gt_1
	mov eax, 1
	jmp sortie_gt_1
faux_gt_1:
	mov eax, 0
sortie_gt_1:
	jz sortie_while_1
	mov eax, a
	push eax
	mov eax, b
	pop ebx
	push ebx
	div ebx, eax
	mul eax, ebx
	pop ebx
	sub ebx, eax
	mov eax, ebx
	mov aux, eax
	mov eax, b
	mov a, eax
	mov eax, aux
	mov b, eax
	jmp debut_while_1
sortie_while_1:
	mov eax, a
	out eax
CODE ENDS
