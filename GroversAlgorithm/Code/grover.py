from random import randint
from qiskit import QuantumCircuit, QuantumRegister, AncillaRegister, ClassicalRegister, transpile
from mix import mix
from oracle import oracle
from diffusion import diffuse
from qiskit_aer import AerSimulator
from qiskit_ibm_runtime import QiskitRuntimeService
from qiskit_ibm_runtime import SamplerV2

QiskitRuntimeService.delete_account()

QiskitRuntimeService.save_account(
    token="API KEY",
    set_as_default=True
)

service = QiskitRuntimeService(
    channel="ibm_quantum_platform"
)

def runGroversFromSim(bits):
    x = QuantumRegister(bits)
    c = ClassicalRegister(bits, "c")
    qc = QuantumCircuit(x, c)

    target = mix(randint(0, (2 ** bits) - 1), bits)

    qc.h(x)

    correct = []
    for i in range(2 ** bits):
        if mix(i, bits) == target:
            correct.append(f"{i:0{bits}b}")

    iterations = {
        4: 3,
        5: 4,
        6: 6
    }[bits]

    for _ in range(iterations):
        oracle(qc, x, target, bits)
        diffuse(qc, x)

    qc.measure(x, c)

    backend = AerSimulator()

    shots = 1024
    result = backend.run(qc, shots=shots).result()

    counts = result.get_counts()

    return getSuccess(counts, correct, shots)

def runGroversFromHardware(bits):
    x = QuantumRegister(bits)
    c = ClassicalRegister(bits, "c")
    qc = QuantumCircuit(x, c)

    target = mix(randint(0, (2 ** bits) - 1), bits)

    qc.h(x)

    correct = []
    for i in range(2 ** bits):
        if mix(i, bits) == target:
            correct.append(f"{i:0{bits}b}")

    iterations = {
        4: 3,
        5: 4,
        6: 6
    }[bits]

    for _ in range(iterations):
        oracle(qc, x, target, bits)
        diffuse(qc, x)

    qc.measure(x, c)

    backend = service.least_busy(
        operational=True,
        simulator=False,
        min_num_qubits=7
    )

    transpiled = transpile(qc, backend)

    sampler = SamplerV2(mode=backend)

    shots = 1024
    job = sampler.run([transpiled], shots=1024)

    result = job.result()
    counts = result[0].data.c.get_counts()

    return getSuccess(counts, correct, shots)

def getSuccess(counts, correct, shots):
    correct_count = 0
    for key in correct:
        correct_count += counts.get(key, 0)
    return (correct_count / shots)