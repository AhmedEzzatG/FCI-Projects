package models

import (
	"database/sql"
	"log"
)

type Card struct {
	Id       int
	BankName string
	ClientId int
}

func AddCard(db *sql.DB, cardId int, bankName string, clientId int) {
	ser, err := db.Query("SELECT * FROM Card where CardNum=?", cardId)
	if err != nil {
		return
	}
	if ser.Next() {
		insertion, err := db.Query("update Card SET BankName=?,SSN=? where CardNum=?", bankName, clientId, cardId)
		if err != nil {
			println(err.Error())
		}
		insertion.Close()
	} else {
		insertion, err := db.Query("INSERT INTO Card (CardNum,BankName,SSN) values (?,?,?)", cardId, bankName, clientId)
		if err != nil {
			println(err.Error())
		}
		insertion.Close()
	}
	ser.Close()
}

func DeleteCard(db *sql.DB, cardId int) {
	deleted, err := db.Query("DELETE FROM Card WHERE SSN=?", cardId)
	if err != nil {
		log.Fatal(err.Error())
	}
	defer deleted.Close()
}

func GetAllCards(db *sql.DB, userId int) []Card {
	result, err := db.Query("SELECT * FROM Card where SSN=?", userId)
	if err != nil {
		return nil
	}
	users := []Card{}
	for result.Next() {
		var u Card
		result.Scan(&u.Id, &u.BankName, &u.ClientId)
		users = append(users, u)
	}
	defer result.Close()
	return users
}
