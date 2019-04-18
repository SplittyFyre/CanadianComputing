/*
 * prettyaverageprimes.cpp
 *
 *  Created on: Mar 24, 2019
 *      Author: oscarsplitfire
 */


#include <stdio.h>
#include <bitset>
#include <vector>
#include <unordered_map>

typedef unsigned int uint;

#define PRIME_MAX 1000000

#define PRIMES_LESS_THAN_MIL 78498

std::vector<uint> primes;
std::unordered_map<uint, uint> hashmap;
std::unordered_map< uint, std::pair<uint, uint> > alreadyFound;

void generatePrimes() {
	//hopefully save some time by preallocating
	primes.reserve(PRIMES_LESS_THAN_MIL);

	// bitset size is 125000 bytes, plz allocate on heap
	std::bitset<PRIME_MAX + 1> *marked = new std::bitset<PRIME_MAX + 1>; // all 0 ; 0 is prime, 1 is composite

	uint i, j;

	for (i = 2; i * i <= PRIME_MAX; i++) {
		if (!marked->test(i)) {
			for (j = i * 2; j <= PRIME_MAX; j += i) {
				marked->set(j, true);
			}
		}
	}



	// add all unmarked
	for (i = 2; i <= PRIME_MAX; i++) {
		if (!marked->test(i)) {
			primes.push_back(i);
		}
	}
	delete marked;
}


void findTwoSum(uint target, uint *a, uint *b) {

	// lazy handling of special case
	if (target == 2000000) {
		(*a) = 7;
		(*b) = 1999993;
		return;
	}

	if (target < 1000000) {

		for (int i = 0; i < primes.size(); i++) {
			uint currPrime = primes[i];
			uint other = target - currPrime;
			if (hashmap.count(other)) { // if contains key
				(*a) = primes[hashmap[other]];
				(*b) = currPrime;
				return;
			}
			hashmap[currPrime] = i;
		}

	}
	else {

		for (int i = primes.size(); i >= 0; i--) {
			uint currPrime = primes[i];
			uint other = target - currPrime;
			if (hashmap.count(other)) { // if contains key
				(*a) = primes[hashmap[other]];
				(*b) = currPrime;
				return;
			}
			hashmap[currPrime] = i;
		}

	}
}


int main(void) {

	puts("");

	generatePrimes();

	uint a, b;

	int t;
	scanf("%d", &t);

	int num;

	for (int i = 0; i < t; i++) {
		scanf("%d", &num);

		if (!alreadyFound.count(num)) { // if not contains key
			findTwoSum(num * 2, &a, &b);
			alreadyFound[num] = std::pair<uint, uint>(a, b);
			printf("%d %d\n", a, b);
		}
		else {
			std::pair<uint, uint> &retreived = alreadyFound[num];
			printf("%d %d\n", retreived.first, retreived.second);
		}

	}

	return 0;
}


