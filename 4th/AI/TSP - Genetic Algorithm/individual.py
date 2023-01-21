from random import shuffle
from City import City


class Individual:
    def __init__(self, cites: list[City]):
        self.cites = cites
        self.gnome = [None for _ in range(len(cites))]

    def initialize_gnome(self):
        self.gnome = [x for x in range(len(self.cites))]
        shuffle(self.gnome)

    def get_distance_between_two_cites(self, x: int, y: int) -> float:
        return self.cites[x].distance_to(self.cites[y])

    def get_distance(self):
        f = self.get_distance_between_two_cites(self.gnome[-1], self.gnome[0])
        for i in range(len(self.gnome) - 1):
            f += self.get_distance_between_two_cites(self.gnome[i], self.gnome[i + 1])
        return f

    def get_fitness(self):
        return 1 / self.get_distance()

    def contains_city(self, city_id: int) -> bool:
        return city_id in self.gnome

    def __repr__(self):
        return str(self.gnome)
