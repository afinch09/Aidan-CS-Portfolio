from qiskit import QuantumCircuit, QuantumRegister

def diffuse(qc, x):
    qc.h(x)
    qc.x(x)

    target = x[-1]
    controls = list(x[:-1])

    qc.h(target)
    qc.mcx(controls, target)
    qc.h(target)

    qc.x(x)
    qc.h(x)