# Define the VM translator function
def translate_vm(commands):
# Define a dictionary to map VM segment names to their corresponding memory addresses
    segment_map = {
        "argument": "ARG",
        "local": "LCL",
        "static": "16",
        "this": "THIS",
        "that": "THAT",
        "temp": "5",
        "SP": "256"
    }
name="input"

# Open the input file and read the lines
f = open('F:\Ganesh\Amrita\Subjects\Sem 2\EOC- 2\Project\Part B\Trial 2\Setup.vm', 'r') 
lines = f.readlines()
print(lines)

#White spaces and comments identification and deletion
lines=[s.replace('\n','') for s in lines]
lines=[s.replace('\t','') for s in lines]
lines=[s.split("\\")[0] for s in lines]
lines=[s.split("//")[0] for s in lines]
lines=[s.strip() for s in lines]
lines = [x for x in lines if x != '']
lines=[s.split(" ") for s in lines]
print(lines)
assembly=[]
output_lines= []
print(output_lines)

# call implentation
def call(function_name, num_args):
    global output_line
    # Push return address label
    return_address = "RETURN_ADDRESS"  # Use a translator-generated label
    assembly = ["@" + return_address, "D=A", "@SP", "A=M", "M=D", "@SP", "M=M+1"]

    # Push LCL, ARG, THIS, and THAT of the caller
    segment_pointers = ["LCL", "ARG", "THIS", "THAT"]
    for pointer in segment_pointers:
        assembly.extend(["@" + pointer, "D=M", "@SP", "A=M", "M=D", "@SP", "M=M+1"])

    # Reposition ARG
    assembly.extend(["@SP", "D=M", "@5", "D=D-A", "@" + str(num_args), "D=D-A", "@ARG", "M=D"])

    # Reposition LCL
    assembly.extend(["@SP", "D=M", "@LCL", "M=D"])

    # Transfer control to the called function
    assembly.extend(["@" + function_name, "0;JMP"])

    # Return address label
    assembly.append("(" + return_address + ")")
    output_line.extend(assembly)

#push/pop for all the stacks
def write_push(segment, index):
    global name
    global output_line
    """
    Writes the assembly lines that implements the "push segment index" command in the VM language.
    """
# Determine the memory segment to push from.
    if segment == "constant":
        assembly = [
            f"@{index}",
            "D=A"
        ]
    elif segment == "local":
        assembly = [
            "@LCL",
            "D=M",
            f"@{index}",
            "A=D+A",
            "D=M"
        ]
    elif segment == "argument":
        assembly = [
            "@ARG",
            "D=M",
            f"@{index}",
            "A=D+A",
            "D=M"
        ]
    elif segment == "this":
        assembly = [
            "@THIS",
            "D=M",
            f"@{index}",
            "A=D+A",
            "D=M"
        ]
    elif segment == "that":
        assembly = [
            "@THAT",
            "D=M",
            f"@{index}",
            "A=D+A",
            "D=M"
        ]
    elif segment == "pointer":
        assembly = [
            f"@{3+index}",
            "D=M"
        ]
    elif segment == "temp":
        assembly = [
            f"@{5+index}",
            "D=M"
        ]
    elif segment == "static":
        assembly = [
            "@"+name+"."+index,
            "D=M"
        ]
    else:
        raise ValueError(f"Invalid segment: {segment}")
    
    
# Push the value onto the stack.
    
    assembly += [
        "@SP",
        "A=M",
        "M=D",
        "@SP",
        "M=M+1"
    ]
    output_lines.extend(assembly)
    print(output_lines)

def write_pop(segment, index):
    global output_lines
    global name
    """
    Writes the assembly code that implements the "pop segment index" command in the VM language.
    """
