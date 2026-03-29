import numpy as np
import matplotlib.pyplot as plt
np.random.seed(42)

def sigmoid(x): return 1 / (1 + np.exp(-x))
def sigmoid_derivative(x): return x * (1 - x)


def getValue(prompt, low, high):
    while True:
        x = input(prompt)
        try:
            x = float(x)
            if low <= x <= high:
                return x
            else:
                print(f"Please input a value in the range {low} to {high}.")
        except ValueError:
            print("Wrong input, please enter a number.")

running = True
while running:
    x = np.array([[0, 0], [1, 0], [0, 1], [1, 1]])
    choice = getValue("What gate would you like?\nAnd(1): \nOr(2): \nXOR(3): \nNXOR(4): ", 1, 4)
    if choice == 1:
        y = np.array([[0], [0], [0], [1]])
    elif choice == 2:
        y = np.array([[0], [1], [1], [1]])
    elif choice == 3:
        y = np.array([[0], [1], [1], [0]])
    elif choice == 4:
        y = np.array([[1], [0], [0], [1]])
    else:
        y = np.array([[0], [0], [0], [0]])

    N = x.shape[0]

    H = int(getValue("How many neurons per hidden layer: ", 1, 32))
    lr = getValue("Whats the learning rate(recommended 0.1): ", 0, 1)
    epochs = int(getValue("How many epochs: ", 1, 1000000))

    W1 = np.random.randn(2, H) * 0.1
    b1 = np.zeros((1, H))
    W2 = np.random.randn(H, 1) * 0.1
    b2 = np.zeros((1, 1))

    loss_history = []
    for epoch in range(epochs):
        Z1 = np.dot(x, W1) + b1
        A1 = sigmoid(Z1)
        Z2 = np.dot(A1, W2) + b2
        y_hat = sigmoid(Z2)
   
        epsilon = 1e-8
        loss = -np.mean(y * np.log(y_hat + epsilon) + (1 - y) * np.log(1 - y_hat + epsilon))
        loss_history.append(loss)

        dYhat = (2.0 / N) * (y_hat - y)
        dZ2 = y_hat - y
        dW2 = np.dot(A1.T, dZ2)
        db2 = np.sum(dZ2, axis=0, keepdims=True)
        dA1 = np.dot(dZ2, W2.T)
        dZ1 = dA1 * sigmoid_derivative(A1)
        dW1 = np.dot(x.T, dZ1)
        db1 = np.sum(dZ1, axis=0, keepdims=True)

        W1 -= lr * dW1
        b1 -= lr * db1
        W2 -= lr * dW2
        b2 -= lr * db2

        if epoch % 2000 == 0:
            print(f"Epoch {epoch}, Loss: {loss:.6f}")

    print("\nPredictions:")
    print(np.round(y_hat, 3))

    plt.plot(loss_history)
    plt.title("Loss over time")
    plt.xlabel("Epoch")
    plt.ylabel("MSE Loss")
    plt.show()

    terminate = getValue("Are you ready to terminate the program: \nYes(1): \nNo(0): ", 0, 1)
    if terminate == 1:
        running = False