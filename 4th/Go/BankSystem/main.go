package main

import (
	"database/sql"
	"html/template"
	"net/http"
	"strconv"

	"github.com/golangbot/mysqltutorial/models"
	"github.com/gorilla/mux"
)

var templates *template.Template
var db *sql.DB

func main() {
	db = models.InitMySQL("sayed", "123", "192.168.43.174", "3306", "Bank")
	templates = template.Must(template.ParseGlob("templates/*.html"))
	r := mux.NewRouter()
	r.HandleFunc("/", indexHandler).Methods("GET")
	r.HandleFunc("/user", AddUserHandler).Methods("POST")
	r.HandleFunc("/card", AddCardHandler).Methods("POST")
	r.HandleFunc("/delete", DeleteHandler).Methods("POST")
	r.HandleFunc("/users", ShowAllUsersHandler).Methods("GET")
	r.HandleFunc("/user-cards", ShowCardsHandler).Methods("GET")
	fs := http.FileServer(http.Dir("./static/"))
	r.PathPrefix("/static/").Handler(http.StripPrefix("/static/", fs))
	fss := http.FileServer(http.Dir("./imges/"))
	r.PathPrefix("/imges/").Handler(http.StripPrefix("/imges/", fss))
	http.Handle("/", r)
	http.ListenAndServe(":8000", nil)
	defer db.Close()
}

func indexHandler(w http.ResponseWriter, r *http.Request) {
	err := templates.ExecuteTemplate(w, "index.html", nil)
	if err != nil {
		println(err.Error())
	}
}

func AddUserHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	id, _ := strconv.Atoi(r.Form.Get("id"))
	name := r.Form.Get("name")
	age, _ := strconv.Atoi(r.Form.Get("age"))
	models.AddUser(db, id, name, age)
	http.Redirect(w, r, "/", 302)
}

func AddCardHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	id, _ := strconv.Atoi(r.Form.Get("id"))
	bankName := r.Form.Get("bank-name")
	clientId, _ := strconv.Atoi(r.Form.Get("Client-id"))
	models.AddCard(db, id, bankName, clientId)
	http.Redirect(w, r, "/", 302)
}

func DeleteHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	tmpType := r.Form.Get("ids")
	id, _ := strconv.Atoi(r.Form.Get("id"))
	if tmpType == "User ID" {
		models.DeleteUser(db, id)
	} else {
		models.DeleteCard(db, id)
	}
	http.Redirect(w, r, "/", 302)
}

func ShowAllUsersHandler(w http.ResponseWriter, r *http.Request) {
	result := models.GetAllUsers(db)
	templates.ExecuteTemplate(w, "showUsers.html", result)
}

func ShowCardsHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	userId, _ := strconv.Atoi(r.Form.Get("id"))
	result := models.GetAllCards(db, userId)
	templates.ExecuteTemplate(w, "showCards.html", result)
}
