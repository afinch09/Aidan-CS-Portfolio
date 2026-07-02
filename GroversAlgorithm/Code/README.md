# Code for Grovers Algorithm

# Description
This project creates a way to test the accuracy of grovers algorithm, a quantum search algorithm that is faster than linear search
To test this, a mixing algorithm was created with simple a bit inversion, a rotation left by two, and an xor gate
The main flow of grovers algorithm is oracle->diffusion(repeat)->results

Assuming you have a base understand of grovers algorithm, i will explain the oracle
The bit inversion was modeled using an x gate
The rotation was a set of swaps by two
and the xor gate was a multicontrolled x gate
If the bits were correct, they're amplitude was inverted
All steps were reversed

The main file will run grovers algorithm several times on real hardware and simulation and you can compare results of each

# To Run
install Python
install Qiskit tools
Add all files to a folder
insert your IBMQ api key into grover.py
Run main.py

# Usage
This can be used as a teaching tool for how quickly increasing circuit depth degrades data in quantum algorithms
