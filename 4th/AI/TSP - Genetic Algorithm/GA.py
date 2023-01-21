from random import random
from City import City
from Population import Population
from individual import Individual


def rolling_wheel_selection(pop: Population):
    sum = 0
    for x in pop.populations:
        sum += x.get_fitness()
    random_val = random() * sum
    for x in pop.populations:
        random_val -= x.get_fitness()
        if random_val <= 0:
            return x
    return None


class GA:
    def __init__(self, cites: list[City], mutation_rate, tournament_size):
        self.cites = cites
        self.mutationRate = mutation_rate
        self.tournamentSize = tournament_size

    def evolve_population(self, pop: Population):
        new_population = Population(self.cites, pop.population_size())
        new_population.populations[0] = pop.get_fittest()

        for i in range(1, new_population.population_size()):
            parent1 = self.tournament_selection(pop)
            parent2 = self.tournament_selection(pop)
            child = self.crossover(parent1, parent2)
            new_population.populations[i] = child

        for i in range(1, new_population.population_size()):
            self.mutate(new_population.populations[i])

        return new_population

    def crossover(self, parent1: Individual, parent2: Individual):
        child = Individual(self.cites)

        startPos = int(random() * len(self.cites))
        endPos = int(random() * len(self.cites))
        for i in range(0, len(self.cites)):
            if (startPos <= endPos and startPos < i < endPos) or not (startPos > i > endPos):
                child.gnome[i] = parent1.gnome[i]

        for i in range(0, len(self.cites)):
            if not child.contains_city(parent2.gnome[i]):
                for ii in range(0, len(self.cites)):
                    if child.gnome[ii] is None:
                        child.gnome[ii] = parent2.gnome[i]
                        break
        return child

    def mutate(self, pop: Individual):
        for pos1 in range(0, len(pop.gnome)):
            if random() < self.mutationRate:
                pos2 = int(len(pop.gnome) * random())
                pop.gnome[pos1], pop.gnome[pos2] = pop.gnome[pos2], pop.gnome[pos1]

    def tournament_selection(self, pop: Population):
        tournament = Population(self.cites, self.tournamentSize)
        for i in range(0, self.tournamentSize):
            random_id = int(random() * pop.population_size())
            tournament.populations[i] = pop.populations[random_id]
        fittest = tournament.get_fittest()
        return fittest
