from math import e

def mix(x, bits):
    if x > (2 ** bits) - 1 or x < 0:
        raise ValueError(f"Must be a {bits} bit input")
    x ^= (1 << 2)
    shift = 2
    shift %= bits
    mask = (1 << bits) - 1
    x &= mask
    x = ((x << shift) | (x >> (bits - shift))) & mask
    x ^= ((1 << bits) - 2)
    return x