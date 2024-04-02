package com.ac.ktandroidapps.oicar_instag_mobile_cmz.model

class User {
    var name : String? = null
    var email : String? = null
    var passwordHash : String? = null
    var image : String? = null

    constructor()

    constructor(name: String?, email: String?, passwordHash: String?, image: String?) {
        this.name = name
        this.email = email
        this.passwordHash = passwordHash
        this.image = image
    }

    constructor(name: String?, email: String?, passwordHash: String?) {
        this.name = name
        this.email = email
        this.passwordHash = passwordHash
    }

    constructor(email: String?, passwordHash: String?) {
        this.email = email
        this.passwordHash = passwordHash
    }
}