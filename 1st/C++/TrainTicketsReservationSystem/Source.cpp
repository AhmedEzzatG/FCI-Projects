#include<iostream>
#include<iomanip>
#include<fstream>
#include<string>
using namespace std;

struct Seat {
	int number;
	bool status;
};
struct car {
	int id;
	Seat seat[30];
} train[10];

void build() {
	fstream data;
	data.open("data.txt");
	for (int i = 0; i < 10; i++) {
		train[i].id = i + 1;
		for (int j = 0; j < 30; j++) {
			train[i].seat[j].number = j + 1;
			if (!data.eof())
				data >> train[i].seat[j].status;
			else
				train[i].seat[j].status = 0;
		}
	}
	data.close();
}

void printMid(string s, int dis, char ch) {
	cout << setfill(ch) << setw(dis) << ch;
	cout << s;
	cout << setfill(ch) << setw(dis) << ch;
}

void clear() {
	system("CLS");
	printMid(" Train Tikets reservation system ", 14, '=');
	cout << '\n' << endl;
}

void mainMenu() {
	clear();
	cout << "main menu : \n";
	cout << setfill('=') << setw(62) << '\n';
	cout << "1. Reserve a ticket.\n";
	cout << "2. Cancel the reservation.\n";
	cout << "3. Check whether a specific car has available seat or not.\n";
	cout << "4. Check whether a specific seat is available or not.\n";
	cout << "5. Get the number of the available seats in a specific car.\n";
	cout << "6. Show all the seats status of a specific car.\n";
	cout << "7. Exit.\n";
	cout << setfill('=') << setw(62) << '\n';
	cout << "please enter your choice : ";
}

int input(string s) {
	bool b = 0;
	int x = 0, f;
	if (s == "car")
		f = 10;
	else
		f = 30;
	do {
		if (b) {
			clear();
			cout << "invalid nubmer" << '\n' << endl;
		}
		cout << "please enter the " << s << " number from 1 to " << f << " : ";
		cin >> x;
		b = 1;
	} while (x < 1 || x > f);
	return x;
}

void inputCar(int& carNumber) {
	clear();
	carNumber = input("car");
	clear();
}

void inputSeat(int& carNumber, int& seatNumber) {
	clear();
	carNumber = input("car");
	seatNumber = input("seat");
	clear();
}

int choiceMenu(string s) {
	while (true) {
		cout << endl;
		cout << setfill('=') << setw(35) << '\n';
		cout << "1. back to main menu \n";
		cout << "2. choice another " << s << " \n";
		cout << "3. Exit\n";
		cout << setfill('=') << setw(35) << '\n';
		cout << "please enter your choice : ";
		int mood;
		cin >> mood;
		if (1 <= mood && mood <= 3)
			return mood;
		clear();
		cout << "choice not available\n";
	}
}

bool reserveTicket(int carNumber, int seatNumber) {
	if (train[carNumber - 1].seat[seatNumber - 1].status)
		return false;
	else {
		train[carNumber - 1].seat[seatNumber - 1].status = true;
		return true;
	}
}

bool cancelReservation(int carNumber, int seatNumber) {
	if (train[carNumber - 1].seat[seatNumber - 1].status) {
		train[carNumber - 1].seat[seatNumber - 1].status = false;
		return true;
	}
	else
		return false;
}

int countOfAvailableSeats(int carNumber) {
	int cnt = 0;
	for (int i = 0; i < 30; i++)
		if (train[carNumber - 1].seat[i].status == 0)
			cnt++;
	return cnt;
}

bool seatIsAvailable(int carNumber, int seatNumber) {
	return (train[carNumber - 1].seat[seatNumber - 1].status == false);
}

void theAvailableSeatsInCar(int carNumber) {
	cout << "the available seats in a car " << carNumber << " are :\n";
	for (int i = 1; i <= 30; i++) {
		if (seatIsAvailable(carNumber, i))
			cout << i << ' ';
		if (i % 10 == 0)
			cout << endl;
	}
	cout << endl;
}

void theStatusOfSeatsInCar(int carNumber) {
	cout << "car " << carNumber << " status : \n";
	for (int i = 0; i < 30; i++) {
		string s = "seat 00 is ";
		s[6] += (i + 1) % 10;
		s[5] += (i + 1) / 10;
		printMid(s, 2, ' ');
		if (train[carNumber - 1].seat[i].status)
			printMid(" not available", 1, ' ');
		else
			printMid(" available", 3, ' ');
		cout << endl;
	}
}

void exit() {
	fstream data;
	data.open("data.txt", fstream::out);
	for (int i = 0; i < 10; i++)
		for (int j = 0; j < 30; j++)
			data << train[i].seat[j].status << endl;
	data.close();
}

int main() {
	build();
	int choice = 0, carNumber = 0, seatNumber = 0, mood = 0;
	do {
		mainMenu();
		cin >> choice;
		cout << endl;
		switch (choice) {
		case 1:
			do {
				inputSeat(carNumber, seatNumber);
				if (reserveTicket(carNumber, seatNumber)) {
					cout << "reservation the seat number " << seatNumber
						<< " in car " << carNumber
						<< " is done successfully\n";
				}
				else {
					cout << "sorry the seat number " << seatNumber << " in car "
						<< carNumber << " is reserved before\n";
				}
				mood = choiceMenu("seat");
			} while (mood == 2);
			break;
		case 2:
			do {
				inputSeat(carNumber, seatNumber);
				if (cancelReservation(carNumber, seatNumber)) {
					cout << "reservation for seat number " << seatNumber
						<< " in car " << carNumber
						<< "  has been canceled successfully\n";
				}
				else {
					cout << "the seat number " << seatNumber << " in car "
						<< carNumber << " is not been reserved before\n";
				}
				mood = choiceMenu("seat");
			} while (mood == 2);
			break;
		case 3:
			do {
				inputCar(carNumber);
				int cnt = countOfAvailableSeats(carNumber);
				cout << "the car number " << carNumber;
				if (cnt == 0)
					cout << "  is not has any seat\n";
				else
					cout << " is has " << cnt << " available seats\n";
				mood = choiceMenu("car");
			} while (mood == 2);
			break;
		case 4:
			do {
				inputSeat(carNumber, seatNumber);
				cout << "seat number " << seatNumber << " in the car number "
					<< carNumber << " is ";
				if (seatIsAvailable(carNumber, seatNumber))
					cout << "available\n";
				else
					cout << "not available\n";
				mood = choiceMenu("seat");
			} while (mood == 2);
			break;
		case 5:
			do {
				inputCar(carNumber);
				theAvailableSeatsInCar(carNumber);
				mood = choiceMenu("car");
			} while (mood == 2);
			break;
		case 6:
			do {
				inputCar(carNumber);
				theStatusOfSeatsInCar(carNumber);
				mood = choiceMenu("car");
			} while (mood == 2);
			break;
		case 7:
			mood = 3;
			break;
		default:
			do {
				clear();
				cout << "invalid input \n" << endl;
				cout << setfill('=') << setw(35) << '\n';
				cout << "1. back to main menu \n";
				cout << "2. Exit\n";
				cout << setfill('=') << setw(35) << '\n';
				cout << "please enter your choice : ";
				cin >> mood;
			} while (mood != 1 && mood != 2);
			mood++;
			break;
		}
	} while (mood != 3);
	exit();
}
