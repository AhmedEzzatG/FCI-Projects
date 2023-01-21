package models

import (
	"database/sql"

	_ "github.com/go-sql-driver/mysql"
)

func InitMySQL(username string, password string, host string, port string, databasename string) *sql.DB {
	println(username + ":" + password + "@tcp(" + host + ":" + port + ")/" + databasename)
	db, err := sql.Open("mysql", username+":"+password+"@tcp("+host+":"+port+")/"+databasename)
	// if there is an error opening the connection, handle it
	if err != nil {
		panic(err.Error())
	}
	println("connected to my sql server is done")
	qr, err := db.Query("SELECT * FROM Customer")
	if err != nil {
		panic(err.Error())
	}
	defer qr.Close()
	return db
}
