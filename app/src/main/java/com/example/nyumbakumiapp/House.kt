package com.example.nyumbakumiapp

class House {
    var houseNumber:String = ""
    var houseSize:String = ""
    var housePrice:String = ""
    var userid:String = ""
    var houseid:String = ""

    constructor(
        houseNumber: String,
        houseSize: String,
        housePrice: String,
        userid: String,
        houseid: String
    ) {
        this.houseNumber = houseNumber
        this.houseSize = houseSize
        this.housePrice = housePrice
        this.userid = userid
        this.houseid = houseid
    }
}