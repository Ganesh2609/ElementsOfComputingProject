load Computer.hdl,

compare-to Computer.cmp;

output-file Computer.out,

output-list time%S1.4.1 reset%B2.1.2 ARegister[0]%D1.7.1 DRegister[0]%D1.7.1 PC[]%D0.4.0 RAM16K[0]%D1.7.1 RAM16K[1]%D1.7.1 RAM16K[2]%D1.7.1 RAM16K[3]%D1.7.1 RAM16K[4]%D1.7.1 RAM16K[256]%D1.7.1;

ROM32K load factorial.hack,

output;

repeat 3021{
    tick, tock, output;
}