# Determine the memory segment to pop to.
    if segment == "local":
        assembly = [
            "@LCL",
            "D=M",
            f"@{index}",
            "D=D+A",
            "@R13",
            "M=D"
        ]
    elif segment == "argument":
        assembly = [
            "@ARG",
            "D=M",
            f"@{index}",
            "D=D+A",
            "@R13",
            "M=D"
        ]
    elif segment == "this":
        assembly = [
            "@THIS",
            "D=M",
            f"@{index}",
            "D=D+A",
            "@R13",
            "M=D"
        ]
    elif segment == "that":
        assembly = [
            "@THAT",
            "D=M",
            f"@{index}",
            "D=D+A",
            "@R13",
            "M=D"
        ]
    elif segment == "pointer":
        assembly = [
            "@SP",
            "AM=M-1",
            "D=M",
            f"@{3+index}",
            "M=D"
        ]
    elif segment == "temp":
        assembly = [
            "@SP",
            "AM=M-1",
            "D=M",
            f"@{5+index}",
            "M=D"
        ]
    elif segment == "static":
        assembly = [
            "@SP",
            "AM=M-1",
            "D=M",
            "@"+name+"."+index,
            "M=D"
        ]
    else:
        raise ValueError(f"Invalid segment: {segment}")
    output_lines.extend(assembly)
    
# Pop the value from the stack to the memory segment.
    
    assembly += [
        "@R13",
        "A=M",
        "M=D"
    ]
    output_lines.extend(assembly)
    

#If-goto and goto
def if_goto(label):
    global output_lines
    assembly = f"""
        // if-goto {label}
        @SP
        AM=M-1
        D=M
        @{label}
        D;JNE
        """
    output_lines.extend(assembly)
     

def write_if(command, label):
    global output_lines
    assembly = f"""
        // {command} {label}
        @SP
        AM=M-1
        D=M
        @R13
        M=D
        @SP
        AM=M-1
        D=M
        @R13
        """
    if command == "if-goto":
        assembly += f"""
        D;JNE
        @{label}
        0;JMP
        """
    elif command == "if":
        assembly += f"""
        D;JEQ
        """
    output_lines.extend(assembly)
    
    
#functions implementation:
def init_locals(n_vars):
    global output_lines
    for i in range(n_vars):
        assembly.extend([
            "@" + str(i),  # Load the counter value
            "D=A",         # Store the counter value in D
            "@SP",         # Load the stack pointer
            "A=M",         # Set A to the address pointed by SP
            "M=D",         # Store the counter value at the address pointed by SP
            "@SP",         # Load the stack pointer
            "M=M+1"        # Increment the stack pointer
        ])
    output_lines.extend(assembly)


# Define a function to generate assembly code for arithmetic operations
def arithmetic(command):
    global output_lines
    if command == "add":
        output_lines.append("@SP")
        output_lines.append("AM=M-1")
        output_lines.append("D=M")
        output_lines.append("A=A-1")
        output_lines.append("M=D+M")
    elif command == "sub":
        assembly.append("@SP")
        assembly.append("AM=M-1")
        assembly.append("D=M")
        assembly.append("A=A-1")
        assembly.append("M=M-D")
    elif command == "neg":
        assembly.append("@SP")
        assembly.append("A=M-1")
        assembly.append("M=-M")
    elif command == "eq":  
        assembly.append("@SP")
        assembly.append("AM=M-1")
        assembly.append("D=M")
        assembly.append("A=A-1")
        assembly.append("D=M-D")
        assembly.append("@EQ_TRUE")
        assembly.append("D;JEQ")
        assembly.append("@SP")
        assembly.append("A=M-1")
        assembly.append("M=0")
        assembly.append("(EQ_END)")
        assembly.append("0;JMP")
        assembly.append("(EQ_TRUE)")
        assembly.append("@SP")
        assembly.append("A=M-1")
        assembly.append("M=-1")
    elif command == "gt":
        assembly.append("@SP")
        assembly.append("AM=M-1")
        assembly.append("D=M")
        assembly.append("A=A-1")
        assembly.append("D=M-D")
        assembly.append("@GT_TRUE")
        assembly.append("D;JGT")
        assembly.append("@SP")
        assembly.append("A=M-1")
        assembly.append("M=0")
        assembly.append("(GT_END)")
        assembly.append("0;JMP")
        assembly.append("(GT_TRUE)")
        assembly.append("@SP")
        assembly.append("A=M-1")
        assembly.append("M=-1")
    elif command == "lt":
        assembly.append("@SP")
        assembly.append("AM=M-1")
        assembly.append("D=M")
        assembly.append("A=A-1")
        assembly.append("D=M-D")
        assembly.append("@LT_TRUE")
        assembly.append("D;JLT")
        assembly.append("@SP")
        assembly.append("A=M-1")
        assembly.append("M=0")
        assembly.append("(LT_END)")
        assembly.append("0;JMP")
        assembly.append("(LT_TRUE)")
        assembly.append("@SP")
        assembly.append("A=M-1")
        assembly.append("M=-1")
    elif command == "and":
        assembly.append("@SP")
        assembly.append("AM=M-1")
        assembly.append("D=M")
        assembly.append("A=A-1")
        assembly.append("M=D&M")     
    elif command == "or":
        assembly.append("@SP")
        assembly.append("AM=M-1")
        assembly.append("D=M")
        assembly.append("A=A-1")
        assembly.append("M=D|M")       
    elif command == "not":
        assembly.append("@SP")
        assembly.append("A=M-1")
        assembly.append("M=!M")
    else:
        return ""
    print(output_lines)



