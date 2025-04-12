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


class Triangle3D:
    def __init__(self, dot1, dot2, dot3):
        self.dot1 = dot1
        self.dot2 = dot2
        self.dot3 = dot3

    def calculate_perimeter(self):
        # Calculate the lengths of the edges
        edge1 = self.dot1.distance_to(self.dot2)
        edge2 = self.dot2.distance_to(self.dot3)
        edge3 = self.dot3.distance_to(self.dot1)
        # Return the sum of the edges
        return edge1 + edge2 + edge3

    def calculate_area(self):
        # Calculate the lengths of the edges
        edge1 = self.dot1.distance_to(self.dot2)
        edge2 = self.dot2.distance_to(self.dot3)
        edge3 = self.dot3.distance_to(self.dot1)
        # Calculate the semi-perimeter
        s = (edge1 + edge2 + edge3) / 2
        # Use Heron's formula to calculate the area
        area = math.sqrt(s * (s - edge1) * (s - edge2) * (s - edge3))
        return area
