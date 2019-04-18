/*
 * voronoi_villages.cpp
 *
 *  Created on: Nov 24, 2018
 *      Author: oscarsplitfire
 */

#include <iostream>
#include <vector>
#include <algorithm>

int main(void) {

	int num;
	std::cin >> num;

	std::vector<int> villages;
	std::vector<double> dists;

	int in;
	for (int i = 0; i < num; i++) {
		std::cin >> in;
		villages.push_back(in);
	}

	std::sort(villages.begin(), villages.end());

	int prev, next;
	for (int i = 1; i < num - 1; i++) {
		prev = villages.at(i - 1);
		next = villages.at(i + 1);
		dists.push_back((next - prev) / 2.0);
	}

	std::sort(dists.begin(), dists.end());

	printf("%.1f\n", dists.at(0));

	return 0;
}


