package com.ac.ktandroidapps.oicar_instag_mobile_cmz.model

class User {
    var username : String? = null
    var email : String? = null
    var image : String? = null

    constructor()

    constructor(username: String?, email: String?, image: String?) {
        this.username = username
        this.email = email
        this.image = image
    }

    constructor(username: String?, email: String?) {
        this.username = username
        this.email = email
    }

    constructor(email: String?) {
        this.email = email
    }
}