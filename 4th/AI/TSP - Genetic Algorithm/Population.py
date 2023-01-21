from City import City
from individual import Individual


class Population:
    def __init__(self, cites: list[City], population_size, initialize=False):
        self.populations = []
        for i in range(population_size):
            cur = Individual(cites)
            if initialize:
                cur.initialize_gnome()
            self.populations.append(cur)

    def get_fittest(self):
        fittest = self.populations[0]
        for population in self.populations:
            if fittest.get_fitness() < population.get_fitness():
                fittest = population
        return fittest

    def population_size(self):
        return len(self.populations)

