from City import City
from GA import GA
from Population import Population
from TSPdp import TSP

if __name__ == '__main__':
    # configuration
    mutation_rate = 0.1
    tournament_size = 5
    population_size = 50
    number_of_generation = 1000
    number_of_cities = 20
    cities = []
    for i in range(number_of_cities):
        cities.append(City())

    current_population = Population(cities, population_size, True)
    ga = GA(cities, mutation_rate, tournament_size)
    current_population = ga.evolve_population(current_population)
    for i in range(0, number_of_generation):
        # print(f"generation {i}-th distance: ", current_population.get_fittest().get_distance())
        current_population = ga.evolve_population(current_population)
    print("final distance: ", current_population.get_fittest().get_distance())
    print(current_population.get_fittest())
    if len(cities) <= 20:
        print("the optimal solution is, ", TSP(cities))
