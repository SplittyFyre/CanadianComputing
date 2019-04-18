/*
 * sunflowers.c
 *
 *  Created on: Nov 27, 2018
 *      Author: oscarsplitfire
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int n;
int temp;

int i, j;

// VALIDATION CODE

bool validateGrowth(int **mat) {
	for (i = 0; i < n; i++) {
		for (j = 1; j < n; j++) {
			if (mat[i][j - 1] >= mat[i][j]) {
				return false;
			}
		}
	}
	return true;
}

bool validateColumns(int **mat) {
	for (j = 0; j < n; j++) {
		for (i = 1; i < n; i++) {
			if (mat[i - 1][j] >= mat[i][j]) {
				return false;
			}
		}
	}
	return true;
}

 bool validate(int **mat) {
	return (validateColumns(mat) && validateGrowth(mat));
}

// MATRIX ROTATION CODE

void transpose(int **mat) {
	for (i = 0; i < n; i++) {
		for (j = i; j < n; j++) {
			temp = mat[j][i];
			mat[j][i] = mat[i][j];
			mat[i][j] = temp;
		}
	}
}

void reverseColumnsOrRows(int **mat) {

	//REVERSE COLUMNS

	/*int k;
	for (i = 0; i < n; i++) {
		for (j = 0, k = n - 1; j < k; j++, k--) {
			temp = mat[j][i];
			mat[j][i] = mat[k][i];
			mat[k][i] = temp;
		}
	}*/

	//REVERSE ROWS

	for (j = 0; j < n; j++) {
		int *row = mat[j];
		for (i = 0; i < n / 2; i++) {
			temp = row[i];
			row[i] = row[n - i - 1];
		    row[n - i - 1] = temp;
		}
	}
}

void rot90(int **mat) {
	transpose(mat);
	reverseColumnsOrRows(mat);
}

int main(void) {

	puts("");

	scanf("%d", &n);
	int **mat = malloc(n * sizeof(int*));
	for (i = 0; i < n; i++) {
		mat[i] = malloc(n * sizeof(int));
	}

	for (i = 0; i < n; i++) {
		for (j = 0; j < n; j++) {
			scanf("%d", &(mat[i][j]));
		}
	}

	// Must declare new variable here
	for (int i = 0; i < 3; i++) {
		if (validate(mat)) {
			break;
		}
		rot90(mat);
	}

	for (i = 0; i < n; i++) {
		for (j = 0; j < n; j++) {
			printf("%d ", mat[i][j]);
		}
		printf("\n");
	}

	for (i = 0; i < n; i++) {
		free(mat[i]);
	}
	free(mat);

	return 0;
}


