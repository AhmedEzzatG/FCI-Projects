import math
from random import random


class City:
    def __init__(self, x=None, y=None):
        if x is not None:
            self.x = x
        else:
            self.x = int(random() * 200)
        if y is not None:
            self.y = y
        else:
            self.y = int(random() * 200)

    def distance_to(self, city):
        x_distance = abs(self.x - city.x)
        y_distance = abs(self.y - city.y)
        distance = math.sqrt((x_distance * x_distance) + (y_distance * y_distance))
        return distance

    def __repr__(self):
        return str(self.x) + ", " + str(self.y)
