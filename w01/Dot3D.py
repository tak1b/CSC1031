import math

class Dot3D(object):

    def __init__(self, x, y, z, label=None):
        self.x = x
        self.y = y
        self.z = z
        self.label = label

    def distance_to(self, other):
        return math.sqrt(
            (other.x - self.x) **2  + (other.y - self.y) **2 + (other.z - self.z) **2
        )

    def add_vector(self, other):
        return Dot3D(self.x + other.x, self.y + other.y, self.z + other.z, label=(self.label + "+" + other.label))