def write_return():
    global output_lines
    """
    Writes the assembly lines that implements the "return" command in the VM language.
    """
# Store the current frame pointer (LCL) in a temporary variable.
# This will be used later to restore the caller's state.
    assembly = [
        "// return",
        "@LCL",
        "D=M",
        "@R13",
        "M=D"
    ]
    output_lines.extend(assembly)
    
# Store the return address (the value at the top of the caller's stack) in a temporary variable.
    assembly += [
# Get the return address (stored at LCL - 5) and store it in R14.
        "@5",
        "A=D-A",
        "D=M",
        "@R14",
        "M=D"
    ]
    output_lines.extend(assembly)
    
# Move the return value (the value at the top of the callee's stack) to the caller's stack.
    assembly += [
# Get the return value (stored at the top of the callee's stack) and store it in ARG 0.
        "@SP",
        "A=M-1",
        "D=M",
        "@ARG",
        "A=M",
        "M=D"
    ]
    output_lines.extend(assembly)
    
# Restore the caller's stack pointer.
    assembly += [
# Set SP to ARG + 1
        "D=A+1",
        "@SP",
        "M=D"
    ]
    output_lines.extend(assembly)
    
# Restore the caller's state by restoring the caller's segment pointers.
    assembly += [
# Restore THAT pointer (THAT = *(frame - 1))
        "@R13",
        "AM=M-1",
        "D=M",
        "@THAT",
        "M=D",

# Restore THIS pointer (THIS = *(frame - 2))
        "@R13",
        "AM=M-1",
        "D=M",
        "@THIS",
        "M=D",

# Restore ARG pointer (ARG = *(frame - 3))
        "@R13",
        "AM=M-1",
        "D=M",
        "@ARG",
        "M=D",

# Restore LCL pointer (LCL = *(frame - 4))
        "@R13",
        "AM=M-1",
        "D=M",
        "@LCL",
        "M=D",
        ]
    
# Jump to the return address.
    assembly += [
# Jump to the return address (stored in R14).
        "@R14",
        "A=M",
        "0;JMP"
    ]
    output_lines.extend(assembly)
    
for line in lines:
    segments = line
    print(segments)
    if len(segments) == 1:
        if segments[0] in ["add", "sub", "neg","eq", "gt", "lt", "and", "or", "not"]:
            output_lines.append(arithmetic(segments[0]))
        else:
            output_lines.append(" ".join(segments))
    elif len(segments)==3:
        print(segments)
        if segments[0] == "push":
            write_push(segments[1],segments[2])
        elif segments[0] == "pop":
            write_pop(segments[1],segments[2])
        elif  segments[0] == "call":
            call(segments[1],segments[2])
        elif segments[0] == "local":
            init_local(segments[1],segments[2])
    elif len(segment) == 2:
        if segment[0] == "if_goto":
             if_goto(segments[1],segments[2])
        elif segement[0] == "if":
             write_if(segments[1],segments[2])
            
print(output_lines)
f2 = open('F:\Ganesh\Amrita\Subjects\Sem 2\EOC- 2\Project\Part B\Trial 2\Setup.asm', 'w') 
for j in output_lines:
    if j== None:
        pass
    else:
        f2.writelines((j)+"\n")
f.close()
f2.close()

