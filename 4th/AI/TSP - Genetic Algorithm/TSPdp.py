import sys

from City import City


def TSP(cities: list[City]):
    n = len(cities)
    dp = [[sys.maxsize] * (1 << n)] * n

    for mask in range((1 << n) - 1, -1, -1):
        for i in range(0, n):
            if mask == (1 << n) - 1:
                dp[i][mask] = cities[i].distance_to(cities[0])
                continue
            for j in range(0, n):
                if mask & (1 << j):
                    continue
                dp[i][mask] = min(dp[i][mask], dp[j][mask | (1 << j)] + cities[i].distance_to(cities[j]))
    return dp[0][0]
