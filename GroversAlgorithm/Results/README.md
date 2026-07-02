# Analysis

Things to note with the data is that as the amount of bits increases on the simulation, practically nothing changes with the success rate, but due to the increasing circuit depth in the real hardware, the data degrades very quickly.
Also for the 4 bit simulation, the success consistently converges to 0.96 due to the amount of runs of the oracle and diffusion done, the formula for this is pi/4 * (sqrt(2^bits)). For 4 bits the needed runs is 3.14, but it has to be rounded, causing this inaccuracy
