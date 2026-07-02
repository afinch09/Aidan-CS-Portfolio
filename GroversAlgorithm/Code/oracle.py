from qiskit import QuantumCircuit, QuantumRegister, AncillaRegister

def oracle(qc, x, target, bits):
    qc.x(x[2])

    for i in reversed(range(2)):
        for j in reversed(range(bits - 1)):
            qc.swap(x[j+1], x[j])

    constant = (1 << bits) - 2

    for i in range(bits):
        if (constant >> i) & 1:
            qc.x(x[i])

    for i in range(bits):
        if ((target >> i) & 1) == 0:
            qc.x(x[i])

    qc.h(x[-1])
    qc.mcx(x[:-1], x[-1])
    qc.h(x[-1])

    for i in range(bits):
        if ((target >> i) & 1) == 0:
            qc.x(x[i])

    for i in range(bits):
        if (constant >> i) & 1:
            qc.x(x[i])

    for i in range(2):
        for j in range(bits-1):
            qc.swap(x[j+1], x[j])

    qc.x(x[2])

    return qc