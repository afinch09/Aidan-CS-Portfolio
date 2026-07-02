from grover import runGroversFromSim, runGroversFromHardware

for bits in range(4, 7):
    trials = 1000
    accuracies = [runGroversFromSim(bits) for _ in range(trials)]
    average = sum(accuracies) / trials

    print("From Simulator:")
    print(f"Number of bits: {bits}")
    print(f"Average accuracy: {average:.4f}")
    print(f"Best: {max(accuracies):.4f}")
    print(f"Worst: {min(accuracies):.4f}\n\n")

for bits in range(4, 7):
    trials = 10
    accuracies = [runGroversFromHardware(bits) for _ in range(trials)]
    average = sum(accuracies) / trials

    print("From Real Hardware:")
    print(f"Number of bits: {bits}")
    print(f"Average accuracy: {average:.4f}")
    print(f"Best: {max(accuracies):.4f}")
    print(f"Worst: {min(accuracies):.4f}\n\n")