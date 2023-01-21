package models

import (
	"database/sql"
	"log"
)

type User struct {
	Id   int
	Name string
	Age  int
}

func GetAllUsers(db *sql.DB) []User {
	result, err := db.Query("SELECT * FROM Customer")
	if err != nil {
		log.Fatal(err.Error())
	}
	users := []User{}
	for result.Next() {
		var u User
		result.Scan(&u.Id, &u.Name, &u.Age)
		users = append(users, u)
	}
	defer result.Close()
	return users
}

func AddUser(db *sql.DB, UserId int, name string, age int) {
	ser, err := db.Query("SELECT * FROM Customer where SSN=?", UserId)
	if err != nil {
		log.Fatal(err.Error())
		return
	}
	if ser.Next() {
		myquery := "update Customer SET Name=?,age=? where SSN=?"
		insertion, err := db.Query(myquery, name, age, UserId)
		if err != nil {
			println(err.Error())
		}
		insertion.Close()
	} else {
		myquery := "INSERT INTO Customer (SSN,Name,Age) values (?,?,?)"
		insertion, err := db.Query(myquery, UserId, name, age)
		if err != nil {
			println(err.Error())
		}
		insertion.Close()
	}
	ser.Close()
}

func DeleteUser(db *sql.DB, userId int) {
	deletedCard, err := db.Query("DELETE FROM Card WHERE SSN=?", userId)
	if err != nil {
		log.Fatal(err.Error())
	}
	deletedCard.Close()

	myquery := "DELETE FROM Customer WHERE SSN=?"
	deleted, err := db.Query(myquery, userId)
	if err != nil {
		log.Fatal(err.Error())
	}
	deleted.Close()
}
